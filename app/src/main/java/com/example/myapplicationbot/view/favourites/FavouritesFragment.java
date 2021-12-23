package com.example.myapplicationbot.view.favourites;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myapplicationbot.base.BaseFragment;
import com.example.myapplicationbot.databinding.FragmentFavBinding;
import com.example.myapplicationbot.model.entities.ItemFilm;
import com.example.myapplicationbot.view.DetailActivity;
import com.example.myapplicationbot.view.recycleview.FilmAdapter;
import com.example.myapplicationbot.view.recycleview.ItemFilmClick;
import com.example.myapplicationbot.viewmodel.FavouriteViewModel;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class FavouritesFragment extends BaseFragment<FragmentFavBinding,FavouriteViewModel> {
    private FilmAdapter filmAdapter;
    private ItemFilmClick itemFilmClick = new ItemFilmClick() {
        @Override
        public void onShowDetailClick(ItemFilm itemFilm) {
            Intent intent = new Intent(getContext(), DetailActivity.class);
            intent.putExtra(DetailActivity.SEND_DATA_DETAIL, itemFilm);
            startDetailForResult.launch(intent);
        }
    };
    private ActivityResultLauncher<Intent> startDetailForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent intent = result.getData();
                        int filmId = intent.getIntExtra(DetailActivity.FILM_ID_RESULT, -1);
                        boolean isFavouritedfilm = intent.getBooleanExtra(DetailActivity.IS_FAVOURITED_RESULT, false);
                        if (!isFavouritedfilm) {
                            filmAdapter.removeFilmById(filmId);
                        }
                    }
                }
            });

    @Override
    protected FragmentFavBinding getBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return FragmentFavBinding.inflate(inflater,container,false);
    }

    @Override
    protected Class<FavouriteViewModel> getViewModelClass() {
        return FavouriteViewModel.class;
    }


    @Override
    protected void initialize() {
        filmAdapter = new FilmAdapter(itemFilmClick);
        binding.rvItemFilm.setAdapter(filmAdapter);
        binding.rvItemFilm.setLayoutManager(new LinearLayoutManager(getContext()));

        viewModel.getFavourite();
    }

    @Override
    protected void setViewModelObs() {
        viewModel.getFavouriteFilmObs.observe(getViewLifecycleOwner(), new Observer<List<ItemFilm>>() {
            @Override
            public void onChanged(List<ItemFilm> itemFilms) {
                filmAdapter.addData(itemFilms);
            }
        });
    }

    @Override
    protected void setViewEvent() {

    }
}
