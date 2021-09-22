package com.example.popcornsoda.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.popcornsoda.BdPopcorn.BdTableAutores;
import com.example.popcornsoda.BdPopcorn.BdTableCategorias;
import com.example.popcornsoda.BdPopcorn.BdTableSeries;
import com.example.popcornsoda.BdPopcorn.BdTableSeries;
import com.example.popcornsoda.R;
import com.example.popcornsoda.adapters.AdaptadorFilmesFavoritos;
import com.example.popcornsoda.adapters.AdaptadorSeriesFavoritas;
import com.example.popcornsoda.adapters.myDbAdapter;
import com.example.popcornsoda.models.Movie;
import com.example.popcornsoda.models.Serie;

import java.util.ArrayList;

public class FavoritosSeries extends AppCompatActivity {

    private static final String ID_SERIE = "ID_SERIE";

    private Menu menu;
    private AdaptadorSeriesFavoritas adaptadorSeries;
    private myDbAdapter helper;
    private Cursor cursor_series_favoritas;
    private ArrayList<Serie> serieArrayList;

    private Serie serie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoritos_series);


        //Botao Voltar

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        serieArrayList = new ArrayList<Serie>();

        helper = new myDbAdapter(this);
        cursor_series_favoritas = helper.getSeriesFavoritas();


        while(cursor_series_favoritas.moveToNext()){
            @SuppressLint("Range") long id1  = cursor_series_favoritas.getLong(0);

            @SuppressLint("Range") String nome_serie = cursor_series_favoritas.getString(
                    cursor_series_favoritas.getColumnIndex(BdTableSeries.CAMPO_NOME)
            );

            @SuppressLint("Range") long categoria = cursor_series_favoritas.getLong(
                    cursor_series_favoritas.getColumnIndex(BdTableSeries.CAMPO_CATEGORIA)
            );

            @SuppressLint("Range") long autor = cursor_series_favoritas.getLong(
                    cursor_series_favoritas.getColumnIndex(BdTableSeries.CAMPO_AUTOR)
            );

            @SuppressLint("Range") double classificacao = cursor_series_favoritas.getDouble(
                    cursor_series_favoritas.getColumnIndex(BdTableSeries.CAMPO_CLASSIFICACAO)
            );

            @SuppressLint("Range") int ano = cursor_series_favoritas.getInt(
                    cursor_series_favoritas.getColumnIndex(BdTableSeries.CAMPO_ANO)
            );

            @SuppressLint("Range") int temporadas = cursor_series_favoritas.getInt(
                    cursor_series_favoritas.getColumnIndex(BdTableSeries.CAMPO_TEMPORADAS)
            );

            @SuppressLint("Range") String descricao = cursor_series_favoritas.getString(
                    cursor_series_favoritas.getColumnIndex(BdTableSeries.CAMPO_DESCRICAO)
            );
            @SuppressLint("Range") byte[] foto_capa = cursor_series_favoritas.getBlob(
                    cursor_series_favoritas.getColumnIndex(BdTableSeries.CAMPO_CAPA)
            );

            @SuppressLint("Range") byte[] foto_fundo = cursor_series_favoritas.getBlob(
                    cursor_series_favoritas.getColumnIndex(BdTableSeries.CAMPO_FUNDO)
            );
            @SuppressLint("Range") String link = cursor_series_favoritas.getString(
                    cursor_series_favoritas.getColumnIndex(BdTableSeries.CAMPO_LINK)
            );

            @SuppressLint("Range") String nomeAutor = cursor_series_favoritas.getString(
                    cursor_series_favoritas.getColumnIndex(BdTableAutores.CAMPO_NOME)
            );

            @SuppressLint("Range") String nomeCategoria = cursor_series_favoritas.getString(
                    cursor_series_favoritas.getColumnIndex(BdTableCategorias.CAMPO_NOME)
            );

            @SuppressLint("Range") boolean visto = Boolean.parseBoolean(cursor_series_favoritas.getString(cursor_series_favoritas.getColumnIndex(BdTableSeries.CAMPO_VISTO)));

            @SuppressLint("Range") boolean favorito = Boolean.parseBoolean(cursor_series_favoritas.getString(cursor_series_favoritas.getColumnIndex(BdTableSeries.CAMPO_FAVORITO)));

            System.out.println("");
            System.out.println("");
            System.out.println("");

            System.out.println("ID_GUARDADO FILME: " + nome_serie  + " ID: " + id1);

            System.out.println("");
            System.out.println("");
            System.out.println("");

            serie = new Serie(nome_serie, nomeCategoria, temporadas, classificacao, ano, nomeAutor, id1);
            serieArrayList.add(serie);

        }





        //Lista Vertical
        ListView listViewSeries = findViewById(R.id.lista_series_vertical);
        adaptadorSeries = new AdaptadorSeriesFavoritas(this,R.layout.item_serie, serieArrayList);
        listViewSeries.setAdapter((ListAdapter) adaptadorSeries);

        listViewSeries.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id2) {

                //Toast.makeText(getApplicationContext(), "You click on position:"+position, Toast.LENGTH_SHORT).show();

                Serie serie1 = serieArrayList.get(position);
                long id_serie = serie1.getId_serie();
                System.out.println("ID DA SERIE FAVORITA clickada: " + id_serie);
                System.out.println("POSICAO CLICKADA: " + position);
                Context context = view.getContext();

                Intent intent = new Intent();
                intent.setClass(context, DetailActivitySerie.class);
                intent.putExtra(ID_SERIE, id_serie);
                context.startActivity(intent);
            }
        });

    }

    //Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_favoritos, menu);
        this.menu = menu;
        return true;
    }

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemEditar:
                Intent intent2 = new Intent(this, AlterarMovie.class);
                intent2.putExtra(ID_FILME, adaptadorFilmes.getFilmeSelecionada().getId_filme());
                startActivity(intent2);
                return true;

            case R.id.itemEliminar:
                Intent intent3 = new Intent(this, ApagarMovie.class);
                intent3.putExtra(ID_FILME, adaptadorFilmes.getFilmeSelecionada().getId_filme());
                startActivity(intent3);
                return true;

            case R.id.itemDetalhe:
                Intent intent4 = new Intent(this, DetailActivityMovie.class);
                intent4.putExtra(ID_FILME, adaptadorFilmes.getFilmeSelecionada().getId_filme());
                startActivity(intent4);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void atualizaOpcoesMenu() {
        if(adaptadorFilmes.isSelecao()){
            menu.findItem(R.id.itemEditar).setVisible(true);
            menu.findItem(R.id.itemEliminar).setVisible(true);
            menu.findItem(R.id.itemDetalhe).setVisible(true);
            menu.findItem(R.id.itemRemoverFavorito).setVisible(true);
        }else{
            menu.findItem(R.id.itemEditar).setVisible(false);
            menu.findItem(R.id.itemEliminar).setVisible(false);
            menu.findItem(R.id.itemDetalhe).setVisible(false);
            menu.findItem(R.id.itemRemoverFavorito).setVisible(false);
        }
    }*/
}