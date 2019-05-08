package com.exodiasolutions.assignment.Adapter;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.exodiasolutions.assignment.Model.ImagesData;
import com.exodiasolutions.assignment.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class EndLessAdapter extends PagerAdapter {

    FragmentActivity activity;
    ArrayList<ImagesData> imageArray;

    public EndLessAdapter(FragmentActivity act, ArrayList<ImagesData> imgArra) {
        imageArray = imgArra;
        activity = act;
    }

    public int getCount() {
        return imageArray.size();
    }

    private int pos = 0;

    public Object instantiateItem(View collection, int position) {

        ImageView mwebView = new ImageView(activity);
        ((ViewPager) collection).addView(mwebView, 0);

        Picasso.with(activity).load(imageArray.get(position).getUrl()).resize(500,  500)
                .centerCrop()
                .placeholder(activity.getResources().getDrawable(R.drawable.ic_stat_name)).into(mwebView);


        if (pos >= imageArray.size() - 1)
            pos = 0;
        else
            ++pos;

        return mwebView;
    }

    @Override
    public void destroyItem(View arg0, int arg1, Object arg2) {
        ((ViewPager) arg0).removeView((View) arg2);
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == ((View) arg1);
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

}