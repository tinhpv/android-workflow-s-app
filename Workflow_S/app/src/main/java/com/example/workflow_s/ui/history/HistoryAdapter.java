package com.example.workflow_s.ui.history;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.workflow_s.R;
import com.example.workflow_s.model.Notification;

import java.nio.file.Path;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Workflow_S
 * Created by TinhPV on 2019-07-25
 * Copyright © 2019 TinhPV. All rights reserved
 **/


public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    private List<Notification> activityList;
    private Context mContext;

    public void setActivityList(List<Notification> activityList) {
        this.activityList = activityList;
        notifyDataSetChanged();
    }

    public HistoryAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.recyclerview_item_history, parent, false);
        HistoryViewHolder viewHolder = new HistoryViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        Notification activity = activityList.get(position);
        Glide.with(holder.itemView.getContext()).load(activity.getImageUrl()).into(holder.imgUser);
        holder.activity.setText(activity.getText());
    }

    @Override
    public int getItemCount() {
        if (null == activityList) {
            return 0;
        } else {
            return activityList.size();
        }
    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView imgUser;
        private TextView activity;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            imgUser = itemView.findViewById(R.id.img_user_avatar);
            activity = itemView.findViewById(R.id.tv_activity);
        }
    }
}
