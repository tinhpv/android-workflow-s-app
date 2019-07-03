package com.example.workflow_s.ui.taskdetail;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
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
import com.example.workflow_s.model.Task;
import com.example.workflow_s.utils.Constant;

import org.w3c.dom.Text;

import java.io.IOException;
import java.nio.file.Path;
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
    Dialog myDialog;
    Button btnCamera,btnGallery;
    private final int REQUEST_CAMERA=1,REQUEST_GALLERY =2;
    //
    private Task mTask;

    private TaskDetailContract.TaskDetailPresenter mPresenter;

    ArrayList<ContentDetail> taskContentList;

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
        //sau khi get data tu text view,... goi presenter
       // postTask(null);
    }

    public void initData() {
        mPresenter = new TaskDetailPresenterImpl(this, new TaskDetailInteractor());
        mPresenter.loadDetails(taskId);



        // FIXME - STATIC DATA HERE FOR TESTING ONLY

        String textForTesting1 = "First thing's first you're going to need to record the candidate's details you're performing the check on, on behalf of the hiring manager. Do so using the form fields below.";
        String textForTesting2 = "Employment background checks are vital for not only you as an employer but also for your company. As a hiring manager, it is your responsibility to exercise caution or due diligence by uncovering any potential complications a person may have in their past that they potentially could bring to the workplace.";
        String imageSrcForTesting1 = "https://images.unsplash.com/photo-1555436169-20e93ea9a7ff?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=2850&q=80";
        String imageSrcForTesting2 = "https://images.unsplash.com/photo-1519336367661-eba9c1dfa5e9?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=2850&q=80";

        taskContentList = new ArrayList<>();
        taskContentList.add(new ContentDetail(1, "img", "", imageSrcForTesting1, 1, 1, ""));
        taskContentList.add(new ContentDetail(2, "text", textForTesting1, "", 1, 2, ""));
        taskContentList.add(new ContentDetail(3, "text", "", "", 1, 3, "Input your name"));
        taskContentList.add(new ContentDetail(4, "img", "", "", 1, 4, "Choose a picture"));
        taskContentList.add(new ContentDetail(5, "text", textForTesting2, "", 1, 5, ""));
        taskContentList.add(new ContentDetail(6, "img", "", imageSrcForTesting2, 1, 6, ""));
    }

    private void getTaskIdFromParentFragment() {
        Bundle arguments = getArguments();
        taskId = Integer.parseInt(arguments.getString("taskId"));
    }

    @Override
    public void setDataToView(ArrayList<ContentDetail> datasource) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        for (ContentDetail detail : taskContentList) {
            switch (detail.getType()) {
                case "img":
                    if (detail.getLabel().isEmpty()) { // image from admin
                        ImageView imgView = (ImageView) inflater.inflate(R.layout.taskdetail_image, mContainerLayout, false);
                        Glide.with(this).load(detail.getImageSrc()).into(imgView);
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
                                showDialog();

                            }
                        });
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

//        for (int i = 0; i < datasource.size(); i++) {
//            if (datasource.get(i).getType().equals("img")) {
//                if (datasource.get(i).getLabel().isEmpty()) {
//                    //LinearLayout imageLayout = findViewById(R.id.img_taskdetail_layout);
//                    //View childImg = inflater.inflate(R.layout.taskdetail_image, (ViewGroup) )
//                    ImageView imgView = (ImageView) inflater.inflate(R.layout.taskdetail_image, mContainerLayout, false);
//                    //ImageView imageView = (ImageView) inflater.inflate(R.layout.taskdetail_image, mContainerLayout, true);
//                    Log.i("IMG", "setDataToView: " + Constant.IMG_BASE_URL + datasource.get(i).getImageSrc()) ;
//
//                    Glide.with(this).load(Constant.IMG_BASE_URL + datasource.get(i).getImageSrc()).into(imgView);
//                    mContainerLayout.addView(imgView);
//                }
//            } else if (datasource.get(i).getType().equals("text")) {
//                if (datasource.get(i).getLabel().isEmpty()) {
//                    //LinearLayout textLayout = findViewById(R.id.textview_taskdetail_layout);
////                    TextView textView = findViewById(R.id.txt_task_detail);
////                    textView.setText(datasource.get(i).getText());
//
//                    TextView txtView = (TextView) inflater.inflate(R.layout.taskdetail_textview, mContainerLayout, false);
//                    txtView.setText(datasource.get(i).getText());
//                    mContainerLayout.addView(txtView);
//
//                }
//            }
//        } // end for
    }



//    @Override
//    public void baonguoidung(String abc) {
//        //diaglog.show bao ng dung thanh cong
//        //Toast.
//    }
    //post 1
//    public void postTask(Task task) {
//        mPresenter = new TaskDetailPresenterImpl(this, new TaskDetailInteractor());
//        mPresenter.postSomething(task);
//    }

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
