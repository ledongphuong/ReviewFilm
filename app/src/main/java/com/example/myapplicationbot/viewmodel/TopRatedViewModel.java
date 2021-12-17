package com.example.myapplicationbot.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.example.myapplicationbot.model.entities.ResultList;
import com.example.myapplicationbot.model.repository.FilmRepository;

public class TopRatedViewModel {
    private FilmRepository filmRepository = new FilmRepository();
    public MutableLiveData<ResultList> getFilmObs = new MutableLiveData<>();
    public MutableLiveData<String> errorObs = new MutableLiveData<>();

    public void getFilmTopRated(int page) {
        filmRepository.getFilmTopRated(page, new FilmRepository.GetFilmResponse() {
            @Override
            public void onResponse(ResultList resultList) {
                getFilmObs.postValue(resultList);
            }

            @Override
            public void onFailure(String errorMessage) {
                errorObs.postValue(errorMessage);
            }
        });
    }
}
