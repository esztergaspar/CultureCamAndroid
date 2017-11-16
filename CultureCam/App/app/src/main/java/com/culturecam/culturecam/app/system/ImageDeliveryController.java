package com.culturecam.culturecam.app.system;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;

import com.culturecam.culturecam.app.gui.LoadViewActivity;
import com.culturecam.culturecam.imageDeliverySystem.PhotoLibraryService;
import com.culturecam.culturecam.imageDeliverySystem.PictureRequest;

public class ImageDeliveryController {
    private static final ImageDeliveryController imageDeliveryController = new ImageDeliveryController();
    private PictureRequest mediaLibraryService;
    private PictureRequest cameraService;
    private Context context;
    private ImageDeliveryController(){
        mediaLibraryService = new PhotoLibraryService();
        cameraService = new PhotoLibraryService();
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public static ImageDeliveryController getInstance(){
     return imageDeliveryController;
    }

    public void pictureRequestFromMediaLibrary(){
        //Bitmap image = mediaLibraryService.requestPicture(context);

    }

    public void pictureRequestFromCamera(){
        Bitmap image = cameraService.requestPicture(context);
    }

}
