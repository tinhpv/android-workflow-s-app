package com.example.workflow_s.ui.task.task_template;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.example.workflow_s.R;
import com.example.workflow_s.model.Task;
import com.example.workflow_s.ui.task.TaskContract;
import com.example.workflow_s.ui.task.TaskInteractor;
import com.example.workflow_s.ui.task.TaskPresenterImpl;
import com.example.workflow_s.ui.task.adapter.ChecklistTaskAdapter;
import com.example.workflow_s.ui.task.adapter.TemplateTaskAdapter;

import java.util.ArrayList;

/**
 * Workflow_S
 * Created by TinhPV on 2019-07-03
 * Copyright © 2019 TinhPV. All rights reserved
 **/


public class TemplateTaskFragment extends Fragment implements TaskContract.TaskView, TemplateTaskAdapter.CheckboxListener {

    private static final String TAG = "TEMPLATE_TASK_FRAGMENT";

    View view;
    private Button btnRunChecklist;
    private TextView mTemplateDescription, mTemplateName;
    private RecyclerView templateTaskRecyclerView;
    private TemplateTaskAdapter mTemplateTaskAdapter;
    private RecyclerView.LayoutManager taskLayoutManager;

    private TaskContract.TaskPresenter mPresenter;
    private int templateId;
    private String templateName, templateDescription;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_task_template, container, false);
        getActivity().setTitle("Template's Tasks");
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initRV();
        initData();
        initUI();
    }

    private void initUI() {
        btnRunChecklist = view.findViewById(R.id.bt_run_checklist);
        mTemplateDescription = view.findViewById(R.id.tv_template_description);
        mTemplateDescription.setText(templateDescription);
        mTemplateName = view.findViewById(R.id.tv_template_name);
        mTemplateName.setText(templateName);
    }

    private void initData() {
        // GET NECESSARY DATA FROM PARENT
        Bundle arguments = getArguments();
        templateId = Integer.parseInt(arguments.getString("templateId"));
        templateName = arguments.getString("templateName");
        templateDescription = arguments.getString("templateDescription");

        // FIXME - HARDCODE HERE
        mPresenter = new TaskPresenterImpl(this, new TaskInteractor());
//        mPresenter.loadTasks(checklistId);
        mPresenter.loadTasks(2);
    }

    private void initRV() {
        templateTaskRecyclerView = view.findViewById(R.id.rv_template_task);
        templateTaskRecyclerView.setHasFixedSize(true);
        taskLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        templateTaskRecyclerView.setLayoutManager(taskLayoutManager);

        mTemplateTaskAdapter = new TemplateTaskAdapter(this, this);
        templateTaskRecyclerView.setAdapter(mTemplateTaskAdapter);
    }

    @Override
    public void setDataToTaskRecyclerView(ArrayList<Task> datasource) {
        mTemplateTaskAdapter.setTemplateTaskList(datasource);
    }

    @Override
    public void onEventCheckBox(Boolean isSelected) {
        Animation shakeAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.shake_animation);
        btnRunChecklist.startAnimation(shakeAnimation);
    }
}
