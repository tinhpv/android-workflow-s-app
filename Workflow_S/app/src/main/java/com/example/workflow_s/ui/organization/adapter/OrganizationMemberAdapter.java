package com.example.workflow_s.ui.organization.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.workflow_s.R;
import com.example.workflow_s.model.User;
import com.example.workflow_s.utils.SharedPreferenceUtils;

import java.util.List;

/**
 * Workflow_S
 * Created by TinhPV on 2019-06-27
 * Copyright © 2019 TinhPV. All rights reserved
 **/


public class OrganizationMemberAdapter extends RecyclerView.Adapter<OrganizationMemberViewHolder> {

    // DataSource for RecyclerView
    private List<User> mUserList;
    private Context mContext;

    public OrganizationMemberAdapter(Context context) {
        this.mContext = context;
    }

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
        if (profileUrlString ==  null) {
            organizationMemberViewHolder.mAvatar.setImageDrawable(ContextCompat.getDrawable(organizationMemberViewHolder.view.getContext(), R.drawable.default_avatar));
        } else {
            Glide.with(organizationMemberViewHolder.view.getContext()).load(profileUrlString).into(organizationMemberViewHolder.mAvatar);
        }

        if (mUserList.get(i).getRole().equals("ADMIN")) {
            organizationMemberViewHolder.mAdmin.setVisibility(View.VISIBLE);
        } else {
            organizationMemberViewHolder.mAdmin.setVisibility(View.INVISIBLE);
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
