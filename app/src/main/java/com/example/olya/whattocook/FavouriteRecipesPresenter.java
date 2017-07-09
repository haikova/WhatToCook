package com.example.olya.whattocook;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class FavouriteRecipesPresenter {

    FavouritesRecipesFragment favouritesRecipesFragment;
    List<String> titles = new ArrayList<>();
    final List<String> listId = new ArrayList<>();
    FavouritesDBHelper favouritesDBHelper;

    public FavouriteRecipesPresenter(FavouritesRecipesFragment favouritesRecipesFragment){
        this.favouritesRecipesFragment = favouritesRecipesFragment;
        loadRecipes();
    }

    void loadRecipes(){
        favouritesDBHelper = new FavouritesDBHelper(favouritesRecipesFragment.getActivity());
        SQLiteDatabase db = favouritesDBHelper.getReadableDatabase();
        String[] projection = {FavouritesDBHelper.COLUMN_ID,
                FavouritesDBHelper.COLUMN_RECIPE_ID,
                FavouritesDBHelper.COLUMN_TITLE};

        Cursor cursor = db.query(FavouritesDBHelper.TABLE,
                projection, null, null, null, null, null);


        while (cursor.moveToNext()){
            String title = cursor.getString(
                    cursor.getColumnIndexOrThrow(FavouritesDBHelper.COLUMN_TITLE));
            String id = cursor.getString(
                    cursor.getColumnIndexOrThrow(FavouritesDBHelper.COLUMN_RECIPE_ID));
            titles.add(title);
            listId.add(id);
        }
        cursor.close();
    }

    List<String> loadRecipesListId(){
        return listId;
    }
    List<String> loadRecipesTitles(){
        return  titles;
    }
    void closeDB(){
        favouritesDBHelper.close();
    }
}
