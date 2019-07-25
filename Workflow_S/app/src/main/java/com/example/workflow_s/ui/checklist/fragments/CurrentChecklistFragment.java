package com.example.workflow_s.ui.checklist.fragments;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workflow_s.R;
import com.example.workflow_s.model.Checklist;
import com.example.workflow_s.model.ChecklistMember;
import com.example.workflow_s.model.Task;
import com.example.workflow_s.model.Template;
import com.example.workflow_s.ui.checklist.ChecklistContract;
import com.example.workflow_s.ui.checklist.ChecklistInteractor;
import com.example.workflow_s.ui.checklist.ChecklistPresenterImpl;
import com.example.workflow_s.ui.checklist.adapter.CurrentChecklistAdapter;
import com.example.workflow_s.ui.checklist.adapter.SwipeToDeleteCallBack;
import com.example.workflow_s.ui.checklist.dialog_fragment.ChecklistDialogFragment;
import com.example.workflow_s.ui.checklist.dialog_fragment.StatusChecklistDialogFragment;
import com.example.workflow_s.utils.SharedPreferenceUtils;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CurrentChecklistFragment extends Fragment implements ChecklistContract.ChecklistView,
        ChecklistDialogFragment.DataBackContract,
        View.OnClickListener, CurrentChecklistAdapter.EventListener , StatusChecklistDialogFragment.DataBackContract{

    private static final String NAME_ARG = "CurrentChecklist";

    View view;
    private CurrentChecklistAdapter mCurrentChecklistAdapter;
    private RecyclerView checklistRecyclerView;
    private RecyclerView.LayoutManager checklistLayoutManager;
    private Button btnTemplateFilter, btnStatusFiler;

    private ArrayList<String> myTemplateListName;
    private ArrayList<Checklist> currentChecklist, myTemplateChecklist, myStatusChecklist;
    private List<ChecklistMember> checklistMembers;
    private List<Template> templateList;
    private String userId, orgId, selectedTemplate, selectedStatus;

    private boolean flagTemplate = false, flagStatus = false;
    private String template = "All", status = "All";

    private ChecklistContract.ChecklistPresenter mPresenter;
    private ShimmerFrameLayout templateLoadingFrame;
    private LinearLayout checklistDataNotFound;
    //private LinearLayout mChecklistDataStatusMessage;

    public CurrentChecklistFragment() {}

    //static constructor
    public static CurrentChecklistFragment newInstance() {
        final CurrentChecklistFragment currentChecklistFragment = new CurrentChecklistFragment();
        // The 1 below is an optimization, being the number of arguments that will
        // be added to this bundle.  If you know the number of arguments you will add
        // to the bundle it stops additional allocations of the backing map.  If
        // unsure, you can construct Bundle without any arguments
        //final Bundle args = new Bundle(1);
        // This stores the argument as an argument in the bundle.  Note that even if
        // the 'name' parameter is NULL then this will work, so you should consider
        // at this point if the parameter is mandatory and if so check for NULL and
        // throw an appropriate error if so
        return currentChecklistFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_current_checklists, container, false);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getActivity().setTitle("Home");
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        templateLoadingFrame.setVisibility(View.VISIBLE);
        templateLoadingFrame.startShimmerAnimation();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu_search, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search_item);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        EditText searchEditText = searchView.findViewById(R.id.search_src_text);
        searchEditText.setTextColor(getResources().getColor(R.color.colorPrimaryText));

        searchEditText.setHint("Search checklist...");
        searchEditText.setHintTextColor(getResources().getColor(R.color.colorPrimaryText));

        Typeface myFont = ResourcesCompat.getFont(getContext(), R.font.avenir_light);
        searchEditText.setTypeface(myFont);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                mCurrentChecklistAdapter.getFilter().filter(s);
                return false;
            }
        });
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //mChecklistDataStatusMessage = view.findViewById(R.id.checklist_data_notfound_message);
        btnTemplateFilter = view.findViewById(R.id.bt_template_checklist);
        btnTemplateFilter.setOnClickListener(this);
        btnTemplateFilter.setText("All");

        templateLoadingFrame = view.findViewById(R.id.checklist_shimmer_view);
        checklistDataNotFound = view.findViewById(R.id.checklist_data_notfound_message);

        btnStatusFiler = view.findViewById(R.id.bt_status_checklist);
        btnStatusFiler.setOnClickListener(this);
        btnStatusFiler.setText("All");

        selectedTemplate = "All";
        selectedStatus = "All";
        orgId = SharedPreferenceUtils.retrieveData(getActivity(), getString(R.string.pref_orgId));
        setupChecklistRV();
        initData();
    }

    private void prepareShowingTemplateDialog() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("template_list", myTemplateListName);
        bundle.putString("selected_template", selectedTemplate);
        ChecklistDialogFragment checklistDialogFragment = ChecklistDialogFragment.newInstance();
        checklistDialogFragment.setTargetFragment(this, 0);
        checklistDialogFragment.setArguments(bundle);
        checklistDialogFragment.show(getFragmentManager(), NAME_ARG);
    }

    private void prepareShowingStatusDialog() {
        Bundle bundle = new Bundle();
        bundle.putString("selected_status", selectedStatus);
        StatusChecklistDialogFragment statusChecklistDialogFragment = StatusChecklistDialogFragment.newInstance();
        statusChecklistDialogFragment.setTargetFragment(this, 0);
        statusChecklistDialogFragment.setArguments(bundle);
        statusChecklistDialogFragment.show(getFragmentManager(), NAME_ARG);

    }

    private void initData() {
        mPresenter = new ChecklistPresenterImpl(this, new ChecklistInteractor());
        orgId = SharedPreferenceUtils.retrieveData(getActivity(), getString(R.string.pref_orgId));
        userId = SharedPreferenceUtils.retrieveData(getActivity(), getString(R.string.pref_userId));
        mPresenter.loadAllChecklist(orgId);
        mPresenter.requestTemplateData(orgId);
        myStatusChecklist = new ArrayList<>();
        myTemplateChecklist = new ArrayList<>();
    }

    private void setupChecklistRV() {
        checklistRecyclerView = view.findViewById(R.id.rv_checklist);
        checklistRecyclerView.setHasFixedSize(true);
        checklistLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        checklistRecyclerView.setLayoutManager(checklistLayoutManager);

        mCurrentChecklistAdapter = new CurrentChecklistAdapter(this, getContext());
        checklistRecyclerView.setAdapter(mCurrentChecklistAdapter);

        SwipeToDeleteCallBack swipeToDeleteCallBack = new SwipeToDeleteCallBack(getContext()) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                final int position = viewHolder.getAdapterPosition();
                mCurrentChecklistAdapter.deleteItem(position);
            }
        };

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallBack);
        itemTouchhelper.attachToRecyclerView(checklistRecyclerView);

    }


    @Override
    public void setDataToChecklistRecyclerView(ArrayList<Checklist> datasource) {
        templateLoadingFrame.stopShimmerAnimation();
        templateLoadingFrame.setVisibility(View.GONE);

        if (null != datasource) {

            if (datasource.size() == 0) {
                checklistDataNotFound.setVisibility(View.VISIBLE);
            } else {
                currentChecklist = new ArrayList<>();

                for (Checklist checklist : datasource) {
                    filterDueTimeofChecklist(checklist);
                    if (checklist.getUserId().equals(userId)) {
                        currentChecklist.add(checklist);
                    } else {
                        checklistMembers = new ArrayList<>();
                        checklistMembers = checklist.getChecklistMembers();
                        if (checklistMembers != null) {
                            for (ChecklistMember member : checklistMembers) {
                                if (member.getUserId().equals(userId)) {
                                    currentChecklist.add(checklist);
                                }
                            }
                        }
                    } // end if
                } // end for
                mCurrentChecklistAdapter.setChecklists(currentChecklist);
            }

        } // end if null
    }

    @Override
    public void finishFirstTaskFromChecklist(Task task, Checklist parentChecklistOfThisTask) {
//        if (task != null) {
//            List<TaskMember> taskMemberList = new ArrayList<>(task.getTaskMemberList());
//            if (!taskMemberList.isEmpty()) {
//                for (int i = 0; i < taskMemberList.size(); i++) {
//                    if (taskMemberList.get(i).getUserId().equals(userId)) {
//                        currentChecklist.add(parentChecklistOfThisTask);
//                    }
//                }
//            }
//            mCurrentChecklistAdapter.setChecklists(currentChecklist);
//        } // end if
    }

    @Override
    public void finishGetTemplates(List<Template> templateList) {
        if (templateList != null) {
            this.templateList = templateList;
            collectTemplateName(templateList);
        }
    }

    private void collectTemplateName(List<Template> templateList) {
        myTemplateListName = new ArrayList<>();
        myTemplateListName.add("All");
        for (Template template : templateList) {
            if (!myTemplateListName.contains(template.getName())) {
                myTemplateListName.add(template.getName());
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bt_template_checklist) {
            prepareShowingTemplateDialog();
        } else if (v.getId() == R.id.bt_status_checklist) {
            prepareShowingStatusDialog();
        }
    }

    @Override
    public void onFinishSelectTemplate(String template) {
        btnTemplateFilter.setText(template);

//        Template tmpTemplate = null;
//        for (Template template1 : templateList) {
//            if (template1.getName().equals(template)) {
//                tmpTemplate = template1;
//            }
//        }

        //categorizeTemplate(tmpTemplate);
        selectedTemplate = template;
        categorizeChecklist(template, selectedStatus);
    }

    @Override
    public void finishDeleteChecklist() {
        mPresenter.loadAllChecklist(orgId);
    }

    private void categorizeChecklist(String template, String status) {
        myTemplateChecklist.clear();
        if (template.equals("All")) {
            if (status.equals("All")) {
                mCurrentChecklistAdapter.setChecklists(currentChecklist);
            } else if (status.equals("Done")){
                for (Checklist checklist : currentChecklist) {
                    if (checklist.getTemplateStatus().equals("Done")) {
                        myTemplateChecklist.add(checklist);
                    } //end if
                } //end for
                mCurrentChecklistAdapter.setChecklists(myTemplateChecklist);
            } else if (status.equals("Running")) {
                for (Checklist checklist : currentChecklist) {
                    if (checklist.getTemplateStatus().equals("Checklist")) {
                        if (!checklist.getExpired()) {
                            myTemplateChecklist.add(checklist);
                        } //end if
                    } //end if
                } //end for
                mCurrentChecklistAdapter.setChecklists(myTemplateChecklist);
            } else if (status.equals("Expired")) {
                for (Checklist checklist : currentChecklist) {
                    if (checklist.getTemplateStatus().equals("Checklist")) {
                        if (checklist.getExpired()) {
                            myTemplateChecklist.add(checklist);
                        } //end if
                    } //end if
                } //end for
                mCurrentChecklistAdapter.setChecklists(myTemplateChecklist);
            }

        } else {
            Template tmpTemplate = null;
            for (Template template1 : templateList) {
                if (template1.getName().equals(template)) {
                    tmpTemplate = template1;
                }
            }
            int templateId = tmpTemplate.getId();
            if (status.equals("All")) {
                for (Checklist checklist : currentChecklist) {
                    if (checklist.getTemplateId() == templateId) {
                        myTemplateChecklist.add(checklist);
                    } //end if
                } //end for
                mCurrentChecklistAdapter.setChecklists(myTemplateChecklist);
            } else if (status.equals("Done")){
                for (Checklist checklist : currentChecklist) {
                    if (checklist.getTemplateId() == templateId && checklist.getTemplateStatus().equals("Done")) {
                        myTemplateChecklist.add(checklist);
                    } //end if
                } //end for
                mCurrentChecklistAdapter.setChecklists(myTemplateChecklist);
            } else if (status.equals("Running")) {
                for (Checklist checklist : currentChecklist) {
                    if (checklist.getTemplateId() == templateId && checklist.getTemplateStatus().equals("Checklist")) {
                        if (!checklist.getExpired()) {
                            myTemplateChecklist.add(checklist);
                        } //end if
                    } //end if
                } //end for
                mCurrentChecklistAdapter.setChecklists(myTemplateChecklist);
            } else if (status.equals("Expired")) {
                for (Checklist checklist : currentChecklist) {
                    if (checklist.getTemplateId() == templateId && checklist.getTemplateStatus().equals("Checklist")) {
                        if (checklist.getExpired()) {
                            myTemplateChecklist.add(checklist);
                        } //end if
                    } //end if
                } //end for
                mCurrentChecklistAdapter.setChecklists(myTemplateChecklist);
            }
        }

    }


    private void categorizeTemplate(Template template) {
        if (template == null) {
            flagTemplate = false;
            selectedTemplate = "All";
            if (flagStatus) {
                mCurrentChecklistAdapter.setChecklists(myStatusChecklist);
            } else {
                mCurrentChecklistAdapter.setChecklists(currentChecklist);
            }
        } else {
            String tempName = template.getName();
            flagTemplate = true;
            if (!template.getName().equals(selectedTemplate)){
                if (flagStatus) {
                    selectedTemplate = tempName;
                    int templateId = template.getId();
                    for (Checklist checklist : currentChecklist) {
                        if (checklist.getTemplateId() == templateId) {
                            myTemplateChecklist.add(checklist);
                        } //end if
                    } //end for
                    categorizeStatus(selectedStatus);
                    mCurrentChecklistAdapter.setChecklists(myTemplateChecklist);
                } else {
                    selectedTemplate = tempName;
                    int templateId = template.getId();
                    for (Checklist checklist : currentChecklist) {
                        if (checklist.getTemplateId() == templateId) {
                            myTemplateChecklist.add(checklist);
                        } //end if
                    } //end for
                    mCurrentChecklistAdapter.setChecklists(myTemplateChecklist);
                }

            }
           // String tempName = template.getName();
//            selectedTemplate = tempName;
//            int templateId = template.getId();
//            myTemplateChecklist = new ArrayList<>();
//            if (flagStatus) {
//                for (Checklist checklist : myStatusChecklist) {
//                    if (checklist.getTemplateId() == templateId) {
//                        myTemplateChecklist.add(checklist);
//                    } //end if
//                } //end for
//                mCurrentChecklistAdapter.setChecklists(myTemplateChecklist);
//            } else {
//                for (Checklist checklist : currentChecklist) {
//                    if (checklist.getTemplateId() == templateId) {
//                        myTemplateChecklist.add(checklist);
//                    } //end if
//                } //end for
//                mCurrentChecklistAdapter.setChecklists(myTemplateChecklist);
//            }
        }
    }

    private void categorizeStatus(String status) {
        myStatusChecklist.clear();
        ArrayList<Checklist> tempChecklist;
        if (flagTemplate) {
            tempChecklist = myTemplateChecklist;
        } else {
            tempChecklist = currentChecklist;
        }
        if (status == "All") {
            flagStatus = false;
            selectedStatus = "All";
            mCurrentChecklistAdapter.setChecklists(tempChecklist);

        } else if (status.equals("Done")){
            flagStatus = true;
            selectedStatus= status;
            for (Checklist checklist : tempChecklist) {
                if (checklist.getTemplateStatus().equals("Done")) {
                    myStatusChecklist.add(checklist);
                } //end if
            } //end for
            mCurrentChecklistAdapter.setChecklists(myStatusChecklist);
        } else if (status.equals("Running")){
            flagStatus = true;
            //myStatusChecklist.clear();
            selectedStatus = status;
            for (Checklist checklist : tempChecklist) {
                if (checklist.getTemplateStatus().equals("Checklist")) {
                   // boolean expired = checklist.getExpired();
                    if (!checklist.getExpired()) {
                        myStatusChecklist.add(checklist);
                    } //end if
                } // end if
            } // end for
            mCurrentChecklistAdapter.setChecklists(myStatusChecklist);
        } else if (status.equals("Expired")) {
            flagStatus = true;
            //myStatusChecklist.clear();
            selectedStatus = status;
            for (Checklist checklist : tempChecklist) {
                if (checklist.getTemplateStatus().equals("Checklist")) {
                    // boolean expired = checklist.getExpired();
                    if (checklist.getExpired()) {
                        myStatusChecklist.add(checklist);
                    } //end if
                } // end if
            } // end for
            mCurrentChecklistAdapter.setChecklists(myStatusChecklist);
        }
    }

    @Override
    public void onEvent(int deletedChecklistId) {
        handleShowConfirmDialog(deletedChecklistId);
    }

    private void handleShowConfirmDialog(final int deletedChecklistId) {
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_confirm_delete_checklist);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        Button confirmButton = dialog.findViewById(R.id.btn_confirm);
        Button cancelButton = dialog.findViewById(R.id.btn_cancel);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v2) {
                mPresenter.deleteChecklist(deletedChecklistId, userId);
                dialog.dismiss();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentChecklistAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
    }

    @Override
    public void onFinishSelectStatus(String status) {
        btnStatusFiler.setText(status);
        //categorizeStatus(status);
        selectedStatus = status;
        categorizeChecklist(selectedTemplate, status);
    }

    private void filterDueTimeofChecklist(Checklist checklist) {
        String time;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        if (checklist.getDueTime() != null) {
            String dateSelected = checklist.getDueTime().split("T")[0];
            String timeSelected = checklist.getDueTime().split("T")[1];
            Date currentTime = Calendar.getInstance().getTime();
            String dueTime = dateSelected + " " + timeSelected;
            try {
                Date overdue = sdf.parse(dueTime);
                long totalTime = overdue.getTime() - currentTime.getTime();
                time = String.format("%dh",
                        TimeUnit.MILLISECONDS.toHours(totalTime));
                Log.i("checlistDueTime", time);
                if (Integer.parseInt(time.split("h")[0]) == 0) {
                    time = String.format("%dm",
                            TimeUnit.MILLISECONDS.toMinutes(totalTime));
                    if (Integer.parseInt(time.split("m")[0]) <= 0) {
                        checklist.setExpired(true);
                    } else {
                        checklist.setExpired(false);
                    }
                }  else if (Integer.parseInt(time.split("h")[0]) <= 0){
                    checklist.setExpired(true);
                } else {
                    checklist.setExpired(false);
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

//


}
