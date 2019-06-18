package com.example.workflow_s.ui.home;

import com.example.workflow_s.model.Checklist;

import java.util.ArrayList;

/**
 * Workflow_S
 * Created by TinhPV on 2019-06-15
 * Copyright © 2019 TinhPV. All rights reserved
 **/


public class HomePresenterImpl implements HomeContract.HomePresenter, HomeContract.GetHomeDataInteractor.OnFinishedListener {

    private HomeContract.HomeView mHomeView;
    private HomeContract.GetHomeDataInteractor mGetHomeDataInteractor;

    public HomePresenterImpl(HomeContract.HomeView homeView, HomeContract.GetHomeDataInteractor getHomeDataInteractor) {
        mHomeView = homeView;
        mGetHomeDataInteractor = getHomeDataInteractor;
    }

    @Override
    public void onFinished(ArrayList<Checklist> checklistArrayList) {
        mHomeView.setDataToRecyclerView(checklistArrayList);
    }

    @Override
    public void onFailure(Throwable t) {

    }

    @Override
    public void onDestroy() {
        mHomeView = null;
    }

    @Override
    public void loadDataFromServer() {
        mGetHomeDataInteractor.getNoticeArrayList(this);
    }
}
