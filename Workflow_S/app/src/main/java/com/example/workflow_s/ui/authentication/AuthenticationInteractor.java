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


    // FIXME - HARDCODE HERE FOR TESTING ONLY
    @Override
    public void updatePhone(String userId, String phoneNum, String devicetoken, final OnFinishedUpdatePhoneListener listener) {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<ResponseBody> call = service.updatePhoneNumber("107757857762956968267", phoneNum, devicetoken);
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

    // FIXME - HARDCODE HERE FOR TESTING ONLY
    @Override
    public void submitVerifyCode(String userId, String code, final OnFinishedSubmitCodeListener listener) {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<String> call = service.submitVerifyCode("107757857762956968267", code);
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


    // FIXME - HARDCODE HERE FOR TESTING ONLY
    @Override
    public void getVerifyCode(String userId, final OnFinishedGetVerifyCodeListener listener) {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<ResponseBody> call = service.getVerifyCode("107757857762956968267");
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
