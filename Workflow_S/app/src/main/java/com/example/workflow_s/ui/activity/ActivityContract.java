package com.example.workflow_s.ui.activity;

import com.example.workflow_s.model.Task;

import java.util.ArrayList;

public interface ActivityContract {

    //presenter
    interface ActivityPresenter {
        void onDestroy();
        void loadTodayTasks(String userId);
        void loadUpcomingTasks(String orgranizationId,String userId);
    }

    //view
    interface ActivityView {
        void setDataToTodayTasksRecyclerView(ArrayList<Task> datasource);
        void setDataToUpcomingTasksRecyclerView(ArrayList<Task> datasource);
    }

    //model
    interface GetActivitiesDataInteractor {
        interface  OnFinishedGetTodayTaskListener {
            void onFinishedGetTodayTasks(ArrayList<Task> taskArrayList);
            void onFailure(Throwable t);
        }

        interface OnFinishedGetUpcomingTaskListener {
            void onFinishedGetUpcomingTasks(ArrayList<Task> taskArrayList);
            void onFailure(Throwable t);
        }

        void getAllTodayTasks(String userId, OnFinishedGetTodayTaskListener onFinishedGetTodayTaskListener);
        void getAllUpcomingTasks(String orgranizationId, String userId,  OnFinishedGetUpcomingTaskListener onFinishedGetUpcomingTaskListener);
    }
}
