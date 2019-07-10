package com.example.workflow_s.ui.home.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.workflow_s.R;
import com.example.workflow_s.model.Task;
import com.example.workflow_s.ui.taskdetail.checklist.ChecklistTaskDetailFragment;
import com.example.workflow_s.utils.CommonUtils;

import java.util.List;

/**
 * Workflow_S
 * Created by TinhPV on 2019-06-13
 * Copyright © 2019 TinhPV. All rights reserved
 **/


public class TodayTaskAdapter extends RecyclerView.Adapter<TodayTaskAdapter.TodayTaskViewHolder> {

    // Constants
    private final int MAX_ITEM_NUMBER = 4;

    // Datasource for RecyclerView
    private List<Task> mTasks;
    private RecyclerView mRecyclerView;

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mRecyclerView = recyclerView;
    }

    public TodayTaskAdapter() {}

    @NonNull
    @Override
    public TodayTaskViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.recyclerview_item_today_task, viewGroup, false);
        TodayTaskViewHolder viewHolder = new TodayTaskViewHolder(view);

        //item click
        viewHolder.mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = mRecyclerView.getChildLayoutPosition(v);
                Bundle args = new Bundle();
                args.putString("checklistId", String.valueOf(mTasks.get(index).getId()));
                args.putString("taskName", mTasks.get(index).getName());
                CommonUtils.replaceFragments(v.getContext(), ChecklistTaskDetailFragment.class, args);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TodayTaskViewHolder todayTaskViewHolder, int i) {
        todayTaskViewHolder.mTextView.setText(mTasks.get(i).getName());
        todayTaskViewHolder.mTextViewTime.setText(mTasks.get(i).getDueTime());
    }

    @Override
    public int getItemCount() {
        if (mTasks == null) {
            return 0;
        }
        return mTasks.size();
    }

    public void setTasks(List<Task> tasks) {
        mTasks = tasks;
        this.notifyDataSetChanged();
    }


    public class TodayTaskViewHolder extends RecyclerView.ViewHolder {

        private TextView mTextView, mTextViewTime;

        public TodayTaskViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.tv_today_activity_name);
            mTextViewTime = itemView.findViewById(R.id.tv_due_time);
        }
    }


}
