package com.example.RvFilm.model.entities;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class Converter {
    @TypeConverter
    public static List<Integer> genreIdstoString(String value){
        Type listType = new TypeToken<List<Integer>>(){}.getType();
        return new Gson().fromJson(value,listType);
    }

    @TypeConverter
    public static String StringtogenreIds(List<Integer> list){
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
}
