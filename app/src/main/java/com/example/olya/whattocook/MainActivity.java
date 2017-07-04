package com.example.olya.whattocook;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;

import com.example.olya.whattocook.network.FoodApi;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements IngredientsPresenter.IngredientsListner{

    private FoodApi foodApi;
    static final String API_KEY = "221a9145a7580bad1fa7ec991bc113b7";
    String ingredients;

    NavigationView navigationView;
    DrawerLayout drawerLayout;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        fragmentManager = getFragmentManager();
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
                                Log.d("qwe", "frag");
                                break;
                            case (R.id.menu_item_recipes):
                                fragment = new RecipesFragment();
                                Bundle arguments = new Bundle();
                                arguments.putString("ing", ingredients);
                                fragment.setArguments(arguments);
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
}
