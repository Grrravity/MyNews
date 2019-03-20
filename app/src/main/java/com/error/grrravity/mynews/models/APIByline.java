package com.error.grrravity.mynews.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class APIByline {

    @SerializedName("original")
    @Expose
    private String original;
    @SerializedName("person")
    @Expose
    private List<APIPerson> person = null;
    @SerializedName("organization")
    @Expose
    private Object organization;

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public List<APIPerson> getPerson() {
        return person;
    }

    public void setPerson(List<APIPerson> person) {
        this.person = person;
    }

    public Object getOrganization() {
        return organization;
    }

    public void setOrganization(Object organization) {
        this.organization = organization;
    }

}