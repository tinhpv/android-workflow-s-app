package com.example.workflow_s.ui.authentication;

import android.util.Log;

/**
 * Workflow_S
 * Created by TinhPV on 2019-06-24
 * Copyright © 2019 TinhPV. All rights reserved
 **/


public class AuthenticationPresenterImpl implements AuthenticationContract.AuthenticationPresenter,
        AuthenticationContract.ManipAuthenticationInteractor.OnFinishedUpdatePhoneListener,
        AuthenticationContract.ManipAuthenticationInteractor.OnFinishedSubmitCodeListener,
AuthenticationContract.ManipAuthenticationInteractor.OnFinishedGetVerifyCodeListener {

    private final static String TAG = "AUTHENTICATION";
    private AuthenticationContract.AuthenticationView mAuthenticationView;
    private AuthenticationContract.ManipAuthenticationInteractor mInteractor;

    public AuthenticationPresenterImpl(AuthenticationContract.AuthenticationView authenticationView, AuthenticationContract.ManipAuthenticationInteractor interactor) {
        mAuthenticationView = authenticationView;
        mInteractor = interactor;
    }


    @Override
    public void updatePhone(String userId, String phoneNumber, String devicetoken) {
        mInteractor.updatePhone(userId, phoneNumber, devicetoken, this);
    }

    @Override
    public void getVerifyCode(String userId) {
        mInteractor.getVerifyCode(userId, this);
    }

    @Override
    public void submitVerifyCode(String userId, String code) {
        mInteractor.submitVerifyCode(userId, code, this);
    }


    @Override
    public void onFinished() {
        mAuthenticationView.onFinishedUpdatePhone();
    }

    @Override
    public void onSubmitCodeFinished(Boolean isSuccess) {
        mAuthenticationView.onFinishedSubmitVerifyCode();
    }

    @Override
    public void onGetCodeFinished(Boolean isSuccess) {
        mAuthenticationView.onFinishedGetVerifyCode();
    }

    @Override
    public void onFailure(Throwable t) {
        Log.d(TAG, "onFailure: " + t.getMessage());
    }
}
