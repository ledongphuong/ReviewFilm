package com.example.myapplicationbot.model.localRepository;

import com.example.myapplicationbot.model.entities.ItemFilm;
import com.example.myapplicationbot.model.room.FilmDAO;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Maybe;


public class LocalFilmRepository {
    private FilmDAO filmDAO;

    @Inject
    public LocalFilmRepository(FilmDAO filmDAO) {
        this.filmDAO = filmDAO;
    }

    public Maybe<List<ItemFilm>> getFavouriteFilm() {
        return filmDAO.getItems();
    }

    public Maybe<ItemFilm> getFavouriteFilmById(int id) {
        return filmDAO.geItemById(id);
    }

    public Maybe<Long> addFavouriteFilm(ItemFilm itemFilm) {
        return filmDAO.insert(itemFilm);
    }

    public Maybe<Integer> deleteFavouriteFilm(ItemFilm itemFilm) {
        return filmDAO.delete(itemFilm);
    }

}
