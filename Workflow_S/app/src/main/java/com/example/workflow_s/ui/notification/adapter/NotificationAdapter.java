package com.example.workflow_s.ui.notification.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.workflow_s.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {

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

    }

    @Override
    public int getItemCount() {
        return 6;
    }

    //viewholder
    public class NotificationViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView mCircleImageView;
        private TextView mTextViewAvatar, mTextViewTime;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            mCircleImageView = itemView.findViewById(R.id.img_user_avatar_notification);
            mTextViewAvatar = itemView.findViewById(R.id.tv_notification);
            mTextViewTime = itemView.findViewById(R.id.tv_time_notification);
        }
    }

}
