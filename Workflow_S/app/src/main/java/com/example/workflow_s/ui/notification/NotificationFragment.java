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
import com.example.workflow_s.model.Comment;
import com.example.workflow_s.ui.notification.adapter.NotificationAdapter;
import com.example.workflow_s.utils.SharedPreferenceUtils;

import java.util.List;

public class NotificationFragment extends Fragment implements NotificationContract.NotificationView {

    View view;
    private NotificationAdapter mNotificationAdapter;
    private RecyclerView notificationRecyclerView;
    private RecyclerView.LayoutManager notificationLayoutManager;

    private NotificationContract.NotificationPresenter mPresenter;
    private List<Comment> commentList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_notification, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initData();
        setupNotificationRV();
    }

    public void initData() {
        mPresenter = new NotificationPresenterImpl(this, new NotificationInteractor());
        String userId = SharedPreferenceUtils.retrieveData(getActivity(), getString(R.string.pref_userId));
        String orgId = SharedPreferenceUtils.retrieveData(getActivity(), getString(R.string.pref_orgId));
        mPresenter.loadCommentNotification(orgId, userId);
    }

    public void setupNotificationRV() {
        notificationRecyclerView = view.findViewById(R.id.rv_notification);
        notificationRecyclerView.setHasFixedSize(true);
        notificationLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        notificationRecyclerView.setLayoutManager(notificationLayoutManager);

        mNotificationAdapter = new NotificationAdapter();
        notificationRecyclerView.setAdapter(mNotificationAdapter);
    }


    @Override
    public void finishedLoadComment(List<Comment> comments) {
        mNotificationAdapter.setComments(comments);
    }
}
