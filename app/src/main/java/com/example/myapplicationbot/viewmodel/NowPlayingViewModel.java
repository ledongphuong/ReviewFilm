package com.example.myapplicationbot.viewmodel;

import androidx.lifecycle.MutableLiveData;
import com.example.myapplicationbot.model.entities.ResultList;
import com.example.myapplicationbot.model.repository.NowPlayingRepository;

public class NowPlayingViewModel {
    private NowPlayingRepository nowPlayingRepository = new NowPlayingRepository();
    public MutableLiveData<ResultList> getFilmObs = new MutableLiveData<>();
    public MutableLiveData<String> errorObs = new MutableLiveData<>();

    public void getFilmNowPlaying(int page) {
        nowPlayingRepository.getFilmNowPlaying(page, new NowPlayingRepository.GetFilmResponse() {
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
