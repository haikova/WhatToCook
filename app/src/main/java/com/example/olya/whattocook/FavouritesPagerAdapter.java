package com.example.olya.whattocook;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.example.olya.whattocook.model.RecipeDetails;

import java.util.ArrayList;


public class FavouritesPagerAdapter extends FragmentStatePagerAdapter {

    private int i;
    ArrayList<String> favourites;

    public FavouritesPagerAdapter(FragmentManager fm, ArrayList<String> favourites) {
        super(fm);
        this.favourites = favourites;
    }

    @Override
    public Fragment getItem(int position) {
        String id = favourites.get(i);
        Log.d("id", favourites.get(i));
        i++;
        return RecipeDetailsFragment.newInstance(position, favourites.get(position));
    }

    @Override
    public int getCount() {
        return favourites.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "Title " + position;
    }
}
