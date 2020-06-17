package com.example.workflow_s.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ContentDetail implements Serializable {
    @SerializedName("id")
    private Integer id;

    @SerializedName("type")
    private String type;

    @SerializedName("text")
    private String text;

    @SerializedName("imageSrc")
    private String imageSrc;

    @SerializedName("taskItemId")
    private Integer taskItemId;

    @SerializedName("orderContent")
    private Integer orderContent;

    @SerializedName("label")
    private String label;

    public ContentDetail(Integer id, String type, String text, String imageSrc, Integer taskItemId, Integer orderContent, String label) {
        this.id = id;
        this.type = type;
        this.text = text;
        this.imageSrc = imageSrc;
        this.taskItemId = taskItemId;
        this.orderContent = orderContent;
        this.label = label;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImageSrc() {
        return imageSrc;
    }

    public void setImageSrc(String imageSrc) {
        this.imageSrc = imageSrc;
    }

    public Integer getTaskItemId() {
        return taskItemId;
    }

    public void setTaskItemId(Integer taskItemId) {
        this.taskItemId = taskItemId;
    }

    public Integer getOrderContent() {
        return orderContent;
    }

    public void setOrderContent(Integer orderContent) {
        this.orderContent = orderContent;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
