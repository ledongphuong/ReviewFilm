package com.example.myapplicationbot.model.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplicationbot.model.entities.ItemFilm;
import com.example.myapplicationbot.model.entities.ResultListI;

import java.util.List;

@Dao
public interface FilmDAO {
    @Insert
    public void insert(ItemFilm... items);

    @Update
    public void update(ItemFilm... items);

    @Delete
    public void delete(ItemFilm item);

    @Query("SELECT * FROM items")
    public List<ItemFilm> getItems();

    @Query("SELECT * FROM items WHERE id= :id")
    public ItemFilm geItemById(int id);

}
