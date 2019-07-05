package com.example.workflow_s.ui.taskdetail.template;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
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
import com.example.workflow_s.ui.taskdetail.TaskDetailContract;
import com.example.workflow_s.ui.taskdetail.TaskDetailInteractor;
import com.example.workflow_s.ui.taskdetail.TaskDetailPresenterImpl;

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
    ArrayList<ContentDetail> taskContentList;

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

        // FIXED - STATIC DATA HERE FOR TESTING ONLY

//        String textForTesting1 = "First thing's first you're going to need to record the candidate's details you're performing the check on, on behalf of the hiring manager. Do so using the form fields below.";
//        String textForTesting2 = "Employment background checks are vital for not only you as an employer but also for your company. As a hiring manager, it is your responsibility to exercise caution or due diligence by uncovering any potential complications a person may have in their past that they potentially could bring to the workplace.";
//        String imageSrcForTesting1 = "https://images.unsplash.com/photo-1555436169-20e93ea9a7ff?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=2850&q=80";
//        String imageSrcForTesting2 = "https://images.unsplash.com/photo-1519336367661-eba9c1dfa5e9?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=2850&q=80";
//
//        taskContentList = new ArrayList<>();
//        taskContentList.add(new ContentDetail(1, "img", "", imageSrcForTesting1, 1, 1, ""));
//        taskContentList.add(new ContentDetail(2, "text", textForTesting1, "", 1, 2, ""));
//        taskContentList.add(new ContentDetail(3, "text", "", "", 1, 3, "Input your name"));
//        taskContentList.add(new ContentDetail(4, "img", "", "", 1, 4, "Choose a picture"));
//        taskContentList.add(new ContentDetail(5, "text", textForTesting2, "", 1, 5, ""));
//        taskContentList.add(new ContentDetail(6, "img", "", imageSrcForTesting2, 1, 6, ""));

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
                        Glide.with(this).load(detail.getImageSrc()).into(imgView);
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
