package com.example.myapplicationbot.view;

import static com.example.myapplicationbot.utils.IntentUtils.openLinkYoutube;
import static com.example.myapplicationbot.utils.Utilities.glideImage;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.room.Room;

import com.example.myapplicationbot.R;
import com.example.myapplicationbot.databinding.ActivityDetailBinding;
import com.example.myapplicationbot.model.entities.ItemFilm;
import com.example.myapplicationbot.model.entities.ItemTrailer;
import com.example.myapplicationbot.model.entities.ResultTrailer;
import com.example.myapplicationbot.model.room.AppDatabase;
import com.example.myapplicationbot.model.room.ItemFavouriteDAO;
import com.example.myapplicationbot.viewmodel.DetailViewModel;

import java.util.List;

public class DetailActivity extends AppCompatActivity {
    private ActivityDetailBinding binding;
    public static final String SEND_DATA_DETAIL = "send_data_to_detail";
    private DetailViewModel viewModel = new DetailViewModel();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        AppDatabase database = Room.databaseBuilder(this, AppDatabase.class, "listFav")
                .allowMainThreadQueries()
                .build();
        viewModel.getTrailerObs.observe(this, new Observer<ResultTrailer>() {
            @Override
            public void onChanged(ResultTrailer resultTrailer) {
                for (ItemTrailer item : resultTrailer.getResult()) {
                    if (item.getSite().equals("YouTube")) {
                        openLinkYoutube(DetailActivity.this, item.getKey());
                        return;
                    }
                }
                Toast.makeText(DetailActivity.this, "VIDEO LINK DELETED", Toast.LENGTH_SHORT).show();
            }
        });
        viewModel.checkFilmIsFavouritedObs.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isFavourite) {
                if(isFavourite){
                    binding.btFav.setText("UnFavourite");
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

        glideImage(this, itemFilm.getBackdropPath(), binding.ivBackDetail);
        glideImage(this, itemFilm.getPosterPath(), binding.ivMainDetail);
        binding.tvTitleDetail.setText(itemFilm.getTitle());
        binding.tvRate.setText(getString(R.string.max_rate, itemFilm.getVoteAverage()));
        binding.tvVotes.setText(getString(R.string.votes, itemFilm.getVoteCount()));
        binding.tvDate.setText(itemFilm.getReleaseDate());
        binding.tvLanguage.setText(getString(R.string.sub, itemFilm.getOriginalLanguage()));
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
                ItemFavouriteDAO itemFavouriteDAO = database.getItemDAO();
                if(itemFavouriteDAO.geItemById(itemFilm.getId()) == null) {
                    itemFavouriteDAO.insert(itemFilm);
                    List<ItemFilm> items = itemFavouriteDAO.getItems();
                    System.out.println("list favou" + items);
                    Toast.makeText(DetailActivity.this, "ADDED TO FAVOURITES", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(DetailActivity.this, "HAD LIST ", Toast.LENGTH_SHORT).show();
                }
            }
        });

        viewModel.checkFilmIsFavourited(itemFilm.getId());
    }
}
