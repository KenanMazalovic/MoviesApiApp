package com.example.movies;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Client {

    public  static final String BASE_URL = "https://api.themoviedb.org/3/search/";
    public static Retrofit retrofit = null;

    private static Client client;

    private int totPages;

    public int getTotPages() {
        return totPages;
    }

    public void setTotPages(int totPages) {
        this.totPages = totPages;
    }

    private String url;
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private Service service;

    private  Client(Service service){
        this.service = service;
    }

    public void Client() {

    }

    public static Client getClient() {

        if(retrofit==null) {
            retrofit = new  Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            client = new Client(retrofit.create(Service.class));
        }

        return client;
    }

    public void getMovies (final MoviesCallback callback) {
        service.getMoviesBySearch(url)
                .enqueue(new Callback<MoviesResponse>() {
                    @Override
                    public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                        if (response.isSuccessful()) {
                            MoviesResponse moviesResponse = response.body();
                            setTotPages(moviesResponse.getTotalPages());
                            if(moviesResponse != null && moviesResponse.getMovies() != null && moviesResponse.getTotalResults() > 0){
                                callback.onSuccess(moviesResponse.getMovies());
                            }

                            else {
                                callback.onError();
                            }
                        } else {
                            callback.onError();
                        }

                    }

                    @Override
                    public void onFailure(Call<MoviesResponse> call, Throwable t) {

                        callback.onError();

                    }
                });
    }
}
