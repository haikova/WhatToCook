package com.example.olya.whattocook.ui;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.olya.whattocook.R;
import com.example.olya.whattocook.presenter.FavouriteRecipesPresenter;

import java.util.List;

public class FavouritesRecipesFragment extends Fragment{


    List<String> titles;
    List<String> listId;
    FavouriteRecipesPresenter favouriteRecipesPresenter;
    FavouriteRecipesListner favouriteRecipesListner;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_favourite_recipes, container, false);

        favouriteRecipesListner = (FavouriteRecipesListner) getActivity();
        favouriteRecipesPresenter = new FavouriteRecipesPresenter(this);
        titles = favouriteRecipesPresenter.loadRecipesTitles();
        listId = favouriteRecipesPresenter.loadRecipesListId();

        ListView listView = (ListView) rootView.findViewById(R.id.list_favourites);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, titles);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                favouriteRecipesListner.showFavuriteRecipeDetailsFragment(listId.get(position));
            }
        });
        return rootView;
    }

    @Override
    public void onDestroy() {
        favouriteRecipesPresenter.closeDB();
        super.onDestroy();
    }

    public interface FavouriteRecipesListner{
        void showFavuriteRecipeDetailsFragment(String recipeId);
    }

}
