package com.example.workflow_s.ui.organization;

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

    interface OrganizationPresenter {
        void requestOrganizationData(String orgId);
    }

    interface OrganizationView {
        void finishedGetMemeber(List<User> userList);
    }

    interface GetOrganizationDataContract {
        interface OnFinishedGetMembersListener {
            void onFinishedGetMembers(ArrayList<User> users);
            void onFailure(Throwable t);
        }

        void getAllMember(String orgId, OnFinishedGetMembersListener onFinishedListener);
    }
}
