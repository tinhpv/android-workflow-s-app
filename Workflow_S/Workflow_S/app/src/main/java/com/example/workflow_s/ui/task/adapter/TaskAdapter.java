package com.example.workflow_s.ui.task.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.workflow_s.R;
import com.example.workflow_s.model.Task;
import com.example.workflow_s.ui.taskdetail.TaskDetailActivity;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    //list task
    private List<Task> mTask;

    //viewholder
    public class TaskViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout mTaskItem;
        private TextView mTextView;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            mTaskItem = itemView.findViewById(R.id.task_item);
            mTextView = itemView.findViewById(R.id.txt_task_name);
        }
    }

    public TaskAdapter(List<Task> tasks) {mTask = tasks;}


    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {
        Context context = viewGroup.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        int layoutId = R.layout.recyclerview_item_task;
        View view = layoutInflater.inflate(layoutId, viewGroup, false);
        TaskViewHolder viewHolder = new TaskViewHolder(view);

        //task item click
        viewHolder.mTaskItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), TaskDetailActivity.class);
                intent.putExtra("taskId", mTask.get(i).getId());
                v.getContext().startActivity(intent);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder taskViewHolder, int i) {
        taskViewHolder.mTextView.setText(mTask.get(i).getName());
    }


    @Override
    public int getItemCount() {
        return mTask.size();
    }
}
