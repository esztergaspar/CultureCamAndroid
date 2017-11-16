package com.culturecam.culturecam.app.gui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.culturecam.culturecam.R;
import com.culturecam.culturecam.app.system.ImageSearchService;

import static com.culturecam.culturecam.app.system.ImageSearchService.EXTRA_IMAGE;

public class LoadViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loadview);
        Intent intent = getIntent();
        //Bitmap image = (Bitmap) intent.getSerializableExtra(ImageSearchService.EXTRA_IMAGE);
    }


}
