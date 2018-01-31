package com.culturecam.culturecam.app.gui;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.culturecam.culturecam.R;
import com.culturecam.culturecam.entities.ImageSearchResult;
import com.culturecam.culturecam.imageSearchSystem.ImageSearchCallback;
import com.culturecam.culturecam.imageSearchSystem.ImageSearchService;
import com.culturecam.culturecam.util.Constants;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Headers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        ImageSearchService.getInstance().searchImages(imageIdentifier, new ImageSearchCallback() {
            @Override
            public void onFailure(Throwable t) {
                failure("Searching images failed", t);
            }

            @Override
            public void onError(int code, ResponseBody body) {
                try {
                    failure("Searching images failed: " + body.string(), null);
                } catch (IOException e) {
                    Log.e(TAG, "Failed to get Body",e);
                }
            }

            @Override
            public void onSuccess(ImageSearchResult imageSearchResult, Headers headers) {
                Log.i(TAG, "Found " + imageSearchResult.getImages().size() + " matching images");
                textView.setText(String.format("Found %d matching images", imageSearchResult.getImages().size()));
                progressBar.setProgress(100);
                startResultView(imageSearchResult);
            }
        });
    }

    private void startResultView(ImageSearchResult result) {
        Log.d(TAG, "start load view");
        Intent intent = new Intent(this, ResultViewActivity.class);
        intent.putExtra(RESULT, result);
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
