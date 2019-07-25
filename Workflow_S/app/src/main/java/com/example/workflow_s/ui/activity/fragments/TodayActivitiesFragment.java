package com.example.workflow_s.ui.activity.fragments;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.workflow_s.R;
import com.example.workflow_s.model.Task;
import com.example.workflow_s.ui.activity.ActivityContract;
import com.example.workflow_s.ui.activity.ActivityInteractor;
import com.example.workflow_s.ui.activity.ActivityPresenterImpl;
import com.example.workflow_s.ui.activity.adapters.TodayActivitiesAdapter;
import com.example.workflow_s.utils.SharedPreferenceUtils;
import java.util.List;

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
    private LinearLayout mDataNotFound;

    private ActivityContract.ActivityPresenter mPresenter;
    private String orgId, userId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_today_activity, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFF")));
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initData();
        mDataNotFound = view.findViewById(R.id.task_data_notfound_message);
        setupTodayActivitiesRV();
    }

    private void initData() {
        mPresenter = new ActivityPresenterImpl(this, new ActivityInteractor());
        orgId = SharedPreferenceUtils.retrieveData(getActivity(), getString(R.string.pref_orgId));
        userId = SharedPreferenceUtils.retrieveData(getActivity(), getString(R.string.pref_userId));
        mPresenter.loadAllDueTasks(orgId, userId);
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
    public void finishedGetDueTasks(List<Task> listDueTask) {
        if (listDueTask.size() == 0) {
            mDataNotFound.setVisibility(View.VISIBLE);
        } else {
            mDataNotFound.setVisibility(View.GONE);
            mTodayActivitiesAdapter.setTasks(listDueTask);
        }

    }
}
