package com.example.workflow_s.ui.task.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.workflow_s.R;
import com.example.workflow_s.model.Task;
import com.example.workflow_s.ui.task.task_template.TemplateTaskFragment;
import com.example.workflow_s.ui.taskdetail.checklist.ChecklistTaskDetailFragment;
import com.example.workflow_s.ui.taskdetail.template.TemplateTaskDetailFragment;
import com.example.workflow_s.utils.CommonUtils;

import java.util.List;

/**
 * Workflow_S
 * Created by TinhPV on 2019-07-03
 * Copyright © 2019 TinhPV. All rights reserved
 **/


public class TemplateTaskAdapter extends RecyclerView.Adapter<TemplateTaskAdapter.TaskViewHolder> {

    TemplateTaskAdapter.CheckboxListener listener;

    public interface CheckboxListener {
        void onEventCheckBox(Boolean isSelected);
    }

    private List<Task> mTemplateTaskList;
    private TemplateTaskFragment mTemplateTaskFragment;
    private RecyclerView mRecyclerView;

    public TemplateTaskAdapter(TemplateTaskFragment templateTaskFragment, CheckboxListener listener) {
        mTemplateTaskFragment = templateTaskFragment;
        this.listener = listener;
    }

    public void setTemplateTaskList(List<Task> templateTaskList) {
        mTemplateTaskList = templateTaskList;
        notifyDataSetChanged();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mRecyclerView = recyclerView;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, final int i) {
        Context context = viewGroup.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        int layoutId = R.layout.recyclerview_item_task;
        View view = layoutInflater.inflate(layoutId, viewGroup, false);
        TaskViewHolder viewHolder = new TaskViewHolder(view);

        viewHolder.mTaskItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = mRecyclerView.getChildLayoutPosition(v);
                Bundle args = new Bundle();
                args.putString("taskId", String.valueOf(mTemplateTaskList.get(index).getId()));
                args.putString("taskName", mTemplateTaskList.get(index).getName());
                CommonUtils.replaceFragments(viewGroup.getContext(), TemplateTaskDetailFragment.class, args);
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder taskViewHolder, int i) {
        taskViewHolder.mTextView.setText(mTemplateTaskList.get(i).getName());
        taskViewHolder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                buttonView.setChecked(false);
                listener.onEventCheckBox(isChecked);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mTemplateTaskList == null) {
            return 0;
        } else {
            return mTemplateTaskList.size();
        } // end if
    }

    // viewholder
    public class TaskViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout mTaskItem;
        private TextView mTextView;
        private CheckBox mCheckBox;
        public View view;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            mTaskItem = itemView.findViewById(R.id.task_item);
            mTextView = itemView.findViewById(R.id.txt_task_name);
            mCheckBox = itemView.findViewById(R.id.cb_complete_task);
        }
    }
}
