package com.example.olya.whattocook;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface FoodApi {

    @GET("/search")
    Call<RecipeSearch> getData(@Query("key") String apiKey, @Query("q") String q,
                               @Query("sort") String sort, @Query("page") int page);
}
