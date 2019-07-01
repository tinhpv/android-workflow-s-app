package com.example.workflow_s.ui.organization.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.workflow_s.R;
import com.example.workflow_s.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Workflow_S
 * Created by TinhPV on 2019-06-27
 * Copyright © 2019 TinhPV. All rights reserved
 **/


public class OrganizationMemberAdapter extends RecyclerView.Adapter<OrganizationMemberViewHolder> {

    // DataSource for RecyclerView
    private List<User> mUserList;

    public OrganizationMemberAdapter() {}

    public void setUserList(List<User> userList) {
        mUserList = userList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OrganizationMemberViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        Context context = viewGroup.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        int layoutId = R.layout.recyclerview_item_org_member;
        View view = layoutInflater.inflate(layoutId, viewGroup, false);
        OrganizationMemberViewHolder viewHolder = new OrganizationMemberViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull OrganizationMemberViewHolder organizationMemberViewHolder, int i) {
        organizationMemberViewHolder.mMemberName.setText(mUserList.get(i).getName());
        organizationMemberViewHolder.mEmail.setText(mUserList.get(i).getEmail());

        String profileUrlString = mUserList.get(i).getAvatar();
        if (profileUrlString.length() == 0) {
            organizationMemberViewHolder.mAvatar.setImageDrawable(ContextCompat.getDrawable(organizationMemberViewHolder.view.getContext(), R.drawable.default_avatar));
        } else {
            Glide.with(organizationMemberViewHolder.view.getContext()).load(profileUrlString).into(organizationMemberViewHolder.mAvatar);
        }

    }

    @Override
    public int getItemCount() {
        if (mUserList == null) {
            return 0;
        } else {
            return mUserList.size();
        } // end if

    }


}
