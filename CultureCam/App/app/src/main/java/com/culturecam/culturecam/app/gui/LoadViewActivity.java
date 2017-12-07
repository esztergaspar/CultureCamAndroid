package com.culturecam.culturecam.app.gui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import com.culturecam.culturecam.R;
import com.culturecam.culturecam.rest.CultureCamAPI;

import java.io.File;


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
        imageView.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));

        // Change base URL to your upload server URL.
        service = new Retrofit.Builder().baseUrl("http://192.168.0.234:3000").build().create(CultureCamAPI.class);

        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("upload", file.getName(), reqFile);
        RequestBody name = RequestBody.create(MediaType.parse("text/plain"), "upload_test");

        retrofit2.Call<okhttp3.ResponseBody> req = service.postImage(body, name);
        req.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG, "upload hat nicht funktioniert!");
                t.printStackTrace();
            }
        });

    }
}
