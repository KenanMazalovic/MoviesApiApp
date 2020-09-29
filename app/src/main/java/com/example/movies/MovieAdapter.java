package com.example.movies;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import java.util.ArrayList;


public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w92";
    private ArrayList<Movie> movies;
    Context context;

    MovieClicked activity;

    public interface MovieClicked{
        void onMovieClicked (int index);

    }

    public MovieAdapter (Context context, ArrayList<Movie> list)
    {
        movies = list;
        this.context = context;
        activity = (MovieClicked) context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvDate, tvDesc;
        ImageView ivPoster;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvDate = (TextView) itemView.findViewById(R.id.tvDate);
            tvDesc = (TextView) itemView.findViewById(R.id.tvDesc);
            ivPoster = (ImageView) itemView.findViewById(R.id.ivPoster);
            itemView.setOnClickListener(view -> activity.onMovieClicked(movies.indexOf((Movie) view.getTag())));


        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout, parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.tvName.setText(movies.get(position).getMovieName());
        if(movies.get(position).getMovieOverview() == null){
            holder.tvDesc.setText(R.string.no_overview_info);
        }
        else {
            holder.tvDesc.setText(movies.get(position).getMovieOverview());
        }

        if(movies.get(position).getReleaseDate() == null){
            holder.tvDate.setText(R.string.no_date_info);
        }
        else {
            holder.tvDate.setText(movies.get(position).getReleaseDate());
        }
        if(movies.get(position).getMoviePoster() == null) {
            Glide.with(context)
                    .load(R.drawable.placeholder)
                    .apply(RequestOptions.placeholderOf(R.color.colorPrimary))
                    .into(holder.ivPoster);
        }
        else {
            Glide.with(context)
                    .load(IMAGE_BASE_URL + movies.get(position).getMoviePoster())
                    .apply(RequestOptions.placeholderOf(R.color.colorPrimary))
                    .into(holder.ivPoster);
        }
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }
}
