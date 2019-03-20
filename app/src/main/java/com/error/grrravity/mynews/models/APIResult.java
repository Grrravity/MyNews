package com.error.grrravity.mynews.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class APIResult {

    public static final String TOPSTORIES_EXTRA = "TOPSTORIES_EXTRA";

    @SerializedName("section")
    @Expose
    private String section;
    @SerializedName("subsection")
    @Expose
    private String subsection;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("adx_keywords")
    @Expose
    private String adxKeywords;
    @SerializedName("column")
    @Expose
    private Object column;
    @SerializedName("byline")
    @Expose
    private String byline;

    @SerializedName("url")
    @Expose
    private String url;

    @SerializedName("updated_date")
    @Expose
    private String updatedDate;

    @SerializedName("media")
    @Expose
    private List<APIMedium> media = null;

    @SerializedName("multimedia")
    @Expose
    private List<APIMultimedium> multimedia = null;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("abstract")
    @Expose
    private String _abstract;
    @SerializedName("published_date")
    @Expose
    private String publishedDate;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("id")
    @Expose
    private long id;
    @SerializedName("asset_id")
    @Expose
    private long assetId;
    @SerializedName("views")
    @Expose
    private Integer views;
    @SerializedName("count_type")
    @Expose
    private String countType;

    public String getCountType() {
        return countType;
    }

    public void setCountType(String countType) {
        this.countType = countType;
    }

    public String getSection() {
        return section;
    }

    public String getSubsection() {
        return subsection;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getUrl() {
        return url;
    }


    public String getUpdatedDate() {
        return updatedDate;
    }


    public List<APIMultimedium> getMultimedia() {
        return multimedia;
    }

    public List<APIMedium> getMedia() {
        return media;
    }

    public String getAdxKeywords() {
        return adxKeywords;
    }

    public void setAdxKeywords(String adxKeywords) {
        this.adxKeywords = adxKeywords;
    }

    public Object getColumn() {
        return column;
    }

    public void setColumn(Object column) {
        this.column = column;
    }

    public String getByline() {
        return byline;
    }

    public void setByline(String byline) {
        this.byline = byline;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAbstract() {
        return _abstract;
    }

    public void setAbstract(String _abstract) {
        this._abstract = _abstract;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAssetId() {
        return assetId;
    }

    public void setAssetId(long assetId) {
        this.assetId = assetId;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public void setMedia(List<APIMedium> media) {
        this.media = media;
    }

}