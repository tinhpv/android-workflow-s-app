package com.example.workflow_s.ui.template;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.workflow_s.R;
import com.example.workflow_s.model.Template;
import com.example.workflow_s.ui.template.adapter.TemplateAdapter;
import com.example.workflow_s.ui.template.dialog_fragment.TemplateDialogFragment;
import com.example.workflow_s.utils.SharedPreferenceUtils;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Workflow_S
 * Created by TinhPV on 2019-06-27
 * Copyright © 2019 TinhPV. All rights reserved
 **/


public class TemplateFragment extends Fragment implements TemplateContract.TemplateView, TemplateDialogFragment.DataBackContract {

    private final static String TAG = "TEMPLATE_FRAGMENT";

    View view;
    private TextView categoryTextView;
    private RecyclerView templateRecyclerView;
    private TemplateAdapter mAdapter;
    private RecyclerView.LayoutManager templateLayoutManager;

    private TemplateContract.TemplatePresenter mTemplatePresenter;

    private ShimmerFrameLayout mShimmerFrameLayout;
    private LinearLayout mTemplateDataStatusMessage;

    private ArrayList<String> categoryList;
    private ArrayList<Template> templateList, categorizedTemplateList;
    private String selectedCategory;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_template, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                Toast.makeText(getActivity(), "SEARCH", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_filter:
                prepareShowingCategoryDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void prepareShowingCategoryDialog() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("category_list", categoryList);
        bundle.putString("selected_category", selectedCategory);
        TemplateDialogFragment templateDialogFragment = TemplateDialogFragment.newInstance();
        templateDialogFragment.setTargetFragment(this, 0);
        templateDialogFragment.setArguments(bundle);
        templateDialogFragment.show(getActivity().getSupportFragmentManager(), TAG);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_template, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mShimmerFrameLayout = view.findViewById(R.id.template_shimmer_view);
        mTemplateDataStatusMessage = view.findViewById(R.id.template_data_notfound_message);
        categoryTextView = view.findViewById(R.id.tv_template_category);
        categoryTextView.setText("All");
        selectedCategory = "All";
        setupTemplateRV();
        initData();
    }


    @Override
    public void onResume() {
        super.onResume();
        mShimmerFrameLayout.startShimmerAnimation();
    }

    private void initData() {
        mTemplatePresenter = new TemplatePresenterImpl(this, new TemplateInteractor());


//        String userId = SharedPreferenceUtils.retrieveData(getActivity(), getString(R.string.pref_userId));
//        String orgId = SharedPreferenceUtils.retrieveData(getActivity(), getString(R.string.pref_orgId));
//        mTemplatePresenter.requestOrganizationData(orgId, userId);
        // FIXME - HARDCODE HERE
        mTemplatePresenter.requestTemplateData("1", "2372592022969346");
    }

    private void setupTemplateRV() {
        templateRecyclerView = view.findViewById(R.id.rv_template);
        templateRecyclerView.setHasFixedSize(true);
        templateLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        templateRecyclerView.setLayoutManager(templateLayoutManager);
        mAdapter = new TemplateAdapter();
        templateRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void finishGetTemplates(List<Template> templateList) {
        mShimmerFrameLayout.setVisibility(View.INVISIBLE);
        mShimmerFrameLayout.stopShimmerAnimation();
        if (templateList.size() == 0) {
            mTemplateDataStatusMessage.setVisibility(View.VISIBLE);
        } else {
            collectCategoryName(templateList);
            mAdapter.setTemplateList(templateList);
        } // end if
    }

    private void collectCategoryName(List<Template> templateList) {

        this.templateList = (ArrayList<Template>) templateList;
        categoryList = new ArrayList<>();
        categoryList.add("All");
        for (Template template : templateList) {
            if (!categoryList.contains(template.getCategory())) {
                categoryList.add(template.getCategory());
            }
        } // end for

    }



    @Override
    public void onFinishSelectCategory(String category) {
        categoryTextView.setText(category);
        categorizeTemplate(category);
    }

    private void categorizeTemplate(String category) {
        selectedCategory = category;
        if (category.equals("All")) {
            mAdapter.setTemplateList(templateList);
        } else {
            categorizedTemplateList = new ArrayList<>();
            for (Template template : this.templateList) {
                if (template.getCategory().equals(category)) {
                    categorizedTemplateList.add(template);
                } // end if
            } // end for

            mAdapter.setTemplateList(categorizedTemplateList);
        }

    }
}
