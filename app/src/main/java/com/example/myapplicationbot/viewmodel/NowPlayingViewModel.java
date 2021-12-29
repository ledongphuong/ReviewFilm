package com.example.myapplicationbot.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.example.myapplicationbot.base.BaseViewModel;
import com.example.myapplicationbot.model.entities.ResultList;
import com.example.myapplicationbot.model.repository.FilmRepository;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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
        disposable.add(
                filmRepository.getFilmNowPlaying(page)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(onSubscribe -> loadingObs.postValue(true))
                        .doOnComplete(()->loadingObs.postValue(false))
                        .doOnError(onError -> loadingObs.postValue(false))
                        .subscribe(response -> {
                            getFilmObs.postValue(response);
                            page++;
                        }, throwable -> {
                            errorObs.postValue(throwable.getMessage());
                        }));
    }

    //map, flatmap
}
