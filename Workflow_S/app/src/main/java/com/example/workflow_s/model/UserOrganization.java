package com.example.workflow_s.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class UserOrganization implements Serializable {

    @SerializedName("id")
    private Integer id;

    @SerializedName("memberId")
    private String memberId;

    @SerializedName("organizationId")
    private Integer organizationId;

    @SerializedName("organizationName")
    private String organizationName;

    @SerializedName("currentOrganization")
    private boolean currentOrganization;

    @SerializedName("member")
    private List<User> member;

    @SerializedName("organization")
    private Organization organization;

    public UserOrganization(Integer id, String memberId, Integer organizationId, boolean currentOrganization, List<User> member, Organization organization) {
        this.id = id;
        this.memberId = memberId;
        this.organizationId = organizationId;
        this.currentOrganization = currentOrganization;
        this.member = member;
        this.organization = organization;
    }

    public UserOrganization(Integer id, String memberId, Integer organizationId, String organizationName, boolean currentOrganization, List<User> member, Organization organization) {
        this.id = id;
        this.memberId = memberId;
        this.organizationId = organizationId;
        this.organizationName = organizationName;
        this.currentOrganization = currentOrganization;
        this.member = member;
        this.organization = organization;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public Integer getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Integer organizationId) {
        this.organizationId = organizationId;
    }

    public boolean isCurrentOrganization() {
        return currentOrganization;
    }

    public void setCurrentOrganization(boolean currentOrganization) {
        this.currentOrganization = currentOrganization;
    }

    public List<User> getMember() {
        return member;
    }

    public void setMember(List<User> member) {
        this.member = member;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }
}
