package com.culturecam.culturecam.app.gui;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.culturecam.culturecam.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/*
* https://developer.android.com/training/camera/photobasics.html#TaskCaptureIntents
*
* Code for Permission granting from official Android Tutorial:
* https://developer.android.com/training/permissions/requesting.html
* */

public class activity_chooseimage extends AppCompatActivity {

    private static final String TAG = "activity_chooseimage";
    private Uri photoURI;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_SELECT_IMAGE = 2;
    private static final int REQUEST_READ_PERMISSION = 3;
    public static final String IMAGE_URI = "image_uri";

    @BindView(R.id.b_mediaLibrary)
    public Button mediaButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(TAG, "activity onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chooseimage);
        ButterKnife.bind(this);
        mediaButton.setEnabled(true);
    }

    public void onClickedMediaLibraryButton(View view){
        Log.v(TAG, "Button Media Library clicked");

        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "Activity has no permission to read external File System");
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Log.w(TAG, "TODO: show permission explanation");
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                requestPermissions();
            } else {
                // No explanation needed, we can request the permission.
                requestPermissions();
            }
        } else {
            Log.i(TAG, "Read file Permission is granted");
            getImageFromMediaLibrary();
        }
    }

    private void requestPermissions() {
        Log.d(TAG, "Requesting read file permissions");
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                REQUEST_READ_PERMISSION);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_READ_PERMISSION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay!
                    Log.i(TAG, "Read file Permission granted");
                    getImageFromMediaLibrary();

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Log.i(TAG,"File permissions denied - disable Media Library Button");
                    mediaButton.setEnabled(false);
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
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

    public void onClickedDropboxButton(View view){
        Log.v(TAG, "Button Dropbox clicked");

        Intent pickPhoto = new Intent(Intent.ACTION_GET_CONTENT);
        pickPhoto.setType("image/*");
        pickPhoto.setPackage("com.dropbox.android");
        try {
            startActivityForResult(pickPhoto , REQUEST_SELECT_IMAGE);
        }catch(ActivityNotFoundException e){
            Log.w(TAG, "Dropbox not installed");

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Dropbox not found");
            builder.setMessage("Please install the Dropbox App");
            builder.show();
        }



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "in onActivityResult method");
        if(resultCode != RESULT_OK) {
            Log.w(TAG, "Got invalid result");
            return;
        }

        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            Log.d(TAG, "received image from camera");

            Intent intent = new Intent(this, LoadViewActivity.class);
            intent.putExtra(IMAGE_URI,photoURI);
            startActivity(intent);
        }

        if (requestCode == REQUEST_SELECT_IMAGE) {
            Log.d(TAG, "received image from file system");

            Intent intent = new Intent(this, LoadViewActivity.class);
            intent.putExtra(IMAGE_URI,data.getData());
            startActivity(intent);
        }
    }

    private void getImageFromMediaLibrary() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(pickPhoto, "Select Image Source"),REQUEST_SELECT_IMAGE);
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
        return image;
    }
}
