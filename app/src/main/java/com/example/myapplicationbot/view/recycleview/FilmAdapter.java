package com.example.myapplicationbot.view.recycleview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplicationbot.R;
import com.example.myapplicationbot.model.entities.ItemFilm;

import java.util.ArrayList;
import java.util.List;

public class FilmAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private ItemFilmClick itemFilmClick;
    private ArrayList<ItemFilm> filmArrayList;

    public FilmAdapter(Context context, ItemFilmClick itemFilmClick) {
        this.context = context;
        this.itemFilmClick = itemFilmClick;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View filmView = inflater.inflate(R.layout.item_film, parent, false);
        ViewHolder viewHolder = new ViewHolder(filmView, itemFilmClick);
        return viewHolder;
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

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView filmImage;
        private TextView filmName;
        private ItemFilmClick itemFilmClick;
        private TextView filmRate;
        private TextView filmVotes;

        public ViewHolder(@NonNull View itemView, ItemFilmClick itemFilmClick) {
            super(itemView);
            filmImage = itemView.findViewById(R.id.film_image);
            filmName = itemView.findViewById(R.id.film_name);
            filmRate = itemView.findViewById(R.id.film_rate);
            filmVotes = itemView.findViewById(R.id.film_vote);
            this.itemFilmClick = itemFilmClick;
        }

        public void bind(ItemFilm itemFilm) {
            Glide.with(context)
                    .load("http://image.tmdb.org/t/p/w500" + itemFilm.getPosterPath())
                    .into(filmImage);
            filmName.setText(itemFilm.getTitle());
            filmRate.setText(String.valueOf(itemFilm.getVoteAverage()) + "/10");
            filmVotes.setText(String.valueOf(itemFilm.getVoteCount()) + " votes");
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemFilmClick.onShowDetailClick(itemFilm);
                }
            });
        }
    }
}
