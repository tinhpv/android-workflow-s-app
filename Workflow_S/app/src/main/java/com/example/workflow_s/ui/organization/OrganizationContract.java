package com.example.workflow_s.ui.organization;

import com.example.workflow_s.model.Organization;
import com.example.workflow_s.model.Task;
import com.example.workflow_s.model.User;
import com.example.workflow_s.model.UserOrganization;

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
        void requestListOrganization(String userId);
        void switchOrganization(String userId, int targetOrgId, int oldOrgId);
    }

    //view
    interface OrganizationView {
        void finishedGetMemeber(List<User> userList);
        void finishedGetListUserOrganization(List<UserOrganization> userOrganizationList);
        void finishedSwitchOrganization();
    }

    //model
    interface GetOrganizationDataContract {
        interface OnFinishedGetMembersListener {
            void onFinishedGetMembers(ArrayList<User> users);
            void onFailure(Throwable t);
        }

        interface OnFinishedGetListUserOrganizationListener {
            void onFinishedGetListUserOrg(List<UserOrganization> userOrganizationList);
            void onFailure(Throwable t);
        }

        interface OnFinishedSwitchOrganizationListener {
            void onFinishedSwitchOrganization();
            void onFailure(Throwable t);
        }

        void getAllMember(int orgId, OnFinishedGetMembersListener onFinishedListener);
        void getListUserOrganization(String userId, OnFinishedGetListUserOrganizationListener onFinishedListener);
        void switchOrganization(String userId, int newOrgId, int oldOrgId, OnFinishedSwitchOrganizationListener onFinishedListener);
    }
}
