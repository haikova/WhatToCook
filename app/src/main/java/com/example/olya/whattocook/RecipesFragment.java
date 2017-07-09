package com.example.olya.whattocook;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import android.support.v4.app.Fragment;
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
import android.widget.ProgressBar;

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

    List<Recipe> recipes = new ArrayList<>();

    private RecyclerView recyclerView;
    RecipeSearch recipeSearch;
    View rootView;
    String ingredients;
    ProgressBar progressBar;
    RecipesPresenter recipesPresenter;
    int page = 1;
    int maxVisibleItem = 29;

    private boolean isLoading;
    private int lastVisibleItem, totalItemCount;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.recipes, container, false);

        recipesPresenter = new RecipesPresenter(this);

        ingredients = getArguments().getString("ing");
        Log.d("ingredients for search:",ingredients);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        setRecyclerView();
        recipesPresenter.loadRecipes(ingredients, page);
        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                Log.d("lastVisibleItem", Integer.toString(lastVisibleItem));
                if (lastVisibleItem == maxVisibleItem) {
                    maxVisibleItem += 30;
                    recipesPresenter.loadRecipes(ingredients, ++page);
                }
            }
        });
        return rootView;
    }

    void setRecyclerView(){

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        RecipesRecyclerAdapter adapter = new RecipesRecyclerAdapter(recipes, this);
        recyclerView.setAdapter(adapter);
    }

    void updateRecyclerView(RecipeSearch recipeSearch){
        recipes.addAll(recipeSearch.getRecipes());
        progressBar.setVisibility(View.INVISIBLE);
        recyclerView.getAdapter().notifyDataSetChanged();
    }

}
