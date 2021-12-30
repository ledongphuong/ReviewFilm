package com.example.RvFilm.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.example.RvFilm.base.BaseViewModel;
import com.example.RvFilm.model.entities.ItemFilm;
import com.example.RvFilm.model.entities.ResultTrailer;
import com.example.RvFilm.model.localRepository.LocalFilmRepository;
import com.example.RvFilm.model.repository.FilmRepository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@HiltViewModel
public class DetailViewModel extends BaseViewModel {
    private FilmRepository filmRepository;
    public MutableLiveData<ResultTrailer> getTrailerObs = new MutableLiveData<>();
    private LocalFilmRepository localFilmRepository;
    public MutableLiveData<Boolean> checkFilmIsFavouritedObs = new MutableLiveData<>();
    public MutableLiveData<Long> addFavouriteFilmObs = new MutableLiveData<>();
    public MutableLiveData<Integer> deleteFavouriteFilmObs = new MutableLiveData<>();

    @Inject
    public DetailViewModel(FilmRepository filmRepository, LocalFilmRepository localFilmRepository) {
        this.filmRepository = filmRepository;
        this.localFilmRepository = localFilmRepository;
    }

    public void getTrailer(int id) {
        disposable.add(
                filmRepository.getTrailer(id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(response -> {
                            getTrailerObs.postValue(response);
                        }, throwable -> {
                            errorObs.postValue(throwable.getMessage());
                        }));
    }

    public void checkFilmIsFavourited(int id) {
        disposable.add(
                localFilmRepository.getFavouriteFilmById(id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(response -> {
                            boolean isExistFilm = response != null;
                            checkFilmIsFavouritedObs.postValue(isExistFilm);
                        }, throwable -> {
                            errorObs.postValue(throwable.getMessage());
                        }));
    }

    public void addFilmFavourite(ItemFilm itemFilm) {
        disposable.add(
                localFilmRepository.addFavouriteFilm(itemFilm)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(response -> {
                            addFavouriteFilmObs.postValue(response);
                        }, throwable -> {
                            errorObs.postValue(throwable.getMessage());
                        }));
    }

    public void deleteFilmFavourite(ItemFilm itemFilm) {
        disposable.add(
                localFilmRepository.deleteFavouriteFilm(itemFilm)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(response -> {
                            deleteFavouriteFilmObs.postValue(response);
                        }, throwable -> {
                            errorObs.postValue(throwable.getMessage());
                        }));
    }
}