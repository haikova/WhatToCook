package com.example.olya.whattocook.presenter;


import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.example.olya.whattocook.ui.IngredientsFragment;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class IngredientsPresenter {

    private SharedPreferences sharedPreferences;
    private IngredientsFragment ingredientsFragment;
    private IngredientsListener listner;
    private ArrayList<String> ingredients = new ArrayList();

    public IngredientsPresenter(IngredientsFragment ingredientsFragment){
        this.ingredientsFragment = ingredientsFragment;
        listner = (IngredientsListener) ingredientsFragment.getActivity();
    }

    public void deleteIngredient(final int position, final ArrayAdapter adapter){
        final AlertDialog.Builder alert = new AlertDialog.Builder(ingredientsFragment.getActivity());
        alert.setTitle("Delete");
        alert.setMessage("Are you sure?");
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                ingredients.remove(position);
                adapter.notifyDataSetChanged();
                saveIngredients();
            }
        });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });
        alert.show();

    }

    public void addIngredient(final ArrayAdapter adapter){
        AlertDialog.Builder alert = new AlertDialog.Builder(ingredientsFragment.getActivity());
        alert.setTitle("Adding ingredient");
        alert.setMessage("Please, write a name of ingredient");
        final EditText input = new EditText(ingredientsFragment.getActivity());
        alert.setView(input);
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String value = input.getText().toString();
                ingredients.add(value);
                saveIngredients();
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
        sharedPreferences = ingredientsFragment.getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sharedPreferences.edit();
        Set<String> ingredientsSet = new HashSet<>(ingredients);
        ed.putStringSet("ingredients", ingredientsSet);
        ed.apply();
        listner.sendIngredients(ingredients);
    }


    public ArrayList<String> loadIngredients() {
        sharedPreferences = ingredientsFragment.getActivity().getPreferences(Context.MODE_PRIVATE);
        Set<String> ingredientsSet = sharedPreferences.getStringSet("ingredients", new HashSet<String>());
        ingredients = new ArrayList<>(ingredientsSet);
        listner.sendIngredients(ingredients);
        return ingredients;
    }

    public interface IngredientsListener{
        void sendIngredients(List<String> ingredients);
    }
}
