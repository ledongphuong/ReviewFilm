package com.example.myapplicationbot.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.bumptech.glide.Glide;
import com.example.myapplicationbot.databinding.ActivityDetailBinding;
import com.example.myapplicationbot.model.entities.ItemFilm;
import com.example.myapplicationbot.model.entities.ItemTrailer;
import com.example.myapplicationbot.model.entities.ResultTrailer;
import com.example.myapplicationbot.viewmodel.TrailerViewModel;

public class DetailActivity extends AppCompatActivity {
    private ActivityDetailBinding binding;
    public static final String SEND_DATA_DETAIL = "send_data_to_detail";
    private TrailerViewModel viewModel = new TrailerViewModel();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel.getTrailerObs.observe(this, new Observer<ResultTrailer>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onChanged(ResultTrailer resultTrailer) {
                ItemTrailer itemTrailer = resultTrailer.getResult().stream().filter(x -> "YouTube".equals(x.getSite())).findAny().orElse(null);
                if (itemTrailer == null) {
                    Toast.makeText(DetailActivity.this, "VIDEO LINK DELETED", Toast.LENGTH_SHORT).show();
                } else {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=" + itemTrailer.getKey())));
                }
            }
        });
        viewModel.errorObs.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(DetailActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        });
        Bundle extras = getIntent().getExtras();
        ItemFilm itemFilm = (ItemFilm) extras.getSerializable(SEND_DATA_DETAIL);

        Glide.with(DetailActivity.this)
                .load("http://image.tmdb.org/t/p/w500" + itemFilm.getBackdropPath())
                .into(binding.backImageDetail);
        Glide.with(DetailActivity.this)
                .load("http://image.tmdb.org/t/p/w500" + itemFilm.getPosterPath())
                .into(binding.mainImageDetail);
        binding.textTitleDetail.setText(itemFilm.getTitle());
        binding.textRate.setText(String.valueOf(itemFilm.getVoteAverage()) + "/10");
        binding.textVotes.setText(String.valueOf(itemFilm.getVoteCount()) + " votes");
        binding.textDate.setText(itemFilm.getReleaseDate());
        binding.textLanguage.setText(itemFilm.getOriginalLanguage() + "-sub");
        binding.textDecription.setText(itemFilm.getOverview());
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        binding.btnTrailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.getTrailer(itemFilm.getId());
            }
        });
        binding.btnWatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this, WatchingActivity.class);
                startActivity(intent);
            }
        });
        binding.btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DetailActivity.this,"ADDED TO FAVOURITES",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
