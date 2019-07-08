package com.example.workflow_s.ui.taskdetail.checklist;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.workflow_s.R;
import com.example.workflow_s.model.ContentDetail;
import com.example.workflow_s.ui.taskdetail.TaskDetailContract;
import com.example.workflow_s.ui.taskdetail.TaskDetailInteractor;
import com.example.workflow_s.ui.taskdetail.TaskDetailPresenterImpl;
import com.example.workflow_s.utils.Constant;
import com.example.workflow_s.utils.SharedPreferenceUtils;

import java.io.IOException;
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
    Dialog myDialog;
    Button btnCamera,btnGallery;
    private final int REQUEST_CAMERA=1,REQUEST_GALLERY =2;

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
//                    if (detail.getLabel().isEmpty()) { // image from admin
                    final int orderContent = detail.getOrderContent();
                    if (detail.getLabel() == null) {
                        ImageView imgView = (ImageView) inflater.inflate(R.layout.taskdetail_image, mContainerLayout, false);
                        Glide.with(this).load(Constant.IMG_BASE_URL + detail.getImageSrc()).into(imgView);
                        mContainerLayout.addView(imgView);
                    } else { // image from user
                        TextView label = (TextView) inflater.inflate(R.layout.taskdetail_label, mContainerLayout, false);
                        label.setText(detail.getLabel());
                        Button uploadButton = (Button) inflater.inflate(R.layout.taskdetail_button, mContainerLayout, false);
                        mContainerLayout.addView(label);
                        mContainerLayout.addView(uploadButton);
                        uploadButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                //sharedpreference
                                SharedPreferenceUtils.saveCurrentOrder(getContext(),orderContent);
                                showDialog();

                            }
                        });
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
                        EditText userEditText = (EditText) inflater.inflate(R.layout.taskdetail_edit_text, mContainerLayout, false);
                        mContainerLayout.addView(userEditText);
                    }
                    break;
            } // end switch
        } // end for
    }
    public void showDialog()
    {

        myDialog = new Dialog(getContext());
        myDialog.setContentView(R.layout.dialog_upload_picture);
        myDialog.setTitle("Choose Image");
        btnCamera=myDialog.findViewById(R.id.btnCamera);
        btnGallery=myDialog.findViewById(R.id.btnGallery);
        btnGallery.setEnabled(true);
        btnCamera.setEnabled(true);
        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentGallery = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intentGallery.setType("image/*");
                startActivityForResult(intentGallery,REQUEST_GALLERY);
            }
        });

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(intentCamera.resolveActivity(getContext().getPackageManager())!=null)
                {
                    startActivityForResult(intentCamera,REQUEST_CAMERA);

                }

            }
        });
        myDialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int order =  SharedPreferenceUtils.retrieveDataInt(getContext(),getContext().getString(R.string.order));
        Log.i("order", order + "");
        if(resultCode== Activity.RESULT_OK)
        {
            if(requestCode==REQUEST_CAMERA)
            {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                // set image to ui here
            }else if(requestCode==REQUEST_GALLERY)
            {
                Uri uri = data.getData();
                try {

                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(),uri);
                    // set image here
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
