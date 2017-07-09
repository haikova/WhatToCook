package com.example.olya.whattocook;


import android.support.v4.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


public class IngredientsFragment extends Fragment {

    FloatingActionButton floatingActionButton;
    ListView listView;
    ArrayAdapter<String> adapter;
    View rootView;
    IngredientsPresenter ingredientsPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             final Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.ingredients, container, false);

        ingredientsPresenter = new IngredientsPresenter(this);

        setListView();
        setFloatingActionButton();

        return rootView;
    }


    void setListView(){
        listView = (ListView) rootView.findViewById(R.id.listViewIngredients);
        adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, ingredientsPresenter.loadIngredients());

        listView.setAdapter(adapter);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                ingredientsPresenter.deleteIngredient(position, adapter);
                return false;
            }
        });

    }

    void setFloatingActionButton(){
        floatingActionButton = (FloatingActionButton) rootView
                .findViewById(R.id.fabAddIngredients);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View rootView) {
                ingredientsPresenter.addIngredient(adapter);
            }
        });
    }
}
