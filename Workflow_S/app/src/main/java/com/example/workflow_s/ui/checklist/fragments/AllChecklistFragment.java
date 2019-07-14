package com.example.workflow_s.ui.checklist.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
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
import com.example.workflow_s.model.Template;
import com.example.workflow_s.ui.checklist.ChecklistContract;
import com.example.workflow_s.ui.checklist.ChecklistInteractor;
import com.example.workflow_s.ui.checklist.ChecklistPresenterImpl;
import com.example.workflow_s.ui.checklist.adapter.CurrentChecklistAdapter;
import com.example.workflow_s.ui.checklist.dialog_fragment.ChecklistDialogFragment;
import com.example.workflow_s.utils.SharedPreferenceUtils;

import java.util.ArrayList;
import java.util.List;

public class AllChecklistFragment extends Fragment implements ChecklistContract.AllChecklistView, ChecklistDialogFragment.DataBackContract, View.OnClickListener {

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

    private String orgId;

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
        searchEditText.setTextColor(getResources().getColor(R.color.white));

        searchEditText.setHint("Search other checklist");
        searchEditText.setHintTextColor(getResources().getColor(R.color.white));

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
        mPresenter.loadAllChecklist(orgId);
        mPresenter.requestTemplateData(orgId);
    }

    private void setupChecklistRV() {
        checklistRecyclerView = view.findViewById(R.id.rv_checklist);
        checklistRecyclerView.setHasFixedSize(true);
        checklistLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        checklistRecyclerView.setLayoutManager(checklistLayoutManager);

        mCurrentChecklistAdapter = new CurrentChecklistAdapter();
        checklistRecyclerView.setAdapter(mCurrentChecklistAdapter);
    }

    @Override
    public void setDataToChecklistRecyclerView(ArrayList<Checklist> datasource) {
//        listSearch = new ArrayList<>();
           checklists = new ArrayList<>();
        if (datasource != null) {
            checklists = datasource;
            mCurrentChecklistAdapter.setChecklists(datasource);
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
        }
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
}
