package com.example.RvFilm.model.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.RvFilm.model.entities.ItemFilm;

@Database(entities = {ItemFilm.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract FilmDAO getItemDAO();
}
