package com.example.RvFilm.base;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import io.reactivex.disposables.CompositeDisposable;

public abstract class BaseViewModel extends ViewModel {
    public MutableLiveData<String> errorObs = new MutableLiveData<>();
    public MutableLiveData<Boolean> loadingObs = new MutableLiveData<>();
    protected CompositeDisposable disposable = new CompositeDisposable();

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
