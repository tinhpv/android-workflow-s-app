package com.example.workflow_s.ui.organization;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.workflow_s.R;
import com.example.workflow_s.model.User;
import com.example.workflow_s.ui.organization.adapter.OrganizationMemberAdapter;
import com.example.workflow_s.utils.SharedPreferenceUtils;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.List;

/**
 * Workflow_S
 * Created by TinhPV on 2019-06-26
 * Copyright © 2019 TinhPV. All rights reserved
 **/


public class OrganizationFragment extends Fragment implements OrganizationContract.OrganizationView {

    View view;
    private RecyclerView organizationRecyclerView;
    private OrganizationMemberAdapter mAdapter;
    private RecyclerView.LayoutManager organizationLayoutManager;

    private OrganizationContract.OrganizationPresenter mPresenter;

    private ShimmerFrameLayout mOrgShimmerLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_organization, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mOrgShimmerLayout = view.findViewById(R.id.org_shimmer_view_container);
        setupOrganizationRV();
        initData();
    }

    private void initData() {
        mOrgShimmerLayout.startShimmerAnimation();
        mPresenter = new OrganizationPresenterImpl(this, new OrganizationInteractor());
        String orgId = SharedPreferenceUtils.retrieveData(getActivity(), getString(R.string.pref_orgId));
        mPresenter.requestOrganizationData(orgId);
    }

    private void setupOrganizationRV() {
        organizationRecyclerView = view.findViewById(R.id.rv_organization);
        organizationRecyclerView.setHasFixedSize(true);
        organizationLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        organizationRecyclerView.setLayoutManager(organizationLayoutManager);
        
        mAdapter = new OrganizationMemberAdapter();
        organizationRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void finishedGetMemeber(List<User> userList) {
        mOrgShimmerLayout.stopShimmerAnimation();
        mOrgShimmerLayout.setVisibility(View.INVISIBLE);
        mAdapter.setUserList(userList);
    }
}
