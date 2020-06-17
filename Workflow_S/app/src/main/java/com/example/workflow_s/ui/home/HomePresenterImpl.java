package com.example.workflow_s.ui.home;

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
        HomeContract.GetHomeDataInteractor.OnFinishedGetDueTasksListener,
        HomeContract.GetHomeDataInteractor.OnFinishedDeleteChecklistListener,
        HomeContract.GetHomeDataInteractor.OnFinishedSetNameOfChecklist{


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
    public void loadRunningChecklists(String orgId) {
        mGetHomeDataInteractor.getAllRunningChecklists(orgId, this);
    }

    @Override
    public void loadDueTasks(String orgId, String userId) {
        mGetHomeDataInteractor.getAllDueTasks(orgId,userId, this);
    }

    @Override
    public void deleteChecklist(int checklistId, String userId) {
        mGetHomeDataInteractor.deleteChecklist(checklistId, userId, this);
    }

    @Override
    public void setNameOfChecklist(int checklistId, String name) {
        mGetHomeDataInteractor.setNameOfChecklist(checklistId, name, this);
    }

    @Override
    public void onFinishedDeleteChecklist() {
        mHomeView.finishDeleteChecklist();
    }

    @Override
    public void onFinishedSetNameChecklist() {

    }

    @Override
    public void onFailure(Throwable t) {

    }
}
