package com.cluturecam.culturecam.app.system;


import android.graphics.Bitmap;

import com.cluturecam.culturecam.entities.ImageDTO;
import com.cluturecam.culturecam.imageSearchSystem.SearchResultObserver;

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
