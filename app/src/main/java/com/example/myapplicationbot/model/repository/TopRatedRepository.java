package com.example.myapplicationbot.model.repository;

import com.example.myapplicationbot.model.entities.ResultList;
import com.example.myapplicationbot.model.retrofit.RetrofitDefault;
import com.example.myapplicationbot.model.retrofit.RetrofitMethod;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopRatedRepository {
    public void getFilmTopRated(int page, GetFilmResponse getFilmResponse) {
        RetrofitMethod retrofitMethod = RetrofitDefault.getInstance().create(RetrofitMethod.class);
        retrofitMethod.getMoreRate(page).enqueue(new Callback<ResultList>() {
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
