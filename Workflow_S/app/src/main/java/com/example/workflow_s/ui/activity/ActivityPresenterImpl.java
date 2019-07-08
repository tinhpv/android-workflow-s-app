package com.example.workflow_s.ui.activity;

import android.util.Log;

import com.example.workflow_s.model.Task;

import java.util.ArrayList;

public class ActivityPresenterImpl implements ActivityContract.ActivityPresenter,
            ActivityContract.GetActivitiesDataInteractor.OnFinishedGetUpcomingTaskListener,
            ActivityContract.GetActivitiesDataInteractor.OnFinishedGetTodayTaskListener {

    private static final  String TAG = "ACTIVITIES_PRESENTER";
    private ActivityContract.ActivityView mActivityView;
    private ActivityContract.GetActivitiesDataInteractor mGetActivityDataInteractor;

    public ActivityPresenterImpl(ActivityContract.ActivityView activityView, ActivityContract.GetActivitiesDataInteractor getActivitiesDataInteractor) {
        mActivityView = activityView;
        mGetActivityDataInteractor = getActivitiesDataInteractor;
    }

    @Override
    public void onDestroy() {
        mActivityView = null;
    }

    @Override
    public void loadTodayTasks(String userId) {
        mGetActivityDataInteractor.getAllTodayTasks(userId, this);
    }

    @Override
    public void loadUpcomingTasks(String orgranizationId, String userId) {
        mGetActivityDataInteractor.getAllUpcomingTasks(orgranizationId, userId, this);
    }

    @Override
    public void onFinishedGetTodayTasks(ArrayList<Task> taskArrayList) {
        mActivityView.setDataToTodayTasksRecyclerView(taskArrayList);
    }

    @Override
    public void onFinishedGetUpcomingTasks(ArrayList<Task> taskArrayList) {
        mActivityView.setDataToUpcomingTasksRecyclerView(taskArrayList);
    }

    @Override
    public void onFailure(Throwable t) {
        Log.d(TAG, "onFailure: " + t.getMessage());
    }
}
