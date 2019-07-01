package com.example.workflow_s.ui.taskdetail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.workflow_s.R;
import com.example.workflow_s.model.ContentDetail;
import com.example.workflow_s.utils.Constant;

import java.util.ArrayList;

/**
 * Workflow_S
 * Created by TinhPV on 2019-06-30
 * Copyright © 2019 TinhPV. All rights reserved
 **/


public class TaskDetailFragment extends Fragment implements TaskDetailContract.TaskDetailView {
    private View view;
    private int taskId;
    private LinearLayout mContainerLayout;

    private TaskDetailContract.TaskDetailPresenter mPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_task_detail, container, false);
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
    }

    @Override
    public void setDataToView(ArrayList<ContentDetail> datasource) {
        //LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LayoutInflater inflater = LayoutInflater.from(getContext());
        for (int i = 0; i < datasource.size(); i++) {
            if (datasource.get(i).getType().equals("img")) {
                if (datasource.get(i).getLabel().isEmpty()) {
                    //LinearLayout imageLayout = findViewById(R.id.img_taskdetail_layout);
                    //View childImg = inflater.inflate(R.layout.taskdetail_image, (ViewGroup) )
                    ImageView imgView = (ImageView) inflater.inflate(R.layout.taskdetail_image, mContainerLayout, false);
                    //ImageView imageView = (ImageView) inflater.inflate(R.layout.taskdetail_image, mContainerLayout, true);
                    Log.i("IMG", "setDataToView: " + Constant.IMG_BASE_URL + datasource.get(i).getImageSrc()) ;

                    Glide.with(this).load(Constant.IMG_BASE_URL + datasource.get(i).getImageSrc()).into(imgView);
                    mContainerLayout.addView(imgView);
                }
            } else if (datasource.get(i).getType().equals("text")) {
                if (datasource.get(i).getLabel().isEmpty()) {
                    //LinearLayout textLayout = findViewById(R.id.textview_taskdetail_layout);
//                    TextView textView = findViewById(R.id.txt_task_detail);
//                    textView.setText(datasource.get(i).getText());

                    TextView txtView = (TextView) inflater.inflate(R.layout.taskdetail_textview, mContainerLayout, false);
                    txtView.setText(datasource.get(i).getText());
                    mContainerLayout.addView(txtView);

                }
            }
        }
    }
}
