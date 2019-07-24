package com.example.workflow_s.ui.checklist.fragments;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;

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
import com.example.workflow_s.model.Template;
import com.example.workflow_s.ui.checklist.ChecklistContract;
import com.example.workflow_s.ui.checklist.ChecklistInteractor;
import com.example.workflow_s.ui.checklist.ChecklistPresenterImpl;
import com.example.workflow_s.ui.checklist.adapter.CurrentChecklistAdapter;
import com.example.workflow_s.ui.checklist.adapter.SwipeToDeleteCallBack;
import com.example.workflow_s.ui.checklist.dialog_fragment.ChecklistDialogFragment;
import com.example.workflow_s.ui.checklist.dialog_fragment.StatusChecklistDialogFragment;
import com.example.workflow_s.utils.SharedPreferenceUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class AllChecklistFragment extends Fragment implements ChecklistContract.AllChecklistView,
        ChecklistDialogFragment.DataBackContract,
        View.OnClickListener,
        CurrentChecklistAdapter.EventListener, StatusChecklistDialogFragment.DataBackContract {


    private static final String NAME_ARG = "AllChecklist";

    View view;
    private Button templateButton, statusButton;
    private CurrentChecklistAdapter mCurrentChecklistAdapter;
    private RecyclerView checklistRecyclerView;
    private RecyclerView.LayoutManager checklistLayoutManager;

    private ChecklistContract.ChecklistPresenter mPresenter;

    private ArrayList<String> templateListName;
    private ArrayList<Checklist> checklists, templateChecklists, myStatusChecklist;
    private List<Template> templateList;
    private String selectedTemplate, selectedStatus;

    private List<ChecklistMember> checklistMembers;
    private boolean flagTemplate = false, flagStatus = false;

    private String orgId, userId;

    public AllChecklistFragment() {}

    //static constructor
    public static AllChecklistFragment newInstance() {
        final AllChecklistFragment allChecklistFragment = new AllChecklistFragment();
        return allChecklistFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_current_checklists, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        templateButton = view.findViewById(R.id.bt_template_checklist);
        templateButton.setOnClickListener(this);
        templateButton.setText("All");

        statusButton = view.findViewById(R.id.bt_status_checklist);
        statusButton.setOnClickListener(this);
        statusButton.setText("All");

        selectedTemplate = "All";
        selectedStatus = "All";
        orgId = SharedPreferenceUtils.retrieveData(getActivity(), getString(R.string.pref_orgId));
        setupChecklistRV();
        initData();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getActivity().setTitle("Home");
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

        searchEditText.setHint("Search");
        searchEditText.setHintTextColor(getResources().getColor(R.color.colorPrimaryDark));

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

    private void prepareShowingTemplateDialog() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("template_list", templateListName);
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
//        listSearch = new ArrayList<>();
          // checklists = new ArrayList<>();
//        if (datasource != null) {
//            checklists = datasource;
//            mCurrentChecklistAdapter.setChecklists(datasource);
//        }

        if (null != datasource) {
            checklists = new ArrayList<>();
            for (Checklist checklist : datasource) {
                filterDueTimeofChecklist(checklist);
                if (!checklist.getUserId().equals(userId)) {
                //    checklists.add(checklist);
                    boolean flag = true;
                    checklistMembers = new ArrayList<>();
                    checklistMembers = checklist.getChecklistMembers();
                    if (checklistMembers != null) {
                        for (ChecklistMember member : checklistMembers) {
                            if (member.getUserId().equals(userId)) {
                                flag = false;
                            }
                        }
                        if (flag) {
                            checklists.add(checklist);
                        }
                    }
                }  // end if
            } // end for
            mCurrentChecklistAdapter.setChecklists(checklists);
        } // end if null

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
                if (Integer.parseInt(time.split("h")[0]) == 0) {
                    time = String.format("%dm",
                            TimeUnit.MILLISECONDS.toMinutes(totalTime));
                    if (Integer.parseInt(time.split("m")[0]) <= 0) {
                        checklist.setExpired(true);
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


    @Override
    public void finishGetTemplates(List<Template> templateList) {
        if (templateList != null) {
            this.templateList = templateList;
            collectTemplateName(templateList);
        }
    }

    private void collectTemplateName(List<Template> templateList) {
        //this.templateList = (ArrayList<Template>) templateList;
        templateListName = new ArrayList<>();
        templateListName.add("All");
        for (Template template : templateList) {
            if (!templateListName.contains(template.getName())) {
                templateListName.add(template.getName());
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
    public void finishDeleteChecklist() {
        mPresenter.loadAllChecklist(orgId);
    }

    @Override
    public void onFinishSelectTemplate(String template) {
        templateButton.setText(template);
//        Template tmpTemplate = null;
//        for (Template template1 : templateList) {
//            if (template1.getName().equals(template)) {
//                tmpTemplate = template1;
//            }
//        }
//        categorizeTemplate(tmpTemplate);
        selectedTemplate = template;
        categorizeChecklist(template, selectedStatus);
    }

    private void categorizeChecklist(String template, String status) {
        myStatusChecklist.clear();
        if (template.equals("All")) {
            if (status.equals("All")) {
                mCurrentChecklistAdapter.setChecklists(checklists);
            } else if (status.equals("Done")){
                for (Checklist checklist : checklists) {
                    if (checklist.getTemplateStatus().equals("Done")) {
                        myStatusChecklist.add(checklist);
                    } //end if
                } //end for
                mCurrentChecklistAdapter.setChecklists(myStatusChecklist);
            } else if (status.equals("Running")) {
                for (Checklist checklist : checklists) {
                    if (checklist.getTemplateStatus().equals("Checklist")) {
                        if (!checklist.getExpired()) {
                            myStatusChecklist.add(checklist);
                        } //end if
                    } //end if
                } //end for
                mCurrentChecklistAdapter.setChecklists(myStatusChecklist);
            } else if (status.equals("Expired")) {
                for (Checklist checklist : checklists) {
                    if (checklist.getTemplateStatus().equals("Checklist")) {
                        if (checklist.getExpired()) {
                            myStatusChecklist.add(checklist);
                        } //end if
                    } //end if
                } //end for
                mCurrentChecklistAdapter.setChecklists(myStatusChecklist);
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
                for (Checklist checklist : checklists) {
                    if (checklist.getTemplateId() == templateId) {
                        myStatusChecklist.add(checklist);
                    } //end if
                } //end for
                mCurrentChecklistAdapter.setChecklists(myStatusChecklist);
            } else if (status.equals("Done")){
                for (Checklist checklist : checklists) {
                    if (checklist.getTemplateId() == templateId && checklist.getTemplateStatus().equals("Done")) {
                        myStatusChecklist.add(checklist);
                    } //end if
                } //end for
                mCurrentChecklistAdapter.setChecklists(myStatusChecklist);
            } else if (status.equals("Running")) {
                for (Checklist checklist : checklists) {
                    if (checklist.getTemplateId() == templateId && checklist.getTemplateStatus().equals("Checklist")) {
                        if (!checklist.getExpired()) {
                            myStatusChecklist.add(checklist);
                        } //end if
                    } //end if
                } //end for
                mCurrentChecklistAdapter.setChecklists(myStatusChecklist);
            } else if (status.equals("Expired")) {
                for (Checklist checklist : checklists) {
                    if (checklist.getTemplateId() == templateId && checklist.getTemplateStatus().equals("Checklist")) {
                        if (checklist.getExpired()) {
                            myStatusChecklist.add(checklist);
                        } //end if
                    } //end if
                } //end for
                mCurrentChecklistAdapter.setChecklists(myStatusChecklist);
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
                mCurrentChecklistAdapter.setChecklists(checklists);
            }
        } else {
            flagTemplate = true;
            String tempName = template.getName();
            selectedTemplate = tempName;
            int templateId = template.getId();
                templateChecklists = new ArrayList<>();
                if (flagStatus) {
                    for (Checklist checklist : myStatusChecklist) {
                        if (checklist.getTemplateId() == templateId) {
                            templateChecklists.add(checklist);
                        } //end if
                    } // end for
                    mCurrentChecklistAdapter.setChecklists(templateChecklists);
                } else {
                    for (Checklist checklist : checklists) {
                        if (checklist.getTemplateId() == templateId) {
                            templateChecklists.add(checklist);
                        } //end if
                    } // end for
                    mCurrentChecklistAdapter.setChecklists(templateChecklists);
                }


        }

    }

    private void categorizeStatus(String status) {
        myStatusChecklist.clear();
        ArrayList<Checklist> tempChecklist;
        if (flagTemplate) {
            tempChecklist = templateChecklists;
        } else {
            tempChecklist = checklists;
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

    @Override
    public void onChange(int checklistId, String name) {
        mPresenter.setNameOfChecklist(checklistId, name);
        //mCurrentChecklistAdapter.notifyDataSetChanged();
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
                String userId = SharedPreferenceUtils.retrieveData(getActivity(), getString(R.string.pref_userId));
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
        statusButton.setText(status);
        //categorizeStatus(status);
        selectedStatus = status;
        categorizeChecklist(selectedTemplate, status);
    }
}
