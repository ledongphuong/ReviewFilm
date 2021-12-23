package com.example.myapplicationbot.view.toprated;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplicationbot.base.BaseFragment;
import com.example.myapplicationbot.databinding.FragmentTopratedBinding;
import com.example.myapplicationbot.view.DetailActivity;
import com.example.myapplicationbot.view.recycleview.FilmAdapter;
import com.example.myapplicationbot.model.entities.ItemFilm;
import com.example.myapplicationbot.view.recycleview.ItemFilmClick;
import com.example.myapplicationbot.model.entities.ResultList;
import com.example.myapplicationbot.viewmodel.TopRatedViewModel;

public class TopRatedFragment extends BaseFragment<FragmentTopratedBinding, TopRatedViewModel> {
    private FilmAdapter filmAdapter;
    private boolean loading = false;
    private ItemFilmClick itemFilmClick = new ItemFilmClick() {
        @Override
        public void onShowDetailClick(ItemFilm itemFilm) {
            Intent intent = new Intent(getContext(), DetailActivity.class);
            intent.putExtra(DetailActivity.SEND_DATA_DETAIL, itemFilm);
            getContext().startActivity(intent);
        }
    };

    @Override
    protected FragmentTopratedBinding getBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return FragmentTopratedBinding.inflate(inflater, container, false);
    }

    @Override
    protected Class<TopRatedViewModel> getViewModelClass() {
        return TopRatedViewModel.class;
    }

    @Override
    protected void initialize() {
        filmAdapter = new FilmAdapter(itemFilmClick);
        binding.rvItemFilm.setAdapter(filmAdapter);
        binding.rvItemFilm.setLayoutManager(new LinearLayoutManager(getActivity()));

        viewModel.getFilmTopRated();
    }

    @Override
    protected void setViewModelObs() {
        viewModel.getFilmObs.observe(getViewLifecycleOwner(), new Observer<ResultList>() {
            @Override
            public void onChanged(ResultList resultList) {
                filmAdapter.addData(resultList.getResult());
                loading = false;
            }
        });
    }

    @Override
    protected void setViewEvent() {
        binding.rvItemFilm.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) { //check for scroll down
                    if (!loading) {
                        if ((((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition() == filmAdapter.getItemCount() - 1)) {
                            loading = true;
                            viewModel.getFilmTopRated();
                        }
                    }
                }
            }
        });
    }
}