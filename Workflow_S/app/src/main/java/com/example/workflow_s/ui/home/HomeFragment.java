package com.example.workflow_s.ui.home;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.workflow_s.R;
import com.example.workflow_s.model.Checklist;
import com.example.workflow_s.model.ChecklistMember;
import com.example.workflow_s.model.Task;
import com.example.workflow_s.model.TaskMember;
import com.example.workflow_s.ui.activity.ActivityFragment;
import com.example.workflow_s.ui.checklist.ChecklistFragment;
import com.example.workflow_s.ui.checklist.adapter.ChecklistProgressAdapter;
import com.example.workflow_s.ui.home.adapter.TodayTaskAdapter;
import com.example.workflow_s.ui.notification.NotificationFragment;
import com.example.workflow_s.ui.template.TemplateFragment;
import com.example.workflow_s.utils.CommonUtils;
import com.example.workflow_s.utils.SharedPreferenceUtils;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.common.internal.service.Common;
import com.google.android.gms.common.util.SharedPreferencesUtils;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.List;

/**
 * Workflow_S
 * Created by TinhPV on 2019-06-26
 * Copyright © 2019 TinhPV. All rights reserved
 **/


public class HomeFragment extends Fragment implements View.OnClickListener, HomeContract.HomeView {

    View view;
    private Button btnTemplate, btnChecklist, btnActivity;

    private RecyclerView checklistProgressRecyclerView, todayTaskRecyclerView;
    private ChecklistProgressAdapter mChecklistProgressAdapter;
    private TodayTaskAdapter mTodayTaskAdapter;
    private RecyclerView.LayoutManager checklistProgressLayoutManager, todayTaskLayoutManager;

    private HomeContract.HomePresenter mPresenter;

    private ShimmerFrameLayout mChecklistShimmerFrameLayout, mTaskShimmerFrameLayout;
    private LinearLayout mCheckListDataStatusMessage, mTaskDataStatusMessage;

    private String userId, orgId;
    private ArrayList<Checklist> checklists;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu_home, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_notif:
                CommonUtils.replaceFragments(getContext(), NotificationFragment.class, null);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        mChecklistShimmerFrameLayout = view.findViewById(R.id.checklist_shimmer_view_container);
        mTaskShimmerFrameLayout = view.findViewById(R.id.task_shimmer_view_container);
        mCheckListDataStatusMessage = view.findViewById(R.id.checklist_data_notfound_message);
        mTaskDataStatusMessage = view.findViewById(R.id.task_data_notfound_message);

        makeDashboardButtonLookGood();
        setupChecklistRV();
        setupTaskRV();
        initData();
        String token = FirebaseInstanceId.getInstance().getToken();
        if (token == null) {
            Log.i("Token", "Token is null");
        } else {
            Log.i("Token", token);
            //Log.i("UserToken", SharedPreferenceUtils.retrieveData(view.getContext(), view.getContext().getString(R.string.pref_token)));
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        mChecklistShimmerFrameLayout.startShimmerAnimation();
        mTaskShimmerFrameLayout.startShimmerAnimation();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_activity:
                CommonUtils.replaceFragments(getContext(), ActivityFragment.class, null);
                break;
            case R.id.btn_checklist:
                CommonUtils.replaceFragments(getContext(), ChecklistFragment.class, null);
                break;
            case R.id.btn_template:
                CommonUtils.replaceFragments(getContext(), TemplateFragment.class, null);
                break;
            case R.id.bt_view_all_checklist:
                CommonUtils.replaceFragments(getContext(), ChecklistFragment.newInstance().getClass(), null);
                break;
            case R.id.bt_view_all_task:
                break;
        } // end switch
    }

    private void initData() {
        mPresenter = new HomePresenterImpl(this, new HomeInteractor());

        userId = SharedPreferenceUtils.retrieveData(getContext(), getString(R.string.pref_userId));
        orgId = SharedPreferenceUtils.retrieveData(getContext(), getString(R.string.pref_orgId));
        mPresenter.loadRunningChecklists(orgId);
        mPresenter.loadDueTasks(orgId, userId);
    }

    private void setupTaskRV() {
        todayTaskRecyclerView = view.findViewById(R.id.rv_today_task);
        todayTaskRecyclerView.setHasFixedSize(true);
        todayTaskLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        todayTaskRecyclerView.setLayoutManager(todayTaskLayoutManager);

        // FIXME - TEST DATASOURCE OF TASK_RV
        mTodayTaskAdapter = new TodayTaskAdapter();
        todayTaskRecyclerView.setAdapter(mTodayTaskAdapter);
    }

    private void setupChecklistRV() {
        checklistProgressRecyclerView = view.findViewById(R.id.rv_checklist_progress);
        checklistProgressRecyclerView.setHasFixedSize(true);
        checklistProgressLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        checklistProgressRecyclerView.setLayoutManager(checklistProgressLayoutManager);

        mChecklistProgressAdapter = new ChecklistProgressAdapter();
        checklistProgressRecyclerView.setAdapter(mChecklistProgressAdapter);
    }

    private void makeDashboardButtonLookGood() {
        btnTemplate = view.findViewById(R.id.btn_template);
        btnChecklist = view.findViewById(R.id.btn_checklist);
        btnActivity = view.findViewById(R.id.btn_activity);

        btnTemplate.setOnClickListener(this);
        btnChecklist.setOnClickListener(this);
        btnActivity.setOnClickListener(this);

        btnTemplate.setBackgroundResource(0);
        btnChecklist.setBackgroundResource(0);
        btnActivity.setBackgroundResource(0);
    }

    @Override
    public void setDataToChecklistRecyclerView(ArrayList<Checklist> datasource) {
        mChecklistShimmerFrameLayout.stopShimmerAnimation();
        mChecklistShimmerFrameLayout.setVisibility(View.INVISIBLE);
        if (datasource.size() == 0) {
            mCheckListDataStatusMessage.setVisibility(View.VISIBLE);
        } else {
            checklists = new ArrayList<>();
            for (Checklist checklist : datasource) {
                if (checklist.getUserId().equals(userId)) {
                    checklists.add(checklist);
                } else {
                    List<ChecklistMember> listMember = checklist.getChecklistMembers();
                    if (listMember != null) {
                        for (ChecklistMember member : listMember) {
                            if (member.getUserId().equals(userId)) {
                                checklists.add(checklist);
                            }
                        }
                    }
                }
            }
            mChecklistProgressAdapter.setChecklists(checklists);
        }

    }

    @Override
    public void setDataToTasksRecyclerView(ArrayList<Task> datasource) {
        mTaskShimmerFrameLayout.stopShimmerAnimation();
        mTaskShimmerFrameLayout.setVisibility(View.INVISIBLE);
        if (datasource == null) {
            mTaskDataStatusMessage.setVisibility(View.VISIBLE);
        } else {
            //tasks = new ArrayList<>();
            mTodayTaskAdapter.setTasks(datasource);
        }
    }

    @Override
    public void onFailGetChecklist() {
        mChecklistShimmerFrameLayout.stopShimmerAnimation();
        mChecklistShimmerFrameLayout.setVisibility(View.INVISIBLE);
        mCheckListDataStatusMessage.setVisibility(View.VISIBLE);
    }

    @Override
    public void onFailGetTask() {
        mTaskShimmerFrameLayout.stopShimmerAnimation();
        mTaskShimmerFrameLayout.setVisibility(View.INVISIBLE);
        mTaskDataStatusMessage.setVisibility(View.VISIBLE);
    }
}
