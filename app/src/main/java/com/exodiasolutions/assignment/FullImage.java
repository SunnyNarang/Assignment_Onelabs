package com.exodiasolutions.assignment;

import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.TransitionInflater;
import android.widget.ImageView;

import com.exodiasolutions.assignment.R;
import com.squareup.picasso.Picasso;

public class FullImage extends AppCompatActivity {

    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image);
        getWindow().setSharedElementEnterTransition(TransitionInflater.from(this).inflateTransition(R.transition.shared_element_transation));
        imageView = findViewById(R.id.image);
        imageView.setTransitionName(getIntent().getStringExtra("url"));
        byte[] byteArray = getIntent().getByteArrayExtra("image");
        imageView.setImageBitmap(BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length));
        //Picasso.with(FullImage.this).load(getIntent().getStringExtra("url")).placeholder(getResources().getDrawable(R.drawable.ic_stat_name)).into(imageView);

    }
}
