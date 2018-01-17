package com.culturecam.culturecam.imageSearchSystem;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.Base64;

import com.culturecam.culturecam.entities.ImageSearchResult;
import com.culturecam.culturecam.entities.SearchResultImage;
import com.culturecam.culturecam.entities.iRSearchEngine.IRSearchResult;
import com.culturecam.culturecam.entities.iRSearchEngine.ResultImage;
import com.culturecam.culturecam.rest.CultureCamAPI;
import com.culturecam.culturecam.rest.ImageSimilarityAPI;
import com.culturecam.culturecam.util.Constants;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class IRSearchEngine implements ImageSearchEngine {
    private static CultureCamAPI cultureCamApi;
    private static ImageSimilarityAPI searchApi;

    public IRSearchEngine() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.addInterceptor(logging);
        OkHttpClient httpClient = httpClientBuilder.build();
        cultureCamApi = new Retrofit.Builder().baseUrl("http://www.culturecam.eu")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient).build().create(CultureCamAPI.class);

        searchApi = new Retrofit.Builder().baseUrl("http://image-similarity.ait.ac.at")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient).build().create(ImageSimilarityAPI.class);
    }

    @Override
    public void uploadImage(Bitmap image, Callback<ResponseBody> callback) {
        String imageBase64 = encodeImageToString(image);
        String serverString = "data:image/jpeg;base64," + imageBase64;
        Long millis = Calendar.getInstance().getTimeInMillis();
        cultureCamApi.postImage(serverString, String.valueOf(millis)).enqueue(callback);
    }

    @Override
    public void searchImages(final String imageIdentifier, final ImageSearchCallback callback) {
        Callback<IRSearchResult> irCallback = new Callback<IRSearchResult>() {

            @Override
            public void onResponse(@NonNull Call<IRSearchResult> call, @NonNull Response<IRSearchResult> response) {
                if (!response.isSuccessful() || response.body() == null) {
                    callback.onError(response.code(), response.errorBody());
                    return;
                }
                List<SearchResultImage> images = new ArrayList<>();
                for (ResultImage image : response.body().getItems()) {
                    images.add(new ImageConverter(image));
                }
                ImageSearchResult result = new ImageSearchResult(images, imageIdentifier);
                callback.onSuccess(result, response.headers());
            }

            @Override
            public void onFailure(@NonNull Call<IRSearchResult> call, @NonNull Throwable t) {
                callback.onFailure(t);
            }
        };
        Call<IRSearchResult> call = searchApi.cultureCamSearchImage(
                "http://culturecam.eu/images/" + imageIdentifier, Constants.START,
                Constants.ROWS, Constants.WSKEY, Constants.PROFILE);
        call.enqueue(irCallback);
    }

    private static String encodeImageToString(Bitmap imageBitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        if (imageBitmap != null) {
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 60, stream);
            byte[] byteArray = stream.toByteArray();
            return Base64.encodeToString(byteArray, Base64.DEFAULT);
        } else {
            return null;
        }
    }

    private static class ImageConverter implements SearchResultImage {
        private final ResultImage resultImage;

        ImageConverter(ResultImage resultImage) {
            this.resultImage = resultImage;
        }

        @Override
        public String getResourceId() {
            return resultImage.getResourceId();
        }

        @Override
        public String getUrl() {
            return resultImage.getCachedThmbUrl();
        }
    }
}
