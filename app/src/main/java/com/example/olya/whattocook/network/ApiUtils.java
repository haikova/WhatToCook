package com.example.olya.whattocook.network;


public class ApiUtils {

    public static final String BASE_URL = "http://food2fork.com/";

    public static FoodApi getFoodApiService() {
        return RetrofitClient.getClient(BASE_URL).create(FoodApi.class);
    }
}