package com.culturecam.culturecam.imageSearchSystem;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.Log;

import com.culturecam.culturecam.entities.ImageSearchResult;
import com.culturecam.culturecam.entities.SearchResultImage;
import com.culturecam.culturecam.entities.lireSearchEngine.LireSearchResult;
import com.culturecam.culturecam.rest.ImageSimilarityAPI;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LireSearchEngine implements ImageSearchEngine {
    private static final String TAG = "LireSearchEngine";
    private final ImageSimilarityAPI searchApi;

    public LireSearchEngine() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.addInterceptor(logging);
        OkHttpClient httpClient = httpClientBuilder.build();

        searchApi = new Retrofit.Builder().baseUrl("http://image-similarity.ait.ac.at")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient).build().create(ImageSimilarityAPI.class);
    }

    @Override
    public void uploadImage(Bitmap image, Callback<ResponseBody> callback) {
        Log.w(TAG, "Image based search currently not supported");
        Log.i(TAG, "Mocking image search with random search");
        ResponseBody responseBody = ResponseBody.create(MediaType.parse("text/plain; charset=utf-8"), "No Image");
        Response<ResponseBody> response = Response.success(responseBody);
        callback.onResponse(null, response);
    }

    @Override
    public void searchImages(final String imageIdentifier, final ImageSearchCallback callback) {
        Callback<LireSearchResult> lireCallback = new Callback<LireSearchResult>() {

            @Override
            public void onResponse(@NonNull Call<LireSearchResult> call, @NonNull Response<LireSearchResult> response) {
                if (!response.isSuccessful() || response.body() == null) {
                    callback.onError(response.code(), response.errorBody());
                    return;
                }
                List<SearchResultImage> images = new ArrayList<>();
                for (com.culturecam.culturecam.entities.lireSearchEngine.Response r : response.body().getResponse()) {
                    images.add(new LireSearchEngine.ImageConverter(r));
                }
                ImageSearchResult result = new ImageSearchResult(images, imageIdentifier);
                callback.onSuccess(result, response.headers());
            }

            @Override
            public void onFailure(@NonNull Call<LireSearchResult> call, @NonNull Throwable t) {
                callback.onFailure(t);
            }
        };
        Call<LireSearchResult> call = searchApi.lireRandomSearch();
        call.enqueue(lireCallback);
    }

    private static class ImageConverter implements SearchResultImage {
        private final com.culturecam.culturecam.entities.lireSearchEngine.Response response;

        ImageConverter(com.culturecam.culturecam.entities.lireSearchEngine.Response response) {
            this.response = response;
        }

        @Override
        public String getResourceId() {
            return response.getId();
        }

        @Override
        public String getUrl() {
            return response.getImgurl();
        }
    }
}
