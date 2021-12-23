package com.example.myapplicationbot.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.example.myapplicationbot.base.BaseViewModel;
import com.example.myapplicationbot.model.entities.ResultList;
import com.example.myapplicationbot.model.repository.FilmRepository;

import javax.inject.Inject;

public class TopRatedViewModel extends BaseViewModel {
    private FilmRepository filmRepository;
    public MutableLiveData<ResultList> getFilmObs = new MutableLiveData<>();
    private int page = 1;

    @Inject
    public TopRatedViewModel(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    public void getFilmTopRated() {
        filmRepository.getFilmTopRated(page, new FilmRepository.GetFilmTopRatedResponse() {
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
