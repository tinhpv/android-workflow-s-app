package com.example.workflow_s.model;

import android.content.Intent;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Comment implements Serializable {

    @SerializedName("id")
    private String id;

    @SerializedName("taskId")
    private Integer taskId;

    @SerializedName("userId")
    private String userId;

    @SerializedName("comment1")
    private String comment1;

    @SerializedName("priority")
    private Integer priority;

    @SerializedName("isRead")
    private boolean isRead;

    @SerializedName("userImage")
    private String userImage;

    @SerializedName("username")
    private String username;

    public Comment(String id, Integer taskId, String userId, String comment1, Integer priority, boolean isRead, String userImage, String username) {
        this.id = id;
        this.taskId = taskId;
        this.userId = userId;
        this.comment1 = comment1;
        this.priority = priority;
        this.isRead = isRead;
        this.userImage = userImage;
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getComment1() {
        return comment1;
    }

    public void setComment1(String comment1) {
        this.comment1 = comment1;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
