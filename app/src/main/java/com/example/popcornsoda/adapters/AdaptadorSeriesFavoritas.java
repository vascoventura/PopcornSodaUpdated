package com.example.popcornsoda.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.popcornsoda.R;
import com.example.popcornsoda.models.Movie;
import com.example.popcornsoda.models.Serie;
import com.example.popcornsoda.ui.DetailActivityMovie;
import com.example.popcornsoda.ui.DetailActivitySerie;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class AdaptadorSeriesFavoritas extends BaseAdapter implements View.OnClickListener {

    private static final String ID_SERIE = "ID_SERIE";
    private Context context;
    private int layout;
    private ArrayList<Serie> seriesList;
    private View row;
    private ViewHolder holder;


    LayoutInflater inflater;

    public AdaptadorSeriesFavoritas(Context context, int layout, ArrayList<Serie> seriesList) {
        this.context = context;
        this.layout = layout;
        this.seriesList = seriesList;

    }


    @Override
    public int getCount() {
        return seriesList.size();
    }

    @Override
    public Object getItem(int position) {
        return seriesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void onClick(View view) {

        long idSerie = row.getId();
        System.out.println("ID DA SERIE (favoritos): " + idSerie);

        Context context = view.getContext();

        Intent intent = new Intent();
        intent.setClass(context, DetailActivitySerie.class);
        intent.putExtra(ID_SERIE, idSerie);
        context.startActivity(intent);
    }

    private class ViewHolder{
        private TextView textViewNome;
        private TextView textViewCategoria;
        private TextView textViewAno;
        private TextView textViewClassificacao;
        private TextView textViewAutor;
        private TextView textViewTemporadas;
        private ImageView imageCapaFilme;

    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {


        row = view;
        holder = new ViewHolder();


        if(row==null){
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);

            holder.textViewNome = (TextView) row.findViewById(R.id.textViewNomeSerie);
            holder.textViewCategoria = (TextView) row.findViewById(R.id.textViewTipoSerie);
            holder.textViewAno = (TextView) row.findViewById(R.id.textViewAnoSerie);
            holder.textViewClassificacao = (TextView) row.findViewById(R.id.textViewClassificacaoSerie);
            holder.textViewAutor = (TextView) row.findViewById(R.id.textViewAutorSerie);
            holder.textViewTemporadas = (TextView) row.findViewById(R.id.textViewTemporadasSerie);

            row.setTag(holder);


        }else{
            holder = (ViewHolder) row.getTag();
        }

        Serie serie = seriesList.get(position);

        holder.textViewNome.setText(serie.getNome_serie());
        holder.textViewCategoria.setText(serie.getNomeCategoria());
        holder.textViewAno.setText(String.valueOf(serie.getAno_serie()));
        holder.textViewClassificacao.setText(String.valueOf(serie.getClassificacao_serie()));
        holder.textViewAutor.setText(serie.getNomeAutor());
        holder.textViewTemporadas.setText(String.valueOf(serie.getTemporadas()));

        return row;
    }
}