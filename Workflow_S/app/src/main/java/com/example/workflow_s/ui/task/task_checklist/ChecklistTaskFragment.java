package com.example.workflow_s.ui.task.task_checklist;

import android.animation.Animator;
import android.app.Dialog;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.workflow_s.R;
import com.example.workflow_s.model.Checklist;
import com.example.workflow_s.model.ChecklistMember;
import com.example.workflow_s.model.Task;
import com.example.workflow_s.model.TaskMember;
import com.example.workflow_s.ui.task.TaskContract;
import com.example.workflow_s.ui.task.TaskInteractor;
import com.example.workflow_s.ui.task.TaskStatusPresenterImpl;
import com.example.workflow_s.ui.task.adapter.ChecklistTaskAdapter;
import com.example.workflow_s.ui.task.dialog.assignment.AssigningDialogFragment;
import com.example.workflow_s.ui.task.dialog.time_setting.TimeSettingDialogFragment;
import com.example.workflow_s.utils.CommonUtils;
import com.example.workflow_s.utils.SharedPreferenceUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

/**
 * Workflow_S
 * Created by TinhPV on 2019-06-30
 * Copyright © 2019 TinhPV. All rights reserved
 **/


public class ChecklistTaskFragment extends Fragment implements TaskContract.TaskView, ChecklistTaskAdapter.CheckboxListener, View.OnClickListener {

    private static final String TAG = "TASK_FRAGMENT";

    View view;
    private FloatingActionButton completeChecklistButton;
    private TextView mChecklistDescription, mChecklistName;
    private RecyclerView checklistTaskRecyclerView;
    private ChecklistTaskAdapter mChecklistChecklistTaskAdapter;
    private RecyclerView.LayoutManager taskLayoutManager;

    private TaskContract.TaskPresenter mPresenter;
    private int checklistId;
    private String checklistName, checklistDescription, checklistUserId, currentDueTime, userId, orgId, checklistStatus;

    private ProgressBar mTaskProgressBar;
    private int totalTask, doneTask, location;
    private List<ChecklistMember> checklistMembers;
    private ArrayList<Task> tasks;

    LottieAnimationView mAnimationView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_task_detail, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public void onDestroyView() {
        switch (location) {
            case 1:
                getActivity().setTitle("Home");
                break;
            case 2:
                getActivity().setTitle("Active checklist");
                break;
        }
        super.onDestroyView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        switch (item.getItemId()) {
            case R.id.action_set_time:
                if (!isChecklistMember()) {
                    CommonUtils.showDialog(getContext(), "You're not a member of this checklist");
                } else {
                    TimeSettingDialogFragment settingDialogFragment
                            = TimeSettingDialogFragment.newInstance(checklistId);
                    settingDialogFragment.show(fm, "fragment_set_time");
                }
                return true;

            case R.id.action_assign:
                if (!isChecklistMember()) {
                    CommonUtils.showDialog(getContext(), "You're not a member of this checklist");
                } else {
                    // convert List to ArrayList so that we can store it in Bundle
                    AssigningDialogFragment assigningDialogFragment = AssigningDialogFragment.newInstance(checklistUserId, checklistId);
                    assigningDialogFragment.show(fm, "fragment_assign_user");
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_task_cheklist, container, false);
        getActivity().setTitle("Checklist's Tasks");
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setupTaskRV();
        initData();
    }

    private void setupTaskRV() {
        checklistTaskRecyclerView = view.findViewById(R.id.rv_checklist_task);
        checklistTaskRecyclerView.setHasFixedSize(true);
        taskLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        checklistTaskRecyclerView.setLayoutManager(taskLayoutManager);

        mChecklistChecklistTaskAdapter = new ChecklistTaskAdapter(this);
        checklistTaskRecyclerView.setAdapter(mChecklistChecklistTaskAdapter);
    }

    public void initData() {
        // GET NECESSARY DATA FROM PARENT
        Bundle arguments = getArguments();
        checklistId = Integer.parseInt(arguments.getString("checklistId"));
        location = arguments.getInt("location");
        checklistMembers = new ArrayList<>();
        checklistMembers = (ArrayList<ChecklistMember>) arguments.getSerializable("listMember");

        // USER's DATA
        userId = SharedPreferenceUtils.retrieveData(getContext(), getString(R.string.pref_userId));
        orgId = SharedPreferenceUtils.retrieveData(getContext(), getString(R.string.pref_orgId));
        mChecklistChecklistTaskAdapter.setChecklistId(checklistId);

        // OK - HARDCODE HERE
        mPresenter = new TaskStatusPresenterImpl(this, new TaskInteractor());
        mPresenter.loadChecklistData(Integer.parseInt(orgId), checklistId);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void finishGetChecklist(Checklist checklist) {
        if (null != checklist) {
            checklistName = checklist.getName();
            checklistStatus = checklist.getTemplateStatus();
            checklistDescription = checklist.getDescription();
            checklistUserId = checklist.getUserId();
            totalTask = checklist.getTotalTask();
            doneTask = checklist.getDoneTask();
            tasks = (ArrayList<Task>) checklist.getTaskItems();
            mChecklistChecklistTaskAdapter.setTaskList(tasks);
            mChecklistChecklistTaskAdapter.setChecklistMembers((ArrayList<ChecklistMember>) checklistMembers);

            mChecklistChecklistTaskAdapter.setChecklistUserId(checklistUserId);
            initUI();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initUI() {
        mTaskProgressBar = view.findViewById(R.id.pb_checklist_task);
        updateProgressBar();

        completeChecklistButton = view.findViewById(R.id.bt_complete_checklist);
        completeChecklistButton.setOnClickListener(this);

        mChecklistDescription = view.findViewById(R.id.tv_task_description);
        mChecklistDescription.setText(checklistDescription);

        mChecklistName = view.findViewById(R.id.tv_task_name);
        mChecklistName.setText(checklistName);

        mAnimationView = view.findViewById(R.id.animation_view);
        mAnimationView.setSpeed(2.0f);
        mAnimationView.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mAnimationView.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        updateButtonAppearanceByStatus(checklistStatus);
        // invalidate data
        if ((doneTask != totalTask) && (checklistStatus.equals(getString(R.string.checklist_done)))) {
            handleCompleteChecklist(getResources().getString(R.string.checklist_running));
        }
    }

    private void updateButtonAppearanceByStatus(String status) {
        if (status.equals(getContext().getResources().getString(R.string.checklist_done))) { // done
            completeChecklistButton.hide();
        } else { // running
            completeChecklistButton.show();
        }
    }

    private void switchOnLoading() {
        mAnimationView.setVisibility(View.VISIBLE);
        if (!mAnimationView.isAnimating()) {
            mAnimationView.playAnimation();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    void updateProgressBar() {
        if (totalTask == 0) {
            mTaskProgressBar.setProgress(0, true);
        } else {
            int progress = (int) ((doneTask / (totalTask * 1.0)) * 100);
            mTaskProgressBar.setProgress(progress, true);
        } // end
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onEventCheckBox(Boolean isSelected, int taskId) {
        if (userId.equals(checklistUserId)) {
            handleTickTaskOnChecklistTaskFragment(taskId, isSelected);
        } else {
            mPresenter.getTaskMember(taskId, isSelected);
        }
    }

    private boolean isChecklistMember() {
        for (ChecklistMember member : checklistMembers) {
            if (member.getUserId().equals(userId)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void finishedChangeTaskStatus() {

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void finishGetTaskMember(List<TaskMember> taskMemberList, boolean isSelected, int taskId) {

        boolean isTaskMember = false;

        for (TaskMember member : taskMemberList) {
            if (userId.equals(member.getUserId())) {
                isTaskMember = true;
                break;
            }
        }

        if (isTaskMember) {
            handleTickTaskOnChecklistTaskFragment(taskId, isSelected);
        } else {
            CommonUtils.showDialog(getContext(), "You're not assigned to this task, so you cannot complete it");
            mChecklistChecklistTaskAdapter.setTaskList(tasks);
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void handleTickTaskOnChecklistTaskFragment(int taskId, boolean isSelected) {
        if (isSelected) {
            doneTask++;
            mPresenter.changeTaskStatus(taskId, getString(R.string.task_done));
        } else {
            doneTask--;
            mPresenter.changeTaskStatus(taskId, getString(R.string.task_running));
        }

        updateProgressBar();

        if (doneTask == totalTask) {
            handleCompleteChecklist(getString(R.string.checklist_done));
        } else if (checklistStatus.equals(getString(R.string.checklist_done))){
            handleCompleteChecklist(getString(R.string.checklist_running));
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bt_complete_checklist) {
            if (isChecklistMember()) {
                handleCompleteChecklist(getString(R.string.checklist_done));
            } else {
                CommonUtils.showDialog(getContext(),"You're not assigned to this checklist, so you cannot complete it");
            }
        }
    }

    private void handleCompleteChecklist(String status) {
        updateButtonAppearanceByStatus(status);
        if (status.equals(getString(R.string.checklist_done))) {
            switchOnLoading();
        }
        mPresenter.changeChecklistStatus(checklistId, status);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void finishChangeChecklistStatus(String status) {
        checklistStatus = status;
        mPresenter.loadChecklistData(Integer.parseInt(orgId), checklistId);
    }



}
