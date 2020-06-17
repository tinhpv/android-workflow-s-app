package com.example.workflow_s.ui.taskdetail.dialog.assignment;

import android.util.Log;

import com.example.workflow_s.model.Checklist;
import com.example.workflow_s.model.ChecklistMember;
import com.example.workflow_s.model.TaskMember;
import com.example.workflow_s.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Workflow_S
 * Created by TinhPV on 2019-07-06
 * Copyright © 2019 TinhPV. All rights reserved
 **/

public class AssigningDialogPresenterImpl implements AssigningDialogContract.AssigningDialogPresenter,
        AssigningDialogContract.GetDataAssignInteractor.OnFinishedGetMembersListener,
        AssigningDialogContract.GetDataAssignInteractor.OnFinishedAssignMemberListener,
        AssigningDialogContract.GetDataAssignInteractor.OnFinishedUnassignMemberListener,
        AssigningDialogContract.GetDataAssignInteractor.OnFinishedGetChecklistInfoListener,
        AssigningDialogContract.GetDataAssignInteractor.OnFinishedGetTaskMemberListener {

    private AssigningDialogContract.AssigningDialogView mDialogView;
    private AssigningDialogInteractor mDialogInteractor;

    public AssigningDialogPresenterImpl(AssigningDialogContract.AssigningDialogView dialogView, AssigningDialogInteractor dialogInteractor) {
        mDialogView = dialogView;
        mDialogInteractor = dialogInteractor;
    }

    @Override
    public void getTaskMember(int taskId) {
        mDialogInteractor.getTaskMember(taskId, this);
    }

    @Override
    public void onFinishedGetTaskMember(List<TaskMember> taskMemberList) {
        mDialogView.finishedGetTaskMember(taskMemberList);
    }

    @Override
    public void getOrgMember(int orgId) {
        mDialogInteractor.getAllMember(orgId, this);
    }

    @Override
    public void assignUser(TaskMember taskMember) {
        mDialogInteractor.assignTaskMember(taskMember, this);
    }

    @Override
    public void unassignUser(int memberId) {
        mDialogInteractor.unassignTaskMember(memberId, this);
    }

    @Override
    public void getChecklistInfo(int checklistId) {
        mDialogInteractor.getChecklistInfo(checklistId, this);
    }

    @Override
    public void onFinishedGetMembers(ArrayList<User> users) {
        mDialogView.finishedGetMember(users);
    }

    @Override
    public void onFinishedAssigning(TaskMember taskMember) {
        mDialogView.finishedAssignMember(taskMember);
    }

    @Override
    public void onFinishedUnassigning(int memberId) {
        mDialogView.finishedUnassignMember(memberId);
    }

    @Override
    public void onFinishedGetChecklist(Checklist checklist) {
        mDialogView.finishedGetChecklistInfoById(checklist);
    }

    @Override
    public void onFailure(Throwable t) {
        Log.d("PRESENTER", "onFailure: " + t.getMessage());
    }
}
