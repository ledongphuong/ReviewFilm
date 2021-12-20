package com.example.myapplicationbot.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.example.myapplicationbot.model.entities.ItemFilm;
import com.example.myapplicationbot.model.entities.ResultTrailer;
import com.example.myapplicationbot.model.localRepository.LocalFilmRepository;
import com.example.myapplicationbot.model.repository.FilmRepository;

public class DetailViewModel {
    private FilmRepository filmRepository = new FilmRepository();
    public MutableLiveData<ResultTrailer> getTrailerObs = new MutableLiveData<>();
    public MutableLiveData<String> errorObs = new MutableLiveData<>();
    private LocalFilmRepository localFilmRepository = new LocalFilmRepository();
    public MutableLiveData<Boolean> checkFilmIsFavouritedObs = new MutableLiveData<>();

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
}