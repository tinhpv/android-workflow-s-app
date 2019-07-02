package com.example.workflow_s.network;

import com.example.workflow_s.model.Checklist;
import com.example.workflow_s.model.ContentDetail;
import com.example.workflow_s.model.Organization;
import com.example.workflow_s.model.Template;
import com.example.workflow_s.model.User;
import com.example.workflow_s.model.Task;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Workflow_S
 * Created by TinhPV on 2019-06-12
 * Copyright © 2019 TinhPV. All rights reserved
 **/

public interface ApiService {

    @GET("api/Checklists/checklistprogress/{organizationId}/{userId}")
    Call<List<Checklist>> getAllRunningChecklists(@Path("organizationId") String organizationId, @Path("userId") String userId);

    @POST("api/Users/login/user")
    Call<User> addUser(@Body User user);

    @GET("api/UserOrganizations/organization/{userId}")
    Call<Organization> getUserOrganizations(@Path("userId") String userId);

    @GET("/api/TaskItems/taskoverdue/{userId}")
    Call<List<Task>> getAllDueTasks(@Path("userId") String userId);

    @POST("/api/Users/updatephone/{userid}/{phone}")
    Call<ResponseBody> updatePhoneNumber(@Path("userid") String userId, @Path("phone") String phoneNumber);

    @POST("/api/Users/verifycode/{userid}/{code}")
    Call<String> submitVerifyCode(@Path("userid") String userId, @Path("code") String verifyCode);

    @GET("/api/Users/getverifycode/{id}")
    Call<ResponseBody> getVerifyCode(@Path("id") String userId);

    @GET("/api/UserOrganizations/member/{organizationId}")
    Call<List<User>> getOrganizationMember(@Path("organizationId") String orgId);

    @GET("/api/TaskItems/taskitems/{checklistId}")
    Call<List<Task>> getTaskFromChecklist(@Path("checklistId") long checklistId);

    @GET("/api/ContentDetails/contentdetail/{taskid}")
    Call<List<ContentDetail>> getContentDetail(@Path("taskid") long taskid);

    @GET("/api/Checklists/listtemplate/{organizationId}/{userId}")
    Call<List<Template>> getAllCreatedTemplates(@Path("organizationId") String orgId, @Path("userId") String userId);
    @GET("/api/TaskItems/getupcoming/{organizationId}/{userId}")
    Call<List<Task>> getUpcomingTasks(@Path("organizationId") String organizationId,
                                      @Path("userId") String userId);

}
