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
import com.example.popcornsoda.models.Serie;
import com.example.popcornsoda.ui.Series;

public class AdaptadorLVSeries extends RecyclerView.Adapter<AdaptadorLVSeries.ViewHolderSerie> {
    private Cursor cursor;
    private Context context;

    public AdaptadorLVSeries(Context context){
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
    public ViewHolderSerie onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemSerie = LayoutInflater.from(context).inflate(R.layout.item_serie, parent, false);

        return new ViewHolderSerie(itemSerie);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolderSerie holderSerie, int position) {
        cursor.moveToPosition(position);
        Serie serie = Serie.fromCursor(cursor);
        holderSerie.setSerie(serie);

    }

    @Override
    public int getItemCount() {
        if (cursor == null) {
            return 0;
        }
        return cursor.getCount();
    }



    public Serie getSerieSelecionada() {
        if(viewHolderSerieSelecionada == null) return null;

        return viewHolderSerieSelecionada.serie;
    }

    private static ViewHolderSerie viewHolderSerieSelecionada = null;

    public class ViewHolderSerie extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView textViewNome;
        private TextView textViewTipo;
        private TextView textViewAno;
        private TextView textViewTemporadas;
        private TextView textViewClassificacao;
        private TextView textViewAutor;

        private Serie serie;

        public ViewHolderSerie(@NonNull View itemView) {
            super(itemView);

            textViewNome = (TextView) itemView.findViewById(R.id.textViewNomeSerie);
            textViewTipo = (TextView) itemView.findViewById(R.id.textViewTipoSerie);
            textViewAno = (TextView) itemView.findViewById(R.id.textViewAnoSerie);
            textViewAutor = (TextView) itemView.findViewById(R.id.textViewAutorSerie);
            textViewTemporadas = (TextView) itemView.findViewById(R.id.textViewTemporadasSerie);
            textViewClassificacao = (TextView) itemView.findViewById(R.id.textViewClassificacaoSerie);

            itemView.setOnClickListener(this);

        }

        public void setSerie(Serie serie) {
            this.serie = serie;

            textViewNome.setText(serie.getNome_serie());
            textViewTipo.setText(String.valueOf(serie.getTipo_serie()));
            textViewAutor.setText(serie.getNomeAutor());
            textViewAno.setText(String.valueOf(serie.getAno_serie()));
            textViewTemporadas.setText(String.valueOf(serie.getTemporadas()));
            textViewClassificacao.setText(String.valueOf(serie.getClassificacao_serie()));

        }

        @Override
        public void onClick(View v) {
            if (viewHolderSerieSelecionada != null) {
                viewHolderSerieSelecionada.desSeleciona();
            }

            viewHolderSerieSelecionada = this;

            ((Series) context).atualizaOpcoesMenu();

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