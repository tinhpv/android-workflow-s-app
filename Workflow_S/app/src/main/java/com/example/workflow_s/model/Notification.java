package com.example.workflow_s.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Notification implements Serializable {

    @SerializedName("id")
    private Integer id;

    @SerializedName("userId")
    private String userId;

    @SerializedName("text")
    private String text;

    @SerializedName("taskId")
    private Integer taskId;

    @SerializedName("checklistId")
    private Integer checklistId;

    @SerializedName("isRead")
    private boolean isRead;

    @SerializedName("imageUrl")
    private String imageUrl;

    public Notification(Integer id, String userId, String text, Integer taskId, Integer checklistId, boolean isRead, String imageUrl) {
        this.id = id;
        this.userId = userId;
        this.text = text;
        this.taskId = taskId;
        this.checklistId = checklistId;
        this.isRead = isRead;
        this.imageUrl = imageUrl;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Integer getChecklistId() {
        return checklistId;
    }

    public void setChecklistId(Integer checklistId) {
        this.checklistId = checklistId;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
