package com.example.workflow_s.ui.activity;

import com.example.workflow_s.model.Task;

import java.util.List;

public interface ActivityContract {

    //presenter
    interface ActivityPresenter {
        void onDestroy();

        void loadAllDueTasks(String organizationId, String userId);
        void loadUpcomingTasks(String organizationId, String userId);
    }

    //view
    interface ActivityView {
        void finishedGetDueTasks(List<Task> listDueTask);
    }

    interface UpcomingActivityView {
        void finishedGetUpcomingTasks(List<Task> lisUpcomingTask);
    }

    //model
    interface GetActivitiesDataInteractor {

        interface OnFinishedGetDueTasksListener {
            void onFinishedGetDueTasks(List<Task> listDueTask);
            void onFailure(Throwable t);
        }

        interface OnFinishedGetUpcomingTasksListener {
            void onFinishedGetUpcomingTasks(List<Task> listUpcomingTask);
            void onFailure(Throwable t);
        }

        void getAllDueTasks(String organizationId, String userId, OnFinishedGetDueTasksListener onFinishedListener);
        void getUpcomingTasks(String organizationId, String userId, OnFinishedGetUpcomingTasksListener onFinishedListener);

    }
}
