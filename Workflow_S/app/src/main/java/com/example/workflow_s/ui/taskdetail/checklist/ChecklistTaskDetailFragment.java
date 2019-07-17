package com.example.workflow_s.ui.taskdetail.checklist;

import android.app.Dialog;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.core.content.FileProvider;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.example.workflow_s.model.Task;
import com.example.workflow_s.ui.taskdetail.TaskDetailContract;
import com.example.workflow_s.ui.taskdetail.TaskDetailInteractor;
import com.example.workflow_s.ui.taskdetail.TaskDetailPresenterImpl;
import com.example.workflow_s.ui.taskdetail.dialog.assignment.AssigningDialogFragment;
import com.example.workflow_s.ui.taskdetail.dialog.package_dialog.ImageDialogFragment;
import com.example.workflow_s.ui.taskdetail.dialog.time_setting.TimeSettingDialogFragment;
import com.example.workflow_s.utils.Constant;
import com.example.workflow_s.utils.FirebaseUtils;
import com.example.workflow_s.utils.ImageUtils;
import com.example.workflow_s.utils.SharedPreferenceUtils;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Workflow_S
 * Created by TinhPV on 2019-06-30
 * Copyright © 2019 TinhPV. All rights reserved
 **/


public class ChecklistTaskDetailFragment extends Fragment implements TaskDetailContract.TaskDetailView,
        View.OnClickListener, FirebaseUtils.UploadImageListener, ImageDialogFragment.ClickEventListener {


    private View view;
    private int taskId, checklistId;
    private String taskName, currentPhotoPath, taskStatus;
    private LinearLayout mContainerLayout;
    private Button buttonCompleteTask, buttonSaveContent;
    private TaskDetailContract.TaskDetailPresenter mPresenter;
    private Task currentTask;
    private Dialog myDialog;
    private Button btnCamera,btnGallery;

    private ArrayList<ContentDetail> mContentDetailArrayList, mChecklistMembers;
    private HashMap<String, String> imageDataEncoded;
    private Boolean isChanged;
    private int totalImagesNumberToUpload, location;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        switch (item.getItemId()) {
            case R.id.action_task_assign:
                AssigningDialogFragment assigningDialogFragment = AssigningDialogFragment.newInstance(taskId, checklistId);
                assigningDialogFragment.show(fm, "fragment_assign_");
                return true;

            case R.id.action_task_set_time:
                TimeSettingDialogFragment settingDialogFragment = TimeSettingDialogFragment.newInstance(taskId);
                settingDialogFragment.show(fm, "fragment_set_time");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_detail, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (isChanged) {
            Toast.makeText(getContext(), "Still not saved", Toast.LENGTH_SHORT).show();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_task_detail_checklist, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mContainerLayout = view.findViewById(R.id.task_detail_layout);
        buttonCompleteTask = view.findViewById(R.id.bt_complete_task);
        buttonCompleteTask.setOnClickListener(this);
        buttonSaveContent = view.findViewById(R.id.bt_save_content);
        buttonSaveContent.setOnClickListener(this);
        getTaskIdFromParentFragment();
        initData();
    }


    public void initData() {
        totalImagesNumberToUpload = 0;
        mPresenter = new TaskDetailPresenterImpl(this, new TaskDetailInteractor());
        mPresenter.getTask(taskId);
    }

    private void getTaskIdFromParentFragment() {
        Bundle arguments = getArguments();
        taskId = Integer.parseInt(arguments.getString("taskId"));
        location = arguments.getInt("location_activity");
        checklistId = arguments.getInt("checklistId");

        getActivity().setTitle("Task Detail");

        isChanged = false;
        imageDataEncoded = new HashMap<String, String>();
    }

    @Override
    public void finishedGetTaskDetail(Task task) {
        if (null != task) {
            currentTask = task;
            taskStatus = currentTask.getTaskStatus();
            updateButtonLayout(taskStatus);
            mPresenter.loadDetails(taskId);
        }
    }

    private void updateButtonLayout(String taskStatus) {
        if (taskStatus.equals(getString(R.string.task_done))) {
            buttonCompleteTask.setText("Reactive");
        } else if (taskStatus.equals(getString(R.string.task_running))) {
            buttonCompleteTask.setText("Complete");
        }
    }

    @Override
    public void setDataToView(ArrayList<ContentDetail> datasource) {
        if (datasource != null) {
            mContentDetailArrayList = datasource;
            LayoutInflater inflater = LayoutInflater.from(getContext());

            for (ContentDetail detail : datasource) {

                final int orderContent = detail.getOrderContent();
                switch (detail.getType()) {
                    case "img":
//                    if (detail.getLabel().isEmpty()) { // image from admin
                        if (detail.getLabel() == null) {
                            ImageView imgView = (ImageView) inflater.inflate(R.layout.taskdetail_image, mContainerLayout, false);
                            Glide.with(this).load(Constant.IMG_BASE_URL + detail.getImageSrc()).into(imgView);
                            mContainerLayout.addView(imgView);
                        } else {

                            // image from user
                            TextView label = (TextView) inflater.inflate(R.layout.taskdetail_label, mContainerLayout, false);
                            label.setText(detail.getLabel());

                            String tmpImageTag = "img_task_detail_" + orderContent;
                            ImageView tmpImg = (ImageView) inflater.inflate(R.layout.taskdetail_image, mContainerLayout, false);
                            tmpImg.setTag(tmpImageTag);

                            tmpImg.setVisibility(View.VISIBLE);
                            ImageUtils.setPicToImageView(tmpImg, "/storage/emulated/0/DCIM/Camera/IMG_20190717_212922.jpg");

//                            tmpImg.setVisibility(View.GONE);

                            if (!detail.getImageSrc().isEmpty()) {
                                tmpImg.setVisibility(View.VISIBLE);
                                Picasso.with(getActivity())
                                        .load(detail.getImageSrc())
                                        .into(tmpImg);
                            }

                            mContainerLayout.addView(tmpImg);

                            // button to upload image
                            Button uploadButton = (Button) inflater.inflate(R.layout.taskdetail_button, mContainerLayout, false);
                            mContainerLayout.addView(label);
                            mContainerLayout.addView(uploadButton);

                            uploadButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //shared preferences
                                    SharedPreferenceUtils.saveCurrentOrder(getContext(),orderContent);
                                    prepareShowingCategoryDialog();
                                }
                            });
                        } // end if
                        break;


                    case "text":
                        TextView description = (TextView) inflater.inflate(R.layout.taskdetail_textview, mContainerLayout, false);
                        description.setText(detail.getText());
                        mContainerLayout.addView(description);
                        break;

                    case "edit-text":
                        TextView label = (TextView) inflater.inflate(R.layout.taskdetail_label, mContainerLayout, false);
                        label.setText(detail.getLabel());
                        mContainerLayout.addView(label);
                        EditText userEditText = (EditText) inflater.inflate(R.layout.taskdetail_edit_text, mContainerLayout, false);
                        userEditText.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                isChanged = true;
                            }

                            @Override
                            public void afterTextChanged(Editable s) {

                            }
                        });

                        String tmpEdtTag = "edit_text_detail_" + orderContent;
                        userEditText.setTag(tmpEdtTag);

                        if (detail.getText() != null) {
                            userEditText.setText(detail.getText());
                        }

                        mContainerLayout.addView(userEditText);
                        break;
                } // end switch
            } // end for
        }
    }


    private void prepareShowingCategoryDialog() {
        ImageDialogFragment imageDialogFragment = ImageDialogFragment.newInstance();
        imageDialogFragment.setTargetFragment(this, 0);
        imageDialogFragment.show(getActivity().getSupportFragmentManager(), "image_dialog");
    }


    @Override
    public void onFinishedPickingImages(String imagePath, Uri imageData) {
        int order =  SharedPreferenceUtils.retrieveDataInt(getContext(), getString(R.string.order));

        String tmpImageTag = "img_task_detail_" + order;
        ImageView imageToShow = mContainerLayout.findViewWithTag(tmpImageTag);
        imageToShow.setVisibility(View.VISIBLE);

        isChanged = true;
        if (imagePath != null) {
            currentPhotoPath = imagePath;
            ImageUtils.setPicToImageView(imageToShow, currentPhotoPath);
            totalImagesNumberToUpload++;
            imageDataEncoded.put(tmpImageTag, currentPhotoPath);
        } else if (imageData != null) {
            imageToShow.setImageURI(imageData);
            totalImagesNumberToUpload++;
            String picturePath = ImageUtils.getPath(getActivity().getApplicationContext(), imageData);
            Log.d("VUTINH", "onFinishedPickingImages: " + picturePath);
            imageDataEncoded.put(tmpImageTag, picturePath);
        }
    }


    @Override
    public void finishedSaveContent() {
        Toast.makeText(getContext(), "Save Successfully", Toast.LENGTH_SHORT).show();
    }

    private void handleSaveContentDetail() {

        if (isChanged) {
            isChanged = false;

            // fill data
            for (ContentDetail detail : mContentDetailArrayList) {
                int orderContent = detail.getOrderContent();
                switch (detail.getType()) {
                    case "img":
                        if (detail.getLabel() != null && (totalImagesNumberToUpload != 0)) {
                            String tmpImageTag = "img_task_detail_" + orderContent;
                            String imagePath = imageDataEncoded.get(tmpImageTag);
                            File file = new File(imagePath);
                            handleUploadImage(file, detail.getId(), orderContent);
                            detail.setImageSrc(file.getName());
                        }
                        break;

                    case "edit-text":
                        if (detail.getLabel() != null) {
                            String tmpEdtTag = "edit_text_detail_" + orderContent;
                            EditText editText = mContainerLayout.findViewWithTag(tmpEdtTag);
                            String textToGet = editText.getText().toString().trim();
                            if (textToGet.length() > 0) {
                                detail.setText(textToGet);
                            }
                        }
                        break;
                } // end switch
            } // end for

            if (totalImagesNumberToUpload == 0) {
                mPresenter.updateTaskDetail(mContentDetailArrayList);
            }


        } // end if
    }

    private void handleUploadImage(File file, int contentId, int orderContent) {
        try {
            RequestBody photoContent = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part photo = MultipartBody.Part.createFormData("image", file.getName(), photoContent);
            mPresenter.uploadImage(contentId, orderContent, photo);
        } catch (Exception e) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void finishedUploadImage(int orderContent) {
        totalImagesNumberToUpload--;
        if (totalImagesNumberToUpload == 0) {
            mPresenter.updateTaskDetail(mContentDetailArrayList);
        }
    }

    @Override
    public void finishedCompleteTask() {
        Toast.makeText(getContext(), "Change task status successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_complete_task:
                if (taskStatus.equals(getString(R.string.task_done))) {
                    taskStatus = getString(R.string.task_running);
                    mPresenter.completeTask(taskId, getString(R.string.task_running));
                } else {
                    taskStatus = getString(R.string.task_done);
                    handleSaveContentDetail();
                    mPresenter.completeTask(taskId, getString(R.string.task_done));
                }

                updateButtonLayout(taskStatus);
                break;
            case R.id.bt_save_content:
                handleSaveContentDetail();
                break;
        }
    }

    @Override
    public void onFinishedUploadToFirebase(Uri downloadImageURL, int orderContent) {
        if (null != downloadImageURL) {
            totalImagesNumberToUpload--;
            mContentDetailArrayList.get(orderContent - 1).setImageSrc(downloadImageURL.toString());
        }

        if (totalImagesNumberToUpload == 0) {
            mPresenter.updateTaskDetail(mContentDetailArrayList);
        } // end if
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        switch (location) {
            case 1:
                getActivity().setTitle("Home");
                break;
            case 2:
                getActivity().setTitle("Checklist's Task");
                break;
        }
    }


}
