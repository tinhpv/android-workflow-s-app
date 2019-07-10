package com.example.workflow_s.ui.activity.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.workflow_s.R;
import com.example.workflow_s.model.Checklist;
import com.example.workflow_s.model.Task;
import com.example.workflow_s.ui.activity.ActivityContract;
import com.example.workflow_s.ui.activity.ActivityInteractor;
import com.example.workflow_s.ui.activity.ActivityPresenterImpl;
import com.example.workflow_s.ui.activity.adapters.TodayActivitiesAdapter;
import com.example.workflow_s.ui.activity.adapters.UpcomingActivitiesAdapter;
import com.example.workflow_s.utils.SharedPreferenceUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Workflow_S
 * Created by TinhPV on 2019-06-25
 * Copyright © 2019 TinhPV. All rights reserved
 **/


public class UpcomingActivitiesFragment extends Fragment implements ActivityContract.UpcomingActivityView {

    View view;
    private RecyclerView upcomingActivitiesRecyclerView;
    private TodayActivitiesAdapter mAdapter;
    private RecyclerView.LayoutManager upcomingLayoutManager;

    private ActivityContract.ActivityPresenter mPresenter;
    private String orgId, userId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_upcoming_activities, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initData();
        setupUpcomingActivitiesRV();
    }

    private void initData() {
        mPresenter = new ActivityPresenterImpl(this, new ActivityInteractor());
        orgId = SharedPreferenceUtils.retrieveData(getActivity(), getString(R.string.pref_orgId));
        userId = SharedPreferenceUtils.retrieveData(getActivity(), getString(R.string.pref_userId));
        mPresenter.loadUpcomingTasks(orgId, userId);
    }

    public void setupUpcomingActivitiesRV() {
        upcomingActivitiesRecyclerView = view.findViewById(R.id.rv_upcoming_activities);
        upcomingActivitiesRecyclerView.setHasFixedSize(true);
        upcomingLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        upcomingActivitiesRecyclerView.setLayoutManager(upcomingLayoutManager);

        mAdapter = new TodayActivitiesAdapter();
        upcomingActivitiesRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void finishedGetUpcomingTasks(List<Task> lisUpcomingTask) {
        mAdapter.setTasks(lisUpcomingTask);
    }
}
