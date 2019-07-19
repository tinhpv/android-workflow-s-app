package com.example.workflow_s.ui.taskdetail.dialog.assignment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    private Button cancelButton;

    private RecyclerView userAssigningRecylerView;
    private MemberAdapter mAdapter;
    private RecyclerView.LayoutManager memberLayoutManager;

    private List<User> mUserList, mUnassignedUserList;
    private ArrayList<ChecklistMember> mChecklistMembers;
    private ArrayList<TaskMember> mTaskMembers;
    private ArrayList<String> emailList;
    private String checklistUserId, userEmailToAssign, orgId;
    private boolean isTaskMember;
    private int taskId, checklistId;
    AssigningDialogContract.AssigningDialogPresenter mDialogPresenter;

    public static AssigningDialogFragment newInstance(int taskId, int checklistId, String checklistUserId, boolean isTaskMember) {
        AssigningDialogFragment frag = new AssigningDialogFragment();
        Bundle args = new Bundle();
        args.putInt("taskId", taskId);
        args.putInt("checklistId", checklistId);
        args.putString("checklistUserId", checklistUserId);
        args.putBoolean("taskMember", isTaskMember);
        frag.setArguments(args);
        return frag;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_assign_user_detail, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        cancelButton = view.findViewById(R.id.bt_dismiss);
        cancelButton.setOnClickListener(this);


        // init list
        mChecklistMembers = new ArrayList<>();
        mTaskMembers = new ArrayList<>();

        // get args
        Bundle args = getArguments();
        taskId = args.getInt("taskId");
        checklistId = args.getInt("checklistId");
        checklistUserId = args.getString("checklistUserId");
        isTaskMember = args.getBoolean("taskMember");
        orgId = SharedPreferenceUtils.retrieveData(getContext(), getString(R.string.pref_orgId));

        setupRV();
        requestData();
    }

    private void setupRV() {
        userAssigningRecylerView = view.findViewById(R.id.rv_assign_user);
        userAssigningRecylerView.setHasFixedSize(true);
        memberLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        userAssigningRecylerView.setLayoutManager(memberLayoutManager);

        mAdapter = new MemberAdapter(this, getContext());
        mAdapter.setTaskMember(isTaskMember);
        userAssigningRecylerView.setAdapter(mAdapter);
        mAdapter.setChecklistUserId(checklistUserId);
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
    public void finishedGetTaskMember(List<TaskMember> taskMemberList) {
        if (null != taskMemberList) {
            mTaskMembers = (ArrayList<TaskMember>) taskMemberList;
            mAdapter.setUserList(mUserList);
            mAdapter.setChecklistMembers(mChecklistMembers);
            mAdapter.setTaskMembers(mTaskMembers);
        }
    }

    @Override
    public void finishedGetChecklistInfoById(Checklist checklist) {
        if (null != checklist) {
            this.mChecklistMembers = (ArrayList<ChecklistMember>) checklist.getChecklistMembers();
            mDialogPresenter.getTaskMember(taskId);
            // FIXME - FIX HERE
//            getUnassignedUser();
//            manipulateDataToDisplayOnRV();
//            setupAutoCompleteTextView();
        } // endif
    }


    @Override
    public void finishedUnassignMember(int memberId) {
        for (TaskMember taskMember : mTaskMembers) {
            if (taskMember.getId() == memberId) {
                mTaskMembers.remove(taskMember);
                mAdapter.setTaskMembers(mTaskMembers);
                break;
            } // end if
        } // end for
    }


    private void updateUsersToDisplay(boolean isAssigned) {
//        if (isAssigned) {
//            // remove user who was assigned out of mUnassignedUserList
//            // add user to the taskMemberList
//            for (User user : mUnassignedUserList) {
//                if (user.getEmail().equals(userEmailToAssign)) {
//                    mUnassignedUserList.remove(user);
//                    mChecklistMembers.add(new ChecklistMember(null, taskId, user.getId()));
//                    userEmailForAssigning.setText("");
//                    break;
//                }
//            } // end for
//        } else {
//            // remove user from taskMemberList
//            // add user to unAssignMemberList
//
//            for (User user : mUserList) {
//                if (user.getId().equals(unassignedUserId)) {
//                    mUnassignedUserList.add(user);
//                    for (ChecklistMember tempUser : mChecklistMembers) {
//                        if (tempUser.getUserId().equals(unassignedUserId) && (tempUser.getChecklistId() == taskId)) {
//                            mChecklistMembers.remove(tempUser);
//                            break;
//                        } // end if
//                    } // end for
//                    break;
//                } // end if
//            } // end for
//        } // end if

        manipulateDataToDisplayOnRV();
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


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bt_dismiss) {
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

    @Override
    public void finishedAssignMember(TaskMember taskMember) {
        mTaskMembers.add(taskMember);
        mAdapter.setTaskMembers(mTaskMembers);
    }

    @Override
    public void onEvent(String userId, Integer taskMemberId, boolean doAssign) {
        for (ChecklistMember member : mChecklistMembers) {
            if (member.getUserId().equals(userId)) {
                if (!doAssign) {
                    mDialogPresenter.unassignUser(taskMemberId);
                } else {
                    TaskMember tmpMember = new TaskMember(null, taskId, member.getUserId());
                    mDialogPresenter.assignUser(tmpMember);
                }
                break;
            } // end if
        } // end for
    }
}
