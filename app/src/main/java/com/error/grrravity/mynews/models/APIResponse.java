package com.error.grrravity.mynews.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class APIResponse {

    @SerializedName("docs")
    @Expose
    private List<APIDoc> docs = null;
    @SerializedName("meta")
    @Expose
    private APIMeta meta;

    public List<APIDoc> getDocs() {
        return docs;
    }

    public void setDocs(List<APIDoc> docs) {
        this.docs = docs;
    }

    public APIMeta getMeta() {
        return meta;
    }

    public void setMeta(APIMeta meta) {
        this.meta = meta;
    }

}