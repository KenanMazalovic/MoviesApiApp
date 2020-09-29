package com.example.movies;

import android.app.Application;
import java.util.ArrayList;

public class ApplicationClass extends Application {

    public static ArrayList<Movie> movies;
    public static ArrayList<String> suggestions;

    @Override
    public void onCreate() {
        super.onCreate();

        movies = new ArrayList<Movie>();
        suggestions = new ArrayList<String>();

    }

}
