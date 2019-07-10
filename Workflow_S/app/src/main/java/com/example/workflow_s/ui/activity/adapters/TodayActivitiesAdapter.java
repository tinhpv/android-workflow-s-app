package com.example.workflow_s.ui.activity.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.workflow_s.R;
import com.example.workflow_s.model.Task;
import com.example.workflow_s.ui.taskdetail.checklist.ChecklistTaskDetailFragment;
import com.example.workflow_s.ui.taskdetail.template.TemplateTaskDetailFragment;
import com.example.workflow_s.utils.CommonUtils;

import java.util.List;

public class TodayActivitiesAdapter extends RecyclerView.Adapter<TodayActivitiesAdapter.TodayActivitiesViewHolder> {

    //datasource for recyclerview

    private List<Task> mTasks;
    private RecyclerView mRecyclerView;

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mRecyclerView = recyclerView;
    }

    @NonNull
    @Override
    public TodayActivitiesViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        int layoutId = R.layout.recyclerview_item_today_task;
        View view = layoutInflater.inflate(layoutId, viewGroup, false);
        TodayActivitiesViewHolder viewHolder = new TodayActivitiesViewHolder(view);

        //activities item click
        viewHolder.mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = mRecyclerView.getChildLayoutPosition(v);
                Bundle args = new Bundle();
                args.putString("checklistId", String.valueOf(mTasks.get(index).getId()));
                args.putString("taskName", mTasks.get(index).getName());
                CommonUtils.replaceFragments(viewGroup.getContext(), ChecklistTaskDetailFragment.class, args);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TodayActivitiesViewHolder todayActivitiesViewHolder, int i) {
        todayActivitiesViewHolder.mTextView.setText(mTasks.get(i).getName());
        todayActivitiesViewHolder.mTextViewTime.setText(mTasks.get(i).getDueTime());
    }

    @Override
    public int getItemCount() {
        if (mTasks == null) {
            return 0;
        }
        return mTasks.size();
    }

    //viewholder
    public class TodayActivitiesViewHolder extends RecyclerView.ViewHolder {

        private TextView mTextView, mTextViewTime;

        public TodayActivitiesViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.tv_today_activity_name);
            mTextViewTime = itemView.findViewById(R.id.tv_due_time);
        }
    }

    public TodayActivitiesAdapter() {}

    public void setTasks(List<Task> tasks) {
        mTasks = tasks;
        this.notifyDataSetChanged();
    }

}
