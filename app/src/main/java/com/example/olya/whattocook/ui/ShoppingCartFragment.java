package com.example.olya.whattocook.ui;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.olya.whattocook.R;
import com.example.olya.whattocook.presenter.ShoppingCartPresenter;

import java.util.ArrayList;

public class ShoppingCartFragment extends Fragment {

    ArrayList<String> cartItems;
    ShoppingCartPresenter shoppingCartPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_shopping_cart, container, false);

        ListView listView = (ListView) rootView.findViewById(R.id.shopping_list);
        shoppingCartPresenter = new ShoppingCartPresenter(this);
        cartItems = shoppingCartPresenter.loadShoppingCart();
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, cartItems);
        listView.setAdapter(adapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                cartItems.remove(position);
                shoppingCartPresenter.saveShoppingCart(cartItems);
                adapter.notifyDataSetChanged();
                return false;
            }
        });

        return rootView;
    }
}
