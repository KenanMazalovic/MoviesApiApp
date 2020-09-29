package com.example.movies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements SuggestionAdapter.ItemClicked, MovieAdapter.MovieClicked {

    ImageView ivSearch;
    EditText etSearch;
    private Client client;
    int pageCounter = 1;
    FragmentManager fragmentManager;
    Fragment searchFrag, listFrag;
    ListFrag lFrag;
    SearchFrag sFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        client = Client.getClient();
        ivSearch = findViewById(R.id.ivSearch);
        etSearch = findViewById(R.id.etSearch);
        ConstraintLayout constraintLayout= findViewById(R.id.consLayout);
        fragmentManager = getSupportFragmentManager();
        searchFrag = fragmentManager.findFragmentById(R.id.searchFrag);
        listFrag = fragmentManager.findFragmentById(R.id.listFrag);
        lFrag = (ListFrag) fragmentManager.findFragmentById(R.id.listFrag);
        sFrag = (SearchFrag) fragmentManager.findFragmentById(R.id.searchFrag);

        fragmentManager.beginTransaction()
                .show(searchFrag)
                .hide(listFrag)
                .commit();

        ivSearch.setOnClickListener(view -> {
            sFrag.getRecyclerView().setVisibility(View.GONE);
            fragmentManager.beginTransaction()
                    .show(searchFrag)
                    .show(listFrag)
                    .commit();

            Search(etSearch.getText().toString().trim());
        });

        etSearch.setOnClickListener(view -> {
            sFrag.getRecyclerView().setVisibility(View.VISIBLE);
            try {
                SuggestionsDB db = new SuggestionsDB(MainActivity.this);
                db.open();
                ArrayList<String> l = db.getData();
                db.close();
                ApplicationClass.suggestions.clear();
                ApplicationClass.suggestions.addAll(l);
                sFrag.notifyDataChanged();
            } catch(SQLException e) {
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        constraintLayout.setOnClickListener(view -> sFrag.getRecyclerView().setVisibility(View.GONE));

    }

    @Override
    public void onItemClicked(int index) {
        sFrag.getRecyclerView().setVisibility(View.GONE);
        fragmentManager.beginTransaction()
                .show(searchFrag)
                .show(listFrag)
                .commit();
        Search(ApplicationClass.suggestions.get(index));
        etSearch.setText(ApplicationClass.suggestions.get(index));

    }

    public void Search(String query){
        client.setTotPages(10);


        if(query.matches("")){
            Toast.makeText(MainActivity.this,"Field is empty!!", Toast.LENGTH_LONG).show();
        }

        else {
            for (pageCounter=1 ;pageCounter <= client.getTotPages(); ++pageCounter) {

                String page = Integer.toString(pageCounter);
                client.setUrl("movie?api_key=2696829a81b1b5827d515ff121700838&query=" + query + "&page=" + page);
                client.getMovies(new MoviesCallback() {
                    @Override
                    public void onSuccess(ArrayList<Movie> movies) {

                        if(pageCounter == 1){
                            try {
                                SuggestionsDB db = new SuggestionsDB(MainActivity.this);
                                db.open();
                                db.createEntry(query);
                                db.close();
                            } catch (SQLException e) {
                                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                        // ApplicationClass.movies.clear();
                        ApplicationClass.movies.addAll(movies);
                        lFrag.notifyDataChanged();
                    }
                    @Override
                    public void onError() {
                        Toast.makeText(MainActivity.this, "Movie does not exist in database", Toast.LENGTH_LONG).show();
                    }
                });
            }

            ApplicationClass.movies.clear();
            pageCounter = 1;
        }

    }


    @Override
    public void onMovieClicked(int index) {
        sFrag.getRecyclerView().setVisibility(View.GONE);
    }
}

