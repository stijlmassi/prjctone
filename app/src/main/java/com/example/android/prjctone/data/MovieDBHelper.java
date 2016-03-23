package com.example.android.prjctone.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.android.prjctone.data.MovieDBContract.Popular;
import com.example.android.prjctone.data.MovieDBContract.Rated;
import com.example.android.prjctone.data.MovieDBContract.Favorites;
import com.example.android.prjctone.data.MovieDBContract.Current;


/**
 * Created by wdc on 10/01/16.
 */
public class MovieDBHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 1;

    static final String DATABASE_NAME = "movie.db";

    public MovieDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Create a table to hold locations.  A location consists of the string supplied in the
        // location setting, the city name, and the latitude and longitude
        final String SQL_CREATE_POPULAR_TABLE = "CREATE TABLE " + Popular.TABLE_NAME + " (" +
                Popular._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                Popular.COLUMN_NAME_ENTRY_ID + " TEXT UNIQUE NOT NULL, " +
                Popular.COLUMN_NAME_TITLE + " TEXT NOT NULL, " +
                Popular.COLUMN_NAME_DESCRIPTION + " TEXT NOT NULL, " +
                Popular.COLUMN_NAME_RELEASE + " TEXT NOT NULL " +
                Popular.COLUMN_NAME_RATING + " INT NOT NULL " +
                Popular.COLUMN_NAME_POSTER + " TEXT NOT NULL " +
                Popular.COLUMN_NAME_IMAGE+ " TEXT NOT NULL " +
                Popular.COLUMN_NAME_TRAILER+ " TEXT NOT NULL " +
                Popular.COLUMN_NAME_REVIEWS+ " TEXT NOT NULL " +
                " );";

        final String SQL_CREATE_RATED_TABLE = "CREATE TABLE " + Rated.TABLE_NAME + " (" +
                Popular._ID + " INTEGER PRIMARY KEY," +
                Popular.COLUMN_NAME_ENTRY_ID + " TEXT UNIQUE NOT NULL, " +
                Popular.COLUMN_NAME_TITLE + " TEXT NOT NULL, " +
                Popular.COLUMN_NAME_DESCRIPTION + " TEXT NOT NULL, " +
                Popular.COLUMN_NAME_RELEASE + " TEXT NOT NULL " +
                Popular.COLUMN_NAME_RATING + " INT NOT NULL " +
                Popular.COLUMN_NAME_POSTER + " TEXT NOT NULL " +
                Popular.COLUMN_NAME_IMAGE+ " TEXT NOT NULL " +
                Popular.COLUMN_NAME_TRAILER+ " TEXT NOT NULL " +
                Popular.COLUMN_NAME_REVIEWS+ " TEXT NOT NULL " +
                " );";
        final String SQL_CREATE_CURRENT_TABLE = "CREATE TABLE " + Current.TABLE_NAME + " (" +
                Popular._ID + " INTEGER PRIMARY KEY," +
                Popular.COLUMN_NAME_ENTRY_ID + " TEXT UNIQUE NOT NULL, " +
                Popular.COLUMN_NAME_TITLE + " TEXT NOT NULL, " +
                Popular.COLUMN_NAME_DESCRIPTION + " TEXT NOT NULL, " +
                Popular.COLUMN_NAME_RELEASE + " TEXT NOT NULL " +
                Popular.COLUMN_NAME_RATING + " INT NOT NULL " +
                Popular.COLUMN_NAME_POSTER + " TEXT NOT NULL " +
                Popular.COLUMN_NAME_IMAGE+ " TEXT NOT NULL " +
                Popular.COLUMN_NAME_TRAILER+ " TEXT NOT NULL " +
                Popular.COLUMN_NAME_REVIEWS+ " TEXT NOT NULL " +
                " );";
        final String SQL_CREATE_FAVORITES_TABLE = "CREATE TABLE " + Current.TABLE_NAME + " (" +
                Popular._ID + " INTEGER PRIMARY KEY," +
                Popular.COLUMN_NAME_ENTRY_ID + " TEXT UNIQUE NOT NULL, " +
                Popular.COLUMN_NAME_TITLE + " TEXT NOT NULL, " +
                Popular.COLUMN_NAME_DESCRIPTION + " TEXT NOT NULL, " +
                Popular.COLUMN_NAME_RELEASE + " TEXT NOT NULL " +
                Popular.COLUMN_NAME_RATING + " INT NOT NULL " +
                Popular.COLUMN_NAME_POSTER + " TEXT NOT NULL " +
                Popular.COLUMN_NAME_IMAGE+ " TEXT NOT NULL " +
                Popular.COLUMN_NAME_TRAILER+ " TEXT NOT NULL " +
                Popular.COLUMN_NAME_REVIEWS+ " TEXT NOT NULL " +
                " );";

        sqLiteDatabase.execSQL(SQL_CREATE_POPULAR_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_RATED_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_FAVORITES_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_CURRENT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        // Note that this only fires if you change the version number for your database.
        // It does NOT depend on the version number for your application.
        // If you want to update the schema without wiping data, commenting out the next 2 lines
        // should be your top priority before modifying this method.
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Popular.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Favorites.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Rated.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Current.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}