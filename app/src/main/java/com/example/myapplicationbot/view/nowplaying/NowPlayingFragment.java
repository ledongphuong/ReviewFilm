package com.example.myapplicationbot.view.nowplaying;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplicationbot.databinding.FragmentNowplayingBinding;
import com.example.myapplicationbot.view.DetailActivity;
import com.example.myapplicationbot.view.recycleview.FilmAdapter;
import com.example.myapplicationbot.model.entities.ItemFilm;
import com.example.myapplicationbot.view.recycleview.ItemFilmClick;
import com.example.myapplicationbot.model.entities.ResultList;
import com.example.myapplicationbot.model.retrofit.RetrofitDefault;
import com.example.myapplicationbot.model.retrofit.RetrofitMethod;
import com.example.myapplicationbot.viewmodel.NowPlayingViewModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NowPlayingFragment extends Fragment {
    private FragmentNowplayingBinding binding;
    private FilmAdapter filmAdapter;
    private ArrayList<ItemFilm> filmArrayList;
    private boolean loading = false;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    private int i = 1;
    private NowPlayingViewModel viewModel = new NowPlayingViewModel();

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        System.out.println("onAttach");
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentNowplayingBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

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

        //get page first
        viewModel.getFilmNowPlaying(i);

        // RecycleView
        filmArrayList = new ArrayList<>();
        filmAdapter = new FilmAdapter(getActivity(), itemFilmClick);
        binding.rvItemFilm.setAdapter(filmAdapter);
        binding.rvItemFilm.setLayoutManager(new LinearLayoutManager(getActivity()));
        LinearLayoutManager mLayoutManager;
        mLayoutManager = new LinearLayoutManager(getActivity());
        binding.rvItemFilm.setLayoutManager(mLayoutManager);
        filmAdapter.addData(filmArrayList);

        binding.rvItemFilm.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {

                if (dy > 0) { //check for scroll down
                    visibleItemCount = mLayoutManager.getChildCount();
                    totalItemCount = mLayoutManager.getItemCount();
                    pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();

                    if (!loading) {
                        if ((mLayoutManager.findLastCompletelyVisibleItemPosition() == filmAdapter.getItemCount() - 1)) {
                            loading = true;
                            System.out.println("show i" + i);
                            i += 1;
                            viewModel.getFilmNowPlaying(i);
                        }
                    }
                }
            }
        });
        return root;
    }

    private ItemFilmClick itemFilmClick = new ItemFilmClick() {
        @Override
        public void onShowDetailClick(ItemFilm itemFilm) {
            Intent intent = new Intent(getActivity(), DetailActivity.class);
            intent.putExtra(DetailActivity.SEND_DATA_DETAIL, itemFilm);
            getActivity().startActivity(intent);
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}