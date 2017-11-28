package com.culturecam.culturecam.imageSearchSystem;

import android.graphics.Bitmap;
import android.util.Log;

import com.culturecam.culturecam.entities.ImageDTO;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArraySet;


public abstract class SearchEngine implements ImageSearchEngine {
    private static final String TAG = "SearchEngine";

    private Set<SearchResultObserver> observers = new CopyOnWriteArraySet<>();

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
