package com.example.olya.whattocook;


import android.support.v7.widget.RecyclerView;
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

    public RecipesRecyclerAdapter(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    public static class RecipeViewHolder extends RecyclerView.ViewHolder {
        private TextView recipeName;
        private ImageView recipeImage;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            recipeName = (TextView) itemView.findViewById(R.id.recipe_name);
            recipeImage = (ImageView) itemView.findViewById(R.id.recipe_image);
        }

        public void bindRecipe(Recipe recipe) {
            Picasso.with(recipeImage.getContext()).load(recipe.getImageUrl()).resize(1280, 720).centerCrop().into(recipeImage);
            recipeName.setText(recipe.getTitle());
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
