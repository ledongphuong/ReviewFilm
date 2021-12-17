package com.example.myapplicationbot.model.repository;

import com.example.myapplicationbot.model.entities.ResultList;
import com.example.myapplicationbot.model.retrofit.RetrofitDefault;
import com.example.myapplicationbot.model.retrofit.FilmServices;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilmRepository {

    public void getFilmNowPlaying(int page, GetFilmResponse getFilmResponse) {
        FilmServices filmServices = RetrofitDefault.getInstance().create(FilmServices.class);
        filmServices.getMoreList(page).enqueue(new Callback<ResultList>() {
            @Override
            public void onResponse(Call<ResultList> call, Response<ResultList> response) {
                ResultList data = response.body();
                getFilmResponse.onResponse(data);
            }

            @Override
            public void onFailure(Call<ResultList> call, Throwable t) {
                getFilmResponse.onFailure(t.getMessage());
            }
        });
    }

    public void getFilmPopuplar(int page, GetFilmResponse getFilmResponse) {
        FilmServices filmServices = RetrofitDefault.getInstance().create(FilmServices.class);
        filmServices.getMorePop(page).enqueue(new Callback<ResultList>() {
            @Override
            public void onResponse(Call<ResultList> call, Response<ResultList> response) {
                ResultList data = response.body();
                getFilmResponse.onResponse(data);
            }

            @Override
            public void onFailure(Call<ResultList> call, Throwable t) {
                getFilmResponse.onFailure(t.getMessage());
            }
        });

    }

    public void getFilmTopRated(int page, GetFilmResponse getFilmResponse) {
        FilmServices filmServices = RetrofitDefault.getInstance().create(FilmServices.class);
        filmServices.getMoreRate(page).enqueue(new Callback<ResultList>() {
            @Override
            public void onResponse(Call<ResultList> call, Response<ResultList> response) {
                ResultList data = response.body();
                getFilmResponse.onResponse(data);
            }

            @Override
            public void onFailure(Call<ResultList> call, Throwable t) {
                getFilmResponse.onFailure(t.getMessage());
            }
        });

    }

    public interface GetFilmResponse {
        void onResponse(ResultList resultList);

        void onFailure(String errorMessage);
    }
}
