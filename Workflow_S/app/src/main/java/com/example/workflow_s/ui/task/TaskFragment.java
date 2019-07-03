package com.example.workflow_s.ui.task;

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
import com.example.workflow_s.model.Task;
import com.example.workflow_s.ui.task.adapter.TaskAdapter;

import java.util.ArrayList;

/**
 * Workflow_S
 * Created by TinhPV on 2019-06-30
 * Copyright © 2019 TinhPV. All rights reserved
 **/


public class TaskFragment extends Fragment implements TaskContract.TaskView {

    private static final String TAG = "TASK_FRAGMENT";

    View view;
    private RecyclerView taskRecyclerView;
    private TaskAdapter mTaskAdapter;
    private RecyclerView.LayoutManager taskLayoutManager;

    private TaskContract.TaskPresenter mPresenter;

    private int checklistId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        getActivity().getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        view = inflater.inflate(R.layout.fragment_task, container, false);
        getActivity().setTitle("Checklist's Tasks");
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setupTaskRV();
        initData();
    }

    private void setupTaskRV() {
        taskRecyclerView = view.findViewById(R.id.rv_task);
        taskRecyclerView.setHasFixedSize(true);
        taskLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        taskRecyclerView.setLayoutManager(taskLayoutManager);

        mTaskAdapter = new TaskAdapter();
        taskRecyclerView.setAdapter(mTaskAdapter);
    }

    public void initData() {
        Bundle arguments = getArguments();
        checklistId = Integer.parseInt(arguments.getString("checklistId"));
        Log.i(TAG, "initData: " + checklistId);

        // FIXME - HARDCODE HERE
        mPresenter = new TaskPresenterImpl(this, new TaskInteractor());
//        mPresenter.loadTasks(checklistId);
        mPresenter.loadTasks(2);
    }


    @Override
    public void setDataToTaskRecyclerView(ArrayList<Task> datasource) {
        mTaskAdapter.setTaskList(datasource);
    }
}
