package com.culturecam.culturecam.app.gui;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.culturecam.culturecam.R;
import com.culturecam.culturecam.app.system.ImageDeliveryController;
import com.culturecam.culturecam.app.system.ImageSearchService;

import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
* https://developer.android.com/training/camera/photobasics.html#TaskCaptureIntents*/

public class activity_chooseimage extends AppCompatActivity {

    private static final String TAG = "activity_chooseimage";
    private ImageDeliveryController imageDeliveryController;
    private ImageSearchService imageSearchService;
    private String mCurrentPhotoPath;
    private Uri photoURI;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int SELECT_PICTURE = 2;
    public static final String IMAGE_URI = "image_uri";

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
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Log.e(TAG,"Error creating file");
                return;
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                photoURI = FileProvider.getUriForFile(this,
                        "com.cluturecam.culturecam.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "in onActivityResult method");

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Log.d(TAG, "received image");

            Intent intent = new Intent(this, LoadViewActivity.class);
            intent.putExtra(IMAGE_URI,mCurrentPhotoPath);
            startActivity(intent);
        }

        /* IMAGE FROM MEDIA LIBRARY
                /*
        Uri imageURI = intent.getParcelableExtra(IMAGE_URI);



        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        android.database.Cursor cursor = getContentResolver().query(imageURI, filePathColumn, null, null, null);
        if (cursor == null)
            return;

        cursor.moveToFirst();

        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String filePath = cursor.getString(columnIndex);
        cursor.close();

         */
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getApplication().getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }
}
