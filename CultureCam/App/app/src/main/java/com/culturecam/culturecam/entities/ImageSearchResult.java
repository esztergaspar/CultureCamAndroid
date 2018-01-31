package com.culturecam.culturecam.entities;

import java.io.Serializable;
import java.util.List;

public class ImageSearchResult implements Serializable {
    private final List<SearchResultImage> images;
    private final String imageIdentifier;

    public ImageSearchResult(List<SearchResultImage> images, String imageIdentifier) {
        this.images = images;
        this.imageIdentifier = imageIdentifier;
    }

    public List<SearchResultImage> getImages() {
        return images;
    }

    public String getImageIdentifier() {
        return imageIdentifier;
    }
}
