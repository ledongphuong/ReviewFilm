package com.example.myapplicationbot.view;

import static com.example.myapplicationbot.utils.IntentUtils.openLinkYoutube;
import static com.example.myapplicationbot.utils.Utilities.glideImage;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.example.myapplicationbot.R;
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
                    openLinkYoutube(DetailActivity.this,itemTrailer.getKey());
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

        glideImage(this,itemFilm.getBackdropPath(),binding.ivBackDetail);
        glideImage(this,itemFilm.getPosterPath(),binding.ivMainDetail);
        binding.tvTitleDetail.setText(itemFilm.getTitle());
        binding.tvRate.setText(getString(R.string.max_rate, itemFilm.getVoteAverage()));
        binding.tvVotes.setText(getString(R.string.votes,itemFilm.getVoteCount()));
        binding.tvDate.setText(itemFilm.getReleaseDate());
        binding.tvLanguage.setText(getString(R.string.sub,itemFilm.getOriginalLanguage()));
        binding.tvDecription.setText(itemFilm.getOverview());
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
                Toast.makeText(DetailActivity.this,"ADDED TO FAVOURITES",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
