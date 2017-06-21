package com.example.olya.whattocook;

import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;


public class IngredientsFragment extends Fragment {

    FloatingActionButton floatingActionButton;
    ListView listView;

    ArrayList<String> ingredients = new ArrayList();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.ingredients, container, false);

        listView = (ListView) rootView.findViewById(R.id.listViewIngredients);
        ingredients.add("ee");
        ingredients.add("dwd");
        final ArrayAdapter adapter = new ArrayAdapter(getActivity(),
                android.R.layout.simple_list_item_1, ingredients);
        listView.setAdapter(adapter);


        floatingActionButton = (FloatingActionButton) rootView
                .findViewById(R.id.fabAddIngredients);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View rootView) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setTitle("Adding ingredient");
                alert.setMessage("Please, write a name of ingredient");
                // Set an EditText view to get user input
                final EditText input = new EditText(getActivity());
                alert.setView(input);
                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String value = input.getText().toString();
                        ingredients.add(value);
                        adapter.notifyDataSetChanged();
                    }
                });
                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Canceled.
                    }
                });
                alert.show();
            }
        });
        return rootView;
    }
}
