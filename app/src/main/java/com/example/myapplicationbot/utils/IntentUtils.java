package com.example.myapplicationbot.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class IntentUtils {

    public static void openLinkYoutube(Context context,String keyURL){
        Intent intent =new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=" + keyURL));
        context.startActivity(intent);
    }
}

