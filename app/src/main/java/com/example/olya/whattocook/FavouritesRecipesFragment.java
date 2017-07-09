package com.example.olya.whattocook;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.olya.whattocook.FavouritesPagerAdapter;
import com.example.olya.whattocook.R;

import java.util.ArrayList;

public class FavouritesRecipesFragment extends Fragment{

    ArrayList<String> favourites;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.favourites_recipes, container, false);

        favourites = getArguments().getStringArrayList("fav");
        Log.d("da", favourites.toString());
        ViewPager pager = (ViewPager) rootView.findViewById(R.id.pager);
        pager.setAdapter(new FavouritesPagerAdapter
                (getActivity().getSupportFragmentManager(), favourites));

        return rootView;
    }
}
