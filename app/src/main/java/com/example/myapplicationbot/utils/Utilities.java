package com.example.myapplicationbot.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class Utilities {
    public static void glideImage(Context context, String keyURL, ImageView imageView){
        Glide.with(context)
                .load("http://image.tmdb.org/t/p/w500" + keyURL )
                .into(imageView);
    }
}
