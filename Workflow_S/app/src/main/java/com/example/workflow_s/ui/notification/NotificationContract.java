package com.example.workflow_s.ui.notification;

import com.example.workflow_s.model.Notification;

import java.util.List;

public interface NotificationContract {

    //presenter
    interface NotificationPresenter {
        void onDestroy();
        void loadCommentNotification(String orgId, String userId);
    }

    //view
    interface NotificationView {
        void finishedLoadComment(List<Notification> comments);
    }

    //model
    interface GetNotificationDataContract {
        interface OnFinishedGetCommentListener {
            void onFinishedGetComment(List<Notification> comments);
            void onFailure(Throwable t);
        }

        void loadCommentNotification(String orgId, String userId, OnFinishedGetCommentListener onFinishedListener);
    }
}
