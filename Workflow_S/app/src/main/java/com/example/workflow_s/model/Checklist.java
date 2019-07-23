package com.example.workflow_s.model;

/**
 * Workflow_S
 * Created by TinhPV on 2019-06-07
 * Copyright © 2019 TinhPV. All rights reserved
 **/

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Checklist implements Serializable {

    @SerializedName("id")
    private Integer id;

    @SerializedName("userId")
    private String userId;

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("timeCreated")
    private String timeCreated;

    @SerializedName("templateStatus")
    private String templateStatus;

    @SerializedName("organizationId")
    private Integer organizationId;

    @SerializedName("templateId")
    private Integer templateId;

    @SerializedName("countAllTask")
    private Integer totalTask;

    @SerializedName("countDoneTask")
    private Integer doneTask;

    @SerializedName("templateName")
    private String templateName;

    @SerializedName("checklistMembers")
    private ArrayList<ChecklistMember> checklistMembers;

    @SerializedName("dueTime")
    private String dueTime;

    @SerializedName("taskItem")
    private List<Task> taskItems;

    public Checklist(Integer id, String userId, String name, String description, String timeCreated, String templateStatus, Integer organizationId, Integer templateId, Integer totalTask, Integer doneTask) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.description = description;
        this.timeCreated = timeCreated;
        this.templateStatus = templateStatus;
        this.organizationId = organizationId;
        this.templateId = templateId;
        this.totalTask = totalTask;
        this.doneTask = doneTask;
    }

    public ArrayList<ChecklistMember> getChecklistMembers() {
        return checklistMembers;
    }

    public void setChecklistMembers(ArrayList<ChecklistMember> checklistMembers) {
        this.checklistMembers = checklistMembers;
    }

    public String getDueTime() {
        return dueTime;
    }

    public void setDueTime(String dueTime) {
        this.dueTime = dueTime;
    }

    public Checklist(Integer id, String userId, String name, String description, String timeCreated, String templateStatus, Integer organizationId, Integer templateId, Integer totalTask, Integer doneTask, String templateName, ArrayList<ChecklistMember> checklistMembers, List<Task> taskItems) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.description = description;
        this.timeCreated = timeCreated;
        this.templateStatus = templateStatus;
        this.organizationId = organizationId;
        this.templateId = templateId;
        this.totalTask = totalTask;
        this.doneTask = doneTask;
        this.templateName = templateName;
        this.checklistMembers = checklistMembers;
        this.taskItems = taskItems;
    }

    public Checklist(Integer id, String userId, String name, String description, String timeCreated, String templateStatus, Integer organizationId, Integer templateId, Integer totalTask, Integer doneTask, ArrayList<ChecklistMember> checklistMembers, String dueTime, List<Task> taskItems) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.description = description;
        this.timeCreated = timeCreated;
        this.templateStatus = templateStatus;
        this.organizationId = organizationId;
        this.templateId = templateId;
        this.totalTask = totalTask;
        this.doneTask = doneTask;
        this.checklistMembers = checklistMembers;
        this.dueTime = dueTime;
        this.taskItems = taskItems;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(String timeCreated) {
        this.timeCreated = timeCreated;
    }

    public String getTemplateStatus() {
        return templateStatus;
    }

    public void setTemplateStatus(String templateStatus) {
        this.templateStatus = templateStatus;
    }

    public Integer getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Integer organizationId) {
        this.organizationId = organizationId;
    }

    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    public Integer getTotalTask() {
        return totalTask;
    }

    public void setTotalTask(Integer totalTask) {
        this.totalTask = totalTask;
    }

    public Integer getDoneTask() {
        return doneTask;
    }

    public void setDoneTask(Integer doneTask) {
        this.doneTask = doneTask;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public List<Task> getTaskItems() {
        return taskItems;
    }

    public void setTaskItems(List<Task> taskItems) {
        this.taskItems = taskItems;
    }
}
