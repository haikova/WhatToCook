package com.example.olya.whattocook.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetRecipe {
    @SerializedName("recipe")
    @Expose
    private RecipeDetails recipeDetails;

    public RecipeDetails getRecipeDetails() {
        return recipeDetails;
    }

    public void setRecipeDetails(RecipeDetails recipeDetails) {
        this.recipeDetails = recipeDetails;
    }
}
