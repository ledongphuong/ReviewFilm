package com.example.myapplicationbot.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.example.myapplicationbot.base.BaseViewModel;
import com.example.myapplicationbot.model.entities.ResultList;
import com.example.myapplicationbot.model.repository.FilmRepository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@HiltViewModel
public class PopularViewModel extends BaseViewModel {
    private FilmRepository filmRepository;
    public MutableLiveData<ResultList> getFilmObs = new MutableLiveData<>();
    private int page = 1;

    @Inject
    public PopularViewModel(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    public void getFimPopular() {
        disposable.add(
                filmRepository.getFilmPopuplar(page)
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
}
