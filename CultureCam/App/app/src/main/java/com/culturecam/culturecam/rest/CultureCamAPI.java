package com.culturecam.culturecam.rest;


import com.culturecam.culturecam.entities.culturecam.ImageDetails;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface CultureCamAPI {

    @FormUrlEncoded
    @POST("upload.php")
    Call<ResponseBody> postImage(@Field("image") String base64, @Field("name") String name);

    @GET("get_object_data.php/")
    Call<ImageDetails> getDetails(@Query("oid") String oid);

}
