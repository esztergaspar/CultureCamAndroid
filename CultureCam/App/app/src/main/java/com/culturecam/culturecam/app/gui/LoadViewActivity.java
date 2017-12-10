package com.culturecam.culturecam.app.gui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.culturecam.culturecam.R;
import com.culturecam.culturecam.entities.SearchResult;
import com.culturecam.culturecam.rest.CultureCamAPI;
import com.culturecam.culturecam.rest.ImageSimilarityAPI;
import com.culturecam.culturecam.util.Constants;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;


import static com.culturecam.culturecam.app.gui.activity_chooseimage.IMAGE_URI;
import static com.culturecam.culturecam.util.Constants.IMAGE_SIZE;

/**
 * This class is doing the actuall uploading
 */

public class LoadViewActivity extends AppCompatActivity {
    private static final String TAG = "LoadViewActivity";
    public static final String RESULT = "result";

    private static CultureCamAPI cultureCamApi;
    private static ImageSimilarityAPI searchApi;

    @BindView(R.id.iv_loadpreview)
    public ImageView imageView;

    @BindView(R.id.tv_loadingText)
    public TextView textView;

    @BindView(R.id.pb_searchProgress)
    public ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "call onCreate method");
        setContentView(R.layout.activity_loadview);
        ButterKnife.bind(this);

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.addInterceptor(logging);
        OkHttpClient httpClient = httpClientBuilder.build();
        cultureCamApi = new Retrofit.Builder().baseUrl(getString(R.string.server_url))
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient).build().create(CultureCamAPI.class);

        searchApi = new Retrofit.Builder().baseUrl(getString(R.string.image_similarity_engine_url))
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient).build().create(ImageSimilarityAPI.class);


        Intent intent = getIntent();
        String imagePath = intent.getStringExtra(IMAGE_URI);

        Picasso.with(this).load(imagePath).resize(IMAGE_SIZE,IMAGE_SIZE).centerInside().into(imageView);

        textView.setText("Uploading Image to Server...");
        progressBar.setProgress(10);
        Bitmap image = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        uploadImage(image);

    }

    private void uploadImage(Bitmap image) {
        String imageBase64 = encodeImageToString(image);
        String serverString = "data:image/jpeg;base64," + imageBase64;
        Log.d(TAG, "Image String; \n" + imageBase64);

        Long millis = Calendar.getInstance().getTimeInMillis();
        cultureCamApi.postImage(serverString, String.valueOf(millis)).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.i(TAG, "Image upload complete");
                String serverResponse = response.message();
                Log.d(TAG, "Server response: " + serverResponse);
                try {
                    String imageIdentifier = response.body().string();
                    Log.d(TAG, "Image Identifier: " + imageIdentifier);
                    searchImages(imageIdentifier);

                } catch (IOException e) {
                    Log.e(TAG, "Error getting response body", e);
                    return;
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG, "" + "Upload failed", t);
            }
        });
    }

    private void searchImages(String imageIdentifier) {
        textView.setText("Searching for similar Images...");
        progressBar.setProgress(50);

        Call<SearchResult> call = searchApi.searchImage(
                getString(R.string.query_url) + imageIdentifier, Constants.START,
                Constants.ROWS, Constants.WSKEY, Constants.PROFILE);

        call.enqueue(new Callback<SearchResult>() {
            @Override
            public void onResponse(Call<SearchResult> call, Response<SearchResult> response) {
                if (!response.isSuccessful()) {
                    Log.e(TAG, "Searching images failed: " + response.toString());
                    return;
                }
                SearchResult result = response.body();
                Log.i(TAG, "Found " + result.getItemsCount() + " matching images");
                textView.setText(String.format("Found %d matching images", result.getItemsCount()));
                progressBar.setProgress(100);
                startLoadView(result);
            }

            @Override
            public void onFailure(Call<SearchResult> call, Throwable t) {
                Log.e(TAG, "Searching images failed", t);
            }
        });
    }

    private void startLoadView(SearchResult result) {
        Log.d(TAG,"start load view");
        Intent intent = new Intent(this, ResultViewActivity.class);
        intent.putExtra(RESULT,result);
        startActivity(intent);
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
}
