package com.example.workflow_s.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Workflow_S
 * Created by TinhPV on 2019-06-20
 * Copyright © 2019 TinhPV. All rights reserved
 **/


public class User implements Serializable {
    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("email")
    private String email;

    @SerializedName("type")
    private String type;

    @SerializedName("role")
    private String role;

    @SerializedName("avatar")
    private String avatar;

    @SerializedName("deviceToken")
    private String token;

    public User(String id, String name, String email, String type, String role, String avatar, String token) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.type = type;
        this.role = role;
        this.avatar = avatar;
        this.token = token;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
