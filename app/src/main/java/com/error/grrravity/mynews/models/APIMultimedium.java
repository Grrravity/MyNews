package com.error.grrravity.mynews.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class APIMultimedium {

    @SerializedName("rank")
    @Expose
    private Integer rank;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("format")
    @Expose
    private String format;
    @SerializedName("height")
    @Expose
    private Integer height;
    @SerializedName("width")
    @Expose
    private Integer width;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("subtype")
    @Expose
    private String subtype;
    @SerializedName("caption")
    @Expose
    private String caption;
    @SerializedName("copyright")
    @Expose
    private String copyright;
    @SerializedName("credit")
    @Expose
    private Object credit;
    @SerializedName("legacy")
    @Expose
    private APILegacy legacy;
    @SerializedName("crop_name")
    @Expose
    private String cropName;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Object getCredit() {
        return credit;
    }

    public void setCredit(Object credit) {
        this.credit = credit;
    }

    public APILegacy getLegacy() {
        return legacy;
    }

    public void setLegacy(APILegacy legacy) {
        this.legacy = legacy;
    }

    public String getCropName() {
        return cropName;
    }

    public void setCropName(String cropName) {
        this.cropName = cropName;
    }
}