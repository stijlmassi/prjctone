package com.example.android.prjctone.data;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by wdc on 10/01/16.
 */

/**
 * Defines table and column names for the weather database.
 */
public final class MovieDBContract {


    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public MovieDBContract(){}


    // The "Content authority" is a name for the entire content provider, similar to the
    // relationship between a domain name and its website.  A convenient string to use for the
    // content authority is the package name for the app, which is guaranteed to be unique on the
    // device.

    public static final String CONTENT_AUTHORITY = "com.example.android.prjctone.app";

    // Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
    // the content provider.
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    // Possible paths (appended to base content URI for possible URI's)
    // For instance, content://com.example.android.sunshine.app/weather/ is a valid path for
    // looking at movie data. content://com.example.android.sunshine.app/givemeroot/ will fail,
    // as the ContentProvider hasn't been given any information on what to do with "givemeroot".
    // At least, let's hope not.  Don't be that dev, reader.  Don't be that dev.
    public static final String PATH_POPULAR = "Popular";
    public static final String PATH_RATED = "Rated";
    public static final String PATH_FAVORITES = "Favorites";
    public static final String PATH_CURRENT = "Current";


    /* Inner class that defines the table contents of the movie table */
    public static abstract class Popular implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_POPULAR).build();
        public static final String TABLE_NAME = "Popular";
        public static final String COLUMN_NAME_ENTRY_ID = "mdbid";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_RELEASE = "release";
        public static final String COLUMN_NAME_RATING = "rating";
        public static final String COLUMN_NAME_POSTER = "posterpath";
        public static final String COLUMN_NAME_IMAGE = "imagepath";
        public static final String COLUMN_NAME_TRAILER = "trailer";
        public static final String COLUMN_NAME_REVIEWS = "review";


        public static Uri buildPopularUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    }

    /* Inner class that defines the table contents of the favorites table */
    public static final class Favorites implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAVORITES).build();

        public static final String TABLE_NAME = "Favorites";
        public static final String COLUMN_NAME_ENTRY_ID = "mdbid";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_RELEASE = "release";
        public static final String COLUMN_NAME_RATING = "rating";
        public static final String COLUMN_NAME_POSTER = "posterpath";
        public static final String COLUMN_NAME_IMAGE = "imagepath";
        public static final String COLUMN_NAME_TRAILER = "trailer";
        public static final String COLUMN_NAME_REVIEWS = "review";

        public static Uri buildFavoritesUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    }

    /* Inner class that defines the table contents of the menu table */
    public static final class Rated implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_RATED).build();
        public static final String TABLE_NAME = "Rated";
        public static final String COLUMN_NAME_ENTRY_ID = "mdbid";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_RELEASE = "release";
        public static final String COLUMN_NAME_RATING = "rating";
        public static final String COLUMN_NAME_POSTER = "posterpath";
        public static final String COLUMN_NAME_IMAGE = "imagepath";
        public static final String COLUMN_NAME_TRAILER = "trailer";
        public static final String COLUMN_NAME_REVIEWS = "review";

        public static Uri buildRatedUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    }

    /* Inner class that defines the table contents of the menu table */
    public static final class Current implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_CURRENT).build();
        public static final String TABLE_NAME = "Current";
        public static final String COLUMN_NAME_ENTRY_ID = "mdbid";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_RELEASE = "release";
        public static final String COLUMN_NAME_RATING = "rating";
        public static final String COLUMN_NAME_POSTER = "posterpath";
        public static final String COLUMN_NAME_IMAGE = "imagepath";
        public static final String COLUMN_NAME_TRAILER = "trailer";
        public static final String COLUMN_NAME_REVIEWS = "review";

        public static Uri buildCurrentUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}