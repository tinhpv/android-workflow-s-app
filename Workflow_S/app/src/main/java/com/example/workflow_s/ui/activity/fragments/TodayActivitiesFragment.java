package com.example.workflow_s.ui.activity.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.workflow_s.R;

/**
 * Workflow_S
 * Created by TinhPV on 2019-06-25
 * Copyright © 2019 TinhPV. All rights reserved
 **/


public class TodayActivitiesFragment extends Fragment {

    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

<<<<<<< HEAD
        view = inflater.inflate(R.layout.fragment_today_activity, container, false);
=======
        view = inflater.inflate(R.layout.today_activities_fragment, container, false);
>>>>>>> 828f753ecd74dc6c7d5c2ded254288314fa046e8
        return view;
    }
}
