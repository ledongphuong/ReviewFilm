package com.example.myapplicationbot.model.repository;

import com.example.myapplicationbot.model.entities.ResultList;
import com.example.myapplicationbot.model.entities.ResultTrailer;
import com.example.myapplicationbot.model.retrofit.FilmServices;

import javax.inject.Inject;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
