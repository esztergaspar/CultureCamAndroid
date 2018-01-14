package com.culturecam.culturecam.rest;

import com.culturecam.culturecam.entities.iRSearchEngine.SearchResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ImageSimilarityAPI {
    @GET("culturecam-web/searchByUrl.json/")
    Call<SearchResult> searchImage(@Query("queryImageUrl") String imageUrl,
                                   @Query("start") int start, @Query("rows") int rows, @Query("wskey") String wskey,
                                   @Query("profile") String profile);


}
