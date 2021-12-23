package com.example.myapplicationbot.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.viewbinding.ViewBinding;

import com.example.myapplicationbot.viewmodel.NowPlayingViewModel;


public abstract class BaseFragment<B extends ViewBinding, V extends BaseViewModel> extends Fragment {
    protected B binding;
    protected V viewModel;

    protected abstract B getBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container);

    protected abstract Class<V> getViewModelClass();

    protected abstract void initialize();

    protected abstract void setViewModelObs();

    protected abstract void setViewEvent();

    protected ViewModelStoreOwner getViewModelStoreOwner() {
        return this;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = getBinding(inflater, container);
        viewModel = new ViewModelProvider(getViewModelStoreOwner()).get(getViewModelClass());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setDefaultViewModelObs();
        setViewModelObs();
        setViewEvent();
        initialize();
    }

    private void setDefaultViewModelObs() {
        viewModel.errorObs.observe(getViewLifecycleOwner(), s -> Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show());
    }

    protected void showToast(int msgId) {
        Toast.makeText(getContext(), getString(msgId), Toast.LENGTH_SHORT).show();
    }
}
