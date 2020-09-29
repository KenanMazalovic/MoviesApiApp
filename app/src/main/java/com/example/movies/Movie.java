package com.example.movies;

import com.google.gson.annotations.SerializedName;

public class Movie {
// @SerializedName is used to map the POJO object into to JSON response properties.

    @SerializedName("poster_path")
    private String moviePoster;
    @SerializedName("title")
    private String movieName;
    @SerializedName("release_date")
    private String releaseDate;
    @SerializedName("overview")
    private String movieOverview;

    public Movie(String moviePoster, String movieName, String releaseDate, String movieOverview) {
        this.moviePoster = moviePoster;
        this.movieName = movieName;
        this.releaseDate = releaseDate;
        this.movieOverview = movieOverview;
    }

    public String getMoviePoster() {
        return moviePoster;
    }

    public void setMoviePoster(String moviePoster) {
        this.moviePoster = moviePoster;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getMovieOverview() {
        return movieOverview;
    }

    public void setMovieOverview(String movieOverview) {
        this.movieOverview = movieOverview;
    }
}
