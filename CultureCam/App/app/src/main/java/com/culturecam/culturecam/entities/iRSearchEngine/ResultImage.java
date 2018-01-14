package com.culturecam.culturecam.entities.iRSearchEngine;


import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class ResultImage implements Serializable {

    @Expose
    private String resourceId;

    @Expose
    private String thmbUrl;

    @Expose
    private String cachedThmbUrl;

    @Expose
    private String score;

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getThmbUrl() {
        return thmbUrl;
    }

    public void setThmbUrl(String thmbUrl) {
        this.thmbUrl = thmbUrl;
    }

    public String getCachedThmbUrl() {
        return cachedThmbUrl;
    }

    public void setCachedThmbUrl(String cachedThmbUrl) {
        this.cachedThmbUrl = cachedThmbUrl;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

}
