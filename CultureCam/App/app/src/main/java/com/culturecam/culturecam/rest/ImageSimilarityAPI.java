package com.culturecam.culturecam.rest;

import com.culturecam.culturecam.entities.iRSearchEngine.IRSearchResult;
import com.culturecam.culturecam.entities.lireSearchEngine.LireSearchResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ImageSimilarityAPI {
    @GET("culturecam-web/searchByUrl.json/")
    Call<IRSearchResult> cultureCamSearchImage(@Query("queryImageUrl") String imageUrl,
                                               @Query("start") int start, @Query("rows") int rows, @Query("wskey") String wskey,
                                               @Query("profile") String profile);

    @GET("solr/lire/lireq")
    Call<LireSearchResult> lireRandomSearch();
}
