package com.example.workflow_s.ui.taskdetail.dialog.time_setting;

import com.example.workflow_s.model.Task;

/**
 * Workflow_S
 * Created by TinhPV on 2019-07-07
 * Copyright © 2019 TinhPV. All rights reserved
 **/


public interface TimeSettingContract {

    interface TimeSettingPresenter {
        void setDueTime(int taskId, String datetime);
        void getTaskInfo(int taskId);
    }

    interface TimeSettingView {
        void finishSetDueTime();
        void finishedGetTask(Task task);
    }


    interface TimeSettingDataContract {

        interface OnFinishedSetTimeListener {
            void onFinishedSetTime();
            void onFailure(Throwable t);
        }

        interface OnFinishedGetTaskInfoListener {
            void onFinishedGetTaskInfo(Task task);
            void onFailure(Throwable t);
        }


        void getTaskInfo(int taskId, OnFinishedGetTaskInfoListener listener);
        void setTime(int taskId, String dateTime, OnFinishedSetTimeListener onFinishedListener);
    }
}
