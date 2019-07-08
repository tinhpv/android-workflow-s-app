package com.example.workflow_s.ui.activity.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.workflow_s.R;
import com.example.workflow_s.model.Task;

import java.util.List;

public class TodayActivitiesAdapter extends RecyclerView.Adapter<TodayActivitiesAdapter.TodayActivitiesViewHolder> {

    //datasource for recyclerview
    private List<Task> mTasks;


    public void setTasks(List<Task> tasks) {
        mTasks = tasks;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TodayActivitiesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        int layoutId = R.layout.recyclerview_item_today_task;
        View view = layoutInflater.inflate(layoutId, viewGroup, false);
        TodayActivitiesViewHolder viewHolder = new TodayActivitiesViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TodayActivitiesViewHolder todayActivitiesViewHolder, int i) {
        todayActivitiesViewHolder.mTextView.setText("Record today");
    }

    @Override
    public int getItemCount() {
        if (mTasks == null) {
            return 0;
        } else {
            return 6;
        }
    }

    //viewholder
    public class TodayActivitiesViewHolder extends RecyclerView.ViewHolder {

        private TextView mTextView;

        public TodayActivitiesViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.tv_today_task_name);
        }
    }


}
