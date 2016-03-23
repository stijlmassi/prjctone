package com.example.android.prjctone;

import android.annotation.TargetApi;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MovieDetailsActivity extends AppCompatActivity {

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setBackgroundDrawable(getDrawable(R.drawable.favoriteheart));
        fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#72729c")));
        fab.setRippleColor(Color.parseColor("#b9bfa9"));

        fab.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Liked!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
                fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF0000")));
            }
        });


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        String mTitle;
        String mPoster;
        String mDesc;
        double mRatingDouble;
        String mRating;
        String mRelease;
        final String mID;
        String mImage;


        if (extras != null) {

            mTitle = extras.getString("movieTitle");
            mPoster = extras.getString("moviePoster");
            mDesc = extras.getString("movieDesc");

            mRatingDouble = extras.getDouble("movieRating");

            mRating = Double.toString(mRatingDouble);
            mRelease = extras.getString("movieRelease");
            mID = extras.getString("movieID");
            mImage = extras.getString("movieImage");

            toolBarLayout.setTitle(mTitle);


            URL posterURL = null;
            URL imageURL = null;
            try {
                imageURL = new URL(mImage);
                posterURL = new URL(mPoster);
            } catch (MalformedURLException e) {
                e.printStackTrace();

            }


            int imageWidth = 512;
            int imageHeight = 180;


            Glide.with(this).
                    load(imageURL)
                    .asBitmap()
                    .into(new SimpleTarget<Bitmap>(imageWidth, imageHeight) {
                        @Override
                        public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {

                            Drawable d = new BitmapDrawable(getResources(), bitmap);
                            toolBarLayout.setBackground(d);

                            // Do something with bitmap here.
                        }
                    });


            ImageView posterImage = (ImageView) findViewById(R.id.movie_poster);
            posterImage.setScaleType(ImageView.ScaleType.CENTER_CROP);

            Glide.with(this)
                    .load(posterURL)
                    .fitCenter()
                    .override(400, 600)
                    .dontAnimate()
                    .into(posterImage);

            TextView mTitleText = (TextView) findViewById(R.id.movie_title);
            mTitleText.setText(mTitle);
            mTitleText.setTextColor(Color.parseColor("#000000"));
            TextView mReleaseText = (TextView) findViewById(R.id.movie_release);
            mReleaseText.setText("Released:  " + mRelease);
            TextView mRatingText = (TextView) findViewById(R.id.movie_rating);
            mRatingText.setText("Rated:  " + mRating + " / 10");
            TextView mDescText = (TextView) findViewById(R.id.movie_desc);
            mDescText.setText(mDesc);
            mDescText.setTextColor(Color.parseColor("#1c1b1b"));

            TextView mTrailer = (TextView) findViewById(R.id.trailer);
            mTrailer.setOnClickListener(new View.OnClickListener() {
                @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onClick(View view) {
                    TaskHelper.execute(new FetchTrailerTask(), mID);
                }
            });

            TaskHelper.execute(new FetchReviewTask(), mID);


        }

    }


    public class FetchReviewTask extends AsyncTask<String, Void, String> {

        private final String LOG_TAG = FetchReviewTask.class.getSimpleName();


        /**
         * Take the String representing the complete forecast in JSON Format and
         * pull out the data we need to construct the Strings needed for the wireframes.
         * <p/>
         * Fortunately parsing is easy:  constructor takes the JSON string and converts it
         * into an Object hierarchy for us.
         */
        private String getReviewFromJson(String movieDBJsonStr)
                throws JSONException {

            JSONObject movieJson = new JSONObject(movieDBJsonStr);

            try {
                JSONArray movieArray = movieJson.getJSONArray("results");


                JSONObject movieItem = movieArray.getJSONObject(0);
                return movieItem.getString("content");


            } catch (JSONException e) {

                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
                return "No reviews available.";
            }


        }


        @Override
        protected String doInBackground(String... params) {


            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String movieJsonStr = null;

            try {

                URL url = new URL("http://api.themoviedb.org/3/movie/" + params[0] + "/reviews?api_key=01a844fdf284bf000e57f284487770ef");

                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setReadTimeout(10000 /* milliseconds */);
                urlConnection.setConnectTimeout(15000 /* milliseconds */);
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
// But it does make debugging a *lot* easier if you print out the completed
// buffer for debugging.
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                movieJsonStr = buffer.toString();

            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                // If the code didn't successfully get the movieDB data, there's no point in attempting
                // to parse it.
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }


            try {

                return getReviewFromJson(movieJsonStr);


            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }

            // This will only happen if there was an error getting or parsing the movieDB.
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {

                TextView mTitleText = (TextView) findViewById(R.id.reviews);
                mTitleText.setText(result);

            }
        }
    }

    public class FetchTrailerTask extends AsyncTask<String, Void, String> {

        private final String LOG_TAG = FetchTrailerTask.class.getSimpleName();


        /**
         * Take the String representing the complete forecast in JSON Format and
         * pull out the data we need to construct the Strings needed for the wireframes.
         * <p/>
         * Fortunately parsing is easy:  constructor takes the JSON string and converts it
         * into an Object hierarchy for us.
         */
        private String getReviewFromJson(String movieDBJsonStr)
                throws JSONException {

            JSONObject movieJson = new JSONObject(movieDBJsonStr);

            try {
                JSONArray movieArray = movieJson.getJSONArray("results");


                JSONObject movieItem = movieArray.getJSONObject(0);
                return movieItem.getString("key");


            } catch (JSONException e) {

                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
                return "no trailer";
            }


        }


        @Override
        protected String doInBackground(String... params) {


            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String movieJsonStr = null;

            try {

                URL url = new URL("http://api.themoviedb.org/3/movie/" + params[0] + "/videos?api_key=01a844fdf284bf000e57f284487770ef");

                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setReadTimeout(10000 /* milliseconds */);
                urlConnection.setConnectTimeout(15000 /* milliseconds */);
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
// But it does make debugging a *lot* easier if you print out the completed
// buffer for debugging.
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                movieJsonStr = buffer.toString();

            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                // If the code didn't successfully get the movieDB data, there's no point in attempting
                // to parse it.
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }


            try {

                return getReviewFromJson(movieJsonStr);


            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }

            // This will only happen if there was an error getting or parsing the movieDB.
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != "no trailer") {

                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + result));
                    startActivity(intent);
                } catch (ActivityNotFoundException ex) {
                    Intent intent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://www.youtube.com/watch?v=" + result));
                    startActivity(intent);
                }

            }
            else{
                TextView mTitleText = (TextView) findViewById(R.id.trailer);
                mTitleText.setText("No Trailer Available");
            }
        }
    }

}