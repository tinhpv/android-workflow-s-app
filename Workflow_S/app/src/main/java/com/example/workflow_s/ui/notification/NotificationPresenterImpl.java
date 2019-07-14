package com.example.workflow_s.ui.notification;

import android.util.Log;

import com.example.workflow_s.model.Comment;

import java.util.List;

public class NotificationPresenterImpl implements NotificationContract.NotificationPresenter,
            NotificationContract.GetNotificationDataContract.OnFinishedGetCommentListener{

    private static final String TAG = "NOTIFICATION_PRESENTER";
    private NotificationContract.NotificationView mView;
    private NotificationContract.GetNotificationDataContract mDataContract;

    public NotificationPresenterImpl(NotificationContract.NotificationView mView, NotificationContract.GetNotificationDataContract mDataContract) {
        this.mView = mView;
        this.mDataContract = mDataContract;
    }


    @Override
    public void onDestroy() {

    }

    @Override
    public void loadCommentNotification(String orgId, String userId) {
        mDataContract.loadCommentNotification(orgId, userId, this);
    }

    @Override
    public void onFinishedGetComment(List<Comment> comments) {
        mView.finishedLoadComment(comments);
    }

    @Override
    public void onFailure(Throwable t) {
        Log.d(TAG, "onFailure: " + t.getMessage());
    }
}
