package com.exodiasolutions.assignment.Model;

import android.graphics.Bitmap;

public class ImagesData {
    public ImagesData(String url, Bitmap bitmap) {
        this.url = url;
        this.bitmap = bitmap;
    }

    String url;
    Bitmap bitmap;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
