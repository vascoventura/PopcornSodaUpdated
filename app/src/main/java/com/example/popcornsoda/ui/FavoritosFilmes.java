package com.example.popcornsoda.ui;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.popcornsoda.BdPopcorn.BdTableFilmes;
import com.example.popcornsoda.R;
import com.example.popcornsoda.adapters.AdaptadorFilmesFavoritos;
import com.example.popcornsoda.adapters.AdaptadorLVFilmes;
import com.example.popcornsoda.adapters.myDbAdapter;
import com.example.popcornsoda.models.Movie;

import java.util.ArrayList;

public class FavoritosFilmes extends AppCompatActivity{

    private static final String ID_FILME = "ID_FILME";

    private Menu menu;
    private AdaptadorFilmesFavoritos adaptadorFilmes;
    private myDbAdapter helper;
    private Cursor cursor_filmes_favoritos;

    private Movie filme;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoritos_filmes);

        //Botao Voltar

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ArrayList<Movie> movieArrayList = new ArrayList<Movie>();

        helper = new myDbAdapter(this);
        cursor_filmes_favoritos = helper.getFilmesFavoritos();


        while(cursor_filmes_favoritos.moveToNext()){
            @SuppressLint("Range") long id = cursor_filmes_favoritos.getLong(cursor_filmes_favoritos.getColumnIndex(BdTableFilmes._ID)
            );

            @SuppressLint("Range") String nome = cursor_filmes_favoritos.getString(
                    cursor_filmes_favoritos.getColumnIndex(BdTableFilmes.CAMPO_NOME)
            );

            @SuppressLint("Range") long categoria = cursor_filmes_favoritos.getLong(
                    cursor_filmes_favoritos.getColumnIndex(BdTableFilmes.CAMPO_CATEGORIA)
            );

            @SuppressLint("Range") long autor = cursor_filmes_favoritos.getLong(
                    cursor_filmes_favoritos.getColumnIndex(BdTableFilmes.CAMPO_AUTOR)
            );

            @SuppressLint("Range") double classificacao = cursor_filmes_favoritos.getDouble(
                    cursor_filmes_favoritos.getColumnIndex(BdTableFilmes.CAMPO_CLASSIFICACAO)
            );

            @SuppressLint("Range") int ano = cursor_filmes_favoritos.getInt(
                    cursor_filmes_favoritos.getColumnIndex(BdTableFilmes.CAMPO_ANO)
            );

            @SuppressLint("Range") String descricao = cursor_filmes_favoritos.getString(
                    cursor_filmes_favoritos.getColumnIndex(BdTableFilmes.CAMPO_DESCRICAO)
            );
            @SuppressLint("Range") byte[] foto_capa = cursor_filmes_favoritos.getBlob(
                    cursor_filmes_favoritos.getColumnIndex(BdTableFilmes.CAMPO_CAPA)
            );

            @SuppressLint("Range") byte[] foto_fundo = cursor_filmes_favoritos.getBlob(
                    cursor_filmes_favoritos.getColumnIndex(BdTableFilmes.CAMPO_FUNDO)
            );
            @SuppressLint("Range") String link = cursor_filmes_favoritos.getString(
                    cursor_filmes_favoritos.getColumnIndex(BdTableFilmes.CAMPO_LINK)
            );

            @SuppressLint("Range") String nomeAutor = cursor_filmes_favoritos.getString(
                    cursor_filmes_favoritos.getColumnIndex(BdTableFilmes.CAMPO_AUTOR)
            );

            @SuppressLint("Range") String nomeCategoria = cursor_filmes_favoritos.getString(
                    cursor_filmes_favoritos.getColumnIndex(BdTableFilmes.CAMPO_CATEGORIA)
            );

            @SuppressLint("Range") boolean visto = Boolean.parseBoolean(cursor_filmes_favoritos.getString(cursor_filmes_favoritos.getColumnIndex(BdTableFilmes.CAMPO_VISTO)));

            @SuppressLint("Range") boolean favorito = Boolean.parseBoolean(cursor_filmes_favoritos.getString(cursor_filmes_favoritos.getColumnIndex(BdTableFilmes.CAMPO_FAVORITO)));

            filme = new Movie(nome, nomeCategoria, classificacao, ano, nomeAutor);
            movieArrayList.add(filme);

        }

        //Lista Vertical
        ListView listViewFilmes = findViewById(R.id.lista_filmes_vertical);
        adaptadorFilmes = new AdaptadorFilmesFavoritos(this,R.layout.item_movie, movieArrayList);
        listViewFilmes.setAdapter((ListAdapter) adaptadorFilmes);


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