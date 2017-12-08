package com.culturecam.culturecam.entities;


import com.example.ResultImage;

import java.util.List;

public class SearchResult {

    private String apikey;

    private String action;

    private Boolean success;

    private Integer itemsCount;

    private Integer totalResults;

    private List<ResultImage> items = null;

    public String getApikey() {
        return apikey;
    }

    public void setApikey(String apikey) {
        this.apikey = apikey;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Integer getItemsCount() {
        return itemsCount;
    }

    public void setItemsCount(Integer itemsCount) {
        this.itemsCount = itemsCount;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    public List<ResultImage> getItems() {
        return items;
    }

    public void setItems(List<ResultImage> items) {
        this.items = items;
    }

}
