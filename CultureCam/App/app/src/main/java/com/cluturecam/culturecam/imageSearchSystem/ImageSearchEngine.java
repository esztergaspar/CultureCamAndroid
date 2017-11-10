package com.cluturecam.culturecam.imageSearchSystem;


import android.graphics.Bitmap;

public interface ImageSearchEngine {
    void searchImage (Bitmap image);
    void addSearchResultObserver (SearchResultObserver observer);
}
