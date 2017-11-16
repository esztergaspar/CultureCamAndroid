package com.cluturecam.culturecam.imageDeliverySystem;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.cluturecam.culturecam.R;

public class PhotoLibraryService implements PictureRequest {

    private static final String TAG = "PhotoLibraryService"; // Bei jeder Klasse einfügen

    @Override
    public Bitmap requestPicture(Context context) {
        Log.v(TAG,"Request Picture called");
        Log.d(TAG,"Getting image from library");
        Drawable drawable = ContextCompat.getDrawable(context, R.drawable.full_size_render);
        Log.i(TAG, "New image was requested");
        Log.v(TAG, "Leaving this method");
        return ((BitmapDrawable)drawable).getBitmap();

    }
}
