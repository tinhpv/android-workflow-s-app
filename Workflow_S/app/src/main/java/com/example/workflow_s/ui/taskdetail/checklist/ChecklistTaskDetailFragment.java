package com.example.workflow_s.ui.taskdetail.checklist;

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
import com.example.workflow_s.utils.Constant;

import java.util.ArrayList;

/**
 * Workflow_S
 * Created by TinhPV on 2019-06-30
 * Copyright © 2019 TinhPV. All rights reserved
 **/


public class ChecklistTaskDetailFragment extends Fragment implements TaskDetailContract.TaskDetailView {

    private View view;
    private int taskId;
    private String taskName;
    private LinearLayout mContainerLayout;
    private TaskDetailContract.TaskDetailPresenter mPresenter;
    ArrayList<ContentDetail> taskContentList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_task_detail_checklist, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mContainerLayout = view.findViewById(R.id.task_detail_layout);
        getTaskIdFromParentFragment();
        initData();
    }

    public void initData() {
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
                    if (detail.getLabel().isEmpty()) { // image from admin
                        ImageView imgView = (ImageView) inflater.inflate(R.layout.taskdetail_image, mContainerLayout, false);
                        Glide.with(this).load(Constant.IMG_BASE_URL + detail.getImageSrc()).into(imgView);
                        mContainerLayout.addView(imgView);
                    } else { // image from user
                        TextView label = (TextView) inflater.inflate(R.layout.taskdetail_label, mContainerLayout, false);
                        label.setText(detail.getLabel());
                        Button uploadButton = (Button) inflater.inflate(R.layout.taskdetail_button, mContainerLayout, false);
                        mContainerLayout.addView(label);
                        mContainerLayout.addView(uploadButton);
                    } // end if
                    break;
                case "text":
                    if (detail.getLabel().isEmpty()) { // this is will be the textView
                        TextView description = (TextView) inflater.inflate(R.layout.taskdetail_textview, mContainerLayout, false);
                        description.setText(detail.getText());
                        mContainerLayout.addView(description);
                    } else { // will be the edit text
                        TextView label = (TextView) inflater.inflate(R.layout.taskdetail_label, mContainerLayout, false);
                        label.setText(detail.getLabel());
                        mContainerLayout.addView(label);
                        EditText userEditText = (EditText) inflater.inflate(R.layout.taskdetail_edit_text, mContainerLayout, false);
                        mContainerLayout.addView(userEditText);
                    }
                    break;
            } // end switch
        } // end for
    }
}
