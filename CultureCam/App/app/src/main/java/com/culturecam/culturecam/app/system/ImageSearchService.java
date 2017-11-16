package com.culturecam.culturecam.app.system;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;

import com.culturecam.culturecam.app.gui.LoadViewActivity;
import com.culturecam.culturecam.entities.ImageDTO;
import com.culturecam.culturecam.imageDeliverySystem.PhotoLibraryService;
import com.culturecam.culturecam.imageDeliverySystem.PictureRequest;
import com.culturecam.culturecam.imageSearchSystem.SearchResultObserver;

import java.util.List;

public class ImageSearchService implements SearchResultObserver{
    private List<ImageDTO> searchResult;
    private Context context;
    private static final ImageSearchService imageSearchService = new ImageSearchService();
    public static final String EXTRA_IMAGE = "image_unicorn";

    private ImageSearchService(){

    }

    public static ImageSearchService getInstance(){
        return imageSearchService;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }


    @Override
    public void update(List<ImageDTO> searchResults) {
        //TODO
    }

    public void searchImage (Bitmap image) {
        Intent intent = new Intent(context, LoadViewActivity.class);
        intent.putExtra(EXTRA_IMAGE, image);
        context.startActivity(intent);
    }

    public List<ImageDTO> getSearchResult() {

        return searchResult;
    }
}
