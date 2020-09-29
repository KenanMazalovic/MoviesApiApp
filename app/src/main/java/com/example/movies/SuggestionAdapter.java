package com.example.movies;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class SuggestionAdapter extends RecyclerView.Adapter<SuggestionAdapter.ViewHolder> {

    private ArrayList<String> suggestions;
    Context context;
    ItemClicked activity;

    public interface ItemClicked {
        void onItemClicked (int index);
    }

    public SuggestionAdapter (Context context, ArrayList<String> list) {
        suggestions = list;
        this.context = context;
        activity = (ItemClicked) context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvSuggestion;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSuggestion = (TextView) itemView.findViewById(R.id.tvSuggestion);
            itemView.setOnClickListener(view -> activity.onItemClicked(suggestions.indexOf((String) view.getTag())));

        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.suggestion_layout, parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.itemView.setTag(suggestions.get(position));
        holder.tvSuggestion.setText(suggestions.get(position));


    }

    @Override
    public int getItemCount() {
        return suggestions.size();
    }
}
