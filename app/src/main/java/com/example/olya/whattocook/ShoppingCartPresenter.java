package com.example.olya.whattocook;


import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ShoppingCartPresenter {

    SharedPreferences sharedPreferences;
    ShoppingCartFragment shoppingCartFragment;
    ArrayList<String> cart;

    public ShoppingCartPresenter(ShoppingCartFragment shoppingCartFragment){
        this.shoppingCartFragment = shoppingCartFragment;
    }


    public ArrayList<String> loadShoppingCart() {
        sharedPreferences = shoppingCartFragment.getActivity().getPreferences(Context.MODE_PRIVATE);
        Set<String> ingredientsSet = sharedPreferences.getStringSet("cart", new HashSet<String>());
        cart = new ArrayList<>(ingredientsSet);
        return cart;
    }

    void saveShoppingCart(ArrayList<String> cart) {
        sharedPreferences = shoppingCartFragment.getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sharedPreferences.edit();
        Set<String> cartSet = new HashSet<>(cart);
        ed.putStringSet("cart", cartSet);
        ed.apply();
    }
}
