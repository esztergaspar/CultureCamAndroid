
package com.culturecam.culturecam.entities.culturecam;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Object implements Serializable
{

    @SerializedName("aggregations")
    @Expose
    private List<Aggregation> aggregations = null;
    @SerializedName("about")
    @Expose
    private String about;
    @SerializedName("title")
    @Expose
    private List<String> title = null;
    @SerializedName("year")
    @Expose
    private List<String> year = null;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("timestamp_created_epoch")
    @Expose
    private Long timestampCreatedEpoch;
    @SerializedName("timestamp_update_epoch")
    @Expose
    private Long timestampUpdateEpoch;
    @SerializedName("timestamp_update")
    @Expose
    private String timestampUpdate;
    @SerializedName("timestamp_created")
    @Expose
    private String timestampCreated;
    private final static long serialVersionUID = 5193120301506958200L;

    public List<Aggregation> getAggregations() {
        return aggregations;
    }

    public void setAggregations(List<Aggregation> aggregations) {
        this.aggregations = aggregations;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public List<String> getTitle() {
        return title;
    }

    public void setTitle(List<String> title) {
        this.title = title;
    }

    public List<String> getYear() {
        return year;
    }

    public void setYear(List<String> year) {
        this.year = year;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getTimestampCreatedEpoch() {
        return timestampCreatedEpoch;
    }

    public void setTimestampCreatedEpoch(Long timestampCreatedEpoch) {
        this.timestampCreatedEpoch = timestampCreatedEpoch;
    }

    public Long getTimestampUpdateEpoch() {
        return timestampUpdateEpoch;
    }

    public void setTimestampUpdateEpoch(Long timestampUpdateEpoch) {
        this.timestampUpdateEpoch = timestampUpdateEpoch;
    }

    public String getTimestampUpdate() {
        return timestampUpdate;
    }

    public void setTimestampUpdate(String timestampUpdate) {
        this.timestampUpdate = timestampUpdate;
    }

    public String getTimestampCreated() {
        return timestampCreated;
    }

    public void setTimestampCreated(String timestampCreated) {
        this.timestampCreated = timestampCreated;
    }

}
