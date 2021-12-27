package com.example.myapplicationbot.model.repository;

import com.example.myapplicationbot.model.entities.ResultList;
import com.example.myapplicationbot.model.entities.ResultTrailer;
import com.example.myapplicationbot.model.retrofit.FilmServices;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FilmRepository {
    private FilmServices filmServices;

    @Inject
    public FilmRepository(FilmServices filmServices) {
        this.filmServices = filmServices;
    }

    public void getFilmNowPlaying(int page, GetFilmNowPlayingResponse getFilmNowPlayingResponse) {
        filmServices.getMoreList(page).enqueue(new Callback<ResultList>() {
            @Override
            public void onResponse(Call<ResultList> call, Response<ResultList> response) {
                ResultList data = response.body();
                getFilmNowPlayingResponse.onResponse(data);
            }

            @Override
            public void onFailure(Call<ResultList> call, Throwable t) {
                getFilmNowPlayingResponse.onFailure(t.getMessage());
            }
        });
    }

    public void getFilmPopuplar(int page, GetFilmPopularResponse getFilmPopularResponse) {
        filmServices.getMorePop(page).enqueue(new Callback<ResultList>() {
            @Override
            public void onResponse(Call<ResultList> call, Response<ResultList> response) {
                ResultList data = response.body();
                getFilmPopularResponse.onResponse(data);
            }

            @Override
            public void onFailure(Call<ResultList> call, Throwable t) {
                getFilmPopularResponse.onFailure(t.getMessage());
            }
        });

    }

    public void getFilmTopRated(int page, GetFilmTopRatedResponse getFilmTopRatedResponse) {
        filmServices.getMoreRate(page).enqueue(new Callback<ResultList>() {
            @Override
            public void onResponse(Call<ResultList> call, Response<ResultList> response) {
                ResultList data = response.body();
                getFilmTopRatedResponse.onResponse(data);
            }

            @Override
            public void onFailure(Call<ResultList> call, Throwable t) {
                getFilmTopRatedResponse.onFailure(t.getMessage());
            }
        });

    }

    public void getTrailer(int id, GetTrailerResponse getTrailerResponse) {
        filmServices.getTrailer(id).enqueue(new Callback<ResultTrailer>() {
            @Override
            public void onResponse(Call<ResultTrailer> call, Response<ResultTrailer> response) {
                ResultTrailer data = response.body();
                getTrailerResponse.onResponse(data);
            }

            @Override
            public void onFailure(Call<ResultTrailer> call, Throwable t) {
                getTrailerResponse.onFailure(t.getMessage());
            }
        });
    }

    public interface GetFilmNowPlayingResponse {
        void onResponse(ResultList resultList);

        void onFailure(String errorMessage);
    }

    public interface GetFilmPopularResponse {
        void onResponse(ResultList resultList);

        void onFailure(String errorMessage);
    }

    public interface GetFilmTopRatedResponse {
        void onResponse(ResultList resultList);

        void onFailure(String errorMessage);
    }

    public interface GetTrailerResponse {
        void onResponse(ResultTrailer resultTrailer);

        void onFailure(String errorMessage);
    }
}
