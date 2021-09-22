package com.example.popcornsoda.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.popcornsoda.R;
import com.example.popcornsoda.models.Movie;
import com.example.popcornsoda.ui.DetailActivityMovie;

import java.util.ArrayList;

public class AdaptadorFilmesFavoritos extends BaseAdapter implements View.OnClickListener {

        private static final String ID_FILME = "ID_FILME";
        private Context context;
        private int layout;
        private ArrayList<Movie> moviesList;
        private View row;
        private ViewHolder holder;


        LayoutInflater inflater;

        public AdaptadorFilmesFavoritos(Context context, int layout, ArrayList<Movie> moviesList) {
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
            System.out.println("ID DO FILME (favoritos): " + idFilme);

            Context context = view.getContext();

            Intent intent = new Intent();
            intent.setClass(context, DetailActivityMovie.class);
            intent.putExtra(ID_FILME, idFilme);
            context.startActivity(intent);
        }

        private class ViewHolder{
            private TextView textViewNome;
            private TextView textViewCategoria;
            private TextView textViewAno;
            private TextView textViewClassificacao;
            private TextView textViewAutor;
            private ImageView imageCapaFilme;

        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {


            row = view;
            holder = new ViewHolder();


            if(row==null){
                inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(layout, null);

                holder.textViewNome = (TextView) row.findViewById(R.id.textViewNomeFilme_eliminar);
                holder.textViewCategoria = (TextView) row.findViewById(R.id.textViewCategoriaSerie_eliminar);
                holder.textViewAno = (TextView) row.findViewById(R.id.textViewAnoSerie_eliminar);
                holder.textViewClassificacao = (TextView) row.findViewById(R.id.textViewClassificacaoSerie_eliminar);
                holder.textViewAutor = (TextView) row.findViewById(R.id.textViewAutorFilme_eliminar);
                holder.imageCapaFilme = (ImageView) row.findViewById(R.id.imageViewCapaFilme);


                row.setTag(holder);


            }else{
                holder = (ViewHolder) row.getTag();
            }

            Movie movie = moviesList.get(position);

            holder.textViewNome.setText(movie.getNome_filme());
            holder.textViewCategoria.setText(movie.getNomeCategoria());
            holder.textViewAno.setText(String.valueOf(movie.getAno_filme()));
            holder.textViewClassificacao.setText(String.valueOf(movie.getClassificacao_filme()));
            holder.textViewAutor.setText(movie.getNomeAutor());


            return row;
        }
}


