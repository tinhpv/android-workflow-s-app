package com.example.workflow_s.ui.home.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workflow_s.R;
import com.example.workflow_s.model.Checklist;
import com.example.workflow_s.ui.task.task_checklist.ChecklistTaskFragment;
import com.example.workflow_s.utils.CommonUtils;
import com.example.workflow_s.utils.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import antonkozyriatskyi.circularprogressindicator.CircularProgressIndicator;

/**
 * Workflow_S
 * Created by TinhPV on 2019-06-13
 * Copyright © 2019 TinhPV. All rights reserved
 **/


public class ChecklistProgressAdapter extends RecyclerView.Adapter<ChecklistProgressAdapter.ChecklistProgressViewHolder> {

    MenuListener mMenuListener;


    public interface MenuListener {
        void onClickMenu(int checklistId, String checklistName, String checklistUserId, String action, int position);
    }

    // Constants
    private final int MAX_ITEM_NUMBER = 4;

    // DataSource for RecyclerView
    private List<Checklist> mChecklists;
    private RecyclerView mRecyclerView;
    private Context mContext;

    private int selectedChecklistId, selectedItemPosition;
    private String selectedChecklistName, selectedChecklistUserId;

    public ChecklistProgressAdapter(MenuListener menuListener, Context context) {
        this.mContext = context;
        this.mMenuListener = menuListener;
    }


    @NonNull
    @Override
    public ChecklistProgressViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.recyclerview_item_checklist_progress, viewGroup, false);
        final ChecklistProgressViewHolder viewHolder = new ChecklistProgressViewHolder(view);


        final Dialog checklistMenuDialog = new Dialog(context);
        checklistMenuDialog.setContentView(R.layout.dialog_checklist_option);
        checklistMenuDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button renameButton = checklistMenuDialog.findViewById(R.id.bt_rename);
        Button deleteButton = checklistMenuDialog.findViewById(R.id.bt_delete);
        renameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMenuListener.onClickMenu(selectedChecklistId, selectedChecklistName, selectedChecklistUserId, "rename", selectedItemPosition);
                checklistMenuDialog.dismiss();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMenuListener.onClickMenu(selectedChecklistId, selectedChecklistName, selectedChecklistUserId, "delete", selectedItemPosition);
                checklistMenuDialog.dismiss();
            }
        });


        viewHolder.item.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                checklistMenuDialog.show();
                int index = viewHolder.getAdapterPosition();
                selectedChecklistId = mChecklists.get(index).getId();
                selectedChecklistName = mChecklists.get(index).getName();
                selectedChecklistUserId = mChecklists.get(index).getUserId();
                selectedItemPosition = index;
                return true;
            }
        });

        viewHolder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = viewHolder.getAdapterPosition();
                String checklistId = String.valueOf(mChecklists.get(index).getId());
                Bundle args = new Bundle();
                args.putString("checklistId", checklistId);
                args.putInt("location", 1);
                //args.putSerializable("listMember", mChecklists.get(index).getChecklistMembers());
                CommonUtils.replaceFragments(viewGroup.getContext(), ChecklistTaskFragment.class, args, true);
            }
        });
        return viewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull ChecklistProgressViewHolder checklistProgressViewHolder, int i) {
        checklistProgressViewHolder.mChecklistName.setText(mChecklists.get(i).getName());
//        checklistProgressViewHolder.mChecklistProgress.setText(mChecklists.get(i).getDoneTask() + "/" + mChecklists.get(i).getTotalTask());
        checklistProgressViewHolder.mTemplateName.setText(mChecklists.get(i).getTemplateName());

        String timeCreated = mChecklists.get(i).getTimeCreated().split("T")[0];
        Date dateCreate = DateUtils.parseDate(timeCreated);
        String monthString = (String) DateFormat.format("MMM", dateCreate);
        String day = (String) DateFormat.format("dd", dateCreate);
        checklistProgressViewHolder.mDayCreated.setText(day);
        checklistProgressViewHolder.mMonthCreated.setText(monthString);

        String dueTime = getDueTimeOfChecklist(i);
        checklistProgressViewHolder.mDueTime.setText(dueTime);

        if (dueTime.equals("expired")) {
            checklistProgressViewHolder.mDueTime.setBackground(mContext.getResources().getDrawable(R.drawable.container_radius_red));
            checklistProgressViewHolder.mDueTime.setTextColor(Color.parseColor("#FFFFFF"));
        }

        int doneTask = mChecklists.get(i).getDoneTask();
        int totalTask = mChecklists.get(i).getTotalTask();

        if (totalTask == 0) {
            checklistProgressViewHolder.progressBar.setProgress(0, 100);
        } else {
            int progress = (int) ((doneTask / (totalTask * 1.0)) * 100);
            checklistProgressViewHolder.progressBar.setProgress(progress, 100);
        }
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

        private TextView mChecklistName, mTemplateName, mDueTime, mDayCreated, mMonthCreated;
        private ConstraintLayout item;
        private CircularProgressIndicator progressBar;

        public ChecklistProgressViewHolder(@NonNull View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.item_checklist_progress);
            mChecklistName = itemView.findViewById(R.id.tv_checklist_name);
            mTemplateName = itemView.findViewById(R.id.tv_template_name);
            progressBar = itemView.findViewById(R.id.pb_checklist_home);
            progressBar.setMaxProgress(100);
            mDueTime = itemView.findViewById(R.id.tv_due_time_checklist);
            mDayCreated = item.findViewById(R.id.tv_day_created);
            mMonthCreated = item.findViewById(R.id.tv_month_created);
        }
    }
}
