package com.example.myapplicationbot.di;

import androidx.room.Room;

import com.example.myapplicationbot.App;
import com.example.myapplicationbot.model.retrofit.FilmServices;
import com.example.myapplicationbot.model.room.AppDatabase;
import com.example.myapplicationbot.model.room.FilmDAO;
import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public class AppModule {
    @Provides
    @Singleton
    public static Retrofit providesRetrofit() {
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        builder.readTimeout(10, TimeUnit.SECONDS);
        builder.connectTimeout(10, TimeUnit.SECONDS);

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        builder.addInterceptor(logging);

        return new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org")
                .addConverterFactory(GsonConverterFactory.create())
                .client(builder.build())
                .build();
    }

    @Provides
    public static FilmServices providesFilmServices(Retrofit retrofit) {
        return retrofit.create(FilmServices.class);
    }

    @Provides
    @Singleton
    public static AppDatabase providesAppDatabase(){
        return Room.databaseBuilder(App.applicationContext, AppDatabase.class, "listFav")
                .allowMainThreadQueries()
                .build();
    }


    @Provides
    @Singleton
    public static FilmDAO providesItemFilmDAO(AppDatabase appDatabase){
        return appDatabase.getItemDAO();
    }


    @Provides
    @Singleton
    public static Gson providesGson() {
        return new Gson();
    }
}
