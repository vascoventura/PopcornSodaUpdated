package com.example.popcornsoda.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.popcornsoda.R;
import com.example.popcornsoda.models.Movie;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyViewHolder> {

    Context context;
    List<Movie> mData;
    MovieItensClickListener movieItemClickListener;

    public MovieAdapter(Context context, List<Movie> mData, MovieItensClickListener listener) {
        this.context = context;
        this.mData = mData;
        movieItemClickListener = listener;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_movie_grid_view, viewGroup, false);
        return new MyViewHolder((view));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        //myViewHolder.TVtitle.setText(mData.get(i).getNome_filme());
        //myViewHolder.ImgMovie.setImageResource(mData.get(i).getFoto_capa_filme());

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView TVtitle;
        private ImageView ImgMovie;

        public MyViewHolder(@NonNull View itemView) {

            super(itemView);
//            TVtitle = itemView.findViewById(R.id.item_movie_title);
            ImgMovie = itemView.findViewById(R.id.item_grid_movie_img);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    movieItemClickListener.onMovieClick(mData.get(getAdapterPosition()),ImgMovie);
                }
            });
        }
    }
}
