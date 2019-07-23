package com.example.workflow_s.ui.comment;

import com.example.workflow_s.model.Comment;
import com.example.workflow_s.network.ApiClient;
import com.example.workflow_s.network.ApiService;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Workflow_S
 * Created by TinhPV on 2019-07-23
 * Copyright © 2019 TinhPV. All rights reserved
 **/


public class CommentInteractor implements CommentContract.CommentDataInteractor  {

    // ApiService service = ApiClient.getClient().create(ApiService.class);


    @Override
    public void writeComment(Comment comment, final OnFinishedWriteComments listener) {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<ResponseBody> call = service.writeComment(comment);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                listener.onFinishedWriteComment();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                listener.onFailure(t);
            }
        });
    }

    @Override
    public void getAllComments(int taskId, final OnFinishedGetComments onFinishedListener) {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<List<Comment>> call = service.getAllComments(taskId);
        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                onFinishedListener.onFinishedGetComment(response.body());
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                onFinishedListener.onFailure(t);
            }
        });

    }
}
