package com.example.workflow_s.utils;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.example.workflow_s.R;

/**
 * Workflow_S
 * Created by TinhPV on 2019-06-30
 * Copyright © 2019 TinhPV. All rights reserved
 **/


public class CommonUtils {

    public static void replaceFragments(Context fragmentContext, Class fragmentClass, String key, String value) {

        Fragment fragment = null;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Insert the fragment by replacing any existing fragment
        if (null != key) {
            Bundle args = new Bundle();
            args.putString(key, value);
            fragment.setArguments(args);
        }

        FragmentManager fragmentManager = ((FragmentActivity)fragmentContext).getSupportFragmentManager();
        fragmentManager
                .beginTransaction()
                .replace(R.id.flContent, fragment)
                .commit();

    }
}
