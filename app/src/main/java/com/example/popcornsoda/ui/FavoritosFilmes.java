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

public class FavoritosFilmes extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int ID_CURSO_LOADER_FILMES = 0;

    private Menu menu;
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
        recyclerViewFilmes.setLayoutManager(new LinearLayoutManager(this));


    }


    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        CursorLoader cursorLoader = new CursorLoader(this, ContentProviderPopcorn.ENDERECO_FILMES, BdTableFilmes.TODAS_COLUNAS, null, null, BdTableFilmes.CAMPO_NOME);
        System.out.print("CURSOR LOADER: " + cursorLoader);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        adaptadorFilmes.setCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        adaptadorFilmes.setCursor(null);
    }

    //Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_favoritos, menu);
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemEditar:
                Intent intent2 = new Intent(this, AlterarMovie.class);
                // intent2.putExtra(ID_FILME, adaptadorFilmes.getFilmeSelecionada().getId_filme());
                startActivity(intent2);
                return true;

            case R.id.itemEliminar:
                Intent intent3 = new Intent(this, ApagarMovie.class);
                // intent3.putExtra(ID_FILME, adaptadorFilmes.getFilmeSelecionada().getId_filme());
                startActivity(intent3);
                return true;

            case R.id.itemDetalhe:
                Intent intent4 = new Intent(this, DetailActivityMovie.class);
                //intent4.putExtra(ID_FILME, adaptadorFilmes.getFilmeSelecionada().getId_filme());
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
    }
}