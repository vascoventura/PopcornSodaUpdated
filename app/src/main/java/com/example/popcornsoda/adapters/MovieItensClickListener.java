package com.example.popcornsoda.adapters;

import android.widget.ImageView;
import com.example.popcornsoda.models.Movie;

public interface MovieItensClickListener {

    void onMovieClick(Movie movie, ImageView movieImageView); //Vamos precisar da imageview para fazer a partilha de media entre duas atividades
}
