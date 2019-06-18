package com.example.workflow_s.network;

import com.google.gson.Gson;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Workflow_S
 * Created by TinhPV on 2019-06-12
 * Copyright © 2019 TinhPV. All rights reserved
 **/

public class ApiClient {

    public static final String BASE_URL = "https://workflow3i.azurewebsites.net/api/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (null == retrofit) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
