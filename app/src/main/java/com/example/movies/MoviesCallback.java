package com.example.movies;

import java.util.ArrayList;

public interface MoviesCallback {

    void onSuccess(ArrayList<Movie> movies);

    void onError();
}
