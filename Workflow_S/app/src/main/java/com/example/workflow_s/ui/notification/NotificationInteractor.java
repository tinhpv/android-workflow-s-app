package com.example.workflow_s.ui.notification;

import com.example.workflow_s.model.Comment;
import com.example.workflow_s.network.ApiClient;
import com.example.workflow_s.network.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationInteractor implements NotificationContract.GetNotificationDataContract {

    @Override
    public void loadCommentNotification(String orgId, String userId, final OnFinishedGetCommentListener onFinishedListener) {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<List<Comment>> call = service.getCommentNotification(orgId, userId);

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
