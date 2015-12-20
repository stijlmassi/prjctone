package com.example.android.prjctone;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by wdc on 20/12/15.
 */
public class CustomImageViewAdapter extends ArrayAdapter<RowItem> {

    Context context;

    public CustomImageViewAdapter(Context context, int resourceId,
                                 List<RowItem> items) {
        super(context, resourceId, items);
        this.context = context;
    }

    public View getView(int position, View recycled, ViewGroup container) {
        final ImageView imageView;

        RowItem rowItem = getItem(position);

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (recycled == null) {
            imageView = (ImageView) inflater.inflate(R.layout.list_item_movie, container, false);
            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) recycled;
        }



        URL posterURL = null;
        try {
            posterURL = new URL(rowItem.getPosterPath());
        } catch (MalformedURLException e) {
            e.printStackTrace();

        }

        Glide.with(context)
                .load(posterURL)
                .centerCrop()
                .placeholder(R.drawable.loading_spinner)
                .crossFade()
                .into(imageView);

        return recycled;
    }
}
