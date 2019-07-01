package com.example.workflow_s.ui.checklist.adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.workflow_s.R;
import com.example.workflow_s.model.Checklist;
import com.example.workflow_s.ui.checklist.viewholder.ChecklistProgressViewHolder;

import java.util.List;

/**
 * Workflow_S
 * Created by TinhPV on 2019-06-30
 * Copyright © 2019 TinhPV. All rights reserved
 **/


public class AccomplishedChecklistAdapter extends RecyclerView.Adapter<ChecklistProgressViewHolder> {

    private List<Checklist> mChecklists;

    public void setChecklists(List<Checklist> checklists) {
        mChecklists = checklists;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ChecklistProgressViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        int layoutId = R.layout.recyclerview_item_checklist_progress;
        View view = layoutInflater.inflate(layoutId, viewGroup, false);
        ChecklistProgressViewHolder viewHolder = new ChecklistProgressViewHolder(view);
        return viewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull ChecklistProgressViewHolder checklistProgressViewHolder, int i) {
        checklistProgressViewHolder.mChecklistName.setText(mChecklists.get(i).getName());
        checklistProgressViewHolder.mChecklistTaskProgress.setText(mChecklists.get(i).getDoneTask() + "/" + mChecklists.get(i).getTotalTask());
        int doneTask = mChecklists.get(i).getDoneTask();
        int totalTask = mChecklists.get(i).getTotalTask();

        if (totalTask == 0) {
            checklistProgressViewHolder.mProgressChecklistBar.setProgress(0, true);
        } else {
            int progress = (int) ((doneTask / totalTask * 1.0) * 100);
            checklistProgressViewHolder.mProgressChecklistBar.setProgress(progress, true);
        } // end if
    }

    @Override
    public int getItemCount() {
        if (null == mChecklists) {
            return 0;
        } else {
            return mChecklists.size();
        } // end if
    }
}
