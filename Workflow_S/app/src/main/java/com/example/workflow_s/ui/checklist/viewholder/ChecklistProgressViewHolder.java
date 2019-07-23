package com.example.workflow_s.ui.checklist.viewholder;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.workflow_s.R;

/**
 * Workflow_S
 * Created by TinhPV on 2019-06-30
 * Copyright © 2019 TinhPV. All rights reserved
 **/


public class ChecklistProgressViewHolder extends RecyclerView.ViewHolder {
    public View view;
    public TextView mChecklistName;
    public TextView mTemplate;
    public ProgressBar mProgressChecklistBar;

    public ChecklistProgressViewHolder(@NonNull View itemView) {
        super(itemView);
        view = itemView;
        mChecklistName = itemView.findViewById(R.id.tv_checklist_name);
        mTemplate = itemView.findViewById(R.id.tv_template_name);
        mProgressChecklistBar = itemView.findViewById(R.id.pb_checklist_home);
    }
}
