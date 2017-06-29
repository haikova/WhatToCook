package com.example.olya.whattocook;

import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class IngredientsFragment extends Fragment {

    FloatingActionButton floatingActionButton;
    ListView listView;
    ArrayAdapter adapter;
    ArrayList<String> ingredients = new ArrayList();

    SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             final Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.ingredients, container, false);

        listView = (ListView) rootView.findViewById(R.id.listViewIngredients);
        loadIngredients();
        updateIngredients();
        adapter = new ArrayAdapter(getActivity(),
                android.R.layout.simple_list_item_1, ingredients);
        listView.setAdapter(adapter);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setTitle("Delete");
                alert.setMessage("Are you sure?");
                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        ingredients.remove(position);
                        adapter.notifyDataSetChanged();
                        saveIngredients();
                        updateIngredients();
                    }
                });
                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Canceled.
                    }
                });
                alert.show();
                return false;
            }
        });

        floatingActionButton = (FloatingActionButton) rootView
                .findViewById(R.id.fabAddIngredients);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View rootView) {
                addIngredient();
            }
        });
        return rootView;
    }

    public void addIngredient(){
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("Adding ingredient");
        alert.setMessage("Please, write a name of ingredient");
        final EditText input = new EditText(getActivity());
        alert.setView(input);
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String value = input.getText().toString();
                ingredients.add(value);
                saveIngredients();
                updateIngredients();
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

    void saveIngredients() {
        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sharedPreferences.edit();
        Set<String> ingredientsSet = new HashSet<String>(ingredients);
        ed.putStringSet("ingredients", ingredientsSet);
        ed.commit();
    }


    void loadIngredients() {
        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        Set<String> ingredientsSet = sharedPreferences.getStringSet("ingredients", new HashSet<String>());
        ingredients = new ArrayList<>(ingredientsSet);
    }


    void updateIngredients(){
        IngredientsListner listner = (IngredientsListner) getActivity();
        listner.sendIngredients(ingredients);
    }


    public interface IngredientsListner{
        void sendIngredients(List<String> ingredients);
    }
}
