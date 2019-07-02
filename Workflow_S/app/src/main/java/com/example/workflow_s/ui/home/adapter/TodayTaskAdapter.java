package com.example.workflow_s.ui.home.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.workflow_s.R;
import com.example.workflow_s.model.Task;
import com.example.workflow_s.ui.home.viewholder.TodayTaskViewHolder;

import java.util.List;

/**
 * Workflow_S
 * Created by TinhPV on 2019-06-13
 * Copyright © 2019 TinhPV. All rights reserved
 **/


public class TodayTaskAdapter extends RecyclerView.Adapter<TodayTaskViewHolder> {

    // Constants
    private final int MAX_ITEM_NUMBER = 4;

    // Datasource for RecyclerView
    private List<Task> mTasks;

    public TodayTaskAdapter() {}

    public void setTasks(List<Task> tasks) {
        mTasks = tasks;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TodayTaskViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        int layoutId = R.layout.recyclerview_item_today_task;
        View view = layoutInflater.inflate(layoutId, viewGroup, false);
        TodayTaskViewHolder viewHolder = new TodayTaskViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TodayTaskViewHolder todayTaskViewHolder, int i) {
        todayTaskViewHolder.mTaskName.setText(mTasks.get(i).getName());
        todayTaskViewHolder.mChecklistTask.setText(mTasks.get(i).getChecklistId());
        todayTaskViewHolder.mDueTime.setText(mTasks.get(i).getDueTime());
    }

    @Override
    public int getItemCount() {
        if (null == mTasks) {
            return 0;
        } else {
            int numberOfItems = mTasks.size();
            return numberOfItems > MAX_ITEM_NUMBER ? MAX_ITEM_NUMBER : numberOfItems;
        }
    }




}
