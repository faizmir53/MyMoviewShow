package com.movie.mymovieshow.network;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.movie.mymovieshow.model.MovieCredit;
import com.movie.mymovieshow.model.MovieDetails;
import com.movie.mymovieshow.model.MovieReview;
import com.movie.mymovieshow.model.MovieSimilar;
import com.movie.mymovieshow.model.Movies;


import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @Headers("Content-Type: application/json")
    @GET("movie/now_playing")
    Observable<Movies> getMoviesList(@Query("api_key") String apiKey, @Query("language") String language,
                               @Query("page") String page,@Query("region") String region);

    @GET("movie/{movie_id}")
    Observable<MovieDetails> movieDetails(@Path("movie_id") String movieId, @Query("api_key") String apiKey);

    @GET("movie/{movie_id}/reviews")
    Observable<MovieReview> movieReview(@Path("movie_id") String movieId, @Query("api_key") String apiKey);

    @GET("movie/{movie_id}/credits")
    Observable<MovieCredit> movieCredit(@Path("movie_id") String movieId, @Query("api_key") String apiKey);

    @GET("movie/{movie_id}/similar")
    Observable<MovieSimilar> movieSimilar(@Path("movie_id") String movieId, @Query("api_key") String apiKey);


}
