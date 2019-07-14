package com.example.workflow_s.ui.login;

import com.example.workflow_s.model.Organization;
import com.example.workflow_s.model.User;
import com.example.workflow_s.model.UserOrganization;

/**
 * Workflow_S
 * Created by TinhPV on 2019-06-21
 * Copyright © 2019 TinhPV. All rights reserved
 **/


public interface LoginContract {

    interface LoginPresenter {
        void addUserToDB(User user);
        void getCurrentOrganization(String userId);
        void checkRoleUser(String userRole);
        void onDestroy();

        void updateToken(String userId, String deviceToken);
    }

    interface LoginView {
        void navigateToCodeVerifyActivity();
        void navigateToMainActivity();
        void onFinishedAddUser(User user);
        void onFinishedGetOrg(UserOrganization org);
    }

    interface GetLoginDataInteractor {

        interface OnFinishedGetOrganizationListener {
            void onFinished(UserOrganization currentOrganization);
            void onFailure(Throwable t);
        }

        interface OnFinishedSaveUserListener {
            void onFinished(User user);
            void onFailure(Throwable t);
        }

        interface OnFinisedUpdateTokeListener {
            void onFinished();
            void onFailure(Throwable t);
        }

        void getCurrentOrganization(String userId,
                                    OnFinishedGetOrganizationListener onFinishedListener);

        void saveUserToDB(User user,
                          LoginContract.GetLoginDataInteractor.OnFinishedSaveUserListener onFinishedListener);

        void updateDeviceToken(String userId, String deviceToken, OnFinisedUpdateTokeListener listener);
    }
}
