package com.example.workflow_s.ui.task.task_template;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.example.workflow_s.model.Task;
import com.example.workflow_s.model.Template;
import com.example.workflow_s.ui.task.TaskContract;
import com.example.workflow_s.ui.task.TaskInteractor;
import com.example.workflow_s.ui.task.TaskPresenterImpl;
import com.example.workflow_s.ui.task.adapter.ChecklistTaskAdapter;
import com.example.workflow_s.ui.task.adapter.TemplateTaskAdapter;
import com.example.workflow_s.utils.SharedPreferenceUtils;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Workflow_S
 * Created by TinhPV on 2019-07-03
 * Copyright © 2019 TinhPV. All rights reserved
 **/


public class TemplateTaskFragment extends Fragment implements TaskContract.TemplateView, TemplateTaskAdapter.CheckboxListener, View.OnClickListener {

    private static final String TAG = "TEMPLATE_TASK_FRAGMENT";

    View view;
    private Button btnRunChecklist;
    private TextView mTemplateDescription, mTemplateName;
    private RecyclerView templateTaskRecyclerView;
    private TemplateTaskAdapter mTemplateTaskAdapter;
    private RecyclerView.LayoutManager taskLayoutManager;

    private TaskContract.TaskPresenter mPresenter;
    private int templateId;
    private String userId, orgId, templateName, templateDescription, runningChecklistName;

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
        btnRunChecklist.setOnClickListener(this);
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
    public void finishGetTemplateObject(Template template) {
        Log.d(TAG, "finishGetTemplateObject: " + templateName);
        handleRunChecklist(template);
    }

    @Override
    public void finishedRunChecklist() {
        Toast.makeText(getActivity(), "RUN SUCCESFULLY", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onEventCheckBox(Boolean isSelected) {
        Animation shakeAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.shake_animation);
        btnRunChecklist.startAnimation(shakeAnimation);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bt_run_checklist) {
            showRunChecklistDialog(getActivity());
        } // end if
    }

    private void showRunChecklistDialog(Context context) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_run_checklist);

        final EditText checklistName = dialog.findViewById(R.id.edt_checklist_name);
        final TextView alertTextView = dialog.findViewById(R.id.tv_alert_run_checklist);
        Button confirmRunButton = dialog.findViewById(R.id.bt_confirm_run);
        Button cancelRunButton = dialog.findViewById(R.id.bt_cancel_run);

        confirmRunButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runningChecklistName = checklistName.getText().toString();
                if (runningChecklistName.trim().length() == 0) {
                    alertTextView.setVisibility(View.VISIBLE);
                    Animation shakeAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.shake_animation);
                    checklistName.startAnimation(shakeAnimation);
                } else {
                    //userId = SharedPreferenceUtils.retrieveData(getActivity(), getString(R.string.pref_userId));
                    //orgId = SharedPreferenceUtils.retrieveData(getActivity(), getString(R.string.pref_orgId));

                    //mPresenter.getTemplateObject(String.valueOf(templateId), userId, orgId);
                    // FIXME - HARDCODE HERE FOR TESTING
                    userId = "107757857762956968267";
                    orgId = "1";
                    mPresenter.getTemplateObject("27", "2372592022969346", orgId);
                    dialog.dismiss();
                }
            }
        });

        cancelRunButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    private void handleRunChecklist(Template template) {
        if (null != template) {
            template.setName(runningChecklistName);
            mPresenter.runChecklist(userId, template);
        } // end if
    }
}
