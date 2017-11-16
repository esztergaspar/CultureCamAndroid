package com.culturecam.culturecam.app.system;


import android.graphics.Bitmap;

import com.culturecam.culturecam.entities.ImageDTO;
import com.culturecam.culturecam.imageSearchSystem.SearchResultObserver;

import java.util.List;

public class ImageSearchService implements SearchResultObserver{
    private List<ImageDTO> searchResult;

    @Override
    public void update(List<ImageDTO> searchResults) {
        //TODO
    }

    public void searchImage (Bitmap image) {
        //TODO
    }

    public List<ImageDTO> getSearchResult() {
        return searchResult;
    }
}
