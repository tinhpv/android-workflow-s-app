package com.example.workflow_s.ui.task.dialog.assignment;

import com.example.workflow_s.model.Checklist;
import com.example.workflow_s.model.ChecklistMember;
import com.example.workflow_s.model.TaskMember;
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
        void assignUser(ChecklistMember checklistMember);
        void unassignUser(int memberId);
        void getChecklistInfo(int checklistId);
    }

    interface AssigningDialogView {
        void finishedGetMember(List<User> userList);
        void finishedAssignMember(ChecklistMember member);
        void finishedUnassignMember();
        void finishedGetChecklistInfoById(Checklist checklist);
    }

    interface GetDataAssignInteractor {

        interface OnFinishedGetMembersListener {
            void onFinishedGetMembers(ArrayList<User> users);
            void onFailure(Throwable t);
        }

        interface OnFinishedGetChecklistInfoListener {
            void onFinishedGetChecklist(Checklist checklist);
            void onFailure(Throwable t);
        }

        interface OnFinishedAssignMemberListener {
            void onFinishedAssigning(ChecklistMember member);
            void onFailure(Throwable t);
        }

        interface OnFinishedUnassignMemberListener {
            void onFinishedUnassigning();
            void onFailure(Throwable t);
        }



        void getAllMember(int orgId, OnFinishedGetMembersListener onFinishedListener);
        void getChecklistInfo(int checklistId, OnFinishedGetChecklistInfoListener listener);
        void assignChecklistMember(ChecklistMember checklistMember, OnFinishedAssignMemberListener listener);
        void unassignChecklistMember(int memberId, OnFinishedUnassignMemberListener listener);

    }
}
