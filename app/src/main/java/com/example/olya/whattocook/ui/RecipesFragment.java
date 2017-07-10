package com.example.olya.whattocook.ui;


import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.olya.whattocook.R;
import com.example.olya.whattocook.presenter.RecipesPresenter;
import com.example.olya.whattocook.adapter.RecipesRecyclerAdapter;
import com.example.olya.whattocook.model.Recipe;
import com.example.olya.whattocook.model.RecipeSearch;

import java.util.ArrayList;
import java.util.List;


public class RecipesFragment extends Fragment {

    List<Recipe> recipes = new ArrayList<>();
    private RecyclerView recyclerView;
    View rootView;
    String ingredients;
    ProgressBar progressBar;
    RecipesPresenter recipesPresenter;
    int page = 1;
    int maxVisibleItem = 29;
    private int lastVisibleItem;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_recipes, container, false);

        recipesPresenter = new RecipesPresenter(this);

        ingredients = getArguments().getString("ing");
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

    public void updateRecyclerView(RecipeSearch recipeSearch){
        recipes.addAll(recipeSearch.getRecipes());
        progressBar.setVisibility(View.INVISIBLE);
        recyclerView.getAdapter().notifyDataSetChanged();
    }

}
