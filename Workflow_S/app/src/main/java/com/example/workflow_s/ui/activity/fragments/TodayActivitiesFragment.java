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
import com.example.workflow_s.model.Task;
import com.example.workflow_s.ui.activity.ActivityContract;
import com.example.workflow_s.ui.activity.adapters.TodayActivitiesAdapter;

import java.util.ArrayList;

/**
 * Workflow_S
 * Created by TinhPV on 2019-06-25
 * Copyright © 2019 TinhPV. All rights reserved
 **/


public class TodayActivitiesFragment extends Fragment implements ActivityContract.ActivityView {

    View view;
    private TodayActivitiesAdapter mTodayActivitiesAdapter;
    private RecyclerView todayActivitiesRecyclerView;
    private RecyclerView.LayoutManager todayLayoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_today_activity, container, false);
        setupTodayActivitiesRV();
        return view;
    }

    public void setupTodayActivitiesRV() {
        todayActivitiesRecyclerView = view.findViewById(R.id.rv_today_activities);
        todayActivitiesRecyclerView.setHasFixedSize(true);
        todayLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        todayActivitiesRecyclerView.setLayoutManager(todayLayoutManager);

        //FIXME - TEST DATASOURCE OF ACTIVITIES_RV
        ArrayList<Task> mTasks = new ArrayList<>();
        mTodayActivitiesAdapter = new TodayActivitiesAdapter(mTasks);
        todayActivitiesRecyclerView.setAdapter(mTodayActivitiesAdapter);
    }

    @Override
    public void setDataToTodayTasksRecyclerView(ArrayList<Task> datasource) {
        mTodayActivitiesAdapter =new TodayActivitiesAdapter(datasource);
        todayActivitiesRecyclerView.setAdapter(mTodayActivitiesAdapter);
    }

    @Override
    public void setDataToUpcomingTasksRecyclerView(ArrayList<Task> datasource) {

    }
}
