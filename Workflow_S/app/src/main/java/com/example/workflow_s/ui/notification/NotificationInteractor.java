package com.example.workflow_s.ui.notification;

import com.example.workflow_s.model.Comment;
import com.example.workflow_s.model.Notification;
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
        Call<List<Notification>> call = service.getCommentNotification(orgId, userId);

        call.enqueue(new Callback<List<Notification>>() {
            @Override
            public void onResponse(Call<List<Notification>> call, Response<List<Notification>> response) {
                onFinishedListener.onFinishedGetComment(response.body());
            }

            @Override
            public void onFailure(Call<List<Notification>> call, Throwable t) {
                onFinishedListener.onFailure(t);
            }
        });
    }
}
