package com.culturecam.culturecam.app.system;


import android.graphics.Bitmap;
import android.util.Log;

import com.culturecam.culturecam.entities.SearchEngines;
import com.culturecam.culturecam.entities.SearchResult;
import com.culturecam.culturecam.imageSearchSystem.IRSearchEngine;
import com.culturecam.culturecam.imageSearchSystem.ImageSearchEngine;

import okhttp3.ResponseBody;
import retrofit2.Callback;

public class ImageSearchService implements ImageSearchEngine {
    private static final String TAG = "ImageSearchService";
    private static ImageSearchService instance;
    private SearchEngines currentSearchEngine = SearchEngines.IR_SEARCH_ENGINE;

    private ImageSearchService(){
    }

    public synchronized static ImageSearchService getInstance(){
        if(instance == null) {
            instance = new ImageSearchService();
        }
        return instance;
    }

    public synchronized void uploadImage(Bitmap image, Callback<ResponseBody> callback) {
        currentSearchEngine.getSearchEngine().uploadImage(image, callback);
    }

    public synchronized void searchImages(String imageIdentifier, Callback<SearchResult> callback){
        currentSearchEngine.getSearchEngine().searchImages(imageIdentifier, callback);
    }

    public synchronized void setCurrentSearchEngine(SearchEngines searchEngine) {
        Log.v(TAG, "set current search engine: " + searchEngine.name());
        currentSearchEngine = searchEngine;
    }
}
