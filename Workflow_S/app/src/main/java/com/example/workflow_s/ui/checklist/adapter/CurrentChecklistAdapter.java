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
import android.widget.Filter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.workflow_s.R;
import com.example.workflow_s.model.Checklist;
import com.example.workflow_s.model.ChecklistMember;
import com.example.workflow_s.ui.task.task_checklist.ChecklistTaskFragment;
import com.example.workflow_s.utils.CommonUtils;
import com.example.workflow_s.utils.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CurrentChecklistAdapter extends RecyclerView.Adapter<CurrentChecklistAdapter.CurrentChecklistViewHolder> {

    //datasource for recyclerview
    private List<Checklist> mChecklists;
    private List<Checklist> mChecklistsFull;
    private RecyclerView mRecyclerView;

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mRecyclerView =recyclerView;
    }

    @NonNull
    @Override
    public CurrentChecklistViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.recyclerview_item_checklist_progress, viewGroup, false);
        CurrentChecklistViewHolder viewHolder = new CurrentChecklistViewHolder(view);

        //checklist item click
        viewHolder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = mRecyclerView.getChildLayoutPosition(v);
                Bundle args = new Bundle();
                args.putString("checklistId", String.valueOf(mChecklists.get(index).getId()));
                args.putInt("location", 1);
                CommonUtils.replaceFragments(v.getContext(), ChecklistTaskFragment.class, args);
            }
        });
        return viewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull CurrentChecklistViewHolder currentChecklistViewHolder, int i) {
        currentChecklistViewHolder.mChecklistName.setText(mChecklists.get(i).getName());
        currentChecklistViewHolder.mChecklistProgress.setText(mChecklists.get(i).getDoneTask() + "/" + mChecklists.get(i).getTotalTask());
        currentChecklistViewHolder.mTemplateName.setText(mChecklists.get(i).getTemplateName());
        currentChecklistViewHolder.mDueTime.setText(getDueTimeOfChecklist(i));
        List<ChecklistMember> checklistMembers = mChecklists.get(i).getChecklistMembers();
        if (checklistMembers != null) {
            int numberMember = checklistMembers.size();
            currentChecklistViewHolder.mMemberNumber.setText(String.valueOf(numberMember));
        }
        int doneTask = mChecklists.get(i).getDoneTask();
        int totalTask = mChecklists.get(i).getTotalTask();

        if (totalTask == 0) {
            currentChecklistViewHolder.progressBar.setProgress(0, true);
        } else {
            int progress = (int) ((doneTask / (totalTask * 1.0)) * 100);
            currentChecklistViewHolder.progressBar.setProgress(progress, true);
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
                if (Integer.parseInt(time.split("h")[0]) == 0) {
                    time = String.format("%dm",
                            TimeUnit.MILLISECONDS.toMinutes(totalTime));
                    if (Integer.parseInt(time.split("m")[0]) <= 0) {
                        time = "expired";
                    }
                }  else if (Integer.parseInt(time.split("h")[0]) <= 0){
                    time = "expired";
                } else {
                    Date upcomingDate = DateUtils.parseDate(dateSelected);
                    time = upcomingDate.toString().split(" ")[1] + " " + upcomingDate.toString().split(" ")[2];
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return  time;
    }

    @Override
    public int getItemCount() {
        if (mChecklists == null) {
            return 0;
        }
        return mChecklists.size();
    }

    public Filter getFilter() {
        return searchFilter;
    }

    private Filter searchFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Checklist> filterList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filterList.addAll(mChecklistsFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Checklist item : mChecklistsFull) {
                    if (item.getName().toLowerCase().contains(filterPattern)) {
                        filterList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filterList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mChecklists.clear();
            mChecklists.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };



    //viewholder
    public class CurrentChecklistViewHolder extends RecyclerView.ViewHolder {

        private TextView mChecklistName, mChecklistProgress, mMemberNumber, mTemplateName, mDueTime;
        private LinearLayout item;
        private ProgressBar progressBar;

        public CurrentChecklistViewHolder(@NonNull View itemView) {
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

    public CurrentChecklistAdapter() {}

    public void setChecklists(List<Checklist> checklists) {
        mChecklists = checklists;
        mChecklistsFull = new ArrayList<>(checklists);
        this.notifyDataSetChanged();
    }
}
