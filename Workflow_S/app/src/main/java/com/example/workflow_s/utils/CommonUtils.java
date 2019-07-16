package com.example.workflow_s.utils;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.example.workflow_s.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Workflow_S
 * Created by TinhPV on 2019-06-30
 * Copyright © 2019 TinhPV. All rights reserved
 **/


public class CommonUtils {

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
        transaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_in_right);

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

}
