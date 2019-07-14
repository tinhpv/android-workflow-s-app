package com.example.workflow_s.ui.notification.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.workflow_s.R;
import com.example.workflow_s.model.Comment;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {

    private List<Comment> mComments;
    private RecyclerView mRecyclerView;

    public void setComments(List<Comment> commentList) {
        mComments = commentList;
        notifyDataSetChanged();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mRecyclerView = recyclerView;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        int layoutId = R.layout.recyclerview_item_notification;
        View view = layoutInflater.inflate(layoutId, viewGroup, false);
        NotificationViewHolder viewHolder = new NotificationViewHolder(view);
        return viewHolder;
    }

    //FIXME set fake data
    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder notificationViewHolder, int i) {
        String urlImg = mComments.get(i).getUserImage();
        if (urlImg == null) {
            notificationViewHolder.mCircleImageView.setImageDrawable(ContextCompat.getDrawable(notificationViewHolder.view.getContext(),R.drawable.default_avatar));
        } else {
            Glide.with(notificationViewHolder.view.getContext()).load(urlImg).into(notificationViewHolder.mCircleImageView);
        }
        notificationViewHolder.mTextViewAvatar.setText(mComments.get(i).getUsername() + " have commented in your task " + mComments.get(i).getTaskName());
    }

    @Override
    public int getItemCount() {
        if (mComments == null) {
            return 0;
        }
        return mComments.size();
    }

    //viewholder
    public class NotificationViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView mCircleImageView;
        private TextView mTextViewAvatar;
        private View view;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            mCircleImageView = itemView.findViewById(R.id.img_user_avatar_notification);
            mTextViewAvatar = itemView.findViewById(R.id.tv_notification);
        }
    }

}
