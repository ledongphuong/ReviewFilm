package com.example.myapplicationbot.viewmodel;

import androidx.lifecycle.MutableLiveData;
import com.example.myapplicationbot.model.entities.ResultList;
import com.example.myapplicationbot.model.repository.PopularRepository;

public class PopularViewModel {
    private PopularRepository popularRepository = new PopularRepository();
    public MutableLiveData<ResultList> getFilmObs = new MutableLiveData<>();
    public MutableLiveData<String> errorObs = new MutableLiveData<>();

    public void getFimPopular(int page) {
        popularRepository.getFilmPopuplar(page, new PopularRepository.GetFilmResponse() {
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
