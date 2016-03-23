package com.example.android.prjctone;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by wdc on 20/12/15.
 */
public class MovieMenuFrag extends Fragment {

    private CustomImageViewAdapter mMovieMenuAdapter;

    public static final String PREFS_NAME = "MovieMenu";

    private GridView listView;

    private OnFragmentInteractionListener mListener;

    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(String title);
    }

    static String[] menuTitle = {
            "Most Popular", "Best Rated", "Fresh & Popping", "My Favs", "Similar"};

    public MovieMenuFrag() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add this line in order for this fragment to handle menu events.
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.moviemenufrag, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        SharedPreferences movieMenuP = this.getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        SharedPreferences.Editor movieEditor = movieMenuP.edit();

        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.by_popularity) {

            movieEditor.putInt("movieMenu", 0);
            movieEditor.apply();
            mListener.onFragmentInteraction(menuTitle[0]);
            listView.setAdapter(null);
            listView.smoothScrollToPosition(0);
            listView.setAdapter(mMovieMenuAdapter);
            updateMovieMenu();
            return true;
        } else if (id == R.id.by_ratings) {

            movieEditor.putInt("movieMenu", 1);
            movieEditor.apply();
            mListener.onFragmentInteraction(menuTitle[1]);
            listView.setAdapter(null);
            listView.smoothScrollToPosition(0);
            listView.setAdapter(mMovieMenuAdapter);
            updateMovieMenu();
            return true;
        } else if (id == R.id.favorite_movies) {

          /*  movieEditor.putInt("movieMenu", 3);
            movieEditor.apply();
            mListener.onFragmentInteraction(menuTitle[3]);
                            listView.setAdapter(null);
                listView.smoothScrollToPosition(0);
                listView.setAdapter(mMovieMenuAdapter);

          return true;
            */

        } else if (id == R.id.by_popularityandratings) {

            movieEditor.putInt("movieMenu", 2);
            movieEditor.apply();
            mListener.onFragmentInteraction(menuTitle[2]);
            listView.setAdapter(null);
            listView.smoothScrollToPosition(0);
            listView.setAdapter(mMovieMenuAdapter);
            updateMovieMenu();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAttach(Activity activity) {

        super.onAttach(activity);


        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        SharedPreferences movieMenuP = this.getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);


        if (mListener != null) {
            mListener.onFragmentInteraction(menuTitle[movieMenuP.getInt("movieMenu", 2)]);
        }

        mMovieMenuAdapter =
                new CustomImageViewAdapter(
                        getActivity().getApplicationContext(),
                        R.layout.list_item_movie,
                        new ArrayList<RowItem>());

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        // Get a reference to the ListView, and attach this adapter to it.
        listView = (GridView) rootView.findViewById(R.id.gridview_moviemenu);
        listView.setAdapter(mMovieMenuAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                String movieDesc = mMovieMenuAdapter.getItem(position).getDesc();
                String moviePoster = mMovieMenuAdapter.getItem(position).getPosterPath();
                String movieTitle = mMovieMenuAdapter.getItem(position).getTitle();
                String movieRelease = mMovieMenuAdapter.getItem(position).getRelease();
                double movieRating = mMovieMenuAdapter.getItem(position).getRating();
                String movieID = mMovieMenuAdapter.getItem(position).getId();
                String movieImage = mMovieMenuAdapter.getItem(position).getImagePath();

                Intent intent = new Intent(getActivity(), MovieDetailsActivity.class);

                Bundle extras = new Bundle();

                extras.putString("movieDesc", movieDesc);
                extras.putString("moviePoster", moviePoster);
                extras.putString("movieTitle", movieTitle);
                extras.putString("movieRelease", movieRelease);
                extras.putDouble("movieRating", movieRating);
                extras.putString("movieID", movieID);
                extras.putString("movieImage", movieImage);

                intent.putExtras(extras);

                startActivity(intent);


            }
        });

        return rootView;
    }

    private void updateMovieMenu() {

        SharedPreferences movieMenuP = this.getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        //  FetchMovieTask movieTask = new FetchMovieTask();

        TaskHelper.execute(new FetchMovieTask(), getMovieURL(movieMenuP.getInt("movieMenu", 2)));
        // movieTask.execute(getMovieURL(movieMenuP.getInt("movieMenu", 2)));

    }


    public String getMovieURL(int movieMenu) {


        Calendar c = Calendar.getInstance();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = df.format(c.getTime());

        c.add(Calendar.MONTH, -3);
        String monthsAgo = df.format(c.getTime());


        String movieURL = "";

        switch (movieMenu) {
            case 0:
                movieURL = "https://api.themoviedb.org/3/discover/movie?api_key=01a844fdf284bf000e57f284487770ef&sort_by=popularity.desc";
                break; // sort by popularity
            case 1:
                movieURL = "https://api.themoviedb.org/3/discover/movie?api_key=01a844fdf284bf000e57f284487770ef&sort_by=vote_average.desc&vote_count.gte=100";
                break; // sort by rating
            case 2:
                movieURL = "https://api.themoviedb.org/3/discover/movie?api_key=01a844fdf284bf000e57f284487770ef&primary_release_date.gte=" + monthsAgo + "&primary_release_date.lte=" + currentDate + "&sort_by=vote_average.desc&vote_count.gte=30";
                break; // sort by all movies released since a few months ago, sorted by rating & vote count (at least 50 votes)

        }
        return movieURL;
    }

    @Override
    public void onStart() {
        super.onStart();
        updateMovieMenu();
    }

    public class FetchMovieTask extends AsyncTask<String, Void, RowItem[]> {

        private final String LOG_TAG = FetchMovieTask.class.getSimpleName();


        /**
         * Take the String representing the complete forecast in JSON Format and
         * pull out the data we need to construct the Strings needed for the wireframes.
         * <p/>
         * Fortunately parsing is easy:  constructor takes the JSON string and converts it
         * into an Object hierarchy for us.
         */
        private RowItem[] getMovieDataFromJson(String movieDBJsonStr, int numMovies)
                throws JSONException {

            // These are the names of the JSON objects that need to be extracted.
            final String MDB_POSTER = "poster_path";
            final String MDB_TITLE = "original_title";
            final String MDB_DESCRIPTION = "overview";
            final String MDB_RATING = "vote_average";
            final String MDB_RELEASE = "release_date";
            final String MDB_LIST = "results";
            final String MDB_ID = "id";
            final String MDB_IMAGE = "backdrop_path";
            final String MDB_TRAILER = "trailer";
            final String MDB_REVIEW = "review";


            JSONObject movieJson = new JSONObject(movieDBJsonStr);
            JSONArray movieArray = movieJson.getJSONArray(MDB_LIST);

            RowItem[] movieMenuRI = {

                    new RowItem("", "", "", 0, "", "", "", "", ""), new RowItem("", "", "", 0, "", "", "", "", ""), new RowItem("", "", "", 0, "", "", "", "", ""), new RowItem("", "", "", 0, "", "", "", "", ""), new RowItem("", "", "", 0, "", "", "", "", ""),
                    new RowItem("", "", "", 0, "", "", "", "", ""), new RowItem("", "", "", 0, "", "", "", "", ""), new RowItem("", "", "", 0, "", "", "", "", ""), new RowItem("", "", "", 0, "", "", "", "", ""), new RowItem("", "", "", 0, "", "", "", "", ""),
                    new RowItem("", "", "", 0, "", "", "", "", ""), new RowItem("", "", "", 0, "", "", "", "", ""), new RowItem("", "", "", 0, "", "", "", "", ""), new RowItem("", "", "", 0, "", "", "", "", ""), new RowItem("", "", "", 0, "", "", "", "", ""),
                    new RowItem("", "", "", 0, "", "", "", "", ""), new RowItem("", "", "", 0, "", "", "", "", ""), new RowItem("", "", "", 0, "", "", "", "", ""), new RowItem("", "", "", 0, "", "", "", "", ""), new RowItem("", "", "", 0, "", "", "", "", "")
            };


            // do I need to add on 'or i < numMovies' condition to this loop?
            for (int i = 0; i < numMovies; i++) {

                JSONObject movieItem = movieArray.getJSONObject(i);


                movieMenuRI[i].setPosterPath("http://image.tmdb.org/t/p/w500" + movieItem.getString(MDB_POSTER));
                movieMenuRI[i].setTitle(movieItem.getString(MDB_TITLE));
                movieMenuRI[i].setRelease(movieItem.getString(MDB_RELEASE));
                movieMenuRI[i].setDesc(movieItem.getString(MDB_DESCRIPTION));
                double MDBRating = Double.parseDouble(movieItem.getString(MDB_RATING));
                movieMenuRI[i].setRating(MDBRating);
                movieMenuRI[i].setId(movieItem.getString(MDB_ID));
                movieMenuRI[i].setImagePath("http://image.tmdb.org/t/p/w500" + movieItem.getString(MDB_IMAGE));

            }

            return movieMenuRI;

        }


        @Override
        protected RowItem[] doInBackground(String... params) {


            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String movieJsonStr = null;

            int numMovies = 20;

            try {

                URL url = new URL(params[0]);

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

                return getMovieDataFromJson(movieJsonStr, numMovies);


            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }

            // This will only happen if there was an error getting or parsing the movieDB.
            return null;
        }

        @Override
        protected void onPostExecute(RowItem[] result) {
            if (result != null) {
                mMovieMenuAdapter.clear();
                for (RowItem movieItem : result) {
                    mMovieMenuAdapter.add(movieItem);
                }

            }
        }
    }


}
