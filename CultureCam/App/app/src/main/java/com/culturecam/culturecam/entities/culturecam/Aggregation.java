
package com.culturecam.culturecam.entities.culturecam;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Aggregation implements Serializable
{

    @SerializedName("about")
    @Expose
    private String about;
    @SerializedName("edmDataProvider")
    @Expose
    private EdmDataProvider edmDataProvider;
    @SerializedName("edmIsShownBy")
    @Expose
    private String edmIsShownBy;
    @SerializedName("edmIsShownAt")
    @Expose
    private String edmIsShownAt;
    @SerializedName("edmObject")
    @Expose
    private String edmObject;
    @SerializedName("edmProvider")
    @Expose
    private EdmProvider edmProvider;
    @SerializedName("edmRights")
    @Expose
    private EdmRights edmRights;
    @SerializedName("aggregatedCHO")
    @Expose
    private String aggregatedCHO;
    @SerializedName("edmPreviewNoDistribute")
    @Expose
    private Boolean edmPreviewNoDistribute;
    private final static long serialVersionUID = -2047629230157744401L;

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public EdmDataProvider getEdmDataProvider() {
        return edmDataProvider;
    }

    public void setEdmDataProvider(EdmDataProvider edmDataProvider) {
        this.edmDataProvider = edmDataProvider;
    }

    public String getEdmIsShownBy() {
        return edmIsShownBy;
    }

    public void setEdmIsShownBy(String edmIsShownBy) {
        this.edmIsShownBy = edmIsShownBy;
    }

    public String getEdmIsShownAt() {
        return edmIsShownAt;
    }

    public void setEdmIsShownAt(String edmIsShownAt) {
        this.edmIsShownAt = edmIsShownAt;
    }

    public String getEdmObject() {
        return edmObject;
    }

    public void setEdmObject(String edmObject) {
        this.edmObject = edmObject;
    }

    public EdmProvider getEdmProvider() {
        return edmProvider;
    }

    public void setEdmProvider(EdmProvider edmProvider) {
        this.edmProvider = edmProvider;
    }

    public EdmRights getEdmRights() {
        return edmRights;
    }

    public void setEdmRights(EdmRights edmRights) {
        this.edmRights = edmRights;
    }

    public String getAggregatedCHO() {
        return aggregatedCHO;
    }

    public void setAggregatedCHO(String aggregatedCHO) {
        this.aggregatedCHO = aggregatedCHO;
    }

    public Boolean getEdmPreviewNoDistribute() {
        return edmPreviewNoDistribute;
    }

    public void setEdmPreviewNoDistribute(Boolean edmPreviewNoDistribute) {
        this.edmPreviewNoDistribute = edmPreviewNoDistribute;
    }

}
