package com.example.popcornsoda.ui;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popcornsoda.BdPopcorn.BdTableSeries;
import com.example.popcornsoda.BdPopcorn.ContentProviderPopcorn;
import com.example.popcornsoda.R;
import com.example.popcornsoda.adapters.AdaptadorLVSeries;
import com.example.popcornsoda.models.Serie;

public class Series extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final String ID_SERIE = "ID_SERIE" ;
    public static final int ID_CURSO_LOADER_SERIES = 0;

    private AdaptadorLVSeries adaptadorSeries;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_series);
        Toolbar toolbar = findViewById(R.id.toolbar);


        getSupportLoaderManager().initLoader(ID_CURSO_LOADER_SERIES, null, this);

        RecyclerView recyclerViewSeries = (RecyclerView) findViewById(R.id.recyclerViewSeries);
        adaptadorSeries = new AdaptadorLVSeries(this);
        recyclerViewSeries.setAdapter(adaptadorSeries);
        recyclerViewSeries.setLayoutManager( new LinearLayoutManager(this) );

    }

    private Menu menu;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tabelas, menu);

        this.menu = menu;

        return true;

    }

    public void atualizaOpcoesMenu() {
        Serie serie = adaptadorSeries.getSerieSelecionada();

        boolean mostraAlterarEliminar = (serie != null);

        menu.findItem(R.id.itemEditar).setVisible(mostraAlterarEliminar);
        menu.findItem(R.id.itemEliminar).setVisible(mostraAlterarEliminar);
        menu.findItem(R.id.itemDetalhe).setVisible(mostraAlterarEliminar);
        menu.findItem(R.id.itemAdicionar).setVisible(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.itemAdicionar:
                Intent intent = new Intent (this, AddSerie.class);
                startActivity(intent);
                return true;

            case R.id.itemEditar:
                Intent intent2 = new Intent(this, AlterarSerie.class);
                intent2.putExtra(ID_SERIE, adaptadorSeries.getSerieSelecionada().getId_serie());
                startActivity(intent2);
                return true;

            case R.id.itemEliminar:
                Intent intent3 = new Intent(this, ApagarSerie.class);
                intent3.putExtra(ID_SERIE, adaptadorSeries.getSerieSelecionada().getId_serie());
                startActivity(intent3);
                return true;

            case R.id.itemDetalhe:
                Intent intent4 = new Intent(this, DetailActivtySerie.class);
                intent4.putExtra(ID_SERIE, adaptadorSeries.getSerieSelecionada().getId_serie());
                startActivity(intent4);
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {
        CursorLoader cursorLoader = new CursorLoader(this, ContentProviderPopcorn.ENDERECO_SERIES, BdTableSeries.TODAS_COLUNAS, null, null, BdTableSeries.CAMPO_NOME);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        adaptadorSeries.setCursor(data);    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        adaptadorSeries.setCursor(null);

    }

    @Override
    protected void onResume() {
        getSupportLoaderManager().restartLoader(ID_CURSO_LOADER_SERIES, null, this);

        super.onResume();
    }
}