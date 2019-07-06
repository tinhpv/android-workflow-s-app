package com.example.workflow_s.ui.task.dialog;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;

import com.example.workflow_s.R;
import com.example.workflow_s.model.User;
import com.example.workflow_s.utils.SharedPreferenceUtils;

import java.util.List;

/**
 * Workflow_S
 * Created by TinhPV on 2019-07-06
 * Copyright © 2019 TinhPV. All rights reserved
 **/


/*
 * WE NEED ARGUMENTS
 *     CHECKLIST_USER_ID
 *
 */


public class AssigningDialogFragment extends DialogFragment implements AssigningDialogContract.AssigningDialogView {

    View view;
    private AutoCompleteTextView userEmailForAssigning;

    private RecyclerView userAssigningRecylerView;
    private MemberAdapter mAdapter;
    private RecyclerView.LayoutManager memberLayoutManager;


    private List<User> mUserList;
    AssigningDialogContract.AssigningDialogPresenter mDialogPresenter;

    public static AssigningDialogFragment newInstance(String checklistUserId) {
        AssigningDialogFragment frag = new AssigningDialogFragment();
        Bundle args = new Bundle();
        args.putString("checklistUserId", checklistUserId);
        frag.setArguments(args);
        return frag;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_assign_user, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        userEmailForAssigning = view.findViewById(R.id.edt_user_email_for_assigning);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDialogPresenter = new AssigningDialogPresenterImpl(this, new AssigningDialogInteractor());
        String orgId = SharedPreferenceUtils.retrieveData(getContext(), getString(R.string.pref_orgId));
        mDialogPresenter.getOrgMember(Integer.parseInt(orgId));
        setupRV();
    }

    private void setupRV() {
        userAssigningRecylerView = view.findViewById(R.id.rv_assign_user);
        userAssigningRecylerView.setHasFixedSize(true);
        memberLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        userAssigningRecylerView.setLayoutManager(memberLayoutManager);

        mAdapter = new MemberAdapter();
        userAssigningRecylerView.setAdapter(mAdapter);
    }

    @Override
    public void finishedGetMember(List<User> userList) {
        mUserList = userList;
        mAdapter.setUserList(mUserList);
    }
}
