package com.example.myapplicationbot.model.repository;

import android.os.Build;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.example.myapplicationbot.model.entities.ResultList;
import com.example.myapplicationbot.model.entities.ResultTrailer;
import com.example.myapplicationbot.model.retrofit.RetrofitDefault;
import com.example.myapplicationbot.model.retrofit.RetrofitMethod;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrailerRepository {
    public void getTrailer(int id, GetTrailerResponse getTrailerResponse) {
        RetrofitMethod retrofitMethod = RetrofitDefault.getInstance().create(RetrofitMethod.class);
        retrofitMethod.getTrailer(id).enqueue(new Callback<ResultTrailer>() {
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

    public interface GetTrailerResponse {
        void onResponse(ResultTrailer resultTrailer);

        void onFailure(String errorMessage);
    }
}
