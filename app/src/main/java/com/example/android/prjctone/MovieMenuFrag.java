package com.example.android.prjctone;

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
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by wdc on 20/12/15.
 */
public class MovieMenuFrag extends Fragment {

    private ArrayAdapter<RowItem> mMovieMenuAdapter;

    static String movieDBURLs[] = {
            "https://api.themoviedb.org/3/discover/movie?api_key=01a844fdf284bf000e57f284487770ef&sort_by=popularity.desc",
            "https://api.themoviedb.org/3/discover/movie?api_key=01a844fdf284bf000e57f284487770ef&sort_by=vote_average.desc&vote_count.gte=150",
            "https://api.themoviedb.org/3/discover/movie?api_key=01a844fdf284bf000e57f284487770ef&primary_release_year=2015&sort_by=vote_average.desc&vote_count.gte=150"};

    private int movieMenu = 2;


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
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            updateMovieMenu();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mMovieMenuAdapter =
                new ArrayAdapter<RowItem>(
                        getActivity(),
                        R.layout.list_item_movie,
                        R.id.list_item_movie_imageview,
                        new ArrayList<RowItem>());

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        // Get a reference to the ListView, and attach this adapter to it.
        ListView listView = (ListView) rootView.findViewById(R.id.listview_moviemenu);
        listView.setAdapter(mMovieMenuAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

            }
        });

        return rootView;
    }

    private void updateMovieMenu() {

        FetchMovieTask movieTask = new FetchMovieTask();

        movieTask.execute(movieDBURLs[2]);

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
         *
         * Fortunately parsing is easy:  constructor takes the JSON string and converts it
         * into an Object hierarchy for us.
         */
        private RowItem[] getMovieDataFromJson(String movieDBJsonStr, int numMovies)
                throws JSONException {

            // These are the names of the JSON objects that need to be extracted.
            final String MDB_POSTER = "poster_path";
            final String MDB_TITLE = "title";
            final String MDB_DESCRIPTION = "overview";
            final String MDB_LIST = "results";
            // final String MDB_RATING = "vote_average";
           // final String MDB_POPULARITY = "popularity";


            JSONObject movieJson = new JSONObject(movieDBJsonStr);
            JSONArray movieArray = movieJson.getJSONArray(MDB_LIST);

            RowItem[] movieMenuRI = new RowItem[numMovies];

            for(int i = 0; i < movieArray.length(); i++) {

                JSONObject movieItem = movieArray.getJSONObject(i);
                movieMenuRI[i].setPosterPath("http://image.tmdb.org/t/p/w500/" + movieItem.getString(MDB_POSTER));
                movieMenuRI[i].setTitle(movieItem.getString(MDB_TITLE));
                movieMenuRI[i].setDesc(movieItem.getString(MDB_DESCRIPTION));

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

                URL url = new URL(movieDBURLs[movieMenu]);

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
                for(RowItem movieItem : result) {
                    mMovieMenuAdapter.add(movieItem);
                }

            }
        }
    }


}
