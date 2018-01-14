package com.culturecam.culturecam.imageSearchSystem;

import com.culturecam.culturecam.entities.ImageSearchResult;

public interface ImageSearchCallback {

    void onFailure(Throwable t);

    void onError(int code, okhttp3.ResponseBody body);

    void onSuccess(ImageSearchResult imageSearchResult, okhttp3.Headers headers);
}
