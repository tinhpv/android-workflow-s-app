package com.example.workflow_s.ui.setting;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
        mNotfication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");

                //for Android 5-7
//                intent.putExtra("app_package", v.getContext().getPackageName());
//                intent.putExtra("app_uid", v.getContext().getApplicationInfo().uid);


                // for Android 8 and above
                intent.putExtra("android.provider.extra.APP_PACKAGE", v.getContext().getPackageName());

                startActivity(intent);
            }
        });

        mLibrariy = view.findViewById(R.id.tv_third_libraries);
        mLibrariy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonUtils.replaceFragments(v.getContext(), LibraryFragment.class, null, true);
            }
        });

        mAbout = view.findViewById(R.id.tv_about);
        mAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonUtils.replaceFragments(v.getContext(), AboutFragment.class, null, true);
            }
        });

    }
}
