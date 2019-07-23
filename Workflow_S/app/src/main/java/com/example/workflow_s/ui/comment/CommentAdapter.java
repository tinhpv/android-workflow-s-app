package com.example.workflow_s.ui.comment;

import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.workflow_s.R;
import com.example.workflow_s.model.Comment;
import com.example.workflow_s.ui.home.adapter.ChecklistProgressAdapter;
import com.example.workflow_s.ui.task.task_checklist.ChecklistTaskFragment;
import com.example.workflow_s.utils.CommonUtils;

import java.util.List;

/**
 * Workflow_S
 * Created by TinhPV on 2019-07-23
 * Copyright © 2019 TinhPV. All rights reserved
 **/


public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    private List<Comment> mCommentList;
    private Context mContext;

    public void setCommentList(List<Comment> commentList) {
        mCommentList = commentList;
        notifyDataSetChanged();
    }

    public CommentAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.recyclerview_item_comment, parent, false);
        CommentViewHolder viewHolder = new CommentViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        Comment comment = mCommentList.get(position);

        String profileUrlString = comment.getUserImage();
        if (profileUrlString ==  null) {
            holder.imgUser.setImageDrawable(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.default_avatar));
        } else {
            Glide.with(holder.itemView.getContext()).load(profileUrlString).into(holder.imgUser);
        }

        holder.commentContent.setText(comment.getComment1());
        holder.userDisplayName.setText(comment.getUsername());
    }

    @Override
    public int getItemCount() {
        if (null == mCommentList) {
            return 0;
        } else {
            return mCommentList.size();
        }
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgUser;
        private TextView userDisplayName, dateCommented, commentContent;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            imgUser = itemView.findViewById(R.id.img_user_commented);
            userDisplayName = itemView.findViewById(R.id.tv_user_display_name);
            dateCommented = itemView.findViewById(R.id.tv_date_time_comment);
            commentContent = itemView.findViewById(R.id.tv_comment_content);
        }
    }
}
