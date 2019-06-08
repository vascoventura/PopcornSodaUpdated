package com.example.popcornsoda.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.popcornsoda.R;
import com.example.popcornsoda.models.Movie;
import com.example.popcornsoda.models.Serie;
import com.example.popcornsoda.ui.Filmes;

public class AdaptadorLVFilmes extends RecyclerView.Adapter<AdaptadorLVFilmes.ViewHolderFilme> {
    private Cursor cursor;
    private Context context;

    public AdaptadorLVFilmes(Context context){
        this.context = context;
    }

    public void setCursor(Cursor cursor){
        if (this.cursor != cursor){
            this.cursor = cursor;
            notifyDataSetChanged();
        }
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
        if(viewHolderFilmeSelecionado == null) return null;

        return viewHolderFilmeSelecionado.movie;
    }

    private static ViewHolderFilme viewHolderFilmeSelecionado = null;

    public class ViewHolderFilme extends RecyclerView.ViewHolder implements View.OnClickListener {

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

        }

        public void setFilme(Movie movie) {
            this.movie = movie;

            textViewNome.setText(movie.getNome_filme());
            textViewTipo.setText(movie.getTipo_filme());
            textViewAno.setText(String.valueOf(movie.getAno_filme()));
            textViewAutor.setText(movie.getNomeAutor());
            textViewAno.setText(String.valueOf(movie.getAno_filme()));
            textViewClassificacao.setText(String.valueOf(movie.getClassificacao_filme()));

        }

        @Override
        public void onClick(View v) {
            if (viewHolderFilmeSelecionado != null) {
                viewHolderFilmeSelecionado.desSeleciona();
            }

            viewHolderFilmeSelecionado = this;

            seleciona();
        }


        private void desSeleciona() {
            itemView.setBackgroundResource(R.color.colorPrimary);
        }

        private void seleciona() {
            itemView.setBackgroundResource(R.color.colorAccent);
        }
    }
}