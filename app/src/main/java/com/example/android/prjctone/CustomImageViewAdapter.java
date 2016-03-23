package com.example.android.prjctone;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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

     //   container.invalidate();// TEMPORARY SOLUTION TO GRAPHICAL CORRUPTION DUE TO GLIDE LIBRARY SEE-> https://github.com/bumptech/glide/issues/743

        final ImageView imageView;


        RowItem rowItem = getItem(position);


        if (recycled == null) {
            imageView = new ImageView(context);
           // imageView.setLayoutParams(new GridView.LayoutParams(370, 556));
           imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
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
                .fitCenter()
                .dontAnimate()
                .placeholder(R.drawable.fillersmall)
                .into(imageView);

        return imageView;
    }
}
