package com.example.workflow_s.ui.login;

import com.example.workflow_s.model.Organization;
import com.example.workflow_s.model.User;
import com.example.workflow_s.model.UserOrganization;
import com.example.workflow_s.network.ApiClient;
import com.example.workflow_s.network.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Workflow_S
 * Created by TinhPV on 2019-06-21
 * Copyright © 2019 TinhPV. All rights reserved
 **/


public class LoginInteractor implements LoginContract.GetLoginDataInteractor {

    @Override
    public void getCurrentOrganization(String userId, final OnFinishedGetOrganizationListener onFinishedListener) {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<UserOrganization> call = service.getUserOrganizations(userId);
        call.enqueue(new Callback<UserOrganization>() {
            @Override
            public void onResponse(Call<UserOrganization> call, Response<UserOrganization> response) {
                onFinishedListener.onFinished(response.body());
            }

            @Override
            public void onFailure(Call<UserOrganization> call, Throwable t) {
                onFinishedListener.onFailure(t);
            }
        });
    }

    @Override
    public void saveUserToDB(User user, final OnFinishedSaveUserListener onFinishedListener) {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<User> call = service.addUser(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                onFinishedListener.onFinished(response.body());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                onFinishedListener.onFailure(t);
            }
        });
    }
}
