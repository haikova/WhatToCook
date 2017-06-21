package com.example.olya.whattocook.model;


import java.util.List;

import com.example.olya.whattocook.model.Recipe;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class RecipeSearch {
    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("recipes")
    @Expose
    private List<Recipe> recipes ;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }

}
