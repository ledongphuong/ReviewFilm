package com.example.myapplicationbot.model.localRepository;

import android.content.Context;

import androidx.room.Room;

import com.example.myapplicationbot.App;
import com.example.myapplicationbot.model.entities.ItemFilm;
import com.example.myapplicationbot.model.entities.ResultList;
import com.example.myapplicationbot.model.room.AppDatabase;

import java.util.List;

public class LocalFilmRepository {
    private AppDatabase appDatabase;

    public LocalFilmRepository() {
        appDatabase = Room.databaseBuilder(App.applicationContext, AppDatabase.class, "listFav")
                .allowMainThreadQueries()
                .build();
    }

    public void getFavouriteFilm(GetFavouriteFilmResponse getFavouriteFilmResponse) {
        try {
            List<ItemFilm> listFavourite = appDatabase.getItemDAO().getItems();
            getFavouriteFilmResponse.onResponse(listFavourite);
        } catch (Exception e) {
            getFavouriteFilmResponse.onFailure(e.getMessage());
        }

    }

    public void getFavouriteFilmById(int id, GetFavouriteFilmByIdResponse response) {
        try {
            ItemFilm film = appDatabase.getItemDAO().geItemById(id);
            response.onResponse(film);
        } catch (Exception e) {
            response.onFailure(e.getMessage());
        }

    }

    public interface GetFavouriteFilmResponse {
        void onResponse(List<ItemFilm> favouriteFilms);

        void onFailure(String errorMessage);
    }

    public interface GetFavouriteFilmByIdResponse {
        void onResponse(ItemFilm favouriteFilm);

        void onFailure(String errorMessage);
    }
}
