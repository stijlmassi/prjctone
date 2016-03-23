package com.example.android.prjctone.data;

import android.annotation.TargetApi;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

/**
 * Created by wdc on 10/01/16.
 */
public class MovieDBProvider extends ContentProvider {

    // The URI Matcher used by this content provider.
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private MovieDBHelper mOpenHelper;


    static final int mPopAll = 1;
    static final int mPopID = 2;
    static final int mCurrAll = 3;
    static final int mCurrID = 4;
    static final int mRatAll = 5;
    static final int mRatID = 6;
    static final int mFavAll = 7;
    static final int mFavID = 8;


    //popular.movieID = ?
    private static final String sPopularMovieIDSelection =
            MovieDBContract.Popular.TABLE_NAME +
                    "." + MovieDBContract.Popular.COLUMN_NAME_ENTRY_ID + " = ? ";
    private static final String sCurrentMovieIDSelection =
            MovieDBContract.Current.TABLE_NAME +
                    "." + MovieDBContract.Current.COLUMN_NAME_ENTRY_ID + " = ? ";
    private static final String sRatedMovieIDSelection =
            MovieDBContract.Rated.TABLE_NAME +
                    "." + MovieDBContract.Rated.COLUMN_NAME_ENTRY_ID + " = ? ";
    private static final String sFavoriteMovieIDSelection =
            MovieDBContract.Favorites.TABLE_NAME +
                    "." + MovieDBContract.Favorites.COLUMN_NAME_ENTRY_ID + " = ? ";


    private Cursor getPopularMovieByID(Uri uri, String[] projection, String sortOrder) {

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        switch (sUriMatcher.match(uri)) {

            case mPopAll:
                queryBuilder.setTables(MovieDBContract.Popular.TABLE_NAME);


        }


    }


    */

    static UriMatcher buildUriMatcher() {

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = MovieDBContract.CONTENT_AUTHORITY;

        // For each type of URI you want to add, create a corresponding code.
        matcher.addURI(authority, MovieDBContract.PATH_POPULAR, mPopAll);
        matcher.addURI(authority, MovieDBContract.PATH_POPULAR + "/#", mPopID);
        matcher.addURI(authority, MovieDBContract.PATH_CURRENT, mCurrAll);
        matcher.addURI(authority, MovieDBContract.PATH_CURRENT + "/#", mCurrID);
        matcher.addURI(authority, MovieDBContract.PATH_RATED, mRatAll);
        matcher.addURI(authority, MovieDBContract.PATH_RATED + "/#", mRatID);
        matcher.addURI(authority, MovieDBContract.PATH_FAVORITES, mFavAll);
        matcher.addURI(authority, MovieDBContract.PATH_FAVORITES + "/#", mFavID);
        return matcher;
    }

    /*
        Students: We've coded this for you.  We just create a new MovieDBHelper for later use
        here.
     */
    @Override
    public boolean onCreate() {
        mOpenHelper = new MovieDBHelper(getContext());
        return true;
    }

    /*
        Students: Here's where you'll code the getType function that uses the UriMatcher.  You can
        test this by uncommenting testGetType in TestProvider.

     */

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        // Here's the switch statement that, given a URI, will determine what kind of request it is,
        // and query the database accordingly.
        Cursor retCursor;
        switch (sUriMatcher.match(uri)) {
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    /*
        Student: Add the ability to insert Locations to the implementation of this function.
     */
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;


        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;
        // this makes delete all rows return the number of rows deleted

        return rowsDeleted;
    }


    @Override
    public int update(
            Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated;

        return rowsUpdated;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);

    }

    // You do not need to call this method. This is a method specifically to assist the testing
    // framework in running smoothly. You can read more at:
    // http://developer.android.com/reference/android/content/ContentProvider.html#shutdown()
    @Override
    @TargetApi(11)
    public void shutdown() {
        mOpenHelper.close();
        super.shutdown();
    }
}
