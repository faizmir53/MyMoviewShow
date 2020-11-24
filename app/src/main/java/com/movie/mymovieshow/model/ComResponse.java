package com.movie.mymovieshow.model;

import com.google.gson.JsonObject;

public class ComResponse {

    public MovieDetails movieDetails;
    public MovieReview movieReview;
    public MovieCredit movieCredit;
    public MovieSimilar movieSimilar;

    public ComResponse(MovieDetails movieDetails, MovieReview movieReview, MovieCredit movieCredit, MovieSimilar movieSimilar) {
        this.movieDetails = movieDetails;
        this.movieReview = movieReview;
        this.movieCredit = movieCredit;
        this.movieSimilar = movieSimilar;
    }

    public MovieDetails getMovieDetails() {
        return movieDetails;
    }

    public void setMovieDetails(MovieDetails movieDetails) {
        this.movieDetails = movieDetails;
    }

    public MovieReview getMovieReview() {
        return movieReview;
    }

    public void setMovieReview(MovieReview movieReview) {
        this.movieReview = movieReview;
    }

    public MovieCredit getMovieCredit() {
        return movieCredit;
    }

    public void setMovieCredit(MovieCredit movieCredit) {
        this.movieCredit = movieCredit;
    }

    public MovieSimilar getMovieSimilar() {
        return movieSimilar;
    }

    public void setMovieSimilar(MovieSimilar movieSimilar) {
        this.movieSimilar = movieSimilar;
    }
}
