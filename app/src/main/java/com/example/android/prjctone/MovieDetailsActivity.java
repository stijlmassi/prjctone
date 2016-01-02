package com.example.android.prjctone;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.net.MalformedURLException;
import java.net.URL;

public class MovieDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);



   /*     FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Liked!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
                fab.setBackground(android.R.drawable.star_big_on);
            }
        });

        */
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        String mTitle;
        String mPoster;
        String mDesc;
        double mRatingDouble;
        String mRating;
        String mRelease;


        if (extras != null) {

            mTitle = extras.getString("movieTitle");
            mPoster = extras.getString("moviePoster");
            mDesc = extras.getString("movieDesc");

            mRatingDouble = extras.getDouble("movieRating");

            mRating = Double.toString(mRatingDouble);
            mRelease = extras.getString("movieRelease");

            toolBarLayout.setTitle(mTitle);

            URL posterURL = null;
            try {
                posterURL = new URL(mPoster);
            } catch (MalformedURLException e) {
                e.printStackTrace();

            }

            ImageView posterImage = (ImageView) findViewById(R.id.movie_poster);

            Glide.with(this)
                    .load(posterURL)
                    .fitCenter()
                    .override(500,750)
                    .dontAnimate()
                    .into(posterImage);

            TextView mTitleText = (TextView) findViewById(R.id.movie_title);
            mTitleText.setText(mTitle);
            TextView mReleaseText = (TextView) findViewById(R.id.movie_release);
            mReleaseText.setText(mRelease);
            TextView mRatingText = (TextView) findViewById(R.id.movie_rating);
            mRatingText.setText(mRating);
            TextView mDescText = (TextView) findViewById(R.id.movie_desc);
            mDescText.setText(mDesc);


        }


    }
}
