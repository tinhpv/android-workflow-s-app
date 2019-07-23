package com.example.workflow_s.ui.checklist.fragments;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;

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
import com.example.workflow_s.utils.SharedPreferenceUtils;

import java.util.ArrayList;
import java.util.List;

public class AllChecklistFragment extends Fragment implements ChecklistContract.AllChecklistView,
        ChecklistDialogFragment.DataBackContract,
        View.OnClickListener,
        CurrentChecklistAdapter.EventListener {


    private static final String NAME_ARG = "AllChecklist";

    View view;
    private Button templateButton;
    private CurrentChecklistAdapter mCurrentChecklistAdapter;
    private RecyclerView checklistRecyclerView;
    private RecyclerView.LayoutManager checklistLayoutManager;

    private ChecklistContract.ChecklistPresenter mPresenter;

    private ArrayList<String> templateListName;
    private ArrayList<Checklist> checklists, templateChecklists;
    private List<Template> templateList;
    private String selectedTemplate;

    private List<ChecklistMember> checklistMembers;


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
        selectedTemplate = "All";
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

    private void initData() {
        mPresenter = new ChecklistPresenterImpl(this, new ChecklistInteractor());
        orgId = SharedPreferenceUtils.retrieveData(getActivity(), getString(R.string.pref_orgId));
        userId = SharedPreferenceUtils.retrieveData(getActivity(), getString(R.string.pref_userId));
        mPresenter.loadAllChecklist(orgId);
        mPresenter.requestTemplateData(orgId);
    }

    private void setupChecklistRV() {
        checklistRecyclerView = view.findViewById(R.id.rv_checklist);
        checklistRecyclerView.setHasFixedSize(true);
        checklistLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
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
        }
    }

    @Override
    public void finishDeleteChecklist() {
        mPresenter.loadAllChecklist(orgId);
    }

    @Override
    public void onFinishSelectTemplate(String template) {
        templateButton.setText(template);
        Template tmpTemplate = null;
        for (Template template1 : templateList) {
            if (template1.getName().equals(template)) {
                tmpTemplate = template1;
            }
        }
        categorizeTemplate(tmpTemplate);
    }

    private void categorizeTemplate(Template template) {

        if (template == null) {
            selectedTemplate = "All";
            mCurrentChecklistAdapter.setChecklists(checklists);
        } else {
            String tempName = template.getName();
            selectedTemplate = tempName;
            int templateId = template.getId();
                templateChecklists = new ArrayList<>();
                for (Checklist checklist : checklists) {
                    if (checklist.getTemplateId() == templateId) {
                        templateChecklists.add(checklist);
                    } //end if
                } // end for
                mCurrentChecklistAdapter.setChecklists(templateChecklists);

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
}
