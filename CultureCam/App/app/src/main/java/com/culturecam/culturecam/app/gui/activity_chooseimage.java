package com.culturecam.culturecam.app.gui;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.culturecam.culturecam.R;
import com.culturecam.culturecam.app.system.ImageDeliveryController;
import com.culturecam.culturecam.app.system.ImageSearchService;

import android.util.Log;

public class activity_chooseimage extends AppCompatActivity {

    private static final String TAG = "activity_chooseimage";
    private ImageDeliveryController imageDeliveryController;
    private ImageSearchService imageSearchService;


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

        Bitmap image = ImageDeliveryController.getInstance().pictureRequestFromMediaLibrary();
        ImageSearchService.getInstance().searchImage(image);

    }

    public void onClickedCameraButton(View view){
        Log.v(TAG, "Button Camera clicked");
    }
}
