package com.example.workflow_s.ui.home.viewholder;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.workflow_s.R;

/**
 * Workflow_S
 * Created by TinhPV on 2019-06-30
 * Copyright © 2019 TinhPV. All rights reserved
 **/


public class TodayTaskViewHolder extends RecyclerView.ViewHolder {

    public TextView mTaskName, mChecklistTask, mDueTime;

    public TodayTaskViewHolder(@NonNull View itemView) {
        super(itemView);
        mTaskName = itemView.findViewById(R.id.tv_task_name);
        mChecklistTask = itemView.findViewById(R.id.tv_checklist_of_task);
        mDueTime = itemView.findViewById(R.id.tv_due_time);
    }
}
