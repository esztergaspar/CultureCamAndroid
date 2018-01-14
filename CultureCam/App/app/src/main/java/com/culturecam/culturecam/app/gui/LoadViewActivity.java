package com.culturecam.culturecam.app.gui;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
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
import com.culturecam.culturecam.app.system.ImageSearchService;
import com.culturecam.culturecam.entities.SearchResult;
import com.culturecam.culturecam.rest.CultureCamAPI;
import com.culturecam.culturecam.rest.ImageSimilarityAPI;
import com.culturecam.culturecam.util.Constants;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

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
    public static final String IMAGE_ID = "image_id";

    private static Target resizeTarget;
    private static String imageId;

    @BindView(R.id.iv_loadpreview)
    public ImageView imageView;

    @BindView(R.id.tv_loadingText)
    public TextView textView;

    @BindView(R.id.pb_searchProgress)
    public ProgressBar progressBar;

    @BindView(R.id.pb_resize)
    public ProgressBar resizeBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "call onCreate method");
        setContentView(R.layout.activity_loadview);
        ButterKnife.bind(this);

        //--- PICASSO ------------------------------------------------------------------
        Picasso picasso = new Picasso.Builder(this)
                .loggingEnabled(Constants.LOG_PICASSO)
                .listener(new Picasso.Listener() {
                    @Override
                    public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                        Log.e("PICASSO", "Image load failed (" + uri + "): " + exception.getMessage(), exception);
                    }
                })
                .build();
        try {
            Picasso.setSingletonInstance(picasso);
        } catch (IllegalStateException e) {
            Log.i(TAG, "Picasso was already initialized");
        }

        textView.setText("Resizing image...");
        progressBar.setProgress(0);
        Intent intent = getIntent();
        Uri imageUri = intent.getParcelableExtra(IMAGE_URI);
        //File file = new File(imagePath);
        resizeTarget = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                Log.d(TAG, "Image resized to " + bitmap.getWidth() + "x" + bitmap.getHeight());
                textView.setText("Uploading Image to Server...");
                progressBar.setProgress(10);
                resizeBar.setVisibility(View.INVISIBLE);
                imageView.setImageBitmap(bitmap);
                uploadImage(bitmap);
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                failure("Loading bitmap failed", null);
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                Log.d(TAG, "Prepared load");
            }
        };
        Picasso.with(this).load(imageUri).resize(IMAGE_SIZE, IMAGE_SIZE).onlyScaleDown().centerInside().into(resizeTarget);
    }

    private void uploadImage(Bitmap image) {
        ImageSearchService.getInstance().uploadImage(image, new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.i(TAG, "Image upload complete");
                String serverResponse = response.message();
                Log.d(TAG, "Server response: " + serverResponse);
                try {
                    String imageIdentifier = response.body().string();
                    Log.d(TAG, "Image Identifier: " + imageIdentifier);
                    imageId = imageIdentifier;
                    searchImages(imageIdentifier);

                } catch (IOException e) {
                    failure("Error getting response body", e);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                failure("Upload failed", t);
            }
        });
    }

    private void searchImages(String imageIdentifier) {
        textView.setText("Searching for similar Images...");
        progressBar.setProgress(50);
        ImageSearchService.getInstance().searchImages(imageIdentifier, new Callback<SearchResult>() {
            @Override
            public void onResponse(Call<SearchResult> call, Response<SearchResult> response) {
                if (!response.isSuccessful()) {
                    failure("Searching images failed: " + response.toString(), null);
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
                failure("Searching images failed", t);
            }
        });
    }

    private void startLoadView(SearchResult result) {
        Log.d(TAG, "start load view");
        Intent intent = new Intent(this, ResultViewActivity.class);
        intent.putExtra(RESULT, result);
        intent.putExtra(IMAGE_ID, imageId);
        startActivity(intent);
    }

    private void failure(String message, Throwable exception) {
        Log.e(TAG, message, exception);
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage(message);
        builder1.setCancelable(false);

        builder1.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        finish();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
}
