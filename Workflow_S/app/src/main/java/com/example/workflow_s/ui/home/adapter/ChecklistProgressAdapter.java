package com.example.workflow_s.ui.home.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.workflow_s.R;
import com.example.workflow_s.model.Checklist;
import com.example.workflow_s.model.ChecklistMember;
import com.example.workflow_s.ui.task.task_checklist.ChecklistTaskFragment;
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


public class ChecklistProgressAdapter extends RecyclerView.Adapter<ChecklistProgressAdapter.ChecklistProgressViewHolder> {

    EventListener listener;

    public interface EventListener {
        void onEvent(int deletedChecklistId, int position);
    }

    // Constants
    private final int MAX_ITEM_NUMBER = 4;

    // DataSource for RecyclerView
    private List<Checklist> mChecklists;
    private RecyclerView mRecyclerView;
    private Context mContext;

    public ChecklistProgressAdapter(EventListener listener, Context context) {
        this.listener = listener;
        this.mContext = context;
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
                args.putInt("location", 1);
                args.putSerializable("listMember", mChecklists.get(index).getChecklistMembers());
                CommonUtils.replaceFragments(viewGroup.getContext(), ChecklistTaskFragment.class, args, true);
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

        String dueTime = getDueTimeOfChecklist(i);
        checklistProgressViewHolder.mDueTime.setText(dueTime);
        if (dueTime.equals("expired")) {
            checklistProgressViewHolder.mDueTime.setBackground(mContext.getResources().getDrawable(R.drawable.container_radius_red));
            checklistProgressViewHolder.mDueTime.setTextColor(Color.parseColor("#FFFFFF"));
        }


        List<ChecklistMember> checklistMembers = mChecklists.get(i).getChecklistMembers();

        if (checklistMembers != null) {
            int numberMember = checklistMembers.size() + 1;
            checklistProgressViewHolder.mMemberNumber.setText(String.valueOf(numberMember));
        }
        int doneTask = mChecklists.get(i).getDoneTask();
        int totalTask = mChecklists.get(i).getTotalTask();

        if (totalTask == 0) {
            checklistProgressViewHolder.progressBar.setProgress(0, true);
        } else {
            int progress = (int) ((doneTask / (totalTask * 1.0)) * 100);
            checklistProgressViewHolder.progressBar.setProgress(progress, true);
        }

    }

    public void deleteItem(int position) {
        Checklist checklist = mChecklists.get(position);
        listener.onEvent(checklist.getId(), position);
    }

    private String getDueTimeOfChecklist(int index) {
        String time = "not set";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        if (mChecklists.get(index).getDueTime() != null) {
            String dateSelected = mChecklists.get(index).getDueTime().split("T")[0];
            String timeSelected = mChecklists.get(index).getDueTime().split("T")[1];
            Date currentTime = Calendar.getInstance().getTime();
            String dueTime = dateSelected + " " + timeSelected;
            try {
                Date overdue = sdf.parse(dueTime);
                long totalTime = overdue.getTime() - currentTime.getTime();
                time = String.format("%dh",
                        TimeUnit.MILLISECONDS.toHours(totalTime));
                if (Integer.parseInt(time.split("h")[0]) < 0) {
                    time = "expired";

                } else if (Integer.parseInt(time.split("h")[0]) == 0){
                    time = String.format("%dm",
                            TimeUnit.MILLISECONDS.toMinutes(totalTime));
                    if (Integer.parseInt(time.split("m")[0]) < 1) {
                        time = "expired";
                    }
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return  time;
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

        private TextView mChecklistName, mChecklistProgress, mMemberNumber, mTemplateName, mDueTime;
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
            mDueTime = itemView.findViewById(R.id.tv_due_time_checklist);
        }
    }
}
