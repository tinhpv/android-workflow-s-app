package com.example.workflow_s.ui.organization;

import android.util.Log;

import com.example.workflow_s.model.Organization;
import com.example.workflow_s.model.User;
import com.example.workflow_s.model.UserOrganization;
import com.example.workflow_s.ui.home.HomeContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Workflow_S
 * Created by TinhPV on 2019-06-27
 * Copyright © 2019 TinhPV. All rights reserved
 **/


public class OrganizationPresenterImpl implements OrganizationContract.OrganizationPresenter,
        OrganizationContract.GetOrganizationDataContract.OnFinishedGetMembersListener,
        OrganizationContract.GetOrganizationDataContract.OnFinishedGetOrganizatonListener,
        OrganizationContract.GetOrganizationDataContract.OnFinishedGetListUserOrganizationListener{

    private static final String TAG = "ORGANIZATION_PRESENTER";
    private OrganizationContract.OrganizationView mOrganizationView;
    private OrganizationContract.GetOrganizationDataContract mOrganizationDataContract;

    public OrganizationPresenterImpl(OrganizationContract.OrganizationView organizationView, OrganizationContract.GetOrganizationDataContract organizationDataContract) {
        mOrganizationView = organizationView;
        mOrganizationDataContract = organizationDataContract;
    }

    @Override
    public void requestOrganizationData(int orgId) {
        mOrganizationDataContract.getAllMember(orgId, this);
    }

    @Override
    public void requestOrganization(String userId) {
        mOrganizationDataContract.getOrganization(userId, this);
    }

    @Override
    public void requestListOrganization(String userId) {
        mOrganizationDataContract.getListUserOrganization(userId, this);
    }

    @Override
    public void onFinishedGetMembers(ArrayList<User> users) {
        mOrganizationView.finishedGetMemeber(users);
    }

    @Override
    public void onFinishedGetOrg(Organization organization) {
        mOrganizationView.finishedGetOrganization(organization);
    }

    @Override
    public void onFinishedGetListUserOrg(List<UserOrganization> userOrganizationList) {
        mOrganizationView.finishedGetListUserOrganization(userOrganizationList);
    }

    @Override
    public void onFailure(Throwable t) {
        Log.d(TAG, "onFailure: " + t.getMessage());
    }
}
