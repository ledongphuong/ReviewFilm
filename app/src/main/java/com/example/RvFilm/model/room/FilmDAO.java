package com.example.RvFilm.model.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.RvFilm.model.entities.ItemFilm;

import java.util.List;

import io.reactivex.Maybe;

@Dao
public interface FilmDAO {
    @Insert
    Maybe<Long> insert(ItemFilm item);

    @Update
    public void update(ItemFilm... items);

    @Delete
    Maybe<Integer> delete(ItemFilm item);

    @Query("SELECT * FROM items")
    Maybe<List<ItemFilm>> getItems();

    @Query("SELECT * FROM items WHERE id= :id")
    Maybe<ItemFilm> geItemById(int id);

}
