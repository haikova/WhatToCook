package com.example.olya.whattocook;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.olya.whattocook.model.GetRecipe;
import com.example.olya.whattocook.model.RecipeDetails;
import com.example.olya.whattocook.network.ApiUtils;
import com.example.olya.whattocook.network.FoodApi;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeDetailsFragment extends Fragment{


    private FoodApi foodApi;
    static final String API_KEY = "221a9145a7580bad1fa7ec991bc113b7";
    View rootView;
    GetRecipe getRecipe;
    String recipeId;
    ProgressBar progressBar;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.recipe_details, container, false);

        loadRecipeDetails();


        return rootView;
    }

    void loadRecipeDetails(){
        recipeId = getArguments().getString("id");
        foodApi = ApiUtils.getFoodApiService();
        foodApi.getRecipeDetails(API_KEY, recipeId).enqueue(new Callback<GetRecipe>() {
            @Override
            public void onResponse(Call<GetRecipe> call, Response<GetRecipe> response) {

                if(response.isSuccessful()) {
                    getRecipe = response.body();

                    RecipeDetails recipeDetails = getRecipe.getRecipeDetails();


                    ImageView image = (ImageView) getActivity().findViewById(R.id.image_recipe);
                    TextView textTitle = (TextView) getActivity().findViewById(R.id.text_title);
                    TextView textPublisher = (TextView) getActivity().findViewById(R.id.text_publisher);
                    TextView textRating = (TextView) getActivity().findViewById(R.id.rating);
                    ListView listIngredients = (ListView) getActivity().findViewById(R.id.list_ingredients);

                    Picasso.with(image.getContext()).load(recipeDetails.getImageUrl())
                            .resize(1280, 720).centerCrop().into(image);
                    textTitle.setText(recipeDetails.getTitle());
                    textPublisher.setText(recipeDetails.getPublisher());
                    textRating.setText("Rating: "+(recipeDetails.getSocialRank()).toString());
                    Log.d("rating", recipeDetails.getSocialRank().toString());
                    ArrayAdapter adapter = new ArrayAdapter(getActivity(),
                            android.R.layout.simple_list_item_1, recipeDetails.getIngredients());
                    listIngredients.setAdapter(adapter);
                    Log.d("MainActivity", "posts loaded from API");
                }else {
                    int statusCode  = response.code();
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

}
