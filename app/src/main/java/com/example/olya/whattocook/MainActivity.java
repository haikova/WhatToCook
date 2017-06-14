package com.example.olya.whattocook;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static FoodApi foodApi;
    static final String API_KEY = "221a9145a7580bad1fa7ec991bc113b7";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        foodApi = RetrofitClient.getApi();

        foodApi.getData(API_KEY, "onion", "r", 1).enqueue(new Callback <RecipeSearch>() {

            @Override
            public void onResponse(Call<RecipeSearch> call, Response<RecipeSearch> response) {
                if (response.isSuccessful()) {
                    Log.d("response","successful");
                    RecipeSearch result = response.body();
                } else {
                    Log.d("response","no successful");
                }
            }

            @Override
            public void onFailure(Call<RecipeSearch> call, Throwable t) {
                Toast.makeText(MainActivity.this, "An error occurred during networking", Toast.LENGTH_SHORT).show();
            }
        });
    }



}
