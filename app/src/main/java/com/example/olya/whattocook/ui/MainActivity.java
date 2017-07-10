package com.example.olya.whattocook.ui;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;

import com.example.olya.whattocook.R;
import com.example.olya.whattocook.adapter.RecipesRecyclerAdapter;
import com.example.olya.whattocook.presenter.IngredientsPresenter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
        IngredientsPresenter.IngredientsListener,
        RecipesRecyclerAdapter.RecipesAdapterListner,
        FavouritesRecipesFragment.FavouriteRecipesListner{


    String ingredients;

    NavigationView navigationView;
    DrawerLayout drawerLayout;
    FragmentManager fragmentManager;
    ArrayList<String> favourites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();
        Fragment fragment = new IngredientsFragment();
        fragmentManager.beginTransaction().replace(R.id.frame_layout, fragment)
                .commit();
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment fragment = null;
                        switch (item.getItemId()){
                            case (R.id.menu_item_ingredients):
                                fragment = new IngredientsFragment();
                                break;
                            case (R.id.menu_item_recipes):
                                fragment = new RecipesFragment();
                                Bundle arguments = new Bundle();
                                arguments.putString("ing", ingredients);
                                Log.d("ing",ingredients);
                                fragment.setArguments(arguments);
                                break;
                            case (R.id.menu_item_favourites):
                                fragment = new FavouritesRecipesFragment();
                                Bundle argumentsF = new Bundle();
                                argumentsF.putStringArrayList("fav", favourites);
                                fragment.setArguments(argumentsF);
                                break;
                            case (R.id.menu_item_cart):
                                fragment = new ShoppingCartFragment();
                                break;
                        }
                        fragmentManager.beginTransaction().replace(R.id.frame_layout, fragment)
                                .commit();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                    }
                });

    }

    @Override
    public void sendIngredients(List<String> ingredients) {
        this.ingredients = TextUtils.join(",", ingredients);
    }

    @Override
    public void showRecipeDetailsFragment(String recipeId) {
        showFragment(recipeId);
    }

    @Override
    public void showFavuriteRecipeDetailsFragment(String recipeId) {
        showFragment(recipeId);
    }

    public void showFragment(String recipeId){
        Fragment fragment = new RecipeDetailsFragment();
        Bundle args = new Bundle();
        args.putString("id", recipeId);
        fragment.setArguments(args);
        fragmentManager.beginTransaction().replace(R.id.frame_layout, fragment).commit();
    }
}
