package com.example.myapplicationbot.view.popular;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplicationbot.R;
import com.example.myapplicationbot.base.BaseFragment;
import com.example.myapplicationbot.databinding.FragmentPopularBinding;
import com.example.myapplicationbot.view.DetailActivity;
import com.example.myapplicationbot.view.recycleview.FilmAdapter;
import com.example.myapplicationbot.model.entities.ItemFilm;
import com.example.myapplicationbot.view.recycleview.ItemFilmClick;
import com.example.myapplicationbot.model.entities.ResultList;
import com.example.myapplicationbot.viewmodel.PopularViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class PopularFragment extends BaseFragment<FragmentPopularBinding,PopularViewModel> {
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
    protected FragmentPopularBinding getBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return FragmentPopularBinding.inflate(inflater,container,false);
    }

    @Override
    protected Class<PopularViewModel> getViewModelClass() {
        return PopularViewModel.class;
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
        viewModel.getFimPopular();
    }

    @Override
    protected void setViewModelObs() {
        viewModel.loadingObs.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    binding.progressLoader.setVisibility(View.VISIBLE);
                }
                else {
                    binding.progressLoader.setVisibility(View.GONE);
                }
            }
        });
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
                            viewModel.getFimPopular();
                        }
                    }
                }
            }
        });
    }
}
