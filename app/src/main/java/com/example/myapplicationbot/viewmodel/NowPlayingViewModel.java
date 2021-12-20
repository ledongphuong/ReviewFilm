package com.example.myapplicationbot.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.example.myapplicationbot.model.entities.ResultList;
import com.example.myapplicationbot.model.repository.FilmRepository;

public class NowPlayingViewModel {
    private FilmRepository filmRepository = new FilmRepository();
    public MutableLiveData<ResultList> getFilmObs = new MutableLiveData<>();
    public MutableLiveData<String> errorObs = new MutableLiveData<>();
    private int page = 1;

    public void getFilmNowPlaying() {
        filmRepository.getFilmNowPlaying(page, new FilmRepository.GetFilmNowPlayingResponse() {
            @Override
            public void onResponse(ResultList resultList) {
                getFilmObs.postValue(resultList);
                page++;
            }

            @Override
            public void onFailure(String errorMessage) {
                errorObs.postValue(errorMessage);
            }
        });
    }
}
