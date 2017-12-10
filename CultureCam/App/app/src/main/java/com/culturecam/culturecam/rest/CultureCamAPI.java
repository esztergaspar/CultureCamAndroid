package com.culturecam.culturecam.rest;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface CultureCamAPI {

    @FormUrlEncoded
    @POST("upload.php")
    Call<ResponseBody> postImage(@Field("image") String base64, @Field("name") String name);


}
