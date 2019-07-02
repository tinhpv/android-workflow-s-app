package com.example.workflow_s.ui.home;

import android.util.Log;

import com.example.workflow_s.model.Checklist;
import com.example.workflow_s.model.Task;

import java.util.ArrayList;

/**
 * Workflow_S
 * Created by TinhPV on 2019-06-15
 * Copyright © 2019 TinhPV. All rights reserved
 **/


public class HomePresenterImpl implements HomeContract.HomePresenter,
        HomeContract.GetHomeDataInteractor.OnFinishedGetRunningChecklistsListener,
        HomeContract.GetHomeDataInteractor.OnFinishedGetDueTasksListener {


    private static final String TAG = "HOME_PRESENTER";
    private HomeContract.HomeView mHomeView;
    private HomeContract.GetHomeDataInteractor mGetHomeDataInteractor;

    public HomePresenterImpl(HomeContract.HomeView homeView, HomeContract.GetHomeDataInteractor getHomeDataInteractor) {
        mHomeView = homeView;
        mGetHomeDataInteractor = getHomeDataInteractor;
    }

    @Override
    public void onFinishedGetChecklists(ArrayList<Checklist> checklistArrayList) {
        mHomeView.setDataToChecklistRecyclerView(checklistArrayList);
    }

    @Override
    public void onFailureGetChecklists(Throwable t) {
        mHomeView.onFailGetChecklist();
    }

    @Override
    public void onFinishedGetTasks(ArrayList<Task> taskArrayList) {
        mHomeView.setDataToTasksRecyclerView(taskArrayList);
    }

    @Override
    public void onFailureGetTasks(Throwable t) {
        mHomeView.onFailGetTask();
    }


    @Override
    public void onDestroy() {
        mHomeView = null;
    }

    @Override
    public void loadRunningChecklists(String userId, String orgId) {
        mGetHomeDataInteractor.getAllRunningChecklists(userId, orgId, this);
    }

    @Override
    public void loadDueTasks(String userId, String orgId) {
        mGetHomeDataInteractor.getAllDueTasks(userId, orgId, this);
    }

}
