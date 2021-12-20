package com.example.myapplicationbot.view.nowplaying;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplicationbot.databinding.FragmentNowplayingBinding;
import com.example.myapplicationbot.model.entities.ItemFilm;
import com.example.myapplicationbot.model.entities.ResultList;
import com.example.myapplicationbot.view.DetailActivity;
import com.example.myapplicationbot.view.recycleview.FilmAdapter;
import com.example.myapplicationbot.view.recycleview.ItemFilmClick;
import com.example.myapplicationbot.viewmodel.NowPlayingViewModel;

public class NowPlayingFragment extends Fragment {
    private FragmentNowplayingBinding binding;
    private FilmAdapter filmAdapter;
    private boolean loading = false;
    private NowPlayingViewModel viewModel = new NowPlayingViewModel();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentNowplayingBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    private ItemFilmClick itemFilmClick = new ItemFilmClick() {
        @Override
        public void onShowDetailClick(ItemFilm itemFilm) {
            Intent intent = new Intent(getContext(), DetailActivity.class);
            intent.putExtra(DetailActivity.SEND_DATA_DETAIL, itemFilm);
            getContext().startActivity(intent);
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //call view model
        viewModel.getFilmObs.observe(getViewLifecycleOwner(), new Observer<ResultList>() {
            @Override
            public void onChanged(ResultList resultList) {
                filmAdapter.addData(resultList.getResult());
                loading = false;
            }
        });
        viewModel.errorObs.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                loading = false;
                Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
            }
        });

        // RecycleView
        filmAdapter = new FilmAdapter(itemFilmClick);
        binding.rvItemFilm.setAdapter(filmAdapter);
        binding.rvItemFilm.setLayoutManager(new LinearLayoutManager(getContext()));

        //get page first
        viewModel.getFilmNowPlaying();

        binding.rvItemFilm.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {

                if (dy > 0) { //check for scroll down
                    if (!loading) {
                        if ((((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition() == filmAdapter.getItemCount() - 1)) {
                            loading = true;
                            viewModel.getFilmNowPlaying();
                        }
                    }
                }
            }
        });
    }
}