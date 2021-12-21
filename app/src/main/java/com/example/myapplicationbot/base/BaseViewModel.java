package com.example.myapplicationbot.base;

import androidx.lifecycle.MutableLiveData;

public abstract class BaseViewModel {
    public MutableLiveData<String> errorObs = new MutableLiveData<>();
    public MutableLiveData<Boolean> loadingObs = new MutableLiveData<>();

}
