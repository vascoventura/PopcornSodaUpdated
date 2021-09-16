package com.example.popcornsoda.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.popcornsoda.BdPopcorn.BdTableFilmes;
import com.example.popcornsoda.BdPopcorn.ContentProviderPopcorn;
import com.example.popcornsoda.R;
import com.example.popcornsoda.adapters.AdaptadorLVFilmes;

public class FavoritosFilmes extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final int ID_CURSO_LOADER_FILMES = 0;


    private AdaptadorLVFilmes adaptadorFilmes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoritos_filmes);

        getSupportLoaderManager().initLoader(ID_CURSO_LOADER_FILMES, null, this);


        //Botao Voltar

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //Lista Vertical
        RecyclerView recyclerViewFilmes = (RecyclerView) findViewById(R.id.lista_filmes_vertical);
        adaptadorFilmes = new AdaptadorLVFilmes(this);
        recyclerViewFilmes.setAdapter(adaptadorFilmes);
        recyclerViewFilmes.setLayoutManager( new LinearLayoutManager(this) );


    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader (int id, @Nullable Bundle args){
        CursorLoader cursorLoader = new CursorLoader(this, ContentProviderPopcorn.ENDERECO_FILMES, BdTableFilmes.TODAS_COLUNAS, null, null, BdTableFilmes.CAMPO_NOME);
        System.out.print("CURSOR LOADER: " + cursorLoader);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished (@NonNull Loader<Cursor> loader, Cursor data){
        adaptadorFilmes.setCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader){
        adaptadorFilmes.setCursor(null);
    }

}