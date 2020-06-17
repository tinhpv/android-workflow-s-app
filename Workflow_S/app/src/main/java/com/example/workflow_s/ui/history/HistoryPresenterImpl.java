package com.example.workflow_s.ui.history;

import com.example.workflow_s.model.Notification;

import java.util.List;

/**
 * Workflow_S
 * Created by TinhPV on 2019-07-25
 * Copyright © 2019 TinhPV. All rights reserved
 **/


public class HistoryPresenterImpl implements HistoryContract.HistoryPresenter,
        HistoryContract.HistoryDataInteractor.OnFinishedGetActitivities {

    private HistoryContract.HistoryDataInteractor mDataInteractor;
    private HistoryContract.HistoryView mHistoryView;

    public HistoryPresenterImpl(HistoryContract.HistoryDataInteractor dataInteractor, HistoryContract.HistoryView historyView) {
        mDataInteractor = dataInteractor;
        mHistoryView = historyView;
    }

    @Override
    public void getActivities(int taskId) {
        mDataInteractor.getAllComments(taskId, this);
    }

    @Override
    public void onFinishedGetActs(List<Notification> list) {
        mHistoryView.finishedGetTaskActivities(list);
    }

    @Override
    public void onFailure(Throwable t) {

    }
}
