package com.error.grrravity.mynews.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class APIDoc {

    @SerializedName("web_url")
    @Expose
    private String webUrl;
    @SerializedName("snippet")
    @Expose
    private String snippet;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("multimedia")
    @Expose
    private List<APIMultimedium> multimedia = null;
    @SerializedName("mAPIHeadline")
    @Expose
    private APIHeadline mAPIHeadline;
    @SerializedName("keywords")
    @Expose
    private List<APIKeyword> keywords = null;
    @SerializedName("pub_date")
    @Expose
    private String pubDate;
    @SerializedName("document_type")
    @Expose
    private String documentType;
    @SerializedName("news_desk")
    @Expose
    private String newsDesk;
    @SerializedName("section_name")
    @Expose
    private String sectionName;
    @SerializedName("byline")
    @Expose
    private APIByline byline;
    @SerializedName("type_of_material")
    @Expose
    private String typeOfMaterial;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("word_count")
    @Expose
    private Integer wordCount;
    @SerializedName("score")
    @Expose
    private Double score;
    @SerializedName("uri")
    @Expose
    private String uri;
    @SerializedName("print_page")
    @Expose
    private String printPage;

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public List<APIMultimedium> getMultimedia() {
        return multimedia;
    }

    public void setMultimedia(List<APIMultimedium> multimedia) {
        this.multimedia = multimedia;
    }

    public APIHeadline getAPIHeadline() {
        return mAPIHeadline;
    }

    public void setAPIHeadline(APIHeadline APIHeadline) {
        this.mAPIHeadline = APIHeadline;
    }

    public List<APIKeyword> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<APIKeyword> keywords) {
        this.keywords = keywords;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getNewsDesk() {
        return newsDesk;
    }

    public void setNewsDesk(String newsDesk) {
        this.newsDesk = newsDesk;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public APIByline getByline() {
        return byline;
    }

    public void setByline(APIByline byline) {
        this.byline = byline;
    }

    public String getTypeOfMaterial() {
        return typeOfMaterial;
    }

    public void setTypeOfMaterial(String typeOfMaterial) {
        this.typeOfMaterial = typeOfMaterial;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getWordCount() {
        return wordCount;
    }

    public void setWordCount(Integer wordCount) {
        this.wordCount = wordCount;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getPrintPage() {
        return printPage;
    }

    public void setPrintPage(String printPage) {
        this.printPage = printPage;
    }

}
