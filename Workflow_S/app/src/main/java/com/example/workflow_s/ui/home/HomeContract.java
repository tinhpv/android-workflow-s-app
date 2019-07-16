package com.example.workflow_s.ui.home;

import com.example.workflow_s.model.Checklist;
import com.example.workflow_s.model.Task;
import com.example.workflow_s.ui.base.MvpView;
import com.example.workflow_s.ui.checklist.ChecklistContract;

import java.util.ArrayList;

/**
 * Workflow_S
 * Created by TinhPV on 2019-06-15
 * Copyright © 2019 TinhPV. All rights reserved
 **/


public interface HomeContract {

    interface HomePresenter {
        void onDestroy();
        void loadRunningChecklists(String orgId);
        void loadDueTasks(String orgId, String userId);
        void deleteChecklist(int checklistId, String userId);
    }

    interface HomeView  {
        void setDataToChecklistRecyclerView(ArrayList<Checklist> datasource);
        void setDataToTasksRecyclerView(ArrayList<Task> datasource);
        void onFailGetChecklist();
        void onFailGetTask();
        void finishDeleteChecklist();
    }

    interface GetHomeDataInteractor {

        interface OnFinishedGetRunningChecklistsListener {
            void onFinishedGetChecklists(ArrayList<Checklist> checklistArrayList);
            void onFailureGetChecklists(Throwable t);
        }

        interface OnFinishedGetDueTasksListener {
            void onFinishedGetTasks(ArrayList<Task> taskArrayList);
            void onFailureGetTasks(Throwable t);
        }

        interface OnFinishedDeleteChecklistListener {
            void onFinishedDeleteChecklist();
            void onFailure(Throwable t);
        }

        void deleteChecklist(int checklistId, String userId, OnFinishedDeleteChecklistListener listener);
        void getAllRunningChecklists(String orgId, OnFinishedGetRunningChecklistsListener onFinishedListener);
        void getAllDueTasks(String orgId, String userId, OnFinishedGetDueTasksListener onFinishedListener);

    }
}
