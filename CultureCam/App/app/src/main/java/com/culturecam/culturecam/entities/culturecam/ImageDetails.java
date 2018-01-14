
package com.culturecam.culturecam.entities.culturecam;

import java.io.Serializable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImageDetails implements Serializable {

    @SerializedName("apikey")
    @Expose
    private String apikey;
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("statsDuration")
    @Expose
    private Integer statsDuration;
    @SerializedName("requestNumber")
    @Expose
    private Integer requestNumber;
    @SerializedName("object")
    @Expose
    private Object object;
    private final static long serialVersionUID = -8936932545547326455L;

    public String getApikey() {
        return apikey;
    }

    public void setApikey(String apikey) {
        this.apikey = apikey;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Integer getStatsDuration() {
        return statsDuration;
    }

    public void setStatsDuration(Integer statsDuration) {
        this.statsDuration = statsDuration;
    }

    public Integer getRequestNumber() {
        return requestNumber;
    }

    public void setRequestNumber(Integer requestNumber) {
        this.requestNumber = requestNumber;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public String getProvider() {
        try {
            return object.getAggregations().get(0).getEdmProvider().getEn().get(0);
        } catch (NullPointerException e) {
            return "No Information available";
        }
    }

    public String getRights() {
        try {
            return object.getAggregations().get(0).getEdmRights().getDef().get(0);
        } catch (NullPointerException e) {
            return "No Information available";
        }
    }

}
