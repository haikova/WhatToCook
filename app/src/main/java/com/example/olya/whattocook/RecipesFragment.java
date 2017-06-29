package com.example.olya.whattocook;

import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.olya.whattocook.model.Recipe;
import com.example.olya.whattocook.model.RecipeSearch;
import com.example.olya.whattocook.network.ApiUtils;
import com.example.olya.whattocook.network.FoodApi;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.olya.whattocook.MainActivity.API_KEY;


public class RecipesFragment extends Fragment {

    private FoodApi foodApi;
    RecipeSearch recipeSearch;
    List<Recipe> recipes;
    static final String API_KEY = "221a9145a7580bad1fa7ec991bc113b7";
    private RecyclerView recyclerView;
    View rootView;
    String ingredients;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {

        ingredients = getArguments().getString("ing");
        Log.d("asa",ingredients);
        rootView = inflater.inflate(R.layout.recipes, container, false);

        recipes = new ArrayList<>();

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        RecipesRecyclerAdapter adapter = new RecipesRecyclerAdapter(recipes, this);
        recyclerView.setAdapter(adapter);

        loadRecipes();

        //todo delete ingredients from list, send ingredients to search recipes, save list ingredients sharedpreferences

        return rootView;
    }

    public void loadRecipes(){
        foodApi = ApiUtils.getFoodApiService();
        foodApi.getData(API_KEY, ingredients, 1).enqueue(new Callback<RecipeSearch>() {
            @Override
            public void onResponse(Call<RecipeSearch> call, Response<RecipeSearch> response) {

                if(response.isSuccessful()) {
                    Log.d("MainActivity", "posts loaded from API");
                    recipeSearch = response.body();
                    recipes.addAll(recipeSearch.getRecipes());
                    recyclerView.getAdapter().notifyDataSetChanged();
                }else {
                    int statusCode  = response.code();
                    Log.d("q", Integer.toString(statusCode));
                    // handle request errors depending on status code
                }
            }

            @Override
            public void onFailure(Call<RecipeSearch> call, Throwable t) {
                Log.d("MainActivity", "error loading from API");

            }
        });
    }
}
