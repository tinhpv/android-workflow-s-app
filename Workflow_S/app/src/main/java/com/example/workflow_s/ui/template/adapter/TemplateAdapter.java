package com.example.workflow_s.ui.template.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.workflow_s.R;
import com.example.workflow_s.model.Template;
import com.example.workflow_s.ui.organization.adapter.OrganizationMemberViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Workflow_S
 * Created by TinhPV on 2019-06-28
 * Copyright © 2019 TinhPV. All rights reserved
 **/


public class TemplateAdapter extends RecyclerView.Adapter<TemplateViewHolder> {

    // datasource for this RV
    List<Template> mTemplateList;

    public TemplateAdapter() {
        mTemplateList = new ArrayList<>();
        mTemplateList.add(new Template(1, "", "Student boarding", "abc", "123", 1, 1, "DEV"));
        mTemplateList.add(new Template(2, "", "Viet boarding", "abc", "123", 1, 1, "DEV"));
        mTemplateList.add(new Template(3, "", "Employee interview", "abc", "123", 1, 1, "DEV"));
    }

    public void setTemplateList(List<Template> templateList) {
        mTemplateList = templateList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TemplateViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        int layoutId = R.layout.recyclerview_item_template;
        View view = layoutInflater.inflate(layoutId, viewGroup, false);
        TemplateViewHolder viewHolder = new TemplateViewHolder(view);
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
