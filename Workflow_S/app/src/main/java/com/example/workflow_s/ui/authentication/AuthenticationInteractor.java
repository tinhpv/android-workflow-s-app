package com.example.workflow_s.ui.authentication;

import com.example.workflow_s.network.ApiClient;
import com.example.workflow_s.network.ApiService;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Workflow_S
 * Created by TinhPV on 2019-06-24
 * Copyright © 2019 TinhPV. All rights reserved
 **/


public class AuthenticationInteractor implements AuthenticationContract.ManipAuthenticationInteractor {


    @Override
    public void updatePhone(String userId, String phoneNum, final OnFinishedUpdatePhoneListener listener) {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<ResponseBody> call = service.updatePhoneNumber(userId, phoneNum);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                listener.onFinished();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                listener.onFailure(t);
            }
        });
    }

    @Override
    public void submitVerifyCode(String userId, String code, final OnFinishedSubmitCodeListener listener) {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<String> call = service.submitVerifyCode(userId, code);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                listener.onSubmitCodeFinished(response.isSuccessful());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                listener.onFailure(t);
            }
        });

    }


    @Override
    public void getVerifyCode(String userId, final OnFinishedGetVerifyCodeListener listener) {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<ResponseBody> call = service.getVerifyCode(userId);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                listener.onGetCodeFinished(response.isSuccessful());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                listener.onFailure(t);
            }
        });
    }
}
