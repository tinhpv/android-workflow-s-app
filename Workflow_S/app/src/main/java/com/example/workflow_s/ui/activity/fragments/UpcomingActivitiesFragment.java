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
import com.example.workflow_s.ui.activity.adapters.UpcomingActivitiesAdapter;

import java.util.ArrayList;

/**
 * Workflow_S
 * Created by TinhPV on 2019-06-25
 * Copyright © 2019 TinhPV. All rights reserved
 **/


public class UpcomingActivitiesFragment extends Fragment implements ActivityContract.ActivityView {

    View view;
    private RecyclerView upcomingActivitiesRecyclerView;
    private UpcomingActivitiesAdapter mUpcomingActivitiesAdapter;
    private RecyclerView.LayoutManager upcomingLayoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_upcoming_activities, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setupUpcomingActivitiesRV();
    }

    public void setupUpcomingActivitiesRV() {
        upcomingActivitiesRecyclerView = view.findViewById(R.id.rv_upcoming_activities);
        upcomingActivitiesRecyclerView.setHasFixedSize(true);
        upcomingLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        upcomingActivitiesRecyclerView.setLayoutManager(upcomingLayoutManager);

        //FIXME - TEST DATASOURCE OF ACTIVITIES_RV
        mUpcomingActivitiesAdapter = new UpcomingActivitiesAdapter();
        upcomingActivitiesRecyclerView.setAdapter(mUpcomingActivitiesAdapter);
    }

    @Override
    public void setDataToChecklistRecyclerView(ArrayList<Checklist> datasource) {

    }

    @Override
    public void finishedGetFirstTask(Task task, Checklist checklist) {

    }

}
