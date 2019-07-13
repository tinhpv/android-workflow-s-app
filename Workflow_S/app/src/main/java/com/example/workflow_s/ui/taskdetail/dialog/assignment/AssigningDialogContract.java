package com.example.workflow_s.ui.taskdetail.dialog.assignment;

import com.example.workflow_s.model.Checklist;
import com.example.workflow_s.model.ChecklistMember;
import com.example.workflow_s.model.TaskMember;
import com.example.workflow_s.model.User;

import java.lang.reflect.Array;
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
        void getTaskMember(int taskId);
        void assignUser(ChecklistMember checklistMember);
        void unassignUser(int memberId);
        void getChecklistInfo(int checklistId);
    }

    interface AssigningDialogView {
        void finishedGetMember(List<User> userList);
        void finishedAssignMember();
        void finishedUnassignMember();
        void finishedGetChecklistInfoById(Checklist checklist);
        void finishedGetTaskMember(List<TaskMember> taskMemberList);
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
            void onFinishedAssigning();
            void onFailure(Throwable t);
        }

        interface OnFinishedUnassignMemberListener {
            void onFinishedUnassigning();
            void onFailure(Throwable t);
        }

        interface OnFinishedGetTaskMemberListener {
            void onFinishedGetTaskMember(List<TaskMember> taskMemberList);
            void onFailure(Throwable t);
        }



        void getAllMember(int orgId, OnFinishedGetMembersListener onFinishedListener);
        void getTaskMember(int taskId, OnFinishedGetTaskMemberListener listener);
        void getChecklistInfo(int checklistId, OnFinishedGetChecklistInfoListener listener);
        void assignChecklistMember(ChecklistMember checklistMember, OnFinishedAssignMemberListener listener);
        void unassignChecklistMember(int memberId, OnFinishedUnassignMemberListener listener);

    }
}
