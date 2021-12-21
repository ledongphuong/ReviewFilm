package com.example.myapplicationbot.model.localRepository;

import androidx.room.Room;

import com.example.myapplicationbot.App;
import com.example.myapplicationbot.model.entities.ItemFilm;
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

    public void getFavouriteFilmById(int id, GetFavouriteFilmByIdResponse getFavouriteFilmByIdResponse) {
        try {
            ItemFilm film = appDatabase.getItemDAO().geItemById(id);
            getFavouriteFilmByIdResponse.onResponse(film);
        } catch (Exception e) {
            getFavouriteFilmByIdResponse.onFailure(e.getMessage());
        }

    }

    public void addFavouriteFilm(ItemFilm itemFilm, AddFavouriteFilmResponse addFavouriteFilmResponse) {
        try {
            appDatabase.getItemDAO().insert(itemFilm);
            List<ItemFilm> listFavourite = appDatabase.getItemDAO().getItems();
            addFavouriteFilmResponse.onResponse(listFavourite);

        } catch (Exception e) {
            addFavouriteFilmResponse.onFailure(e.getMessage());
        }
    }

    public void deleteFavouriteFilm(ItemFilm itemFilm, DeleteFavouriteFilmResponse deleteFavouriteFilmResponse) {
        try {
            appDatabase.getItemDAO().delete(itemFilm);
            deleteFavouriteFilmResponse.onResponse(itemFilm);
        } catch (Exception e) {
            deleteFavouriteFilmResponse.onFailure(e.getMessage());
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

    public interface AddFavouriteFilmResponse {
        void onResponse(List<ItemFilm> favouriteFilms);

        void onFailure(String errorMessage);
    }

    public interface DeleteFavouriteFilmResponse {
        void onResponse(ItemFilm itemFilm);

        void onFailure(String errorMessage);
    }
}
