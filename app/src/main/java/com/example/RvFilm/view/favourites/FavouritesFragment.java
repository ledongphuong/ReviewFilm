package com.example.RvFilm.view.favourites;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.RvFilm.R;
import com.example.RvFilm.base.BaseFragment;
import com.example.RvFilm.databinding.FragmentFavBinding;
import com.example.RvFilm.model.entities.ItemFilm;
import com.example.RvFilm.view.DetailActivity;
import com.example.RvFilm.view.recycleview.FilmAdapter;
import com.example.RvFilm.view.recycleview.ItemFilmClick;
import com.example.RvFilm.viewmodel.FavouriteViewModel;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class FavouritesFragment extends BaseFragment<FragmentFavBinding, FavouriteViewModel> {
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
        return FragmentFavBinding.inflate(inflater, container, false);
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

        //line gray
        DividerItemDecoration divider = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.recycler_view_divider));
        binding.rvItemFilm.addItemDecoration(divider);

        //get data
        viewModel.getFavourite();
    }

    @Override
    protected void setViewModelObs() {
        viewModel.loadingObs.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    binding.progressLoader.setVisibility(View.VISIBLE);
                } else {
                    binding.progressLoader.setVisibility(View.GONE);
                }
            }
        });
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
