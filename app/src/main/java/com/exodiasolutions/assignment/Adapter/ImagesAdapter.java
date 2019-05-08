package com.exodiasolutions.assignment.Adapter;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.exodiasolutions.assignment.FullImage;
import com.exodiasolutions.assignment.Model.ImagesData;
import com.exodiasolutions.assignment.R;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;


public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ProductViewHolder> {

    private int height;
    private Context mCtx;
    private List<ImagesData> ImagesDataList;
    private  ImagesData imagesData;

    public ImagesAdapter(Context mCtx, List<ImagesData> ImagesDataList,int height) {
        this.mCtx = mCtx;
        this.height = height;
        this.ImagesDataList = ImagesDataList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.images_card, null);
        view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        return new ProductViewHolder(view,mCtx, (ArrayList<ImagesData>) ImagesDataList);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        ImagesData ImagesData = ImagesDataList.get(position);
        Picasso.with(mCtx).load(ImagesData.getUrl()).resize(height,  height)
                .centerCrop().placeholder(mCtx.getResources().getDrawable(R.drawable.ic_stat_name)).into(holder.imageView);
        holder.imageView.setTransitionName(ImagesData.getUrl());
    }

    @Override
    public int getItemCount() {
        return ImagesDataList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

       ImageView imageView;
        ArrayList<ImagesData> properties = new ArrayList<ImagesData>();
        Context ctx;
        public ProductViewHolder(View itemView, Context ctx, ArrayList<ImagesData> properties) {
            super(itemView);
            this.properties = properties;
            this.ctx=ctx;
            itemView.setOnClickListener(this);
            imageView = itemView.findViewById(R.id.image);
            imageView.getLayoutParams().height = height;
            int postion = getAdapterPosition();
            //imageView.setTransitionName(properties.get(postion).getUrl());
        }


        @Override
        public void onClick(View v) {
            if(imageView.getDrawable() != null) {
                int postion = getAdapterPosition();
                imagesData = this.properties.get(postion);
                Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                Intent intent = new Intent(ctx, FullImage.class);
                ByteArrayOutputStream bStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, bStream);
                byte[] byteArray = bStream.toByteArray();
                intent.putExtra("image", byteArray);
                intent.putExtra("pos",""+postion);
                intent.putExtra("images",new Gson().toJson(this.properties));
                intent.putExtra("url",this.properties.get(postion).getUrl());
                Pair<View, String> pair1 = Pair.create((View) imageView, imageView.getTransitionName());
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) mCtx, pair1);
                ctx.startActivity(intent, optionsCompat.toBundle());


            }

        }
    }
}
