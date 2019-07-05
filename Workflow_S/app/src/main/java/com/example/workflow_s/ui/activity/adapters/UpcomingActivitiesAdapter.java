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

public class UpcomingActivitiesAdapter extends RecyclerView.Adapter<UpcomingActivitiesAdapter.UpcomingActivitiesViewHolder>{

    //datasource for recyclerview
    private List<Task> mTasks;

    public void setTasks(List<Task> tasks) {
        mTasks = tasks;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UpcomingActivitiesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        int layoutId = R.layout.recyclerview_item_upcoming_task;
        View view = layoutInflater.inflate(layoutId, viewGroup, false);
        UpcomingActivitiesViewHolder viewHolder = new UpcomingActivitiesViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UpcomingActivitiesViewHolder upcomingActivitiesViewHolder, int i) {
        upcomingActivitiesViewHolder.mTextView.setText("Recprd upcoming");
    }

    @Override
    public int getItemCount() {
        if (null == mTasks) {
            return 0;
        } else {
            return 7;
        }
    }

    //viewholder
    public class UpcomingActivitiesViewHolder extends RecyclerView.ViewHolder {

        private TextView mTextView;

        public UpcomingActivitiesViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.tv_upcoming_task_name);
        }
    }


}
