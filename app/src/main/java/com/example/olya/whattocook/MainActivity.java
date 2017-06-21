package com.example.olya.whattocook;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.olya.whattocook.network.FoodApi;

public class MainActivity extends AppCompatActivity {

    private FoodApi foodApi;
    static final String API_KEY = "221a9145a7580bad1fa7ec991bc113b7";

    NavigationView navigationView;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final FragmentManager fragmentManager = getFragmentManager();
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
                                break;
                        }
                        fragmentManager.beginTransaction().replace(R.id.frame_layout, fragment)
                                .commit();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                    }
                });

        /*foodApi = ApiUtils.getFoodApiService();
        foodApi.getData(API_KEY, "milk", 1).enqueue(new Callback<RecipeSearch>() {
            @Override
            public void onResponse(Call<RecipeSearch> call, Response<RecipeSearch> response) {

                if(response.isSuccessful()) {
                    RecipeSearch recipeSearch = response.body();
                    Log.d("MainActivity", "posts loaded from API");
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
        });*/

    }
}
