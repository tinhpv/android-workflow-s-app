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
import android.widget.Toast;

import com.example.workflow_s.R;
import com.example.workflow_s.model.Template;
import com.example.workflow_s.ui.organization.adapter.OrganizationMemberAdapter;
import com.example.workflow_s.ui.template.adapter.TemplateAdapter;
import com.example.workflow_s.utils.SharedPreferenceUtils;

import java.util.List;

/**
 * Workflow_S
 * Created by TinhPV on 2019-06-27
 * Copyright © 2019 TinhPV. All rights reserved
 **/


public class TemplateFragment extends Fragment implements TemplateContract.TemplateView {

    View view;
    private RecyclerView templateRecyclerView;
    private TemplateAdapter mAdapter;
    private RecyclerView.LayoutManager templateLayoutManager;

    private TemplateContract.TemplatePresenter mTemplatePresenter;

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
                Toast.makeText(getActivity(), "FILTER", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_template, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setupTemplateRV();
        initData();
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
    public void finishGetTemplates(List<Template> userList) {
        mAdapter.setTemplateList(userList);
    }
}
