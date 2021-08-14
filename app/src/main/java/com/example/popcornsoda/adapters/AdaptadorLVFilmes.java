package com.example.popcornsoda.adapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popcornsoda.R;
import com.example.popcornsoda.models.Movie;
import com.example.popcornsoda.ui.DetailActivityMovie;
import com.example.popcornsoda.ui.Filmes;


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

    public boolean isSelecao() {
        return selecao;
    }

    @NonNull
    @Override
    public ViewHolderFilme onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemFilme = LayoutInflater.from(context).inflate(R.layout.item_movie_lista, parent, false);
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
            return 0;
        }
        return cursor.getCount();
    }

    public Movie getFilmeSelecionada() {
        if(viewHolderFilmeSelecionado == null){
            return null;
        } else{
            return viewHolderFilmeSelecionado.movie;
        }


    }

    private static ViewHolderFilme viewHolderFilmeSelecionado = null;

    public class ViewHolderFilme extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        private TextView textViewNome;
        private TextView textViewTipo;
        private TextView textViewAno;
        private TextView textViewClassificacao;
        private TextView textViewAutor;

        private Movie movie;

        public ViewHolderFilme(@NonNull View itemView) {
            super(itemView);

            textViewNome = (TextView) itemView.findViewById(R.id.textViewNomeFilme);
            textViewTipo = (TextView) itemView.findViewById(R.id.textViewTipoFilme);
            textViewAno = (TextView) itemView.findViewById(R.id.textViewAnoFilme);
            textViewAutor = (TextView) itemView.findViewById(R.id.textViewAutorFilme);
            textViewClassificacao = (TextView) itemView.findViewById(R.id.textViewClassificacaoFilme);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

        }

        public void setFilme(Movie movie) {
            this.movie = movie;

            textViewNome.setText(movie.getNome_filme());
            textViewTipo.setText(movie.getNomeCategoria());
            textViewAno.setText(String.valueOf(movie.getAno_filme()));
            textViewAutor.setText(movie.getNomeAutor());
            textViewAno.setText(String.valueOf(movie.getAno_filme()));
            textViewClassificacao.setText(String.valueOf(movie.getClassificacao_filme()));

        }

        @Override
        public void onClick(View v) {

            if(click) {
                long idFilme = movie.getId_filme();
                System.out.println("ID_FILME: " + idFilme);
                Context context = v.getContext();

                Intent intent = new Intent();
                intent.setClass(context, DetailActivityMovie.class);
                intent.putExtra(ID_FILME, movie.getId_filme());
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
        public boolean onLongClick(View v) {
            click = false;
            if(selecao){
                viewHolderFilmeSelecionado.desSeleciona();
                ((Filmes) context).atualizaOpcoesMenu();
                System.out.println("Estado: " + viewHolderFilmeSelecionado);
                System.out.println("Filme Selecionado: " + viewHolderFilmeSelecionado.movie);

            } else{
                viewHolderFilmeSelecionado = this;
                viewHolderFilmeSelecionado.seleciona();
                ((Filmes) context).atualizaOpcoesMenu();
                System.out.println("Estado1: " + viewHolderFilmeSelecionado);
                System.out.println("Filme Selecionado1 : " + viewHolderFilmeSelecionado.movie);

            }
                return false;
        }
    }
}