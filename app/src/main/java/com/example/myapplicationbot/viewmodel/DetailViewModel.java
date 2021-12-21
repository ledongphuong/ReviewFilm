package com.example.myapplicationbot.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.example.myapplicationbot.base.BaseViewModel;
import com.example.myapplicationbot.model.entities.ItemFilm;
import com.example.myapplicationbot.model.entities.ResultTrailer;
import com.example.myapplicationbot.model.localRepository.LocalFilmRepository;
import com.example.myapplicationbot.model.repository.FilmRepository;

import java.util.List;

public class DetailViewModel extends BaseViewModel {
    private FilmRepository filmRepository = new FilmRepository();
    public MutableLiveData<ResultTrailer> getTrailerObs = new MutableLiveData<>();
    private LocalFilmRepository localFilmRepository = new LocalFilmRepository();
    public MutableLiveData<Boolean> checkFilmIsFavouritedObs = new MutableLiveData<>();
    public MutableLiveData<List<ItemFilm>> addFavouriteFilmObs = new MutableLiveData<>();
    public MutableLiveData<ItemFilm> deleteFavouriteFilmObs = new MutableLiveData<>();

    public void getTrailer(int id) {
        filmRepository.getTrailer(id, new FilmRepository.GetTrailerResponse() {
            @Override
            public void onResponse(ResultTrailer resultTrailer) {
                getTrailerObs.postValue(resultTrailer);
            }

            @Override
            public void onFailure(String errorMessage) {
                errorObs.postValue(errorMessage);
            }
        });
    }

    public void checkFilmIsFavourited(int id) {
        localFilmRepository.getFavouriteFilmById(id, new LocalFilmRepository.GetFavouriteFilmByIdResponse() {
            @Override
            public void onResponse(ItemFilm favouriteFilm) {
                boolean isExistFilm = favouriteFilm != null;
                checkFilmIsFavouritedObs.postValue(isExistFilm);
            }

            @Override
            public void onFailure(String errorMessage) {
                errorObs.postValue(errorMessage);
            }
        });
    }

    public void addFilmFavourite(ItemFilm itemFilm) {
        localFilmRepository.addFavouriteFilm(itemFilm, new LocalFilmRepository.AddFavouriteFilmResponse() {
            @Override
            public void onResponse(List<ItemFilm> favouriteFilms) {
                addFavouriteFilmObs.postValue(favouriteFilms);
            }

            @Override
            public void onFailure(String errorMessage) {
                errorObs.postValue(errorMessage);
            }
        });
    }

    public void deleteFilmFavourite(ItemFilm itemFilm) {
        localFilmRepository.deleteFavouriteFilm(itemFilm, new LocalFilmRepository.DeleteFavouriteFilmResponse() {
            @Override
            public void onResponse(ItemFilm itemFilm) {
                deleteFavouriteFilmObs.postValue(itemFilm);
            }

            @Override
            public void onFailure(String errorMessage) {
                errorObs.postValue(errorMessage);
            }
        });
    }
}