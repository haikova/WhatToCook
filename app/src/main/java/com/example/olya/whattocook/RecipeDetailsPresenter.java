package com.example.olya.whattocook;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.olya.whattocook.model.GetRecipe;
import com.example.olya.whattocook.model.RecipeDetails;
import com.example.olya.whattocook.network.ApiUtils;
import com.example.olya.whattocook.network.FoodApi;
import com.squareup.picasso.Picasso;

import java.util.HashSet;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeDetailsPresenter {

    static final String API_KEY = "221a9145a7580bad1fa7ec991bc113b7";

    RecipeDetailsFragment recipeDetailsFragment;
    RecipeDetails recipeDetails;
    private FoodApi foodApi;
    GetRecipe getRecipe;
    String recipeId;
    Set<String> favourites;

    public RecipeDetailsPresenter(RecipeDetailsFragment recipeDetailsFragment, String recipeId){
        this.recipeDetailsFragment = recipeDetailsFragment;
        this.recipeId = recipeId;
    }


    void loadRecipeDetails() {
        foodApi = ApiUtils.getFoodApiService();
        foodApi.getRecipeDetails(API_KEY, recipeId).enqueue(new Callback<GetRecipe>() {
            @Override
            public void onResponse(Call<GetRecipe> call, Response<GetRecipe> response) {

                if (response.isSuccessful()) {
                    getRecipe = response.body();
                    recipeDetails = getRecipe.getRecipeDetails();
                    recipeDetailsFragment.setView(recipeDetails);
                    Log.d("MainActivity", "posts loaded from API");
                } else {
                    int statusCode = response.code();
                    Log.d("q", Integer.toString(statusCode));
                    // handle request errors depending on status code
                }
            }

            @Override
            public void onFailure(Call<GetRecipe> call, Throwable t) {
                Log.d("MainActivity", "error loading from API");

            }
        });
    }

    public void saveFavouriteState() {
        SharedPreferences sharedPreferences = recipeDetailsFragment.getActivity()
                .getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPreferencesEdit = sharedPreferences
                .edit();
        favourites.add(recipeId);
        sharedPreferencesEdit.putStringSet("favourites", favourites);
        sharedPreferencesEdit.commit();
        updateFavourites();
    }

    public void readFavouriteState() {
        SharedPreferences sharedPreferences = recipeDetailsFragment.getActivity()
                .getPreferences(Context.MODE_PRIVATE);
        favourites = sharedPreferences.getStringSet("favourites", new HashSet<String>());
    }

    public void deleteFavouriteState() {
        SharedPreferences sharedPreferences = recipeDetailsFragment.getActivity()
                .getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPreferencesEdit = sharedPreferences
                .edit();
        favourites.remove(recipeId);
        sharedPreferencesEdit.putStringSet("favourites", favourites);
        sharedPreferencesEdit.commit();
        updateFavourites();
    }

    public boolean checkFavouriteState(){
        readFavouriteState();
        if (favourites.contains(recipeId))
            return true;
        else
            return false;
    }

    void updateFavourites(){
        FavouritesListner listner = (FavouritesListner) recipeDetailsFragment.getActivity();
        listner.sendFavourites(favourites);
        Log.d("qawere", favourites.toString());
    }


    public interface FavouritesListner{
        void sendFavourites(Set<String> favourites);
    }
}