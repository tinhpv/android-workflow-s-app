package com.example.workflow_s.ui.checklist.adapter;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.workflow_s.R;
import com.example.workflow_s.model.Checklist;
import com.example.workflow_s.model.ChecklistMember;
import com.example.workflow_s.ui.checklist.viewholder.ChecklistProgressViewHolder;
import com.example.workflow_s.ui.task.task_checklist.ChecklistTaskFragment;
import com.example.workflow_s.utils.CommonUtils;

import java.util.List;

/**
 * Workflow_S
 * Created by TinhPV on 2019-06-13
 * Copyright © 2019 TinhPV. All rights reserved
 **/


public class ChecklistProgressAdapter extends RecyclerView.Adapter<ChecklistProgressAdapter.ChecklistProgressViewHolder> {

    // Constants
    private final int MAX_ITEM_NUMBER = 4;

    // DataSource for RecyclerView
    private List<Checklist> mChecklists;
    private RecyclerView mRecyclerView;

    public ChecklistProgressAdapter() {
    }

    @NonNull
    @Override
    public ChecklistProgressViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.recyclerview_item_checklist_progress, viewGroup, false);
        ChecklistProgressViewHolder viewHolder = new ChecklistProgressViewHolder(view);

        viewHolder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = mRecyclerView.getChildLayoutPosition(v);
                String checklistId = String.valueOf(mChecklists.get(index).getId());
                Bundle args = new Bundle();
                args.putString("checklistId", checklistId);
                CommonUtils.replaceFragments(viewGroup.getContext(), ChecklistTaskFragment.class, args);
            }
        });
        return viewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull ChecklistProgressViewHolder checklistProgressViewHolder, int i) {
        checklistProgressViewHolder.mChecklistName.setText(mChecklists.get(i).getName());
        checklistProgressViewHolder.mChecklistProgress.setText(mChecklists.get(i).getDoneTask() + "/" + mChecklists.get(i).getTotalTask());
        checklistProgressViewHolder.mTemplateName.setText(mChecklists.get(i).getTemplateName());
        List<ChecklistMember> checklistMembers = mChecklists.get(i).getChecklistMembers();
        if (checklistMembers != null) {
            int numberMember = checklistMembers.size();
            checklistProgressViewHolder.mMemberNumber.setText(String.valueOf(numberMember));
        }
        int doneTask = mChecklists.get(i).getDoneTask();
        int totalTask = mChecklists.get(i).getTotalTask();

        if (totalTask == 0) {
            checklistProgressViewHolder.progressBar.setProgress(0, true);
        } else {
            int progress = (int) ((doneTask / totalTask * 1.0) * 100);
            checklistProgressViewHolder.progressBar.setProgress(progress, true);
        }
    }


    public void setChecklists(List<Checklist> checklists) {
        mChecklists = checklists;
        this.notifyDataSetChanged();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mRecyclerView = recyclerView;
    }


    @Override
    public int getItemCount() {
        // DONE - MODIFY SIZE LIST
        if (mChecklists == null) {
            return 0;
        } else {
            int numberOfItems = mChecklists.size();
            return numberOfItems > MAX_ITEM_NUMBER ? MAX_ITEM_NUMBER : numberOfItems;
        } // end if
    }

    public class ChecklistProgressViewHolder extends RecyclerView.ViewHolder {

        private TextView mChecklistName, mChecklistProgress, mMemberNumber, mTemplateName;
        private LinearLayout item;
        private ProgressBar progressBar;
        public ChecklistProgressViewHolder(@NonNull View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.item_checklist_progress);
            mChecklistName = itemView.findViewById(R.id.tv_checklist_name);
            mChecklistProgress = itemView.findViewById(R.id.tv_checklist_progress);
            mMemberNumber = itemView.findViewById(R.id.tv_people_number);
            mTemplateName = itemView.findViewById(R.id.tv_template_name);
            progressBar = itemView.findViewById(R.id.pb_checklist_home);
        }
    }
}
