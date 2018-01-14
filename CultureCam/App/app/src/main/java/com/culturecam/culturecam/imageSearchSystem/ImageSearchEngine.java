package com.culturecam.culturecam.imageSearchSystem;

import android.graphics.Bitmap;

import okhttp3.ResponseBody;
import retrofit2.Callback;

public interface ImageSearchEngine {
    void uploadImage(Bitmap image, Callback<ResponseBody> callback);

    void searchImages(String imageIdentifier, final ImageSearchCallback callback);
}
