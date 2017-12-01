package com.culturecam.culturecam.app.gui;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.culturecam.culturecam.R;
import com.culturecam.culturecam.app.system.ImageDeliveryController;
import com.culturecam.culturecam.app.system.ImageSearchService;

import android.util.Log;

/*
* https://developer.android.com/training/camera/photobasics.html#TaskCaptureIntents*/

public class activity_chooseimage extends AppCompatActivity {

    private static final String TAG = "activity_chooseimage";
    private ImageDeliveryController imageDeliveryController;
    private ImageSearchService imageSearchService;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int SELECT_PICTURE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(TAG, "Activity onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chooseimage);

        //ueber den Kontext der App kann man auf die Ressourcen zugreifen
        ImageDeliveryController.getInstance().setContext(this);
        ImageSearchService.getInstance().setContext(this);
    }

    public void onClickedMediaLibraryButton(View view){
        Log.v(TAG, "Button Media Library clicked");

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
       // if (intent.resolveActivity(getPackageManager()) != null) {
            Log.d(TAG, "start image select");
            intent.setType("image/*");
            startActivityForResult(Intent.createChooser(intent,"Select Picture"), SELECT_PICTURE);
        //}
    }

    public void onClickedCameraButton(View view){
        Log.v(TAG, "Button Camera clicked");

        if(!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            Log.e(TAG, "Camera not found. ");
            return;
        }

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            //finish();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "in onActivityResult method");

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();

            Log.d(TAG, "received image from camera");

            Intent intent = new Intent(this, LoadViewActivity.class);
            intent.putExtras(data.getExtras());
            startActivity(intent);
        }

        if (requestCode == SELECT_PICTURE && resultCode == RESULT_OK) {

            Uri imageUri = data.getData();
            //TODO
            //Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);

            Log.d(TAG, "received image from media library");

            Intent intent = new Intent(this, LoadViewActivity.class);
            intent.putExtras(data.getExtras());
            startActivity(intent);
        }
    }
}
