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
import com.example.workflow_s.model.Checklist;
import com.example.workflow_s.ui.task.task_checklist.ChecklistTaskFragment;
import com.example.workflow_s.utils.CommonUtils;

import java.util.List;

public class TodayActivitiesAdapter extends RecyclerView.Adapter<TodayActivitiesAdapter.TodayActivitiesViewHolder> {

    //datasource for recyclerview
    private List<Checklist> mChecklists;
    private List<String> mDueTime;
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
                args.putString("checklistId", String.valueOf(mChecklists.get(index).getId()));
                CommonUtils.replaceFragments(v.getContext(), ChecklistTaskFragment.class, args);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TodayActivitiesViewHolder todayActivitiesViewHolder, int i) {
        todayActivitiesViewHolder.mTextView.setText(mChecklists.get(i).getName());
        todayActivitiesViewHolder.mTextViewTime.setText(mDueTime.get(i));
    }

    @Override
    public int getItemCount() {
        if (mChecklists == null) {
            return 0;
        }
        return mChecklists.size();
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

    public void setmChecklists(List<Checklist> checklists, List<String> dueTime) {
        mChecklists = checklists;
        mDueTime = dueTime;
        this.notifyDataSetChanged();
    }

}
