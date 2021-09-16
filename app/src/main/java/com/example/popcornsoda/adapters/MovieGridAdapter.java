package com.example.popcornsoda.adapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popcornsoda.R;
import com.example.popcornsoda.models.Movie;
import com.example.popcornsoda.ui.DetailActivityMovie;
import com.example.popcornsoda.ui.FavoritosFilmes;

import java.util.ArrayList;

public class MovieGridAdapter extends BaseAdapter implements View.OnClickListener {

    private static final String ID_FILME = "ID_FILME";
    Context context;
    private int layout;
    private ArrayList<Movie> moviesList;
    View row;


    LayoutInflater inflater;

    public MovieGridAdapter(Context context, int layout, ArrayList<Movie> moviesList) {
        this.context = context;
        this.layout = layout;
        this.moviesList = moviesList;

    }


    @Override
    public int getCount() {
        return moviesList.size();
    }

    @Override
    public Object getItem(int position) {
        return moviesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void onClick(View view) {

        long idFilme = row.getId();
        System.out.println("ID DO FILME: " + idFilme);

        Context context = view.getContext();

        Intent intent = new Intent();
        intent.setClass(context, DetailActivityMovie.class);
        intent.putExtra(ID_FILME, idFilme);
        context.startActivity(intent);
    }

    private class ViewHolder{
        ImageView imageCapa;
        TextView nomeFilme;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        row = view;
        ViewHolder holder = new ViewHolder();

        if(row==null){
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);

            holder.nomeFilme = (TextView) row.findViewById(R.id.item_grid_movie_name);
            holder.imageCapa = (ImageView) row.findViewById(R.id.item_grid_movie_img);

            row.setTag(holder);


        }else{
            holder = (ViewHolder) row.getTag();
        }

        Movie movie = moviesList.get(position);

        holder.nomeFilme.setText(movie.getNome_filme());

        byte[] capaImage = movie.getFoto_capa_filme();
        Bitmap bitmap = BitmapFactory.decodeByteArray(capaImage, 0, capaImage.length);
        holder.imageCapa.setImageBitmap(bitmap);

        return row;
    }
}