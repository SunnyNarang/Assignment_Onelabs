package com.exodiasolutions.assignment;

import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.TransitionInflater;
import android.view.View;
import android.widget.ImageView;

import com.exodiasolutions.assignment.Adapter.EndLessAdapter;
import com.exodiasolutions.assignment.Adapter.ImagesAdapter;
import com.exodiasolutions.assignment.Model.ImagesData;
import com.exodiasolutions.assignment.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class FullImage extends AppCompatActivity {
    ViewPager mPager;
    ImageView imageView;
    Store store;
    Gson gson;
    ArrayList<ImagesData> arrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image);
        getSupportActionBar().hide();
        store = new Store(FullImage.this);
        gson = new Gson();
        Type type = new TypeToken<ArrayList<ImagesData>>() {}.getType();
        arrayList = gson.fromJson((getIntent().getStringExtra("images")), type);
        mPager = (ViewPager) findViewById(R.id.mpager);
        mPager.setVisibility(View.INVISIBLE);
        mPager.setOffscreenPageLimit(1);
        mPager.setAdapter(new EndLessAdapter(this, arrayList));
        mPager.setCurrentItem(Integer.parseInt(getIntent().getStringExtra("pos")));
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                mPager.setVisibility(View.VISIBLE);
                imageView.setVisibility(View.INVISIBLE);
            }
        },700);
        getWindow().setSharedElementEnterTransition(TransitionInflater.from(this).inflateTransition(R.transition.shared_element_transation));
        imageView = findViewById(R.id.image);
        imageView.setTransitionName(getIntent().getStringExtra("url"));
        byte[] byteArray = getIntent().getByteArrayExtra("image");
        imageView.setImageBitmap(BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length));
    }
}
