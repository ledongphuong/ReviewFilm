package com.example.myapplicationbot.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.example.myapplicationbot.base.BaseViewModel;
import com.example.myapplicationbot.model.entities.ResultList;
import com.example.myapplicationbot.model.repository.FilmRepository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@HiltViewModel
public class TopRatedViewModel extends BaseViewModel {
    private FilmRepository filmRepository;
    public MutableLiveData<ResultList> getFilmObs = new MutableLiveData<>();
    private int page = 1;

    @Inject
    public TopRatedViewModel(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    public void getFilmTopRated() {
disposable.add(
        filmRepository.getFilmTopRated(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    getFilmObs.postValue(response);
                    page++;
                }, throwable -> {
                    errorObs.postValue(throwable.getMessage());
                }));
    }
}
