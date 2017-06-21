package com.example.olya.whattocook.network;


import com.example.olya.whattocook.model.RecipeSearch;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface FoodApi {

    @GET("/api/search")
    Call<RecipeSearch> getData(@Query("key") String apiKey, @Query("q") String q, @Query("page") int page);
}
