package com.example.movies;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class SearchFrag extends Fragment {

    private RecyclerView recyclerView;
    RecyclerView.Adapter myAdapter;
    RecyclerView.LayoutManager layoutManager;
    View view;

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public SearchFrag() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_search, container, false);

        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        recyclerView = view.findViewById(R.id.suggestions);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this.getActivity());

        recyclerView.setLayoutManager(layoutManager);

        myAdapter = new SuggestionAdapter(this.getActivity(),ApplicationClass.suggestions);
        recyclerView.setAdapter(myAdapter);

    }


    public void notifyDataChanged(){

        myAdapter.notifyDataSetChanged();
    }



}