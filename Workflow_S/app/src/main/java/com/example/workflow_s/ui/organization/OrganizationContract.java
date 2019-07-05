package com.example.workflow_s.ui.organization;

import com.example.workflow_s.model.Organization;
import com.example.workflow_s.model.Task;
import com.example.workflow_s.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Workflow_S
 * Created by TinhPV on 2019-06-27
 * Copyright © 2019 TinhPV. All rights reserved
 **/

public interface OrganizationContract {

    //presenter
    interface OrganizationPresenter {
        void requestOrganizationData(int orgId);
        void requestOrganization(String userId);
    }

    //view
    interface OrganizationView {
        void finishedGetMemeber(List<User> userList);
        void finishedGetOrganization(Organization organization);
    }

    //model
    interface GetOrganizationDataContract {
        interface OnFinishedGetMembersListener {
            void onFinishedGetMembers(ArrayList<User> users);
            void onFailure(Throwable t);
        }

        interface OnFinishedGetOrganizatonListener {
            void onFinishedGetOrg(Organization organization);
            void onFailure(Throwable t);
        }

        void getAllMember(int orgId, OnFinishedGetMembersListener onFinishedListener);
        void getOrganization(String userId, OnFinishedGetOrganizatonListener onFinishedListener);
    }
}
