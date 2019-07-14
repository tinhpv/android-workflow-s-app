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
import com.example.workflow_s.model.Checklist;
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


/*
 * WE NEED ARGUMENTS
 *     CHECKLIST_USER_ID
 *     CHECKLIST_ID
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
    private ArrayList<ChecklistMember> mChecklistMembers;
    private ArrayList<String> emailList;
    private String checklistUserId, userEmailToAssign, unassignedUserId, orgId;
    private int checklistId;
    AssigningDialogContract.AssigningDialogPresenter mDialogPresenter;

    public static AssigningDialogFragment newInstance(String checklistUserId, int checklistId) {
        AssigningDialogFragment frag = new AssigningDialogFragment();
        Bundle args = new Bundle();
        args.putString("checklistUserId", checklistUserId);
        args.putInt("checklistId", checklistId);
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
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        addUserButton = view.findViewById(R.id.bt_add_user);
        addUserButton.setOnClickListener(this);
        cancelButton = view.findViewById(R.id.bt_cancel_add);
        cancelButton.setOnClickListener(this);


        // get args
        Bundle args = getArguments();
        mChecklistMembers = new ArrayList<>();
        checklistUserId = args.getString("checklistUserId");
        checklistId = args.getInt("checklistId");
        orgId = SharedPreferenceUtils.retrieveData(getContext(), getString(R.string.pref_orgId));

        setupRV();
        requestData();
    }

    private void setupRV() {
        userAssigningRecylerView = view.findViewById(R.id.rv_assign_user);
        userAssigningRecylerView.setHasFixedSize(true);
        memberLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        userAssigningRecylerView.setLayoutManager(memberLayoutManager);

        mAdapter = new MemberAdapter(this);
        userAssigningRecylerView.setAdapter(mAdapter);
    }

    private void requestData() {
        mDialogPresenter = new AssigningDialogPresenterImpl(this, new AssigningDialogInteractor());
        mDialogPresenter.getOrgMember(Integer.parseInt(orgId));
    }


    @Override
    public void finishedGetMember(List<User> userList) {
        mUserList = userList;
        mDialogPresenter.getChecklistInfo(checklistId);
    }

    @Override
    public void finishedGetChecklistInfoById(Checklist checklist) {
        if (null != checklist) {
            this.mChecklistMembers = (ArrayList<ChecklistMember>) checklist.getChecklistMembers();
            getUnassignedUser();
            manipulateDataToDisplayOnRV();
            setupAutoCompleteTextView();
        } // endif
    }

    @Override
    public void finishedAssignMember(ChecklistMember member) {
        updateUsersToDisplay(true, member);
    }

    @Override
    public void finishedUnassignMember() {
        updateUsersToDisplay(false, null);
    }


    private void updateUsersToDisplay(boolean isAssigned, ChecklistMember member) {
        if (isAssigned) {
            // remove user who was assigned out of mUnassignedUserList
            // add user to the taskMemberList
            for (User user : mUnassignedUserList) {
                if (user.getEmail().equals(userEmailToAssign)) {
                    mUnassignedUserList.remove(user);
                    mChecklistMembers.add(member);
                    userEmailForAssigning.setText("");
                    break;
                }
            } // end for
        } else {
            // remove user from taskMemberList
            // add user to unAssignMemberList

            for (User user : mUserList) {
                if (user.getId().equals(unassignedUserId)) {
                    mUnassignedUserList.add(user);
                    for (ChecklistMember tempUser : mChecklistMembers) {
                        if (tempUser.getUserId().equals(unassignedUserId) && (tempUser.getChecklistId() == checklistId)) {
                            mChecklistMembers.remove(tempUser);
                            break;
                        } // end if
                    } // end for
                    break;
                } // end if
            } // end for
        } // end if

        manipulateDataToDisplayOnRV();
        setupAutoCompleteTextView();
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
        if (mChecklistMembers != null) {
            for (ChecklistMember member : mChecklistMembers) {
                if (member.getUserId().equals(userId)) {
                    return true;
                }
            } // end for
        } // end if

        return false;
    }

    private void getUnassignedUser() {
        // all members in organization ==> mUserList
        // members that haven been assigned ==> mChecklistMembers
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

    private void setupAutoCompleteTextView() {
        userEmailForAssigning = view.findViewById(R.id.edt_user_email_for_assigning);

        emailList = new ArrayList<>();
        for (User user : mUnassignedUserList) {
            emailList.add(user.getEmail());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.select_dialog_singlechoice, emailList);
        userEmailForAssigning.setAdapter(adapter);
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
        ChecklistMember checklistMember = new ChecklistMember(null, checklistId, user.getId());
        mDialogPresenter.assignUser(checklistMember);
    }

    @Override
    public void onEvent(String userId) {
        unassignedUserId = userId;
        for (ChecklistMember member : mChecklistMembers) {
            if (member.getUserId().equals(unassignedUserId)) {
                mDialogPresenter.unassignUser(member.getId());
                break;
            } // end if
        } // end for
    }
}
