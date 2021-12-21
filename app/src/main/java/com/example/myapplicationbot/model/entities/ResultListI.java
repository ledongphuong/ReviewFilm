package com.example.myapplicationbot.model.entities;

import java.util.List;

public class ResultListI<I> {
    List<ItemFilm> results;

    public List<ItemFilm> getResult() {
        return results;
    }

    public void setResult(List<ItemFilm> result) {
        this.results = result;
    }
}
