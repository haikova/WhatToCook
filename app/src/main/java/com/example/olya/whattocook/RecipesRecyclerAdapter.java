package com.example.olya.whattocook;


import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.olya.whattocook.model.Recipe;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecipesRecyclerAdapter extends RecyclerView.Adapter<RecipesRecyclerAdapter.RecipeViewHolder>{

    private List<Recipe> recipes;
    static RecipesFragment recipesFragment;


    public RecipesRecyclerAdapter(List<Recipe> recipes, RecipesFragment recipesFragment) {
        this.recipesFragment = recipesFragment;
        this.recipes = recipes;



            }

    public static class RecipeViewHolder extends RecyclerView.ViewHolder {
        private TextView recipeName;
        private ImageView recipeImage;
        private String recipeId;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            recipeName = (TextView) itemView.findViewById(R.id.recipe_name);
            recipeImage = (ImageView) itemView.findViewById(R.id.recipe_image);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    Log.d("recipe","onClick");
                    Fragment fragment = new RecipeDetailsFragment();
                    Bundle args = new Bundle();
                    args.putString("id", recipeId);
                    fragment.setArguments(args);
                    FragmentTransaction transaction = recipesFragment.getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame_layout, fragment);
                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            });
        }

        public void bindRecipe(Recipe recipe) {
            Picasso.with(recipeImage.getContext()).load(recipe.getImageUrl()).resize(1280, 720).centerCrop().into(recipeImage);
            recipeName.setText(recipe.getTitle());
            recipeId = recipe.getRecipeId();
        }
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }


    @Override
    public RecipesRecyclerAdapter.RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item,
                parent, false);
        return new RecipeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecipesRecyclerAdapter.RecipeViewHolder holder, int position) {
        Recipe itemRecipe = recipes.get(position);
        holder.bindRecipe(itemRecipe);
    }

}
