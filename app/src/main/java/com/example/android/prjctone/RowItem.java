package com.example.android.prjctone;

/**
 * Created by wdc on 20/12/15.
 */
public class RowItem {
    private String posterPath;
    private String title;
    private String desc;

    public RowItem(String posterPath, String title, String desc) {
        this.posterPath = posterPath;
        this.title = title;
        this.desc = desc;
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
