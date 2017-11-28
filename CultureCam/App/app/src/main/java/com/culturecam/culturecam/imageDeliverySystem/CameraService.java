package com.culturecam.culturecam.imageDeliverySystem;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;

public class CameraService implements PictureRequest{
    @Override
    public Bitmap requestPicture(Context context) {
        //TODO: delete this, just for testing olé
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        //activity.startActivityForResult(intent, 1001);
        return null;
    }
}
