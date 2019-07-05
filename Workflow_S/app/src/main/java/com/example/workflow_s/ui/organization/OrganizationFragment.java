package com.example.workflow_s.ui.organization;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.workflow_s.R;
import com.example.workflow_s.model.Organization;
import com.example.workflow_s.model.User;
import com.example.workflow_s.model.UserOrganization;
import com.example.workflow_s.ui.organization.adapter.OrganizationMemberAdapter;
import com.example.workflow_s.ui.organization.dialog_fragment.OrganizationDialogFragment;
import com.example.workflow_s.utils.SharedPreferenceUtils;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Workflow_S
 * Created by TinhPV on 2019-06-26
 * Copyright © 2019 TinhPV. All rights reserved
 **/


public class OrganizationFragment extends Fragment implements OrganizationContract.OrganizationView, OrganizationDialogFragment.DataBackContract {

    private final static String TAG = "ORGANIZATION_FRAGMENT";

    View view;
    private RecyclerView organizationRecyclerView;
    private OrganizationMemberAdapter mAdapter;
    private RecyclerView.LayoutManager organizationLayoutManager;
    private TextView txtOrgName;
    private Button btnFilter;

    private OrganizationContract.OrganizationPresenter mPresenter;

    private ShimmerFrameLayout mOrgShimmerLayout;
    private LinearLayout mOrganizationStatusMessage;

    private ArrayList<String> orgNameList;
    private ArrayList<User> users;
    private List<UserOrganization> userOrganizationArrayList;
    private String selectedOrgName;
    private ArrayList<Organization> organizationArrayList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_organization, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        mOrgShimmerLayout = view.findViewById(R.id.org_shimmer_view_container);
        txtOrgName = view.findViewById(R.id.tv_org_name);
        btnFilter = view.findViewById(R.id.btn_org_filter);
        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prepareShowOrgDialog();
            }
        });
        //initData();
        setupData();
        setupOrganizationRV();

    }

    private void prepareShowOrgDialog() {
        //FIXME HARDCODE FOR TESTING
//        orgNameList.add("Vietnam");
//        orgNameList.add("Education");
//        orgNameList.add("PRM391");
        selectedOrgName = "Vietnam";
        Bundle bundle = new Bundle();
        bundle.putSerializable("org_list", orgNameList);
        bundle.putString("selected_org_name", selectedOrgName);
        OrganizationDialogFragment organizationDialogFragment = OrganizationDialogFragment.newInstance();
        organizationDialogFragment.setTargetFragment(this, 0);
        organizationDialogFragment.setArguments(bundle);
        organizationDialogFragment.show(getActivity().getSupportFragmentManager(), TAG);
    }

    @Override
    public void onResume() {
        super.onResume();
        mOrgShimmerLayout.startShimmerAnimation();
    }

    private void initData() {
        mOrgShimmerLayout.startShimmerAnimation();
        mPresenter = new OrganizationPresenterImpl(this, new OrganizationInteractor());
        String userId = SharedPreferenceUtils.retrieveData(getActivity(), getString(R.string.pref_userId));
        mPresenter.requestOrganization(userId);
    }

    private void setupOrganizationRV() {
        organizationRecyclerView = view.findViewById(R.id.rv_organization);
        organizationRecyclerView.setHasFixedSize(true);
        organizationLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        organizationRecyclerView.setLayoutManager(organizationLayoutManager);
        
        mAdapter = new OrganizationMemberAdapter();
        organizationRecyclerView.setAdapter(mAdapter);
    }

    private void setupData() {
        //FIXME HARDCODE
        mOrgShimmerLayout.startShimmerAnimation();
        mPresenter = new OrganizationPresenterImpl(this, new OrganizationInteractor());
        String userId = SharedPreferenceUtils.retrieveData(getActivity(), getString(R.string.pref_userId));
        Log.i("userId", userId);

        mPresenter.requestListOrganization(userId);
        mPresenter.requestOrganizationData(1);

        //saveData
        Organization organization = new Organization(1, "Vietnam");
        SharedPreferenceUtils.saveCurrentUserData(getActivity(), null, organization);
    }

    @Override
    public void finishedGetMemeber(List<User> userList) {
        mOrgShimmerLayout.stopShimmerAnimation();
        mOrgShimmerLayout.setVisibility(View.INVISIBLE);
        mAdapter.setUserList(userList);
    }

    @Override
    public void finishedGetOrganization(Organization organization) {
//        txtOrgName.setText(organization.getName());
//        mOrgShimmerLayout.startShimmerAnimation();
//        mPresenter = new OrganizationPresenterImpl(this, new OrganizationInteractor());
//        mPresenter.requestOrganizationData(organization.getId());

        //Organization organization = new Organization(orgId, orgName);
        //SharedPreferenceUtils.saveCurrentUserData(getActivity(), null, organization);
    }

    @Override
    public void finishedGetListUserOrganization(List<UserOrganization> userOrganizationList) {
        userOrganizationArrayList = new ArrayList<>();
        userOrganizationArrayList = userOrganizationList;
        organizationArrayList = new ArrayList<>();
        orgNameList = new ArrayList<>();
        if (!userOrganizationArrayList.isEmpty()) {
            for (int i = 0; i < userOrganizationArrayList.size(); i++) {
                organizationArrayList.add(userOrganizationArrayList.get(i).getOrganization());
            }
        }
        if (!organizationArrayList.isEmpty()) {
            for (int i = 0; i < organizationArrayList.size(); i++) {
                orgNameList.add(organizationArrayList.get(i).getName());
            }

        }
    }




    @Override
    public void onFinishSelectOrgName(String orgName) {
        txtOrgName.setText(orgName);
            for (int i = 0; i < organizationArrayList.size(); i++) {
                if (organizationArrayList.get(i).getName().equals(orgName)) {
                    mPresenter.requestOrganizationData(organizationArrayList.get(i).getId());
                }
            }


    }
}
