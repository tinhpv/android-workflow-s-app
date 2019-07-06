package com.example.workflow_s.ui.task.dialog;

import android.util.Log;

import com.example.workflow_s.model.TaskMember;
import com.example.workflow_s.model.User;

import java.util.ArrayList;

/**
 * Workflow_S
 * Created by TinhPV on 2019-07-06
 * Copyright © 2019 TinhPV. All rights reserved
 **/


public class AssigningDialogPresenterImpl implements AssigningDialogContract.AssigningDialogPresenter,
        AssigningDialogContract.GetDataAssignInteractor.OnFinishedGetMembersListener,
        AssigningDialogContract.GetDataAssignInteractor.OnFinishedAssignMemberListener,
        AssigningDialogContract.GetDataAssignInteractor.OnFinishedUnassignMemberListener {

    private AssigningDialogContract.AssigningDialogView mDialogView;
    private AssigningDialogInteractor mDialogInteractor;

    public AssigningDialogPresenterImpl(AssigningDialogContract.AssigningDialogView dialogView, AssigningDialogInteractor dialogInteractor) {
        mDialogView = dialogView;
        mDialogInteractor = dialogInteractor;
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
    public void onFinishedGetMembers(ArrayList<User> users) {
        mDialogView.finishedGetMember(users);
    }

    @Override
    public void onFinishedAssigning() {
        mDialogView.finishedAssignMember();
    }


    @Override
    public void onFinishedUnassigning() {
        mDialogView.finishedUnassignMember();
    }

    @Override
    public void onFailure(Throwable t) {
        Log.d("PRESENTER", "onFailure: " + t.getMessage());
    }
}
