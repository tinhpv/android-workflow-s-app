package com.example.workflow_s.ui.template.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.workflow_s.R;
import com.example.workflow_s.model.Template;
import com.example.workflow_s.ui.task.task_checklist.ChecklistTaskFragment;
import com.example.workflow_s.ui.task.task_template.TemplateTaskFragment;
import com.example.workflow_s.utils.CommonUtils;

import java.util.List;

/**
 * Workflow_S
 * Created by TinhPV on 2019-06-28
 * Copyright © 2019 TinhPV. All rights reserved
 **/


public class TemplateAdapter extends RecyclerView.Adapter<TemplateViewHolder> {

    // datasource for this RV
    List<Template> mTemplateList;
    RecyclerView mRecyclerView;

    public TemplateAdapter() {
    }

    public void setTemplateList(List<Template> templateList) {
        mTemplateList = templateList;
        notifyDataSetChanged();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mRecyclerView = recyclerView;
    }

    @NonNull
    @Override
    public TemplateViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, final int i) {
        Context context = viewGroup.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        int layoutId = R.layout.recyclerview_item_template;
        View view = layoutInflater.inflate(layoutId, viewGroup, false);
        TemplateViewHolder viewHolder = new TemplateViewHolder(view);

        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int index = mRecyclerView.getChildLayoutPosition(v);

                String templateId = String.valueOf(mTemplateList.get(index).getId());
                String templateName = mTemplateList.get(index).getName();
                String templateDescription = mTemplateList.get(index).getDescription();

                Bundle args = new Bundle();
                args.putString("templateId", templateId);
                args.putString("templateName", templateName);
                args.putString("templateDescription", templateDescription);

                CommonUtils.replaceFragments(viewGroup.getContext(), TemplateTaskFragment.class, args);
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TemplateViewHolder templateViewHolder, int i) {
        templateViewHolder.mTemplateName.setText(mTemplateList.get(i).getName());
        templateViewHolder.mTemplateCategory.setText(mTemplateList.get(i).getCategory());
    }

    @Override
    public int getItemCount() {
        if (null == mTemplateList) {
            return 0;
        } else {
            return mTemplateList.size();
        }
    }
}
