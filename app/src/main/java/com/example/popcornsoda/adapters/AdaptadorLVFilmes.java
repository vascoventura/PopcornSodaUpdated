package com.example.popcornsoda.adapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popcornsoda.R;
import com.example.popcornsoda.models.Movie;
import com.example.popcornsoda.ui.DetailActivityMovie;
import com.example.popcornsoda.ui.FavoritosFilmes;


public class AdaptadorLVFilmes extends RecyclerView.Adapter<AdaptadorLVFilmes.ViewHolderFilme> {

    public static final String ID_FILME = "ID_FILME" ;

    private Cursor cursor;
    private Context context;
    private boolean selecao = false;
    private boolean click = false;

    public AdaptadorLVFilmes(Context context){
        this.context = context;
    }

    public void setCursor(Cursor cursor){
        if (this.cursor != cursor){
            this.cursor = cursor;
            notifyDataSetChanged();
        }
    }

    public boolean isSelecao(){
        return selecao;
    }

    @NonNull
    @Override
    public ViewHolderFilme onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemFilme = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);

        return new ViewHolderFilme(itemFilme);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolderFilme holderFilme, int position) {
        cursor.moveToPosition(position);
        Movie movie = Movie.fromCursor(cursor);
        holderFilme.setFilme(movie);

    }

    @Override
    public int getItemCount() {
        if (cursor == null) {
            System.out.println("Nao tem nada!");
            return 0;
        } else{
            System.out.println("Tem " + cursor.getCount() + "registos de filmes");
            return cursor.getCount();
        }
    }

    private static ViewHolderFilme viewHolderFilmeSelecionado = null;


    public Movie getFilmeSelecionada() {
        if(viewHolderFilmeSelecionado == null){
            return null;
        }else{
            return viewHolderFilmeSelecionado.movie;
        }
    }


    public static class ViewHolderFilme extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        private TextView textViewNome;
        private TextView textViewCategoria;
        private TextView textViewAno;
        private TextView textViewClassificacao;
        private TextView textViewAutor;
        private ImageView imageCapaFilme;

        private Movie movie;
        private boolean click;
        private boolean selecao;
        private Object context;

        public ViewHolderFilme(@NonNull View itemView) {
            super(itemView);

            textViewNome = (TextView) itemView.findViewById(R.id.textViewNomeFilme_eliminar);
            textViewCategoria = (TextView) itemView.findViewById(R.id.textViewCategoriaSerie_eliminar);
            textViewAno = (TextView) itemView.findViewById(R.id.textViewAnoSerie_eliminar);
            textViewAutor = (TextView) itemView.findViewById(R.id.textViewAutorFilme_eliminar);
            textViewClassificacao = (TextView) itemView.findViewById(R.id.textViewClassificacaoSerie_eliminar);
            imageCapaFilme = (ImageView) itemView.findViewById(R.id.imageViewCapaFilme);


            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

        }

        public void setFilme(Movie movie) {
            this.movie = movie;

                textViewNome.setText(movie.getNome_filme());
                textViewCategoria.setText(movie.getNomeCategoria());
                textViewAno.setText(String.valueOf(movie.getAno_filme()));
                textViewAutor.setText(movie.getNomeAutor());
                textViewAno.setText(String.valueOf(movie.getAno_filme()));
                textViewClassificacao.setText(String.valueOf(movie.getClassificacao_filme()));

                byte[] filmeImageCapaByte = movie.getFoto_capa_filme();
                Bitmap bitmap_filmeImage = BitmapFactory.decodeByteArray(filmeImageCapaByte, 0, filmeImageCapaByte.length);
                imageCapaFilme.setImageBitmap(bitmap_filmeImage);

        }

        @Override
        public void onClick(View v) {
            if(click){
                long idFilme = movie.getId_filme();
                System.out.println("ID DO FILME: " + idFilme);
                Context context = v.getContext();

                Intent intent = new Intent();
                intent.setClass(context, DetailActivityMovie.class);
                intent.putExtra(ID_FILME, idFilme);
                context.startActivity(intent);
            }
            click = true;
        }

        private void desSeleciona() {

            itemView.setBackgroundResource(R.color.colorPrimary);
            selecao = false;
        }

        private void seleciona() {

            itemView.setBackgroundResource(R.color.colorAccent);
            selecao = true;
        }

        @Override
        public boolean onLongClick(View view) {
            click = false;
            if(selecao){
                viewHolderFilmeSelecionado.desSeleciona();
//                ((FavoritosFilmes) context).atualizaOpcoesMenu();


            }else{
                viewHolderFilmeSelecionado = this;
                viewHolderFilmeSelecionado.seleciona();
        //       ((FavoritosFilmes) context).atualizaOpcoesMenu();

            }

            return false;
        }
    }
}