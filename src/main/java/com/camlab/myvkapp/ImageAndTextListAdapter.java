package com.camlab.myvkapp;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by alex on 15.10.2014.
 */
public class ImageAndTextListAdapter extends ArrayAdapter<Friend> {
    private AsyncImageLoader asyncImageLoader;
    public ImageAndTextListAdapter(Activity activity, List<Friend> imageAndTexts) {

        super(activity, 0, imageAndTexts);
        asyncImageLoader = new AsyncImageLoader();

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Activity activity = (Activity) getContext();
        LayoutInflater inflater = activity.getLayoutInflater();
        // Inflate the views from XML
        View rowView = inflater.inflate(R.layout.program_list, null);
        Friend friend = getItem(position);
        // Load the image and set it on the ImageView
        final ImageView imageView = (ImageView) rowView.findViewById(R.id.imageView1);
        Drawable cachedImage = asyncImageLoader.loadDrawable(friend.getPhoto(), new AsyncImageLoader.ImageCallback() {
            public void imageLoaded(Drawable imageDrawable, String imageUrl) {
                imageView.setImageDrawable(imageDrawable);
            }
        });
        imageView.setImageDrawable(cachedImage);

        TextView textView = (TextView) rowView.findViewById(R.id.textView1);
        textView.setText(friend.getName());

        return rowView;
    }


}
