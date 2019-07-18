package com.example.workflow_s.ui.organization.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.workflow_s.R;

/**
 * Workflow_S
 * Created by TinhPV on 2019-06-27
 * Copyright © 2019 TinhPV. All rights reserved
 **/

public class OrganizationMemberViewHolder extends RecyclerView.ViewHolder {

    public TextView mMemberName;
    public TextView mEmail;
    public ImageView mAvatar;
    public View view;

    public OrganizationMemberViewHolder(@NonNull View itemView) {
        super(itemView);
        view = itemView;
        mMemberName = itemView.findViewById(R.id.tv_user_display_name);
        mEmail = itemView.findViewById(R.id.tv_user_email);
        mAvatar = itemView.findViewById(R.id.img_user_avatar);
    }
}