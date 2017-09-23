package com.example.yogeshgarg.source.mvp;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by Braintech on 9/22/2016.
 */
public class ImageModel implements Serializable {

    int imageId;

    byte[] byte_image;
    Bitmap bmp_image;


    public ImageModel(int imageId, byte[] byte_image, Bitmap bmp_image) {
        this.imageId=imageId;
        this.byte_image = byte_image;
        this.bmp_image = bmp_image;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public byte[] getByte_image() {
        return byte_image;
    }

    public void setByte_image(byte[] byte_image) {
        this.byte_image = byte_image;
    }

    public Bitmap getBmp_image() {
        return bmp_image;
    }

    public void setBmp_image(Bitmap bmp_image) {
        this.bmp_image = bmp_image;
    }
}
