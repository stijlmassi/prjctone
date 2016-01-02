package com.example.android.prjctone;

/**
 * Created by wdc on 20/12/15.
 */
public class RowItem {
    private String posterPath;
    private String title;
    private String desc;
    private double rating;
    private String release;

    public RowItem(String posterPath, String title, String desc, double rating, String release) {
        this.posterPath = posterPath;
        this.title = title;
        this.desc = desc;
        this.rating = rating;
        this.release = release;

    }

    public void setRating(double rating){this.rating = rating;}

    public double getRating() {
        return rating;
    }

    public void setRelease(String release){this.release = release;}

    public String getRelease() {
        return release;
    }

    public String getPosterPath() {
        return posterPath;
    }



    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title + "\n" + desc;
    }
}
