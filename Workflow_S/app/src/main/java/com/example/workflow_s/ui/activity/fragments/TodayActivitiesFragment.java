package com.example.workflow_s.ui.activity.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.example.workflow_s.utils.DateUtils;
import com.example.workflow_s.utils.SharedPreferenceUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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

    private ActivityContract.ActivityPresenter mPresenter;
    private String orgId;
    private ArrayList<Checklist> checklists;
    private ArrayList<String> dueTimeList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_today_activity, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initData();
        setupTodayActivitiesRV();
    }

    private void initData() {
        mPresenter = new ActivityPresenterImpl(this, new ActivityInteractor());
        orgId = SharedPreferenceUtils.retrieveData(getActivity(), getString(R.string.pref_orgId));
        mPresenter.loadAllChecklist(orgId);
    }

    public void setupTodayActivitiesRV() {
        todayActivitiesRecyclerView = view.findViewById(R.id.rv_today_activities);
        todayActivitiesRecyclerView.setHasFixedSize(true);
        todayLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        todayActivitiesRecyclerView.setLayoutManager(todayLayoutManager);

        mTodayActivitiesAdapter = new TodayActivitiesAdapter();
        todayActivitiesRecyclerView.setAdapter(mTodayActivitiesAdapter);
    }


    @Override
    public void setDataToChecklistRecyclerView(ArrayList<Checklist> datasource) {
        if (datasource != null) {
            checklists = new ArrayList<>();
            dueTimeList = new ArrayList<>();
            for (Checklist checklist : datasource) {
                mPresenter.loadFirstTaskFromChecklist(checklist.getId(), checklist);
            }
            mTodayActivitiesAdapter.setmChecklists(checklists, dueTimeList);
        }
    }

    @Override
    public void finishedGetFirstTask(Task task, Checklist checklist) {
        if (task != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            String dateSelected = task.getDueTime().split("T")[0];
            Log.i("dateSelected", dateSelected);
            Date dateTask = null;
            Date currentDateTime = null;
            Date current = Calendar.getInstance().getTime();

            String currentDate = sdf.format(current);
            try {
                dateTask = sdf.parse(dateSelected);
                Log.i("dateTask", dateTask.toString());
                String timeSelected = task.getDueTime().split("T")[1];
                //Date timeTask = DateUtils.parseTime(timeSelected);
                //Log.i("timeTask", timeTask.toString());

                String dateTimeSelected = dateSelected + " " + timeSelected;
                currentDateTime = sdf.parse(currentDate);

                if (currentDateTime.equals(dateTask) ) {
                    Log.i("compareTime", "equal");
                    checklists.add(checklist);
                    dueTimeList.add(dateTimeSelected);
                }
                mTodayActivitiesAdapter.setmChecklists(checklists, dueTimeList);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            //Date dateTask = DateUtils.parseDate(dateSelected);

        }
    }
}
