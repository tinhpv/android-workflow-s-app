package com.example.workflow_s.ui.notification;

public interface NotificationContract {

    //presenter
    interface NotificationPresenter {
        void onDestroy();
        void loadNotification();
    }
}
