package com.culturecam.culturecam.imageSearchSystem;

import android.graphics.Bitmap;
import android.util.Log;

import com.culturecam.culturecam.entities.ImageDTO;

import java.util.List;
import java.util.concurrent.ConcurrentSkipListSet;


public abstract class SearchEngine implements ImageSearchEngine {
    private static final String TAG = "SearchEngine";

    private ConcurrentSkipListSet<SearchResultObserver> observers = new ConcurrentSkipListSet<>();

    @Override
    public void addSearchResultObserver(SearchResultObserver observer) {
        Log.d(TAG, "Add search result observer");
        observers.add(observer);
    }

    protected void returnSearchresults(List<ImageDTO> results) {
        synchronized (observers) {
            for(SearchResultObserver observer: observers) {
                observer.update(results);
            }
        }
    }

}
