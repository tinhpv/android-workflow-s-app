package com.example.workflow_s.ui.home.adapter;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.workflow_s.R;
import com.example.workflow_s.model.Task;
import com.example.workflow_s.ui.taskdetail.checklist.ChecklistTaskDetailFragment;
import com.example.workflow_s.utils.CommonUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
    public TodayTaskViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.recyclerview_item_today_task, viewGroup, false);
        TodayTaskViewHolder viewHolder = new TodayTaskViewHolder(view);

        //item click
        viewHolder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = mRecyclerView.getChildLayoutPosition(v);
                Bundle args = new Bundle();
                args.putString("taskId", String.valueOf(mTasks.get(index).getId()));
                args.putString("taskName", mTasks.get(index).getName());
                args.putInt("location_activity", 1);
                CommonUtils.replaceFragments(viewGroup.getContext(), ChecklistTaskDetailFragment.class, args, false);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TodayTaskViewHolder todayTaskViewHolder, int i) {
        todayTaskViewHolder.mTextView.setText(mTasks.get(i).getName());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String dateSelected = mTasks.get(i).getDueTime().split("T")[0];
        String timeSelected = mTasks.get(i).getDueTime().split("T")[1];
        Date currentTime = Calendar.getInstance().getTime();
        String dueTime = dateSelected + " " + timeSelected;
        Date overdue = null;
        try {
            overdue = sdf.parse(dueTime);
            long totalTime = overdue.getTime() - currentTime.getTime();
            String time  = String.format("%dh",
                    TimeUnit.MILLISECONDS.toHours(totalTime)
            );
            if (Integer.parseInt(time.split("h")[0]) < 0) {
                time = "expired";
            } else if (Integer.parseInt(time.split("h")[0]) == 0) {
                time = String.format("%dm",
                        TimeUnit.MILLISECONDS.toMinutes(totalTime));
                if (Integer.parseInt(time.split("m")[0]) <= 0) {
                    time = "expired";
                }
            }
            todayTaskViewHolder.mTextViewTime.setText(time);

        } catch (ParseException e) {
            e.printStackTrace();
        }
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
        private LinearLayout item;


        public TodayTaskViewHolder(@NonNull View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.item_today_activity);
            mTextView = itemView.findViewById(R.id.tv_today_activity_name);
            mTextViewTime = itemView.findViewById(R.id.tv_due_time);
        }
    }


}
