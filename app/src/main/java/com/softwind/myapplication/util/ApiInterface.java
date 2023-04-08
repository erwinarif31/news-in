package com.softwind.myapplication.util;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("news")
    Call<NewsResponse> getLatestNews(
            @Query("country") String country,
            @Query("apiKey") String apiKey
    );
    @GET("news")
    Call<NewsResponse> getNewsWithCategory(
            @Query("country") String country,
            @Query("apiKey") String apiKey,
            @Query("category") String category
    );
}
