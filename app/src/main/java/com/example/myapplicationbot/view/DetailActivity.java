package com.example.myapplicationbot.view;

import static com.example.myapplicationbot.utils.IntentUtils.openLinkYoutube;
import static com.example.myapplicationbot.utils.Utilities.glideImage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.myapplicationbot.R;
import com.example.myapplicationbot.base.BaseActivity;
import com.example.myapplicationbot.databinding.ActivityDetailBinding;
import com.example.myapplicationbot.model.entities.ItemFilm;
import com.example.myapplicationbot.model.entities.ItemTrailer;
import com.example.myapplicationbot.model.entities.ResultTrailer;
import com.example.myapplicationbot.viewmodel.DetailViewModel;
import com.google.gson.Gson;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class DetailActivity extends BaseActivity<ActivityDetailBinding, DetailViewModel> {
    public static final String SEND_DATA_DETAIL = "send_data_to_detail";
    public static final String FILM_ID_RESULT = "film_id_result";
    public static final String IS_FAVOURITED_RESULT = "is_favourite_result";
    private ItemFilm itemFilm;

    @Override
    protected ActivityDetailBinding getBinding() {
        return ActivityDetailBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initialize() {
        Bundle extras = getIntent().getExtras();
        itemFilm = (ItemFilm) extras.getSerializable(SEND_DATA_DETAIL);

        viewModel.checkFilmIsFavourited(itemFilm.getId());

        glideImage(this, itemFilm.getBackdropPath(), binding.ivBackDetail);
        glideImage(this, itemFilm.getPosterPath(), binding.ivMainDetail);
        binding.tvTitleDetail.setText(itemFilm.getTitle());
        binding.tvRate.setText(getString(R.string.max_rate, itemFilm.getVoteAverage()));
        binding.tvVotes.setText(getString(R.string.votes, itemFilm.getVoteCount()));
        binding.tvDate.setText(itemFilm.getReleaseDate());
        binding.tvLanguage.setText(getString(R.string.sub, itemFilm.getOriginalLanguage()));
        binding.tvDecription.setText(itemFilm.getOverview());
    }

    @Override
    protected void setViewModelObs() {
        viewModel.getTrailerObs.observe(this, new Observer<ResultTrailer>() {
            @Override
            public void onChanged(ResultTrailer resultTrailer) {
                for (ItemTrailer item : resultTrailer.getResult()) {
                    if (item.getSite().equals("YouTube")) {
                        openLinkYoutube(DetailActivity.this, item.getKey());
                        return;
                    }
                }
                showToast(R.string.msg_video_deleted);
            }
        });
        viewModel.checkFilmIsFavouritedObs.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isFavourite) {
                if (isFavourite) {
                    binding.btFav.setText(getString(R.string.unfavourite));
                } else {
                    binding.btFav.setText(getString(R.string.favourite));
                }
            }
        });
        viewModel.addFavouriteFilmObs.observe(this, new Observer<Long>() {
            @Override
            public void onChanged(Long aLong) {
                setChangeFavouriteStatus(true);
            }
        });
        viewModel.deleteFavouriteFilmObs.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                setChangeFavouriteStatus(false);
            }
        });
    }

    @Override
    protected void setViewEvent() {
        binding.btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        binding.btTrailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.getTrailer(itemFilm.getId());
            }
        });
        binding.btWatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this, WatchingActivity.class);
                startActivity(intent);
            }
        });
        binding.btFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentFavText = binding.btFav.getText().toString();
                if (currentFavText.equals(getString(R.string.unfavourite))) {
                    viewModel.deleteFilmFavourite(itemFilm);
                    binding.btFav.setText(getString(R.string.favourite));
                } else if (currentFavText.equals(getString(R.string.favourite))) {
                    viewModel.addFilmFavourite(itemFilm);
                    binding.btFav.setText(getString(R.string.unfavourite));
                }
            }
        });
    }

    @Override
    protected Class<DetailViewModel> getViewModelClass() {
        return DetailViewModel.class;
    }

    private void setChangeFavouriteStatus(boolean isFavouritedfilm) {
        Intent intent = new Intent();
        intent.putExtra(FILM_ID_RESULT, itemFilm.getId());
        intent.putExtra(IS_FAVOURITED_RESULT, isFavouritedfilm);
        setResult(DetailActivity.RESULT_OK, intent);
    }
}
