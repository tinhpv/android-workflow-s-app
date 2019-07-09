package com.example.workflow_s.ui.activity;

import android.util.Log;

import com.example.workflow_s.model.Checklist;
import com.example.workflow_s.model.Task;

import java.util.ArrayList;

public class ActivityPresenterImpl implements ActivityContract.ActivityPresenter,
            ActivityContract.GetActivitiesDataInteractor.OnFinishedGetChecklistListener,
            ActivityContract.GetActivitiesDataInteractor.OnFinishedGetFirstTaskListener{

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
    public void loadAllChecklist(String organizationId) {
        mGetActivityDataInteractor.getAllChecklist(organizationId, this);
    }

    @Override
    public void loadFirstTaskFromChecklist(int checklistId, Checklist checklist) {
        mGetActivityDataInteractor.getFirstTask(checklistId, checklist ,this);
    }

    @Override
    public void onFinishedGetChecklist(ArrayList<Checklist> checklistArrayList) {
        mActivityView.setDataToChecklistRecyclerView(checklistArrayList);
    }

    @Override
    public void onFinishedGetFirstTask(Task task, Checklist checklist) {
        mActivityView.finishedGetFirstTask(task, checklist);
    }

    @Override
    public void onFailure(Throwable t) {
        Log.d(TAG, "onFailure: " + t.getMessage());
    }
}
