package com.example.workflow_s.ui.history;

import com.example.workflow_s.model.Notification;

import java.util.List;

/**
 * Workflow_S
 * Created by TinhPV on 2019-07-25
 * Copyright © 2019 TinhPV. All rights reserved
 **/


public interface HistoryContract {
    interface HistoryPresenter {
        void getActivities(int taskId);
    }

    interface HistoryView {
        void finishedGetTaskActivities(List<Notification> notificationList);
    }

    interface HistoryDataInteractor {
        interface OnFinishedGetActitivities {
            void onFinishedGetActs(List<Notification> list);
            void onFailure(Throwable t);
        }

        void getAllComments(int taskId, OnFinishedGetActitivities onFinishedListener);
    }
}
