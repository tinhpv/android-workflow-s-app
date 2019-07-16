package com.example.workflow_s.ui.task.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.workflow_s.R;
import com.example.workflow_s.model.ChecklistMember;
import com.example.workflow_s.model.Task;
import com.example.workflow_s.model.TaskMember;
import com.example.workflow_s.model.User;
import com.example.workflow_s.ui.taskdetail.checklist.ChecklistTaskDetailFragment;
import com.example.workflow_s.ui.taskdetail.template.TemplateTaskDetailFragment;
import com.example.workflow_s.utils.CommonUtils;
import com.example.workflow_s.utils.SharedPreferenceUtils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ChecklistTaskAdapter extends RecyclerView.Adapter<ChecklistTaskAdapter.TaskViewHolder> {


    CheckboxListener listener;
    private RecyclerView mRecyclerView;
    private ArrayList<Task> mTaskList;
    private Dialog errorDialog;
    private ArrayList<ChecklistMember> mChecklistMembers;
    private int checklistId;
    private String userId;

    public void setChecklistId(int checklistId) {
        this.checklistId = checklistId;
    }


    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mRecyclerView = recyclerView;
    }

    public interface CheckboxListener {
        void onEventCheckBox(Boolean isSelected, int taskId);
    }


    // viewholder
    public class TaskViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout mTaskItem;
        private CheckBox mCheckBox;
        private TextView mTextView;
        public View view;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            mTaskItem = itemView.findViewById(R.id.task_item);
            mTextView = itemView.findViewById(R.id.txt_task_name);
            mCheckBox = itemView.findViewById(R.id.cb_complete_task);
        }
    }

    public ChecklistTaskAdapter(CheckboxListener listener) {
        this.listener = listener;
    }

    public void setTaskList(ArrayList<Task> taskList) {
        mTaskList = taskList;
        notifyDataSetChanged();
    }

    public void setChecklistMembers(ArrayList<ChecklistMember> checklistMembers) {
        mChecklistMembers = checklistMembers;
    }


    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, final int i) {
        Context context = viewGroup.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        int layoutId = R.layout.recyclerview_item_task;
        final View view = layoutInflater.inflate(layoutId, viewGroup, false);
        final TaskViewHolder viewHolder = new TaskViewHolder(view);

        //create dialog
        errorDialog = new Dialog(context);
        errorDialog.setContentView(R.layout.dialog_error_task);
        errorDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Button btnOk = errorDialog.findViewById(R.id.btn_ok);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                errorDialog.cancel();
            }
        });

        userId = SharedPreferenceUtils.retrieveData(viewGroup.getContext(), viewGroup.getContext().getString(R.string.pref_userId));


                //task item click
        viewHolder.mTaskItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int index = mRecyclerView.getChildLayoutPosition(v);
                Bundle args = new Bundle();

                args.putString("taskId", String.valueOf(mTaskList.get(index).getId()));
                args.putString("taskName", mTaskList.get(index).getName());
                args.putInt("location_activity", 2);
                args.putInt("checklistId", checklistId);

                boolean flag = false;

                for (ChecklistMember checklistMember : mChecklistMembers) {
                    if (checklistMember.getUserId().equals(userId)) {
                        flag = true;
                    }
                }

                if (flag) {
                    CommonUtils.replaceFragments(viewGroup.getContext(), ChecklistTaskDetailFragment.class, args, true);
                } else {
                    errorDialog.show();
                }

            }
        });
        return viewHolder;
    }



    @Override
    public void onBindViewHolder(@NonNull final TaskViewHolder taskViewHolder, int i) {
        taskViewHolder.mTextView.setText(mTaskList.get(i).getName());

        if (mTaskList.get(i).getTaskStatus().equals("Done")) {
            taskViewHolder.mCheckBox.setChecked(true);
            taskViewHolder.mTextView.setPaintFlags(taskViewHolder.mTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            taskViewHolder.mCheckBox.setChecked(false);
        }

        taskViewHolder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    taskViewHolder.mTextView.setPaintFlags(taskViewHolder.mTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                } else {
                    taskViewHolder.mTextView.setPaintFlags(taskViewHolder.mTextView.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
                }

                listener.onEventCheckBox(isChecked, mTaskList.get(taskViewHolder.getAdapterPosition()).getId());

            }
        });
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
