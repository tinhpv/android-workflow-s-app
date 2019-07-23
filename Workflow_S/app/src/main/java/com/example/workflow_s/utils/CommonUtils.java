package com.example.workflow_s.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.workflow_s.R;

/**
 * Workflow_S
 * Created by TinhPV on 2019-06-30
 * Copyright © 2019 TinhPV. All rights reserved
 **/


public class CommonUtils {

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void showDialog(Context context, String message) {
        final Dialog errorDialog = new Dialog(context);
        errorDialog.setContentView(R.layout.dialog_error_task);
        errorDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Button btnOk = errorDialog.findViewById(R.id.btn_ok);
        TextView tvErrorMessage = errorDialog.findViewById(R.id.tv_error_message);
        tvErrorMessage.setText(message);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                errorDialog.cancel();
            }
        });

        errorDialog.show();
    }


    public static void replaceFragments(Context fragmentContext, Class fragmentClass, Bundle args, boolean isAddToBackStack) {

        Fragment fragment = null;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Insert the fragment by replacing any existing fragment
        if (null != args) {
            fragment.setArguments(args);
        }

        FragmentManager fragmentManager = ((FragmentActivity)fragmentContext).getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
//        transaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_in_right);

        if (isAddToBackStack) {
            transaction
                    .replace(R.id.flContent, fragment,fragment.getClass().getName().toString())
                    .addToBackStack(null)
                    .commit();
        } else {
            transaction
                    .replace(R.id.flContent, fragment,fragment.getClass().getName().toString())
                    .commit();
        }

    }

    public static void replaceFragments_2(Context fragmentContext, Class fragmentClass, Bundle args, boolean isAddToBackStack) {

        Fragment fragment = null;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Insert the fragment by replacing any existing fragment
        if (null != args) {
            fragment.setArguments(args);
        }

        FragmentManager fragmentManager = ((FragmentActivity)fragmentContext).getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
//        transaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_in_right);

        if (isAddToBackStack) {
            transaction
                    .add(R.id.flContent, fragment,fragment.getClass().getName().toString())
                    .addToBackStack(null)
                    .commit();
        } else {
            transaction
                    .add(R.id.flContent, fragment,fragment.getClass().getName().toString())
                    .commit();
        }

    }



}
