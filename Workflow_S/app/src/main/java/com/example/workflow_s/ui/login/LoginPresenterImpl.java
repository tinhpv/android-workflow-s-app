package com.example.workflow_s.ui.login;

import android.util.Log;

import com.example.workflow_s.model.Organization;
import com.example.workflow_s.model.User;
import com.example.workflow_s.model.UserOrganization;

/**
 * Workflow_S
 * Created by TinhPV on 2019-06-21
 * Copyright © 2019 TinhPV. All rights reserved
 **/


public class LoginPresenterImpl implements LoginContract.LoginPresenter,
        LoginContract.GetLoginDataInteractor.OnFinishedSaveUserListener,
        LoginContract.GetLoginDataInteractor.OnFinishedGetOrganizationListener{

    private final static String TAG = "LoginPresenterImpl";

    private LoginContract.LoginView mLoginView;
    private LoginContract.GetLoginDataInteractor mGetLoginDataInteractor;

    public LoginPresenterImpl(LoginContract.LoginView loginView, LoginContract.GetLoginDataInteractor getLoginDataInteractor) {
        mLoginView = loginView;
        mGetLoginDataInteractor = getLoginDataInteractor;
    }

    @Override
    public void getCurrentOrganization(String userId) {
        mGetLoginDataInteractor.getCurrentOrganization(userId, this);
    }

    @Override
    public void checkRoleUser(String userRole) {
        if (userRole.isEmpty() || userRole == null) {
            mLoginView.navigateToCodeVerifyActivity();
        } else {
            mLoginView.navigateToMainActivity();
        }
    }

    @Override
    public void addUserToDB(User user) {
        mGetLoginDataInteractor.saveUserToDB(user, this);
    }

    @Override
    public void onDestroy() {
        mLoginView = null;
    }

    // API Callback
    @Override
    public void onFinished(User user) {
        mLoginView.onFinishedAddUser(user);
    }

    @Override
    public void onFinished(UserOrganization currentOrganization) {
        mLoginView.onFinishedGetOrg(currentOrganization);
    }

    @Override
    public void onFailure(Throwable t) {
        Log.d(TAG, "onFailure: ");
    }
}
