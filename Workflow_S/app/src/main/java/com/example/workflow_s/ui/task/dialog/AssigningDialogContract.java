package com.example.workflow_s.ui.task.dialog;

import com.example.workflow_s.model.User;
import com.example.workflow_s.ui.organization.OrganizationContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Workflow_S
 * Created by TinhPV on 2019-07-06
 * Copyright © 2019 TinhPV. All rights reserved
 **/


public interface AssigningDialogContract {
    interface AssigningDialogPresenter {
        void getOrgMember(int orgId);
    }

    interface AssigningDialogView {
        void finishedGetMember(List<User> userList);
    }

    interface GetDataAssignInteractor {
        interface OnFinishedGetMembersListener {
            void onFinishedGetMembers(ArrayList<User> users);
            void onFailure(Throwable t);
        }
        void getAllMember(int orgId, OnFinishedGetMembersListener onFinishedListener);
    }
}
