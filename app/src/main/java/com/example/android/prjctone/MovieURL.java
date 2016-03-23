package com.example.android.prjctone;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by wdc on 11/01/16.
 */
public class MovieURL {


    public static String getMovieURL(int movieMenu) {


        Calendar c = Calendar.getInstance();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = df.format(c.getTime());

        c.add(Calendar.MONTH, -4);
        String monthsAgo = df.format(c.getTime());


        String movieURL = "";

        switch (movieMenu) {
            case 0:
                movieURL = "https://api.themoviedb.org/3/discover/movie?api_key=01a844fdf284bf000e57f284487770ef&sort_by=popularity.desc";
                break;
            case 1:
                movieURL = "https://api.themoviedb.org/3/discover/movie?api_key=01a844fdf284bf000e57f284487770ef&sort_by=vote_average.desc&vote_count.gte=150";
                break;
            case 2:
                movieURL = "https://api.themoviedb.org/3/discover/movie?api_key=01a844fdf284bf000e57f284487770ef&primary_release_date.gte="+currentDate+"&primary_release_date.lte="+monthsAgo+"&sort_by=vote_average.desc&vote_count.gte=50";
                break;

        }
        return movieURL;
    }


}
