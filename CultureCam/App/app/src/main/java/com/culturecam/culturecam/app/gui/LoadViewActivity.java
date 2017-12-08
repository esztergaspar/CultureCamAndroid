package com.culturecam.culturecam.app.gui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import com.culturecam.culturecam.R;
import com.culturecam.culturecam.rest.CultureCamAPI;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Calendar;


import static com.culturecam.culturecam.app.gui.activity_chooseimage.IMAGE_URI;

/**
 * This class is doing the actuall uploading
 */

public class LoadViewActivity extends AppCompatActivity {
    private static final String TAG = "LoadViewActivity";

    private CultureCamAPI service;

    @BindView(R.id.iv_loadpreview)
    public ImageView imageView;

    @BindView(R.id.tv_loadingText)
    public TextView textView;

    @BindView(R.id.pb_searchProgress)
    public ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "call onCreate method");
        setContentView(R.layout.activity_loadview);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String imagePath = intent.getStringExtra(IMAGE_URI);
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
        File file = new File(imagePath);
        Bitmap image = BitmapFactory.decodeFile(file.getAbsolutePath());
        imageView.setImageBitmap(image);

        String imageBase64 = convertImageToStringForServer(image);
        String serverString = "data:image/jpeg;base64," + imageBase64;
        Log.d(TAG, "Image String; \n" + imageBase64);

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
// set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
// add your other interceptors …

// add logging as last interceptor
        httpClient.addInterceptor(logging);  // <-- this is the important line!

        // Change base URL to your upload server URL.
        service = new Retrofit.Builder().baseUrl(getString(R.string.server_url)).client(httpClient.build()).build().create(CultureCamAPI.class);
/*
        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("image", file.getName(), reqFile);
        Long millis = Calendar.getInstance().getTimeInMillis();
        RequestBody name = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(millis));
*/
        Long millis = Calendar.getInstance().getTimeInMillis();
        service.postImage(serverString, String.valueOf(millis)).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.i(TAG, "Image upload complete.");
                String serverResponse = response.message();
                Log.d(TAG,"Server response: "+ serverResponse);
                //Log.d(TAG, response.toString());
                try {
                    String imageIdentifier = response.body().string();
                    Log.d(TAG,"Image Identifier: " + imageIdentifier );
                } catch (IOException e) {
                    Log.e(TAG,"Error getting response body",e);
                    return;
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG, "" + "Upload hat nicht funktioniert!");
                t.printStackTrace();
            }
        });

    }

    public static String convertImageToStringForServer(Bitmap imageBitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        if(imageBitmap != null) {
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 60, stream);
            byte[] byteArray = stream.toByteArray();
            return Base64.encodeToString(byteArray, Base64.DEFAULT);
        }else{
            return null;
        }
    }
}
