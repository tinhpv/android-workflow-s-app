package com.example.workflow_s.ui.login;

<<<<<<< HEAD
=======
import android.content.Intent;
import android.util.Log;

>>>>>>> 828f753ecd74dc6c7d5c2ded254288314fa046e8
import com.example.workflow_s.model.Organization;
import com.example.workflow_s.model.User;
import com.example.workflow_s.network.ApiClient;
import com.example.workflow_s.network.ApiService;
<<<<<<< HEAD
=======
import com.example.workflow_s.ui.home.HomeActivity;
import com.example.workflow_s.utils.SharedPreferenceUtils;
>>>>>>> 828f753ecd74dc6c7d5c2ded254288314fa046e8

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
        Call<Organization> call = service.getUserOrganizations(userId);
        call.enqueue(new Callback<Organization>() {
            @Override
            public void onResponse(Call<Organization> call, Response<Organization> response) {
                onFinishedListener.onFinished(response.body());
            }

            @Override
            public void onFailure(Call<Organization> call, Throwable t) {
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
