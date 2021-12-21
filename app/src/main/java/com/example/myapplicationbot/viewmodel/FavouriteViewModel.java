package com.example.myapplicationbot.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.example.myapplicationbot.base.BaseViewModel;
import com.example.myapplicationbot.model.entities.ItemFilm;
import com.example.myapplicationbot.model.localRepository.LocalFilmRepository;

import java.util.List;

public class FavouriteViewModel extends BaseViewModel {
    private LocalFilmRepository localFilmRepository = new LocalFilmRepository();
    public MutableLiveData<List<ItemFilm>> getFavouriteFilmObs = new MutableLiveData<>();

    public void getFavourite() {
        localFilmRepository.getFavouriteFilm(new LocalFilmRepository.GetFavouriteFilmResponse() {
            @Override
            public void onResponse(List<ItemFilm> favouriteFilms) {
                getFavouriteFilmObs.postValue(favouriteFilms);
            }

            @Override
            public void onFailure(String errorMessage) {
                errorObs.postValue(errorMessage);
            }
        });

    }
}
