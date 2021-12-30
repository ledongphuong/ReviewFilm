package com.example.RvFilm.model.repository;

import com.example.RvFilm.model.entities.ResultList;
import com.example.RvFilm.model.entities.ResultTrailer;
import com.example.RvFilm.model.retrofit.FilmServices;

import javax.inject.Inject;

import io.reactivex.Observable;

public class FilmRepository {
    private FilmServices filmServices;

    @Inject
    public FilmRepository(FilmServices filmServices) {
        this.filmServices = filmServices;
    }

    public Observable<ResultList> getFilmNowPlaying(int page) {
        return filmServices.getMoreList(page);
    }

    public Observable<ResultList>  getFilmPopuplar(int page) {
        return filmServices.getMorePop(page);
    }

    public Observable<ResultList> getFilmTopRated(int page) {
        return filmServices.getMoreRate(page);
    }

    public Observable<ResultTrailer> getTrailer(int id) {
        return filmServices.getTrailer(id);
    }
}
