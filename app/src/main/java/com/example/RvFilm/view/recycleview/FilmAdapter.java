package com.example.RvFilm.view.recycleview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.RvFilm.R;
import com.example.RvFilm.databinding.ItemFilmBinding;
import com.example.RvFilm.model.entities.ItemFilm;
import com.example.RvFilm.utils.Utilities;

import java.util.ArrayList;
import java.util.List;

public class FilmAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ItemFilmClick itemFilmClick;
    private ArrayList<ItemFilm> filmArrayList;

    public FilmAdapter(ItemFilmClick itemFilmClick) {
        this.itemFilmClick = itemFilmClick;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemFilmBinding binding = ItemFilmBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding, itemFilmClick);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ItemFilm itemFilm = filmArrayList.get(position);
        ((ViewHolder) holder).bind(itemFilm);
    }

    @Override
    public int getItemCount() {
        return filmArrayList == null ? 0 : filmArrayList.size();
    }

    public void addData(List<ItemFilm> filmArrayList) {
        if (filmArrayList.isEmpty()) {
            return;
        }
        if (this.filmArrayList == null) {
            this.filmArrayList = new ArrayList<>();
        }

        int privSize = this.filmArrayList.size();
        this.filmArrayList.addAll(filmArrayList);
        notifyItemRangeInserted(privSize, filmArrayList.size());
    }

    public void removeFilmById(int id) {
        for (int i = 0; i < filmArrayList.size(); i++) {
            if (filmArrayList.get(i).getId() == id) {
                filmArrayList.remove(i);
                notifyItemRemoved(i);
                break;
            }
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemFilmBinding binding;
        private ItemFilmClick itemFilmClick;

        public ViewHolder(@NonNull ItemFilmBinding binding, ItemFilmClick itemFilmClick) {
            super(binding.getRoot());
            this.binding = binding;
            this.itemFilmClick = itemFilmClick;
        }

        public void bind(ItemFilm itemFilm) {
            Utilities.glideImage(itemView.getContext(), itemFilm.getPosterPath(), binding.ivImage);
            binding.tvTitle.setText(itemFilm.getTitle());
            binding.tvRate.setText(itemView.getContext().getString(R.string.max_rate, itemFilm.getVoteAverage()));
            binding.tvVotes.setText(itemView.getContext().getString(R.string.votes, itemFilm.getVoteCount()));
            binding.rbRate.setRating(itemFilm.getVoteAverage()/10*5);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemFilmClick.onShowDetailClick(itemFilm);
                }
            });
        }
    }
}
