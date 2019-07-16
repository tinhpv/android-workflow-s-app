package com.example.workflow_s.ui.taskdetail.dialog.package_dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.workflow_s.R;
import com.example.workflow_s.ui.template.dialog_fragment.TemplateDialogFragment;

/**
 * Workflow_S
 * Created by TinhPV on 2019-07-16
 * Copyright © 2019 TinhPV. All rights reserved
 **/


public class ImageDialogFragment extends BottomSheetDialogFragment implements View.OnClickListener {

    View view;
    Button cameraButton, galleryButton;

    public interface ClickEventListener {
        void onFinishedChooseImagePickingMethod(boolean doUseCamera);
    }

    ClickEventListener mListener;


    public static ImageDialogFragment newInstance() {
        return new ImageDialogFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (ClickEventListener) getTargetFragment();
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        view = View.inflate(getContext(), R.layout.fragment_checklistdetail_dialog_image, null);
        dialog.setContentView(view);
        ((View) view.getParent()).setBackgroundColor(getResources().getColor(android.R.color.transparent));
        cameraButton = view.findViewById(R.id.bt_camera_pick);
        cameraButton.setOnClickListener(this);
        galleryButton = view.findViewById(R.id.bt_gallery_pick);
        galleryButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bt_camera_pick) {
            mListener.onFinishedChooseImagePickingMethod(true);
        } else {
            mListener.onFinishedChooseImagePickingMethod(false);
        }
        dismiss();
    }
}
