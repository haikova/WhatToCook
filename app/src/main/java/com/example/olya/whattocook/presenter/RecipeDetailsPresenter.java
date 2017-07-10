package com.example.olya.whattocook.presenter;


import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.olya.whattocook.util.FavouritesDBHelper;
import com.example.olya.whattocook.model.GetRecipe;
import com.example.olya.whattocook.model.RecipeDetails;
import com.example.olya.whattocook.util.ApiUtils;
import com.example.olya.whattocook.network.FoodApi;
import com.example.olya.whattocook.ui.RecipeDetailsFragment;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeDetailsPresenter {

    static final String API_KEY = "221a9145a7580bad1fa7ec991bc113b7";

    private RecipeDetailsFragment recipeDetailsFragment;
    private RecipeDetails recipeDetails;
    private FoodApi foodApi;
    private GetRecipe getRecipe;
    private String recipeId;
    private FavouritesDBHelper favouritesDBHelper;
    private SharedPreferences sharedPreferences;

    public RecipeDetailsPresenter(RecipeDetailsFragment recipeDetailsFragment, String recipeId){
        this.recipeDetailsFragment = recipeDetailsFragment;
        this.recipeId = recipeId;
        favouritesDBHelper = new FavouritesDBHelper(recipeDetailsFragment.getActivity());
    }


    public void loadRecipeDetails() {
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


    public boolean searchOnDB(){
        SQLiteDatabase db = favouritesDBHelper.getReadableDatabase();
        String[] projection = {FavouritesDBHelper.COLUMN_ID,
                FavouritesDBHelper.COLUMN_RECIPE_ID,
                FavouritesDBHelper.COLUMN_TITLE
        };
        String selection = FavouritesDBHelper.COLUMN_RECIPE_ID+"=?";
        String selecitonArgs[] = {recipeId};

        Cursor cursor = db.query(FavouritesDBHelper.TABLE,
                projection, selection, selecitonArgs, null, null, null);


        String recipeId = null;
        while (cursor.moveToNext()){
            recipeId = cursor.getString(
                    cursor.getColumnIndexOrThrow(FavouritesDBHelper.COLUMN_RECIPE_ID));
        }
        cursor.close();
        return recipeId != null;
    }

    public void insertOnDB(){
        SQLiteDatabase db = favouritesDBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FavouritesDBHelper.COLUMN_RECIPE_ID, recipeId);
        values.put(FavouritesDBHelper.COLUMN_TITLE, recipeDetails.getTitle());
        db.insert(FavouritesDBHelper.TABLE, null, values);
    }
    public void deleteFromDB(){
        SQLiteDatabase db = favouritesDBHelper.getWritableDatabase();
        db.delete(FavouritesDBHelper.TABLE, FavouritesDBHelper.COLUMN_RECIPE_ID+"=?",
                new String[]{recipeId});
    }
    public void closeDB(){
        favouritesDBHelper.close();
    }

    public void saveShoppingCart(ArrayList<String> cart) {
        sharedPreferences = recipeDetailsFragment.getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sharedPreferences.edit();
        Set<String> cartSet = new HashSet<>(cart);
        ed.putStringSet("cart", cartSet);
        ed.apply();
    }

    public ArrayList<String> loadShoppingCart() {
        sharedPreferences = recipeDetailsFragment.getActivity().getPreferences(Context.MODE_PRIVATE);
        Set<String> ingredientsSet = sharedPreferences.getStringSet("cart", new HashSet<String>());
        return new ArrayList<>(ingredientsSet);
    }

}