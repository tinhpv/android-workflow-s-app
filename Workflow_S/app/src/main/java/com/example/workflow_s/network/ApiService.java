package com.example.workflow_s.network;

import com.example.workflow_s.model.Checklist;
import com.example.workflow_s.model.ChecklistMember;
import com.example.workflow_s.model.Comment;
import com.example.workflow_s.model.ContentDetail;
import com.example.workflow_s.model.Notification;
import com.example.workflow_s.model.Organization;
import com.example.workflow_s.model.Task;
import com.example.workflow_s.model.TaskMember;
import com.example.workflow_s.model.Template;
import com.example.workflow_s.model.User;
import com.example.workflow_s.model.UserOrganization;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * Workflow_S
 * Created by TinhPV on 2019-06-12
 * Copyright © 2019 TinhPV. All rights reserved
 **/

public interface ApiService {

    @GET("api/Checklists/checklistprogress/{organizationId}")
    Call<List<Checklist>> getAllRunningChecklists(@Path("organizationId") String organizationId);

    @GET("api/Checklists/checklistprogress/{organizationId}/{userId}")
    Call<List<Checklist>> getAllRunningChecklists(@Path("organizationId") String organizationId,
                                                  @Path("userId") String userId);

    @POST("api/Users/login/user")
    Call<User> addUser(@Body User user);

    @GET("/api/UserOrganizations/organization/{userId}")
    Call<UserOrganization> getUserOrganizations(@Path("userId") String userId);

    @GET("/api/TaskItems/taskoverdue/{organizationId}/{userId}")
    Call<List<Task>> getAllDueTasks(@Path("organizationId") String organizationId,
                                    @Path("userId") String userId);

    @PUT("/api/Users/updatephone/{userid}/{phone}")
    Call<ResponseBody> updatePhoneNumber(@Path("userid") String userId,
                                         @Path("phone") String phoneNumber);

    @POST("/api/Users/verifycode/{userid}/{code}")
    Call<String> submitVerifyCode(@Path("userid") String userId,
                                  @Path("code") String verifyCode);

    @GET("/api/Users/getverifycode/{id}")
    Call<ResponseBody> getVerifyCode(@Path("id") String userId);

    @GET("/api/UserOrganizations/member/{organizationId}")
    Call<List<User>> getOrganizationMember(@Path("organizationId") int orgId);


    @PUT("/api/UserOrganizations/switch/{userId}/{targetid}/{oldid}")
    Call<ResponseBody> switchOrganization(@Path("userId") String userId,
                                          @Path("targetid") int targetOrgId,
                                          @Path("oldid") int oldOrgId);

    @GET("/api/TaskItems/taskitems/{checklistId}")
    Call<List<Task>> getTaskFromChecklist(@Path("checklistId") long checklistId);

    @GET("/api/ContentDetails/contentdetail/{taskid}")
    Call<List<ContentDetail>> getContentDetail(@Path("taskid") long taskid);

    @GET("/api/Checklists/listtemplate/{organizationId}")
    Call<List<Template>> getAllCreatedTemplates(@Path("organizationId") String orgId);

    @GET("/api/Checklists/checklistmobile/{organizationId}/{checklistId}")
    Call<Checklist> getChecklistById(@Path("organizationId") int orgId,
                                     @Path("checklistId") int checklistId);


    @GET("/api/TaskItems/getupcoming/{organizationId}/{userId}")
    Call<List<Task>> getUpcomingTasks(@Path("organizationId") String organizationId,
                                      @Path("userId") String userId);

    @GET("/api/Checklists/template/{organizationId}/{templateId}/{userId}")
    Call<Template> getTemplateObject(@Path("organizationId") String organizationId,
                                         @Path("templateId") String templateId,
                                         @Path("userId") String userId);

    @POST("/api/Checklists/run/{userId}")
    Call<Checklist> runChecklist(@Path("userId") String userId,
                                    @Body Template template);

    @GET("/api/UserOrganizations/organization/{userId}")
    Call<Organization> getOrganization(@Path("userId") String userId);

    @GET("/api/Organizations/current/{userId}")
    Call<UserOrganization> getCurrentOrganization(@Path("userId") String userId);

    @GET("/api/UserOrganizations/user/{userId}")
    Call<List<UserOrganization>> getListUserOrganization(@Path("userId") String userId);

    @POST("/api/TaskMembers/assign")
    Call<TaskMember> assignTaskMember(@Body TaskMember taskMember);

    @POST("/api/ChecklistMembers/member")
    Call<ChecklistMember> assignChecklistMember(@Body ChecklistMember checklistMember);

    @DELETE("/api/TaskMembers/delete/{memberId}")
    Call<ResponseBody> unassignTaskMember(@Path("memberId") int memberId);

    @DELETE("/api/ChecklistMembers/delete/{memberid}")
    Call<ResponseBody> unassignChecklistMember(@Path("memberid") int memberId);

    @GET("/api/TaskMembers/taskmember/{taskId}")
    Call<List<TaskMember>> getAllTaskMember(@Path("taskId") int taskId);

    @PUT("/api/TaskItems/duetime/{taskid}")
    Call<ResponseBody> setDueTime(@Path("taskid") int taskId,
                                  @Body String datetime);

    @GET("/api/TaskItems/getfirsttask/{checklistId}")
    Call<Task> getFirstTaskFromChecklist(@Path("checklistId") int checklistId);

    @GET("/api/TaskItems/gettaskitem/{taskitemId}")
    Call<Task> getTaskById(@Path("taskitemId") int taskId);

    @PUT("/api/Checklists/done/{checklistid}")
    Call<ResponseBody> completeChecklist(@Path("checklistid") int checklistId);

    @PUT("/api/TaskItems/setstatus/{userid}/{taskid}/{status}")
    Call<ResponseBody> changeTaskStatus(@Path("userid") String userId,
                                        @Path("taskid") int taskId,
                                        @Path("status") String taskStatus);

    @PUT("/api/ContentDetails/update")
    Call<ResponseBody> saveTaskContentDetail(@Body List<ContentDetail> detailList);

    @PUT("/api/Checklists/setstatus/{checklistid}/{status}/{userid}")
    Call<ResponseBody> completeChecklist(@Path("checklistid") int checklistId,
                                         @Path("status") String status,
                                         @Path("userid") String userId);

    @DELETE("/api/Checklists/delete/{checklistid}/{userId}")
    Call<ResponseBody> deleteChecklist(@Path("checklistid") int checklistId,
                                       @Path("userId") String userId);


    @GET("/api/Comments/mobilenotification/{organizationId}/{userId}")
    Call<List<Notification>> getCommentNotification(@Path("organizationId") String orgId, @Path("userId") String userId);

    //@Headers("Content-Type: application/json")
    @PUT("/api/Users/updatedevicetoken/{userid}/{devicetoken}")
    Call<ResponseBody> updateDeviceToken(@Path("userid") String userId, @Path("devicetoken") String deviceToken);

    @Multipart
    @PUT("/api/ContentDetails/uploadimage/{contentid}/{ordercontent}")
    Call<ResponseBody> uploadImage(
            @Path("contentid") int contentId,
            @Path("ordercontent") int orderContent,
            @Part MultipartBody.Part photo);

    @GET("/api/Checklists/getchecklistmobile/{checklistid}")
    Call<Checklist> getChecklistById(@Path("checklistid") int checklistId);

    @PUT("/api/Checklists/duetime/{checklistid}/{datetime}")
    Call<ResponseBody> setChecklistDueTime(@Path("checklistid") int checklistId, @Path("datetime") String datetime);


    @GET("/api/Comments/taskcomment/{taskId}")
    Call<List<Comment>> getAllComments(@Path("taskId") int taskId);

    @POST("/api/Comments/comment")
    Call<ResponseBody> writeComment(@Body Comment comment);

    @GET("/api/Users/{id}")
    Call<User> getUserById(@Path("id") String userID);


    @PUT("/api/TaskItems/setname/{taskid}/{name}")
    Call<ResponseBody> renameTask(@Path("taskid") int taskId,
                                  @Path("name") String taskName);


    @GET("/api/Comments/tasknotification/{taskid}")
    Call<List<Notification>> getAllNotifs(@Path("taskid") int taskId);

    @PUT("/api/Checklists/setname/{checklistid}/{name}")
    Call<ResponseBody> setNameOfChecklist(@Path("checklistid") int checklistId, @Path("name") String name);

    @PUT("/api/TaskItems/priority")
    Call<ResponseBody> changePriority(@Body List<Task> taskList);
}
