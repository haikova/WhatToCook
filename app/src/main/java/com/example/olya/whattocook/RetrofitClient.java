package com.example.olya.whattocook;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Converter;

public class RetrofitClient{

    static final String BASE_URL = "http://food2fork.com/api/";

    public static FoodApi getApi(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        FoodApi foodApi = retrofit.create(FoodApi.class);
        return foodApi;
    }
}
