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
    private String imagePath;
    private String id;
    private String trailer;
    private String review;

    public RowItem(String posterPath, String title, String desc, double rating, String release, String id, String imagePath, String trailer, String review) {
        this.posterPath = posterPath;
        this.title = title;
        this.desc = desc;
        this.rating = rating;
        this.release = release;
        this.id = id;
        this.imagePath = imagePath;
        this.trailer = trailer;
        this.review = review;

    }


    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public double getRating() {
        return rating;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRelease() {
        return release;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
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
