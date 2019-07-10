package com.example.workflow_s.ui.checklistrunning;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.workflow_s.R;
import com.example.workflow_s.model.Checklist;
import com.example.workflow_s.model.Template;
import com.example.workflow_s.ui.task.task_checklist.ChecklistTaskFragment;
import com.example.workflow_s.utils.CommonUtils;
import com.example.workflow_s.utils.SharedPreferenceUtils;

/**
 * Workflow_S
 * Created by TinhPV on 2019-07-05
 * Copyright © 2019 TinhPV. All rights reserved
 **/


// WE NEED TEMPLATE NAME, TEMPLATE ID

public class ChecklistRunningFragment extends Fragment implements ChecklistRunningContract.ChecklistRunningView, View.OnClickListener {

    View view;
    private int templateId;
    private String templateName, checklistName, templateUserId, orgId, userId;
    private TextView templateNameTextView;
    private EditText checklistNameEditText;
    private Button confirmRunButton;

    private ChecklistRunningContract.ChecklistRunningPresenter mRunningPresenter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_checklistrunning, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        getDataFromParent();
        initView();
    }

    private void getDataFromParent() {
        Bundle args = getArguments();
        templateId = args.getInt("templateId");
        templateName = args.getString("templateName");
        templateUserId = args.getString("templateUserId");

        orgId = SharedPreferenceUtils.retrieveData(getContext(), getString(R.string.pref_orgId));
        userId = SharedPreferenceUtils.retrieveData(getContext(), getString(R.string.pref_userId));
    }

    private void initView() {
        templateNameTextView = view.findViewById(R.id.tv_template_name);
        templateNameTextView.setText(templateName);
        checklistNameEditText = view.findViewById(R.id.edt_checklist_name);
        confirmRunButton = view.findViewById(R.id.bt_confirm);
        confirmRunButton.setOnClickListener(this);

        mRunningPresenter = new ChecklistRunningPresenterImpl(this, new ChecklistRunningInteractor());
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bt_confirm) {
            handleRunChecklist();
        }
    }

    private void handleRunChecklist() {
        checklistName = checklistNameEditText.getText().toString();
        if (checklistName.isEmpty()) {
            Animation shakeAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.shake_animation);
            checklistNameEditText.startAnimation(shakeAnimation);
        } else {
            // fixme - hardcode
            mRunningPresenter.getTemplateObject(String.valueOf(templateId), templateUserId, orgId);
        }
    }

    @Override
    public void finishGetTemplateObject(Template template) {
        if (null != template) {
            template.setName(checklistName);
            mRunningPresenter.runChecklist(userId, template);
        }
    }

    @Override
    public void finishedRunChecklist(Checklist checklist) {
        Bundle args = new Bundle();
        args.putString("checklistId", String.valueOf(checklist.getId()));
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        CommonUtils.replaceFragments(getContext(), ChecklistTaskFragment.class, args);
    }
}
