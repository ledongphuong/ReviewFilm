package com.example.myapplicationbot.model.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.myapplicationbot.model.entities.ItemFilm;

@Database(entities = {ItemFilm.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ItemFavouriteDAO getItemDAO();
}
