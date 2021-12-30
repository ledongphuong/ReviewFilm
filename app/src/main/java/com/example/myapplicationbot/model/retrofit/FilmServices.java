package com.example.myapplicationbot.model.retrofit;

import com.example.myapplicationbot.model.entities.ResultList;
import com.example.myapplicationbot.model.entities.ResultTrailer;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface FilmServices {

    @GET("3/movie/now_playing?api_key=96cd1b475603f246f4234d9d04ecbbaf&language=en-US&page=1")
    Observable<ResultList> getMoreList(@Query("page") int number);

    @GET("3/movie/popular?api_key=96cd1b475603f246f4234d9d04ecbbaf&language=en-US")
    Observable<ResultList> getMorePop(@Query("page") int number);

    @GET("3/movie/top_rated?api_key=96cd1b475603f246f4234d9d04ecbbaf&language=en-US&page=1")
    Observable<ResultList> getMoreRate(@Query("page") int number);

    @GET("3/movie/{id}/videos?api_key=96cd1b475603f246f4234d9d04ecbbaf&language=en-US")
    Observable<ResultTrailer> getTrailer(@Path("id") int id);
}
