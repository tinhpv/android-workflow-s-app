package com.example.workflow_s.ui.task.task_checklist;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.workflow_s.R;
import com.example.workflow_s.model.Task;
import com.example.workflow_s.ui.task.TaskContract;
import com.example.workflow_s.ui.task.TaskInteractor;
import com.example.workflow_s.ui.task.TaskPresenterImpl;
import com.example.workflow_s.ui.task.adapter.ChecklistTaskAdapter;
import com.example.workflow_s.ui.task.dialog.AssigningDialogFragment;
import com.example.workflow_s.utils.SharedPreferenceUtils;

import java.util.ArrayList;

/**
 * Workflow_S
 * Created by TinhPV on 2019-06-30
 * Copyright © 2019 TinhPV. All rights reserved
 **/


public class ChecklistTaskFragment extends Fragment implements TaskContract.TaskView, ChecklistTaskAdapter.CheckboxListener, View.OnClickListener {

    private static final String TAG = "TASK_FRAGMENT";

    View view;
    private Button completeChecklistButton;
    private TextView mChecklistDescription, mChecklistName;
    private RecyclerView checklistTaskRecyclerView;
    private ChecklistTaskAdapter mChecklistChecklistTaskAdapter;
    private RecyclerView.LayoutManager taskLayoutManager;

    private TaskContract.TaskPresenter mPresenter;
    private int checklistId;
    private String checklistName, checklistDescription;

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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_set_time:
                Toast.makeText(getActivity(), "SET TIME", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_assign:
                FragmentManager fm = getActivity().getSupportFragmentManager();
                AssigningDialogFragment editNameDialogFragment = AssigningDialogFragment.newInstance("Some Title");
                editNameDialogFragment.show(fm, "fragment_edit_name");
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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setupTaskRV();
        initData();
        initUI();
    }

    private void setupTaskRV() {
        checklistTaskRecyclerView = view.findViewById(R.id.rv_checklist_task);
        checklistTaskRecyclerView.setHasFixedSize(true);
        taskLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        checklistTaskRecyclerView.setLayoutManager(taskLayoutManager);

        mChecklistChecklistTaskAdapter = new ChecklistTaskAdapter(this);
        checklistTaskRecyclerView.setAdapter(mChecklistChecklistTaskAdapter);
    }

    public void initData() {
        // GET NECESSARY DATA FROM PARENT
        Bundle arguments = getArguments();
        checklistId = Integer.parseInt(arguments.getString("checklistId"));
        checklistName = arguments.getString("checklistName");
        checklistDescription = arguments.getString("checklistDescription");

        // OK - HARDCODE HERE
        mPresenter = new TaskPresenterImpl(this, new TaskInteractor());
        mPresenter.loadTasks(checklistId);
    }

    private void initUI() {
        completeChecklistButton = view.findViewById(R.id.bt_complete_checklist);
        completeChecklistButton.setOnClickListener(this);
        mChecklistDescription = view.findViewById(R.id.tv_task_description);
        mChecklistDescription.setText(checklistDescription);
        mChecklistName = view.findViewById(R.id.tv_task_name);
        mChecklistName.setText(checklistName);
    }


    @Override
    public void setDataToTaskRecyclerView(ArrayList<Task> datasource) {
        mChecklistChecklistTaskAdapter.setTaskList(datasource);
    }

    @Override
    public void onEventCheckBox(Boolean isSelected) {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bt_complete_checklist) {
            // DO SOMETHING HERE
        }
    }
}
