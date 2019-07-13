package com.example.workflow_s.ui.taskdetail.dialog.time_setting;

import android.util.Log;

import com.example.workflow_s.model.Checklist;

/**
 * Workflow_S
 * Created by TinhPV on 2019-07-07
 * Copyright © 2019 TinhPV. All rights reserved
 **/


public class TimeSettingPresenterImpl implements TimeSettingContract.TimeSettingPresenter,
        TimeSettingContract.TimeSettingDataContract.OnFinishedSetTimeListener,
        TimeSettingContract.TimeSettingDataContract.OnFinishedGetChecklistInfoListener {

    TimeSettingContract.TimeSettingView mSettingView;
    TimeSettingContract.TimeSettingDataContract mDataContract;

    public TimeSettingPresenterImpl(TimeSettingContract.TimeSettingView settingView, TimeSettingContract.TimeSettingDataContract dataContract) {
        mSettingView = settingView;
        mDataContract = dataContract;
    }

    @Override
    public void setDueTime(int checklistId, String datetime) {
        mDataContract.setTime(checklistId, datetime, this);
    }

    @Override
    public void getChecklistInfo(int checklistId) {
        mDataContract.getChecklistInfo(checklistId, this);
    }

    @Override
    public void onFinishedSetTime() {
        mSettingView.finishSetDueTime();
    }


    @Override
    public void onFinishedGetChecklistInfo(Checklist checklist) {
        mSettingView.finishedGetChecklist(checklist);
    }

    @Override
    public void onFailure(Throwable t) {
        Log.d("SET TIME", "onFailure: " + t.getMessage());
    }
}
