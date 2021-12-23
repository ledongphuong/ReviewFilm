package com.example.myapplicationbot.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.example.myapplicationbot.base.BaseViewModel;
import com.example.myapplicationbot.model.entities.ResultList;
import com.example.myapplicationbot.model.repository.FilmRepository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class NowPlayingViewModel extends BaseViewModel {
    public MutableLiveData<ResultList> getFilmObs = new MutableLiveData<>();
    private int page = 1;
    private FilmRepository filmRepository;

    @Inject
    public NowPlayingViewModel(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

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
