package com.example.workflow_s.ui.activity;

import android.util.Log;

import com.example.workflow_s.model.Checklist;
import com.example.workflow_s.model.Task;

import java.util.ArrayList;
import java.util.List;

public class ActivityPresenterImpl implements ActivityContract.ActivityPresenter,
            ActivityContract.GetActivitiesDataInteractor.OnFinishedGetDueTasksListener,
            ActivityContract.GetActivitiesDataInteractor.OnFinishedGetUpcomingTasksListener{

    private static final  String TAG = "ACTIVITIES_PRESENTER";
    private ActivityContract.ActivityView mActivityView;
    private ActivityContract.UpcomingActivityView mUpcomingView;
    private ActivityContract.GetActivitiesDataInteractor mGetActivityDataInteractor;

    public ActivityPresenterImpl(ActivityContract.ActivityView activityView, ActivityContract.GetActivitiesDataInteractor getActivitiesDataInteractor) {
        mActivityView = activityView;
        mUpcomingView = null;
        mGetActivityDataInteractor = getActivitiesDataInteractor;
    }

    public ActivityPresenterImpl(ActivityContract.UpcomingActivityView mUpcomingView, ActivityContract.GetActivitiesDataInteractor getActivitiesDataInteractor) {
        mActivityView = null;
        this.mUpcomingView = mUpcomingView;
        mGetActivityDataInteractor = getActivitiesDataInteractor;
    }

    @Override
    public void onDestroy() {
        mActivityView = null;
    }

    @Override
    public void loadAllDueTasks(String organizationId, String userId) {
        mGetActivityDataInteractor.getAllDueTasks(organizationId, userId, this);
    }

    @Override
    public void loadUpcomingTasks(String organizationId, String userId) {
        mGetActivityDataInteractor.getUpcomingTasks(organizationId, userId, this);
    }

    @Override
    public void onFinishedGetDueTasks(List<Task> listDueTask) {
        mActivityView.finishedGetDueTasks(listDueTask);
    }

    @Override
    public void onFinishedGetUpcomingTasks(List<Task> listUpcomingTask) {
        mUpcomingView.finishedGetUpcomingTasks(listUpcomingTask);
    }

    @Override
    public void onFailure(Throwable t) {
        Log.d(TAG, "onFailure: " + t.getMessage());
    }
}
