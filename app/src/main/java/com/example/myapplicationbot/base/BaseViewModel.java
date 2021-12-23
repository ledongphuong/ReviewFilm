package com.example.myapplicationbot.base;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public abstract class BaseViewModel extends ViewModel {
    public MutableLiveData<String> errorObs = new MutableLiveData<>();
    public MutableLiveData<Boolean> loadingObs = new MutableLiveData<>();

    @Override
    protected void onCleared() {
        super.onCleared();

    }
}
