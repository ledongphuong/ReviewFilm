package com.example.myapplicationbot.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.example.myapplicationbot.base.BaseViewModel;
import com.example.myapplicationbot.model.entities.ItemFilm;
import com.example.myapplicationbot.model.localRepository.LocalFilmRepository;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@HiltViewModel
public class FavouriteViewModel extends BaseViewModel {
    private LocalFilmRepository localFilmRepository;
    public MutableLiveData<List<ItemFilm>> getFavouriteFilmObs = new MutableLiveData<>();

    @Inject
    public FavouriteViewModel(LocalFilmRepository localFilmRepository) {
        this.localFilmRepository = localFilmRepository;
    }

    public void getFavourite() {
        disposable.add(
        localFilmRepository.getFavouriteFilm()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    getFavouriteFilmObs.postValue(response);
                }, throwable -> {
                    errorObs.postValue(throwable.getMessage());
                }));
    }
}
