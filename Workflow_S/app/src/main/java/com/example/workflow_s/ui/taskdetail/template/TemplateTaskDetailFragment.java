package com.example.workflow_s.ui.taskdetail.template;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.workflow_s.R;
import com.example.workflow_s.model.ContentDetail;
import com.example.workflow_s.model.Task;
import com.example.workflow_s.ui.taskdetail.TaskDetailContract;
import com.example.workflow_s.ui.taskdetail.TaskDetailInteractor;
import com.example.workflow_s.ui.taskdetail.TaskDetailPresenterImpl;
import com.example.workflow_s.utils.Constant;

import java.util.ArrayList;

/**
 * Workflow_S
 * Created by TinhPV on 2019-07-03
 * Copyright © 2019 TinhPV. All rights reserved
 **/


public class TemplateTaskDetailFragment extends Fragment implements TaskDetailContract.TaskDetailView {

    View view;
    private int taskId;
    private String taskName;
    private LinearLayout mContainerLayout;
    private TaskDetailContract.TaskDetailPresenter mPresenter;

    @Override
    public void finishedUploadImage(int orderContent) {
        // do nothings
    }

    @Override
    public void finishedSaveContent() {

    }

    @Override
    public void finishedGetTaskDetail(Task task) {
        // do nothing huh?
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void finishedCompleteTask() {
        // do nothings huh?
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_task_detail_template, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mContainerLayout = view.findViewById(R.id.task_detail_layout);
        getTaskIdFromParentFragment();
        initData();
    }

    private void initData() {

        mPresenter = new TaskDetailPresenterImpl(this, new TaskDetailInteractor());
        mPresenter.loadDetails(taskId);
    }

    private void getTaskIdFromParentFragment() {
        Bundle arguments = getArguments();
        taskId = Integer.parseInt(arguments.getString("taskId"));
        taskName = arguments.getString("taskName");
        getActivity().setTitle(taskName);
    }


    @Override
    public void setDataToView(ArrayList<ContentDetail> datasource) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        for (ContentDetail detail : datasource) {
            switch (detail.getType()) {
                case "img":
//                    if (detail.getLabel().isEmpty()) { // image from admin
                    if (detail.getLabel() == null) {
                        ImageView imgView = (ImageView) inflater.inflate(R.layout.taskdetail_image, mContainerLayout, false);
                        Glide.with(this).load(Constant.IMG_BASE_URL + detail.getImageSrc()).into(imgView);
                        mContainerLayout.addView(imgView);
                    } else { // image from user
                        TextView label = (TextView) inflater.inflate(R.layout.taskdetail_label, mContainerLayout, false);
                        label.setText(detail.getLabel());
                        Button uploadButton = (Button) inflater.inflate(R.layout.taskdetail_button_disable, mContainerLayout, false);
                        mContainerLayout.addView(label);
                        mContainerLayout.addView(uploadButton);
                    } // end if
                    break;
                case "text":
//                    if (detail.getLabel().isEmpty()) { // this is will be the textView
                    if (detail.getLabel() == null) {
                        TextView description = (TextView) inflater.inflate(R.layout.taskdetail_textview, mContainerLayout, false);
                        description.setText(detail.getText());
                        mContainerLayout.addView(description);
                    } else { // will be the edit text
                        TextView label = (TextView) inflater.inflate(R.layout.taskdetail_label, mContainerLayout, false);
                        label.setText(detail.getLabel());
                        mContainerLayout.addView(label);
                        EditText userEditText = (EditText) inflater.inflate(R.layout.taskdetail_edittext_disable, mContainerLayout, false);
                        mContainerLayout.addView(userEditText);
                    }
                    break;
            } // end switch
        } // end for
    }
}
