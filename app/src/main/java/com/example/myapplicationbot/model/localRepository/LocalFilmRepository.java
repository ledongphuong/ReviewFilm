package com.example.myapplicationbot.model.localRepository;

import com.example.myapplicationbot.model.entities.ItemFilm;
import com.example.myapplicationbot.model.room.FilmDAO;

import java.util.List;

import javax.inject.Inject;


public class LocalFilmRepository {
    private FilmDAO filmDAO;

    @Inject
    public LocalFilmRepository(FilmDAO filmDAO) {
        this.filmDAO = filmDAO;
    }

    public void getFavouriteFilm(GetFavouriteFilmResponse getFavouriteFilmResponse) {
        try {
            List<ItemFilm> listFavourite = filmDAO.getItems();
            getFavouriteFilmResponse.onResponse(listFavourite);
        } catch (Exception e) {
            getFavouriteFilmResponse.onFailure(e.getMessage());
        }

    }

    public void getFavouriteFilmById(int id, GetFavouriteFilmByIdResponse getFavouriteFilmByIdResponse) {
        try {
            ItemFilm film = filmDAO.geItemById(id);
            getFavouriteFilmByIdResponse.onResponse(film);
        } catch (Exception e) {
            getFavouriteFilmByIdResponse.onFailure(e.getMessage());
        }

    }

    public void addFavouriteFilm(ItemFilm itemFilm, AddFavouriteFilmResponse addFavouriteFilmResponse) {
        try {
            filmDAO.insert(itemFilm);
            List<ItemFilm> listFavourite = filmDAO.getItems();
            addFavouriteFilmResponse.onResponse(listFavourite);

        } catch (Exception e) {
            addFavouriteFilmResponse.onFailure(e.getMessage());
        }
    }

    public void deleteFavouriteFilm(ItemFilm itemFilm, DeleteFavouriteFilmResponse deleteFavouriteFilmResponse) {
        try {
            filmDAO.delete(itemFilm);
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
