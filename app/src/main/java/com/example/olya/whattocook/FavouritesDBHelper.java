package com.example.olya.whattocook;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class FavouritesDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "favrecipesstore.db"; // название бд
    private static final int VERSION = 1; // версия базы данных
    public static final String TABLE = "favourites"; // название таблицы в бд
    // названия столбцов
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_RECIPE_ID = "recipeId";
    public static final String COLUMN_TITLE = "title";

    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE favourites (" + COLUMN_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_RECIPE_ID
            + " TEXT, " + COLUMN_TITLE + " TEXT);";

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS "+TABLE;

    public FavouritesDBHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
