package com.example.workflow_s.ui.taskdetail.checklist;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.example.workflow_s.model.Comment;
import com.example.workflow_s.model.ContentDetail;
import com.example.workflow_s.model.Task;
import com.example.workflow_s.model.TaskMember;
import com.example.workflow_s.ui.comment.CommentAdapter;
import com.example.workflow_s.ui.comment.CommentFragment;
import com.example.workflow_s.ui.taskdetail.TaskDetailContract;
import com.example.workflow_s.ui.taskdetail.TaskDetailInteractor;
import com.example.workflow_s.ui.taskdetail.TaskDetailPresenterImpl;
import com.example.workflow_s.ui.taskdetail.dialog.assignment.AssigningDialogFragment;
import com.example.workflow_s.ui.taskdetail.dialog.package_dialog.ImageDialogFragment;
import com.example.workflow_s.ui.taskdetail.dialog.time_setting.TimeSettingDialogFragment;
import com.example.workflow_s.utils.CommonUtils;
import com.example.workflow_s.utils.Constant;
import com.example.workflow_s.utils.ImageUtils;
import com.example.workflow_s.utils.SharedPreferenceUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Workflow_S
 * Created by TinhPV on 2019-06-30
 * Copyright © 2019 TinhPV. All rights reserved
 **/


public class ChecklistTaskDetailFragment extends Fragment implements TaskDetailContract.TaskDetailView,
        View.OnClickListener, ImageDialogFragment.ClickEventListener {

    private View view;
    private int taskId, checklistId;
    private String currentPhotoPath, taskStatus, checklistUserId, userId;
    private LinearLayout mContainerLayout;
    private FloatingActionButton buttonCompleteTask, buttonSaveContent;
    private TextView tvAlertMember, tvCommentNumber;
    private TaskDetailContract.TaskDetailPresenter mPresenter;
    private Task currentTask;

    private ArrayList<ContentDetail> mContentDetailArrayList;
    private List<TaskMember> mTaskMemberList;
    private HashMap<String, String> imageDataEncoded;
    private Boolean isChanged, isTaskMember;
    private int totalImagesNumberToUpload, location;

    private RecyclerView commentRV;
    private CommentAdapter mCommentAdapter;
    private RecyclerView.LayoutManager commentLayoutManager;

    private LinearLayout layoutChatbox;
    private CircleImageView userImageView;


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        switch (item.getItemId()) {
            case R.id.action_task_assign:
                AssigningDialogFragment assigningDialogFragment = AssigningDialogFragment.newInstance(taskId, checklistId, checklistUserId, isTaskMember);
                assigningDialogFragment.show(fm, "fragment_assign_");
                return true;

            case R.id.action_task_set_time:
                TimeSettingDialogFragment settingDialogFragment = TimeSettingDialogFragment.newInstance(taskId, mTaskMemberList);
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
        tvAlertMember = view.findViewById(R.id.tv_alert_member);

        buttonCompleteTask = view.findViewById(R.id.bt_complete_task);
        buttonCompleteTask.setOnClickListener(this);
        buttonSaveContent = view.findViewById(R.id.bt_save_content);
        buttonSaveContent.setOnClickListener(this);

        userImageView = view.findViewById(R.id.img_user_commented);
        tvCommentNumber = view.findViewById(R.id.tv_comment_number);
        layoutChatbox = view.findViewById(R.id.layout_chatbox);
        layoutChatbox.setOnClickListener(this);

        userId = SharedPreferenceUtils.retrieveData(getContext(), getActivity().getString(R.string.pref_userId));

        getTaskIdFromParentFragment();
        bindUI();
        initData();
    }

    private void bindUI() {
        commentRV = view.findViewById(R.id.rv_comment);
        commentLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        commentRV.setLayoutManager(commentLayoutManager);

        mCommentAdapter = new CommentAdapter(getContext());
        commentRV.setAdapter(mCommentAdapter);

        String avatar = SharedPreferenceUtils.retrieveData(getActivity(), getActivity().getString(R.string.pref_avatar));
        Glide.with(getActivity()).load(avatar).into(userImageView);
    }

    private void getTaskIdFromParentFragment() {
        Bundle arguments = getArguments();
        taskId = Integer.parseInt(arguments.getString("taskId"));
        location = arguments.getInt("location_activity");
        checklistId = arguments.getInt("checklistId");
        checklistUserId = arguments.getString("checklistUserId");

        getActivity().setTitle("Task Detail");

        isChanged = false;
        imageDataEncoded = new HashMap<String, String>();
    }


    public void initData() {
        totalImagesNumberToUpload = 0;
        mPresenter = new TaskDetailPresenterImpl(this, new TaskDetailInteractor());
        mPresenter.getTaskMember(taskId);
        mPresenter.loadComment(taskId);
    }

    @Override
    public void finishGetTaskMember(List<TaskMember> memberList) {
        if (memberList != null) {
            mTaskMemberList = memberList;
            isTaskMember = checkIfCurrentUserIsTaskMember();
            updateUIWithAuth(isTaskMember);
            mPresenter.getTask(taskId);
        }
    }

    @Override
    public void finishLoadComment(List<Comment> commentList) {
        if (null != commentList) {
            tvCommentNumber.setText("Comments (" + commentList.size() + ")");
            mCommentAdapter.setCommentList(commentList);
        }
    }

    public boolean checkIfCurrentUserIsTaskMember() {
        String userId = SharedPreferenceUtils.retrieveData(getContext(), getString(R.string.pref_userId));

        if (userId.equals(checklistUserId)) {
            return true;
        }

        for (TaskMember member : mTaskMemberList) {
            if (member.getUserId().equals(userId)) {
                return true;
            }
        }

        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void finishedGetTaskDetail(Task task) {
        if (null != task) {
            currentTask = task;
            taskStatus = currentTask.getTaskStatus();
            updateLayout(taskStatus);
            mPresenter.loadDetails(taskId);
        }
    }

    void updateUIWithAuth(boolean isMember) {
        if (!isMember) {
            tvAlertMember.setVisibility(View.VISIBLE);
            buttonCompleteTask.hide();
            buttonSaveContent.hide();
        } else {
            tvAlertMember.setVisibility(View.GONE);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void updateLayout(String taskStatus) {

        if (taskStatus.equals(getString(R.string.task_done))) {
            buttonCompleteTask.setImageDrawable(ContextCompat.getDrawable(getContext(), R.mipmap.reactivate_task_ic));
            tvAlertMember.setVisibility(View.VISIBLE);
            tvAlertMember.setBackgroundColor(getContext().getColor(R.color.accomplishedColor));
            tvAlertMember.setText("This task is completed");

        } else if (taskStatus.equals(getString(R.string.task_running))) {
            buttonCompleteTask.setImageDrawable(ContextCompat.getDrawable(getContext(), R.mipmap.checkmark_ic_white));
            tvAlertMember.setVisibility(View.INVISIBLE);

        } else if (taskStatus.equals("Failed")) {
            tvAlertMember.setVisibility(View.VISIBLE);
            tvAlertMember.setText("This task is skipped!!");
            tvAlertMember.setBackgroundResource(R.color.home_red);
            tvAlertMember.setTextColor(getContext().getColor(R.color.extreme_white));
            buttonCompleteTask.hide();
            buttonSaveContent.hide();
        }
    }


    private void setupImageView(ContentDetail detail, final int orderOfContent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());

        if (detail.getLabel() == null) {

            ImageView imgView = (ImageView) inflater.inflate(R.layout.taskdetail_image,
                    mContainerLayout,
                    false);

            Glide.with(this)
                    .load(Constant.IMG_BASE_URL + detail.getImageSrc())
                    .into(imgView);

            mContainerLayout.addView(imgView);

        } else {  // image from user

            // setup label above "upload" button
            TextView label = (TextView) inflater.inflate(R.layout.taskdetail_label,
                                                         mContainerLayout,
                                                        false);
            label.setText(detail.getLabel());

            // setup hidden "imageView" for setting right after picking an image
            String tmpImageTag = "img_task_detail_" + orderOfContent;
            ImageView tmpImg = (ImageView) inflater.inflate(R.layout.taskdetail_image,
                                                            mContainerLayout,
                                                            false);
            tmpImg.setTag(tmpImageTag);

            tmpImg.setVisibility(View.GONE);

            // if the image has been set before...
            if (!detail.getImageSrc().isEmpty()) {
                tmpImg.setVisibility(View.VISIBLE);
                Picasso.with(getActivity())
                        .load(detail.getImageSrc())
                        .into(tmpImg);
            }

            mContainerLayout.addView(tmpImg);
            mContainerLayout.addView(label);

            if (isTaskMember) {
                if (currentTask.getTaskStatus().equals("Failed")) {

                    // button to upload image
                    Button uploadButton = (Button) inflater.inflate(R.layout.taskdetail_button_disable,
                            mContainerLayout,
                            false);

                    mContainerLayout.addView(uploadButton);


                } else {

                    // button to upload image
                    Button uploadButton = (Button) inflater.inflate(R.layout.taskdetail_button,
                            mContainerLayout,
                            false);

                    mContainerLayout.addView(uploadButton);

                    uploadButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //shared preferences
                            SharedPreferenceUtils.saveCurrentOrder(getContext(), orderOfContent);
                            prepareShowingCategoryDialog();
                        }
                    });

                }

            } else {
                Button uploadButton = (Button) inflater.inflate(R.layout.taskdetail_button_disable,
                        mContainerLayout,
                        false);
                mContainerLayout.addView(uploadButton);
            }

        } // end if
    }

    private void setupEditText(ContentDetail detail, int orderOfContent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());

        // the label above edit-text
        TextView label = (TextView) inflater.inflate(R.layout.taskdetail_label,
                                                     mContainerLayout,
                                                    false);

        label.setText(detail.getLabel());
        mContainerLayout.addView(label);

        // ... and the edit text here
        if (isTaskMember) {
            if (currentTask.getTaskStatus().equals("Failed")) {
                EditText userEditText = (EditText) inflater.inflate(R.layout.taskdetail_edittext_disable, mContainerLayout, false);
                mContainerLayout.addView(userEditText);
            } else {
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

                String tmpEdtTag = "edit_text_detail_" + orderOfContent;
                userEditText.setTag(tmpEdtTag);

                if (detail.getText() != null) {
                    userEditText.setText(detail.getText());
                }

                mContainerLayout.addView(userEditText);
            }


        } else {
            EditText userEditText = (EditText) inflater.inflate(R.layout.taskdetail_edittext_disable, mContainerLayout, false);
            mContainerLayout.addView(userEditText);
        }
    }

    @Override
    public void setDataToView(ArrayList<ContentDetail> datasource) {

        if (datasource != null) {
            mContentDetailArrayList = datasource;
            LayoutInflater inflater = LayoutInflater.from(getContext());

            for (ContentDetail detail : datasource) {

                // the order number of detail
                final int orderContent = detail.getOrderContent();

                switch (detail.getType()) {
                    case "img":
                        setupImageView(detail, orderContent);
                        break;


                    case "text":
                        TextView description = (TextView) inflater.inflate(R.layout.taskdetail_textview,
                                mContainerLayout,
                                false);

                        description.setText(detail.getText());
                        mContainerLayout.addView(description);
                        break;

                    case "edit-text":
                        setupEditText(detail, orderContent);
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

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_complete_task:
                if (taskStatus.equals(getString(R.string.task_done))) {
                    taskStatus = getString(R.string.task_running);
                    mPresenter.completeTask(userId, taskId, getString(R.string.task_running));
                } else {
                    taskStatus = getString(R.string.task_done);
                    handleSaveContentDetail();
                    mPresenter.completeTask(userId, taskId, getString(R.string.task_done));
                }

                updateLayout(taskStatus);
                break;

            case R.id.layout_chatbox:
                Bundle args = new Bundle();
                args.putInt("taskId", taskId);
                CommonUtils.replaceFragments(getContext(), CommentFragment.class, args, true);
                break;

            case R.id.bt_save_content:
                handleSaveContentDetail();
                break;
        }
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
