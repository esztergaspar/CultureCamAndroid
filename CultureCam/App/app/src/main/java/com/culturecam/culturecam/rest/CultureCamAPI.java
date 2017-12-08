package com.culturecam.culturecam.rest;


import com.culturecam.culturecam.entities.SearchResult;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface CultureCamAPI {

    @FormUrlEncoded
    @POST("upload.php")
    Call<ResponseBody> postImage(@Field("image") String base64,@Field("name") String name);

    //queryImageUrl=http://culturecam.eu/images/1512725391296_f66a3af3401446787f23fed2473d0397.jpg&start=0&rows=32&wskey=xy&profile=similarimage

    @GET("show")
    SearchResult searchImage(@Query("uri") String uri,@Query("queryImageUrl") String imageUrl );
}
