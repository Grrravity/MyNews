package com.error.grrravity.mynews.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class APISearch implements Parcelable {

    @SerializedName("results")
    @Expose
    private List<APIResult> result = null;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("copyright")
    @Expose
    private String copyright;
    @SerializedName("response")
    @Expose
    private APIResponse response;

    protected APISearch(Parcel in) {
        status = in.readString();
        copyright = in.readString();
    }

    public static final Creator<APISearch> CREATOR = new Creator<APISearch>() {
        @Override
        public APISearch createFromParcel(Parcel in) {
            return new APISearch(in);
        }

        @Override
        public APISearch[] newArray(int size) {
            return new APISearch[size];
        }
    };

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public APIResponse getResponse() {
        return response;
    }

    public void setResponse(APIResponse response) {
        this.response = response;
    }

    public List<APIResult> getResult() {
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(status);
        dest.writeString(copyright);
    }
}