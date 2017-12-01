package com.culturecam.culturecam.app.gui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.culturecam.culturecam.R;
import com.culturecam.culturecam.app.system.ImageSearchService;

import static com.culturecam.culturecam.app.gui.activity_chooseimage.REQUEST_IMAGE_CAPTURE;
import static com.culturecam.culturecam.app.system.ImageSearchService.EXTRA_IMAGE;

public class LoadViewActivity extends AppCompatActivity {

    public ImageView imageView;
    private static final String TAG = "LoadViewActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loadview);
        imageView = (ImageView) findViewById(R.id.imageView);
        Intent intent = getIntent();

        Log.d(TAG, "in onCreate method");

        Bundle extras = intent.getExtras();
        Bitmap imageBitmap = (Bitmap) extras.get("data");
        imageView.setImageBitmap(imageBitmap);
    }
}
