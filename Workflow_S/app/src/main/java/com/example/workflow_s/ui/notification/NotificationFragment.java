package com.example.workflow_s.ui.notification;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;

import com.example.workflow_s.R;
import com.example.workflow_s.ui.notification.adapter.NotificationAdapter;

public class NotificationFragment extends Fragment {

    View view;
    private NotificationAdapter mNotificationAdapter;
    private RecyclerView notificationRecyclerView;
    private RecyclerView.LayoutManager notificationLayoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_notification, container, false);
        setupNotificationRV();
        return view;
    }

    public void setupNotificationRV() {
        notificationRecyclerView = view.findViewById(R.id.rv_notification);
        notificationRecyclerView.setHasFixedSize(true);
        notificationLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        notificationRecyclerView.setLayoutManager(notificationLayoutManager);

        //FIXME - TEST FAKE DATA
        mNotificationAdapter = new NotificationAdapter();
        notificationRecyclerView.setAdapter(mNotificationAdapter);
    }


}
