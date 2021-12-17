package com.example.myapplicationbot.viewmodel;

import androidx.lifecycle.MutableLiveData;
import com.example.myapplicationbot.model.entities.ResultList;
import com.example.myapplicationbot.model.repository.PopularRepository;
import com.example.myapplicationbot.model.repository.TopRatedRepository;

public class TopRatedViewModel {
    private TopRatedRepository topRatedRepository = new TopRatedRepository();
    public MutableLiveData<ResultList> getFilmObs = new MutableLiveData<>();
    public MutableLiveData<String> errorObs = new MutableLiveData<>();

    public void getFilmTopRated(int page) {
        topRatedRepository.getFilmTopRated(page, new TopRatedRepository.GetFilmResponse() {
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
