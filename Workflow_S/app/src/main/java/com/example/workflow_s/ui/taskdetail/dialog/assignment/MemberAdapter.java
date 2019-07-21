package com.example.workflow_s.ui.taskdetail.dialog.assignment;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.workflow_s.R;
import com.example.workflow_s.model.ChecklistMember;
import com.example.workflow_s.model.TaskMember;
import com.example.workflow_s.model.User;
import com.example.workflow_s.utils.SharedPreferenceUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Workflow_S
 * Created by TinhPV on 2019-07-06
 * Copyright © 2019 TinhPV. All rights reserved
 **/


public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.MemberViewHolder> {


    EventListener listener;

    public interface EventListener {
        void onEvent(String userId, Integer taskMemberId, boolean doAssign);
    }

    private List<User> mUserList;
    private ArrayList<TaskMember> mTaskMembers;
    private ArrayList<ChecklistMember> mChecklistMembers;
    private RecyclerView mRecyclerView;
    private String checklistUserId;
    private Context mContext;

    public void setChecklistUserId(String checklistUserId) {
        this.checklistUserId = checklistUserId;
    }

    public void setUserList(List<User> userList) {
        mUserList = userList;
    }

    public void setTaskMembers(ArrayList<TaskMember> taskMembers) {
        mTaskMembers = taskMembers;
        notifyDataSetChanged();
    }

    public void setChecklistMembers(ArrayList<ChecklistMember> checklistMembers) {
        mChecklistMembers = checklistMembers;
    }

    public MemberAdapter(EventListener listener, Context context) {
        this.listener = listener;
        this.mContext = context;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mRecyclerView = recyclerView;
    }

    @NonNull
    @Override
    public MemberViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        int layoutId = R.layout.recycler_item_user_assigning_detail;
        View view = layoutInflater.inflate(layoutId, viewGroup, false);
        MemberViewHolder viewHolder = new MemberViewHolder(view);
        return viewHolder;
    }


    private User getUser(String userId) {
        for (User user : mUserList) {
            if (user.getId().equals(userId)) {
                return user;
            }
        }
        return null;
    }

    private boolean isTaskMember(String userId) {
        for (TaskMember member : mTaskMembers) {
            if (member.getUserId().equals(userId)) {
                return true;
            }
        }
        return false;
    }

    private Integer taskMemberId(String userId) {
        for (TaskMember member : mTaskMembers) {
            if (member.getUserId().equals(userId)) {
                return member.getId();
            }
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MemberViewHolder memberViewHolder, final int i) {
        String userId = SharedPreferenceUtils.retrieveData(mContext, mContext.getString(R.string.pref_userId));
        User user = null;
        if (!userId.equals(checklistUserId)) {
            /* user is not owner of this checklist
            only show information about assigned user, not any buttons */
            user = getUser(mTaskMembers.get(i).getUserId());
            memberViewHolder.btUnassign.setVisibility(View.INVISIBLE);
            memberViewHolder.btAssign.setVisibility(View.INVISIBLE);

        } else {
            user = getUser(mChecklistMembers.get(i).getUserId());
            if (isTaskMember(user.getId())) {
                memberViewHolder.btAssign.setVisibility(View.INVISIBLE);
                if (!user.getId().equals(userId)) {
                    memberViewHolder.btUnassign.setVisibility(View.VISIBLE);
                    memberViewHolder.btUnassign.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // DONE - HANDLE UNASSIGN USER HERE
                            //int index = mRecyclerView.getChildLayoutPosition(v);
                            String userId = mChecklistMembers.get(i).getUserId();
                            int taskMemberId = taskMemberId(userId);
                            listener.onEvent(userId, taskMemberId, false);
                        }
                    });
                }
            } else {
                memberViewHolder.btAssign.setVisibility(View.VISIBLE);
                memberViewHolder.btUnassign.setVisibility(View.INVISIBLE);
                memberViewHolder.btAssign.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // DONE - HANDLE UNASSIGN USER HERE
                        //int index = mRecyclerView.getChildLayoutPosition(v);
                        listener.onEvent(mChecklistMembers.get(i).getUserId(), null, true);
                    }
                });
            } // end if
        }


        memberViewHolder.mMemberName.setText(user.getName());
        memberViewHolder.mEmail.setText(user.getEmail());
        String profileUrlString = user.getAvatar();
        if (profileUrlString ==  null) {
            memberViewHolder.mAvatar.setImageDrawable(ContextCompat.getDrawable(memberViewHolder.view.getContext(), R.drawable.default_avatar));
        } else {
            Glide.with(memberViewHolder.view.getContext()).load(profileUrlString).into(memberViewHolder.mAvatar);
        }

    }


    @Override
    public int getItemCount() {
        String userId = SharedPreferenceUtils.retrieveData(mContext, mContext.getString(R.string.pref_userId));
        if (!userId.equals(checklistUserId)) {
            if (null == mTaskMembers) {
                return 0;
            }
            return mTaskMembers.size();
        } else {
            if (null == mChecklistMembers) {
                return 0;
            }
            return mChecklistMembers.size();
        }
    }


    public class MemberViewHolder extends RecyclerView.ViewHolder {

        public TextView mMemberName;
        public TextView mEmail;
        public ImageView mAvatar;
        public ImageButton btAssign, btUnassign;
        public View view;

        public MemberViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            mMemberName = itemView.findViewById(R.id.tv_user_display_name);
            mEmail = itemView.findViewById(R.id.tv_user_email);
            mAvatar = itemView.findViewById(R.id.img_user_avatar);
            btUnassign = itemView.findViewById(R.id.bt_unassign_user);
            btAssign = itemView.findViewById(R.id.bt_assign_user);
        }
    }
}
