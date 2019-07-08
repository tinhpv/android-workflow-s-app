package com.example.workflow_s.ui.task.dialog.time_setting;

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
    }

    interface TimeSettingView {
        void finishSetDueTime();
    }


    interface TimeSettingDataContract {

        interface OnFinishedSetTimeListener {
            void onFinishedSetTime();
            void onFailure(Throwable t);
        }


        void setTime(int taskId, String dateTime, OnFinishedSetTimeListener onFinishedListener);
    }
}
