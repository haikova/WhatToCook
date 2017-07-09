package com.example.olya.whattocook;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

import java.util.ArrayList;
import java.util.List;

public class FavouritesRecipesFragment extends Fragment{


    List<String> titles;
    List<String> listId;
    FavouriteRecipesPresenter favouriteRecipesPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.favourites_recipes, container, false);

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
                Fragment fragment = new RecipeDetailsFragment();
                Bundle args = new Bundle();
                args.putString("id", listId.get(position));
                fragment.setArguments(args);
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, fragment);
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        return rootView;
    }

    @Override
    public void onDestroy() {
        favouriteRecipesPresenter.closeDB();
        super.onDestroy();
    }
}
