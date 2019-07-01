package com.example.workflow_s.ui.home.viewholder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.workflow_s.R;

/**
 * Workflow_S
 * Created by TinhPV on 2019-06-30
 * Copyright © 2019 TinhPV. All rights reserved
 **/


public class TodayTaskViewHolder extends RecyclerView.ViewHolder {

    public TextView mTextView;

    public TodayTaskViewHolder(@NonNull View itemView) {
        super(itemView);
        mTextView = itemView.findViewById(R.id.tv_task_name);
    }
}
