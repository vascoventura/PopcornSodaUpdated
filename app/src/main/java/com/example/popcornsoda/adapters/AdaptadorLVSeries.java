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
import com.example.popcornsoda.models.Serie;
import com.example.popcornsoda.ui.DetailActivtySerie;
import com.example.popcornsoda.ui.Series;

public class AdaptadorLVSeries extends RecyclerView.Adapter<AdaptadorLVSeries.ViewHolderSerie> {

    public static final String ID_SERIE = "ID_SERIE" ;

    private Cursor cursor;
    private Context context;
    private boolean selecao = false;
    private boolean click = false;


    public AdaptadorLVSeries(Context context){
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
        if(viewHolderSerieSelecionada == null){
            return null;
        }else{
            return viewHolderSerieSelecionada.serie;
        }
    }

    private static ViewHolderSerie viewHolderSerieSelecionada = null;

    public class ViewHolderSerie extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
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
            itemView.setOnLongClickListener(this);

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

            if(click) {
                long idSerie = serie.getId_serie();
                System.out.println("ID_SERIE: " + idSerie);
                Context context = v.getContext();

                Intent intent = new Intent();
                intent.setClass(context, DetailActivtySerie.class);
                intent.putExtra(ID_SERIE, idSerie);
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
                viewHolderSerieSelecionada.desSeleciona();
                ((Series) context).atualizaOpcoesMenu();


            }else{
                viewHolderSerieSelecionada = this;
                viewHolderSerieSelecionada.seleciona();
                ((Series) context).atualizaOpcoesMenu();

            }
            return false;
        }
    }
}