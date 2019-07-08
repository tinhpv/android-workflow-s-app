package com.example.workflow_s.ui.authentication;

/**
 * Workflow_S
 * Created by TinhPV on 2019-06-24
 * Copyright © 2019 TinhPV. All rights reserved
 **/


public interface AuthenticationContract {

    interface AuthenticationPresenter {
        void updatePhone(String userId, String phoneNumber, String devicetoken);
        void getVerifyCode(String userId);
        void submitVerifyCode(String userId, String code);
    }

    interface AuthenticationView {
        void onFinishedUpdatePhone();
        void onFinishedGetVerifyCode();
        void onFinishedSubmitVerifyCode();
    }

    interface ManipAuthenticationInteractor {

        interface OnFinishedUpdatePhoneListener {
            void onFinished();
            void onFailure(Throwable t);
        }

        interface OnFinishedSubmitCodeListener {
            void onSubmitCodeFinished(Boolean isSuccess);
            void onFailure(Throwable t);
        }

        interface OnFinishedGetVerifyCodeListener {
            void onGetCodeFinished(Boolean isSuccess);
            void onFailure(Throwable t);
        }

        void updatePhone(String userId, String phoneNum, String deviceToken, ManipAuthenticationInteractor.OnFinishedUpdatePhoneListener listener);
        void getVerifyCode(String userId, ManipAuthenticationInteractor.OnFinishedGetVerifyCodeListener listener);
        void submitVerifyCode(String userId, String code, ManipAuthenticationInteractor.OnFinishedSubmitCodeListener listener);
    }
}
