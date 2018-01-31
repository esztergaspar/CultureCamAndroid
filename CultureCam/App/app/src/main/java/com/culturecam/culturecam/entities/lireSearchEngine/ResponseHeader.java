
package com.culturecam.culturecam.entities.lireSearchEngine;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ResponseHeader implements Serializable {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("QTime")
    @Expose
    private Integer qTime;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getQTime() {
        return qTime;
    }

    public void setQTime(Integer qTime) {
        this.qTime = qTime;
    }

}
