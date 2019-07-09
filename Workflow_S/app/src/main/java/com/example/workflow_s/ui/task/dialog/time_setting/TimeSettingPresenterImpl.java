package com.example.workflow_s.ui.task.dialog.time_setting;

import android.util.Log;

import com.example.workflow_s.model.Task;

/**
 * Workflow_S
 * Created by TinhPV on 2019-07-07
 * Copyright © 2019 TinhPV. All rights reserved
 **/


public class TimeSettingPresenterImpl implements TimeSettingContract.TimeSettingPresenter,
        TimeSettingContract.TimeSettingDataContract.OnFinishedSetTimeListener,
        TimeSettingContract.TimeSettingDataContract.OnFinishedGetTaskListener {

    TimeSettingContract.TimeSettingView mSettingView;
    TimeSettingContract.TimeSettingDataContract mDataContract;

    public TimeSettingPresenterImpl(TimeSettingContract.TimeSettingView settingView, TimeSettingContract.TimeSettingDataContract dataContract) {
        mSettingView = settingView;
        mDataContract = dataContract;
    }

    @Override
    public void setDueTime(int taskId, String datetime) {
        mDataContract.setTime(taskId, datetime, this);
    }

    @Override
    public void getFirstTask(int checklistId) {
        mDataContract.getTask(checklistId, this);
    }

    @Override
    public void onFinishedSetTime() {
        mSettingView.finishSetDueTime();
    }

    @Override
    public void onFinishedGetTask(Task task) {
        mSettingView.finishedGetFirstTask(task);
    }

    @Override
    public void onFailure(Throwable t) {
        Log.d("SET TIME", "onFailure: " + t.getMessage());
    }
}
