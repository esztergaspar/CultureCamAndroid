package com.culturecam.culturecam.entities;

import java.io.Serializable;
import java.util.List;

public class ImageSearchResult implements Serializable {
    private List<SearchResultImage> images;

    public ImageSearchResult(List<SearchResultImage> images) {
        this.images = images;
    }

    public List<SearchResultImage> getImages() {
        return images;
    }

    public void setImages(List<SearchResultImage> images) {
        this.images = images;
    }
}
