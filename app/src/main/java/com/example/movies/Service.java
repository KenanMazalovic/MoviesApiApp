package com.example.movies;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface Service {

    //"3/search/movie?api_key=2696829a81b1b5827d515ff121700838&page=1&"
    @GET()
    Call<MoviesResponse> getMoviesBySearch(@Url String ur);

}
