package com.example.workflow_s.ui.template.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.workflow_s.R;

/**
 * Workflow_S
 * Created by TinhPV on 2019-06-28
 * Copyright © 2019 TinhPV. All rights reserved
 **/


public class TemplateViewHolder extends RecyclerView.ViewHolder {

    public TextView mTemplateName, mTemplateCategory;
    public View view;

    public TemplateViewHolder(@NonNull View itemView) {
        super(itemView);
        view = itemView;
        mTemplateName = itemView.findViewById(R.id.tv_template_name);
        mTemplateCategory = itemView.findViewById(R.id.tv_template_category);
    }
}
