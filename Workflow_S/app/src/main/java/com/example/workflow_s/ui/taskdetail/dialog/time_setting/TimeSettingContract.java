package com.example.workflow_s.ui.taskdetail.dialog.time_setting;

import com.example.workflow_s.model.Checklist;

/**
 * Workflow_S
 * Created by TinhPV on 2019-07-07
 * Copyright © 2019 TinhPV. All rights reserved
 **/


public interface TimeSettingContract {

    interface TimeSettingPresenter {
        void setDueTime(int checklistId, String datetime);
        void getChecklistInfo(int checklistId);
    }

    interface TimeSettingView {
        void finishSetDueTime();
        void finishedGetChecklist(Checklist checklist);
    }


    interface TimeSettingDataContract {

        interface OnFinishedSetTimeListener {
            void onFinishedSetTime();
            void onFailure(Throwable t);
        }

        interface OnFinishedGetChecklistInfoListener {
            void onFinishedGetChecklistInfo(Checklist checklist);
            void onFailure(Throwable t);
        }


        void getChecklistInfo(int checklistId, OnFinishedGetChecklistInfoListener listener);
        void setTime(int checklistId, String dateTime, OnFinishedSetTimeListener onFinishedListener);
    }
}
