package com.example.workflow_s.ui.task.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.workflow_s.R;
import com.example.workflow_s.model.Task;
import com.example.workflow_s.ui.taskdetail.TaskDetailActivity;
import com.example.workflow_s.ui.taskdetail.TaskDetailFragment;
import com.example.workflow_s.utils.CommonUtils;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    // list task
    private List<Task> mTaskList;

    // viewholder
    public class TaskViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout mTaskItem;
        private TextView mTextView;
        public View view;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            mTaskItem = itemView.findViewById(R.id.task_item);
            mTextView = itemView.findViewById(R.id.txt_task_name);
        }
    }

    public TaskAdapter() {}

    public void setTaskList(List<Task> taskList) {
        mTaskList = taskList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, final int i) {
        Context context = viewGroup.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        int layoutId = R.layout.recyclerview_item_task;
        View view = layoutInflater.inflate(layoutId, viewGroup, false);
        final TaskViewHolder viewHolder = new TaskViewHolder(view);

        //task item click
        viewHolder.mTaskItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonUtils.replaceFragments(viewGroup.getContext(), TaskDetailFragment.class, "taskId", String.valueOf(mTaskList.get(i).getId()));
            }
        });
        return viewHolder;
    }



    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder taskViewHolder, int i) {
        taskViewHolder.mTextView.setText(mTaskList.get(i).getName());
    }


    @Override
    public int getItemCount() {
        if (mTaskList == null) {
            return 0;
        } else {
            return mTaskList.size();
        } // end if
    }
}
