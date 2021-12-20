package com.example.myapplicationbot.viewmodel;

import androidx.lifecycle.MutableLiveData;
import com.example.myapplicationbot.model.entities.ResultTrailer;
import com.example.myapplicationbot.model.repository.FilmRepository;

public class TrailerViewModel {
    private FilmRepository filmRepository =new FilmRepository();
    public MutableLiveData <ResultTrailer> getTrailerObs = new MutableLiveData<>();
    public MutableLiveData <String> errorObs =new MutableLiveData<>();

    public void getTrailer (int id){
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

}
