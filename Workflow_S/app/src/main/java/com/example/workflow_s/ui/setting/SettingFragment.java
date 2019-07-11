package com.example.workflow_s.ui.setting;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.workflow_s.R;
import com.example.workflow_s.ui.setting.fragment.AboutFragment;
import com.example.workflow_s.ui.setting.fragment.LibraryFragment;
import com.example.workflow_s.utils.CommonUtils;

public class SettingFragment extends Fragment {

    private View view;
    private TextView mNotfication, mLibrariy, mAbout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_setting, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mNotfication = view.findViewById(R.id.tv_switch_notification);

        mLibrariy = view.findViewById(R.id.tv_third_libraries);
        mLibrariy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonUtils.replaceFragments(v.getContext(), LibraryFragment.class, null);
            }
        });

        mAbout = view.findViewById(R.id.tv_about);
        mAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonUtils.replaceFragments(v.getContext(), AboutFragment.class, null);
            }
        });

    }
}
