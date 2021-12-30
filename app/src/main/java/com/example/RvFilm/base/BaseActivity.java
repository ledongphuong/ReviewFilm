package com.example.RvFilm.base;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewbinding.ViewBinding;

public abstract class BaseActivity<B extends ViewBinding, V extends BaseViewModel> extends AppCompatActivity {
    protected B binding;
    protected V viewModel;

    protected abstract B getBinding();

    protected abstract void initialize();

    protected abstract void setViewModelObs();

    protected abstract void setViewEvent();

    protected abstract Class<V> getViewModelClass();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = getBinding();
        viewModel = new ViewModelProvider(this).get(getViewModelClass());

        setContentView(binding.getRoot());

        setViewModelObs();
        setDefaultViewModelObs();
        setViewEvent();
        initialize();
    }

    private void setDefaultViewModelObs() {
        viewModel.errorObs.observe(this, s -> Toast.makeText(BaseActivity.this, s, Toast.LENGTH_SHORT).show());
    }

    protected void showToast(int msgId) {
        Toast.makeText(BaseActivity.this, getString(msgId), Toast.LENGTH_SHORT).show();
    }
}
