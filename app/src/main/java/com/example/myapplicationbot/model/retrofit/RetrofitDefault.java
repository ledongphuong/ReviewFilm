package com.example.myapplicationbot.model.retrofit;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitDefault {
    public static Retrofit retrofit;

    public static Retrofit getInstance() {
        if (retrofit == null) {
            OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
            builder.readTimeout(10, TimeUnit.SECONDS);
            builder.connectTimeout(10, TimeUnit.SECONDS);

            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            builder.addInterceptor(logging);

            retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.themoviedb.org")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(builder.build())
                    .build();
        }
        return retrofit;
    }
}
