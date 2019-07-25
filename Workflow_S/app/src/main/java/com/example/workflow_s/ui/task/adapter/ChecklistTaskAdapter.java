package com.example.workflow_s.ui.task.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.workflow_s.R;
import com.example.workflow_s.model.ChecklistMember;
import com.example.workflow_s.model.Task;
import com.example.workflow_s.model.User;
import com.example.workflow_s.ui.taskdetail.checklist.ChecklistTaskDetailFragment;
import com.example.workflow_s.utils.CommonUtils;
import com.example.workflow_s.utils.SharedPreferenceUtils;

import java.util.Collections;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChecklistTaskAdapter extends RecyclerView.Adapter<ChecklistTaskAdapter.TaskViewHolder> {

    public interface CheckboxListener {
        void onEventCheckBox(Boolean isSelected, int taskId, int position);
    }

    public interface MenuListener {
        void onClickMenu(int taskId, String taskName, String action);
    }

    public interface DragDropListener {
        void onChangePriority(List<Task> taskList);
    }


    CheckboxListener listener;
    MenuListener mMenuListener;
    DragDropListener mDDListener;
    Context mContext;


    private RecyclerView mRecyclerView;
    private List<Task> mTaskList;
    private List<User> mUserList;
    private Dialog errorDialog;
    private List<ChecklistMember> mChecklistMembers;
    private int checklistId;
    private String userId, checklistUserId, checklistStatus;


    public void setChecklistId(int checklistId) {
        this.checklistId = checklistId;
    }

    public void setChecklistStatus(String checklistStatus) {
        this.checklistStatus = checklistStatus;
    }

    public void setChecklistUserId(String checklistUserId) {
        this.checklistUserId = checklistUserId;
    }

    public void setChecklistMembers(List<ChecklistMember> checklistMembers) {
        mChecklistMembers = checklistMembers;
    }

    public void setTaskList(List<Task> taskList) {
        mTaskList = taskList;
        notifyDataSetChanged();
    }

    public void setUserList(List<User> userList) {
        mUserList = userList;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mRecyclerView = recyclerView;
    }

    // viewholder
    public class TaskViewHolder extends RecyclerView.ViewHolder {

        private ConstraintLayout mTaskItem;
        private LinearLayout taskUnavailable, taskAction;
        private CheckBox mCheckBox;
        private TextView mTextView, tvTaskAction;
        private ImageButton menuButton;
        private CircleImageView userAvatarAction;
        public View view;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            mTaskItem = itemView.findViewById(R.id.task_item);
            mTextView = itemView.findViewById(R.id.txt_task_name);
            mCheckBox = itemView.findViewById(R.id.cb_complete_task);
            menuButton = itemView.findViewById(R.id.bt_item_menu);
            taskUnavailable = itemView.findViewById(R.id.task_being_unavailable);
            tvTaskAction = itemView.findViewById(R.id.tv_task_action);
            userAvatarAction = itemView.findViewById(R.id.img_user_avatar);
            taskAction = itemView.findViewById(R.id.task_action_container);
        }
    }


    public ChecklistTaskAdapter(Context context, MenuListener menuListener, CheckboxListener checkboxListener, DragDropListener dragDropListener) {
        mContext = context;
        mMenuListener = menuListener;
        listener = checkboxListener;
        mDDListener = dragDropListener;
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

        // task item click
        viewHolder.mTaskItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int index = mRecyclerView.getChildLayoutPosition(v);
                Bundle args = new Bundle();

                args.putString("taskId", String.valueOf(mTaskList.get(index).getId()));
                args.putInt("location_activity", 2);
                args.putInt("checklistId", checklistId);
                args.putString("checklistUserId", checklistUserId);
                args.putString("checklistStatus", checklistStatus);

                boolean flag = false;

                for (ChecklistMember checklistMember : mChecklistMembers) {
                    if (checklistMember.getUserId().equals(userId)) {
                        flag = true;
                        break;
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

        taskViewHolder.mCheckBox.setTag(i);
        taskViewHolder.menuButton.setTag(i);

        final Task currentTask = mTaskList.get(i);

        if (currentTask.getTaskStatus().equals("Failed")) {
            taskViewHolder.taskUnavailable.setVisibility(View.VISIBLE);
            taskViewHolder.mCheckBox.setChecked(false);
            taskViewHolder.taskAction.setVisibility(View.VISIBLE);
            User user = getUserFromList(currentTask.getActionUser());
            if (user != null) {
                Glide.with(mContext).load(user.getAvatar()).into(taskViewHolder.userAvatarAction);
                taskViewHolder.tvTaskAction.setText(user.getName() + " skipped this task");
            }

        } else if (currentTask.getTaskStatus().equals("Done")) {
            taskViewHolder.mCheckBox.setChecked(true);
            taskViewHolder.mTextView.setPaintFlags(taskViewHolder.mTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            taskViewHolder.taskAction.setVisibility(View.VISIBLE);

            User user = getUserFromList(currentTask.getActionUser());
            if (user != null) {
                Glide.with(mContext).load(user.getAvatar()).into(taskViewHolder.userAvatarAction);
                taskViewHolder.tvTaskAction.setText(user.getName() + " completed this task");
            }

        } else {
            taskViewHolder.mCheckBox.setChecked(false);
            taskViewHolder.taskAction.setVisibility(View.GONE);
        }


        // checkbox
        if ((checklistStatus != null) && (!checklistStatus.equals("Done"))) {
            if (!currentTask.getTaskStatus().equals("Failed") && (!currentTask.getTaskStatus().equals("Done"))) {
                taskViewHolder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            taskViewHolder.mTextView.setPaintFlags(taskViewHolder.mTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        } else {
                            taskViewHolder.mTextView.setPaintFlags(taskViewHolder.mTextView.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
                        }

                        listener.onEventCheckBox(isChecked, mTaskList.get((Integer) taskViewHolder.mCheckBox.getTag()).getId(),
                                (Integer) taskViewHolder.mCheckBox.getTag());
                    }
                });
            }
        } else {
            taskViewHolder.mCheckBox.setEnabled(false);
        }



        // menu
        taskViewHolder.menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = (int) taskViewHolder.menuButton.getTag();
                final int taskId = mTaskList.get(index).getId();
                final String taskName = mTaskList.get(index).getName();


                //mMenuListener.onClickMenu(taskId, taskName);
                PopupMenu popup = new PopupMenu(mContext, v);
                if (currentTask.getTaskStatus().equals("Failed")) {
                    popup.inflate(R.menu.menu_task_unavailable);
                } else if (currentTask.getTaskStatus().equals("Done")) {
                    popup.inflate(R.menu.menu_task_alternative);
                } else {
                    popup.inflate(R.menu.item_popup_menu);
                }

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_rename:
                                mMenuListener.onClickMenu(taskId, taskName, mContext.getString(R.string.item_action_rename));
                                return true;
                            case R.id.action_skip:
                                mMenuListener.onClickMenu(taskId, taskName, mContext.getString(R.string.item_action_skip));
                                return true;
                            case R.id.action_reactivate:
                                mMenuListener.onClickMenu(taskId, taskName, mContext.getString(R.string.item_action_reactivate));
                            default: return false;
                        }
                    }
                });

                popup.show();
            }
        });
    }

    private User getUserFromList(String id) {
        for (User user : mUserList) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public int getItemCount() {
        if (mTaskList == null) {
            return 0;
        } else {
            return mTaskList.size();
        } // end if
    }

    //drag and drop
    public void onMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(mTaskList, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(mTaskList, i, i - 1);
            }
        }

        mTaskList.get(fromPosition).setPriority(fromPosition);
        mTaskList.get(toPosition).setPriority(toPosition);
        //mDDListener.onChangePriority(mTaskList);
        notifyItemMoved(fromPosition, toPosition);
    }

    public List<Task> getTaskList() {
        return  mTaskList;
    }
}
