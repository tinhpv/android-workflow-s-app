package com.example.workflow_s.ui.task.dialog.assignment;

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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.example.workflow_s.R;
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


/*
 * WE NEED ARGUMENTS
 *     CHECKLIST_USER_ID
 *
 */


public class AssigningDialogFragment extends DialogFragment
        implements AssigningDialogContract.AssigningDialogView,
        View.OnClickListener, MemberAdapter.EventListener {

    View view;
    private AutoCompleteTextView userEmailForAssigning;
    private Button cancelButton, addUserButton;

    private RecyclerView userAssigningRecylerView;
    private MemberAdapter mAdapter;
    private RecyclerView.LayoutManager memberLayoutManager;

    private List<User> mUserList, mUnassignedUserList;
    private ArrayList<TaskMember> mTaskMemberList;
    private ArrayList<String> emailList;
    private String checklistUserId, taskId, userEmailToAssign, unassignedUserId;
    AssigningDialogContract.AssigningDialogPresenter mDialogPresenter;

    public static AssigningDialogFragment newInstance(String checklistUserId, String taskId, ArrayList<TaskMember> memberList) {
        AssigningDialogFragment frag = new AssigningDialogFragment();
        Bundle args = new Bundle();
        args.putString("checklistUserId", checklistUserId);
        args.putSerializable("memberList", memberList);
        args.putString("taskId", taskId);
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
        // get args
        Bundle args = getArguments();
        mTaskMemberList = (ArrayList<TaskMember>) args.getSerializable("memberList");
        checklistUserId = args.getString("checklistUserId");
        taskId = args.getString("taskId");


        setupRV();
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDialogPresenter = new AssigningDialogPresenterImpl(this, new AssigningDialogInteractor());
        String orgId = SharedPreferenceUtils.retrieveData(getContext(), getString(R.string.pref_orgId));
        mDialogPresenter.getOrgMember(Integer.parseInt(orgId));

        addUserButton = view.findViewById(R.id.bt_add_user);
        addUserButton.setOnClickListener(this);
        cancelButton = view.findViewById(R.id.bt_cancel_add);
        cancelButton.setOnClickListener(this);
    }

    private void initUI() {
        userEmailForAssigning = view.findViewById(R.id.edt_user_email_for_assigning);
        emailList = new ArrayList<>();
        for (User user : mUnassignedUserList) {
            emailList.add(user.getEmail());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.select_dialog_singlechoice, emailList);
        userEmailForAssigning.setAdapter(adapter);
    }

    private void setupRV() {
        userAssigningRecylerView = view.findViewById(R.id.rv_assign_user);
        userAssigningRecylerView.setHasFixedSize(true);
        memberLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        userAssigningRecylerView.setLayoutManager(memberLayoutManager);

        mAdapter = new MemberAdapter(this);
        userAssigningRecylerView.setAdapter(mAdapter);
    }

    @Override
    public void finishedGetMember(List<User> userList) {
        mUserList = userList;

        categorizeUser(userList);
        manipulateDataToDisplayOnRV();
        initUI();
    }

    @Override
    public void finishedAssignMember() {
        updateUsersToDisplay(true);
    }

    @Override
    public void finishedUnassignMember() {
        updateUsersToDisplay(false);
    }

    @Override
    public void finishedGetTaskMember(List<TaskMember> taskMemberList) {
        if (taskMemberList != null) {
            for (TaskMember member : taskMemberList) {
                if (member.getUserId().equals(unassignedUserId)) {
                    mDialogPresenter.unassignUser(member.getId());
                }
            } // end for
        }
    }

    private void updateUsersToDisplay(boolean isAssigned) {
        if (isAssigned) {
            for (User user : mUnassignedUserList) {
                if (user.getEmail().equals(userEmailToAssign)) {
                    mUnassignedUserList.remove(user);
                    mTaskMemberList.add(new TaskMember(1, Integer.parseInt(taskId), user.getId()));
                    manipulateDataToDisplayOnRV();
                    userEmailForAssigning.setText("");
                    break;
                }
            } // end for
        } else {
//            unassignedUserId => có id rồi làm gì?
            // them vao unassigned list
            for (User user : mUserList) {
                if (user.getId().equals(unassignedUserId)) {
                    mUnassignedUserList.add(user);
                    for (TaskMember tempUser : mTaskMemberList) {
                        if (tempUser.getUserId().equals(unassignedUserId) && (tempUser.getTaskId() == Integer.parseInt(taskId))) {
                            mTaskMemberList.remove(tempUser);
                            break;
                        } // end if
                    } // end for
                    manipulateDataToDisplayOnRV();
                    break;
                }
            }

        }

    }

    private void manipulateDataToDisplayOnRV() {
        List<User> usersToDisplay = new ArrayList<>();
        for (User user : mUserList) {
            if (isContain(user.getId()) || user.getId().equals(checklistUserId)) {
                usersToDisplay.add(user);
            }
        } // end for
        mAdapter.setUserList(usersToDisplay);
    }

    private boolean isContain(String userId) {
        if (mTaskMemberList != null) {
            for (TaskMember member : mTaskMemberList) {
                if (member.getUserId().equals(userId)) {
                    return true;
                }
            } // end for
        } // end if

        return false;
    }

    private void categorizeUser(List<User> userList) {
        // all members in organization
        this.mUserList = userList;
        // members that haven been assigned ==> mTaskMemberList
        // user that are owner of this checklist ==> checklistUserId

        // member that haven't been assigned ==> UnassignedUserList
        mUnassignedUserList = new ArrayList<>();
        for (User user : mUserList) {
            String userId = user.getId();
            if (!isContain(userId) && !userId.equals(checklistUserId)) {
                mUnassignedUserList.add(user);
            } // end if
        } // end for
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bt_add_user) {
            userEmailToAssign = userEmailForAssigning.getText().toString();
            if (!emailList.contains(userEmailToAssign)) {
                Animation shakeAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.shake_animation);
                userEmailForAssigning.startAnimation(shakeAnimation);
            } else {
                handleAssignUser(userEmailToAssign);
            }
        } else {
            dismiss();
        }
    }

    private User findUser(String email) {
        for (User user : mUnassignedUserList) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        } // end for
        return null;
    }

    private void handleAssignUser(String userEmail) {
        User user = findUser(userEmail);
        TaskMember taskMember = new TaskMember(null, Integer.parseInt(taskId), user.getId());
        mDialogPresenter.assignUser(taskMember);
    }

    @Override
    public void onEvent(String userId) {
        unassignedUserId = userId;
        mDialogPresenter.getTaskMember(Integer.parseInt(taskId));
    }
}
