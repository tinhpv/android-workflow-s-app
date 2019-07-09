package com.example.workflow_s.ui.task.dialog.time_setting;

import com.example.workflow_s.model.Task;
import com.example.workflow_s.model.TaskMember;
import com.example.workflow_s.ui.task.dialog.assignment.AssigningDialogContract;

import java.util.ArrayList;

/**
 * Workflow_S
 * Created by TinhPV on 2019-07-07
 * Copyright © 2019 TinhPV. All rights reserved
 **/


public interface TimeSettingContract {

    interface TimeSettingPresenter {
        void setDueTime(int taskId, String datetime);
        void getFirstTask(int checklistId);
    }

    interface TimeSettingView {
        void finishSetDueTime();
        void finishedGetFirstTask(Task task);
    }


    interface TimeSettingDataContract {

        interface OnFinishedSetTimeListener {
            void onFinishedSetTime();
            void onFailure(Throwable t);
        }

        interface OnFinishedGetTaskListener {
            void onFinishedGetTask(Task task);
            void onFailure(Throwable t);
        }


        void setTime(int taskId, String dateTime, OnFinishedSetTimeListener onFinishedListener);
        void getTask(int checklistId, OnFinishedGetTaskListener listener);
    }
}
