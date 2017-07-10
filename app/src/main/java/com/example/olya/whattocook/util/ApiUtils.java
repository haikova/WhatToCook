package com.example.olya.whattocook.util;


import com.example.olya.whattocook.network.FoodApi;
import com.example.olya.whattocook.network.RetrofitClient;

public class ApiUtils {

    public static final String BASE_URL = "http://food2fork.com/";

    public static FoodApi getFoodApiService() {
        return RetrofitClient.getClient(BASE_URL).create(FoodApi.class);
    }
}