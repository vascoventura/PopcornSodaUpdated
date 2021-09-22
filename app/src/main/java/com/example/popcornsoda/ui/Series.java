package com.example.popcornsoda.ui;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.viewpager.widget.ViewPager;

import com.example.popcornsoda.BdPopcorn.BdTableFilmes;
import com.example.popcornsoda.BdPopcorn.BdTableSeries;
import com.example.popcornsoda.BdPopcorn.ContentProviderPopcorn;
import com.example.popcornsoda.R;
import com.example.popcornsoda.adapters.AdaptadorLVSeries;
import com.example.popcornsoda.adapters.SerieGridAdapter;
import com.example.popcornsoda.adapters.Slider;
import com.example.popcornsoda.adapters.SliderPageAdapter;
import com.example.popcornsoda.adapters.myDbAdapter;
import com.example.popcornsoda.models.Movie;
import com.example.popcornsoda.models.Serie;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Series extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final String ID_SERIE = "ID_SERIE";
    public static final int ID_CURSO_LOADER_SERIES = 0;

    private AdaptadorLVSeries adaptadorSeries;

    private Cursor cursor_series;

    private List<Slider> itensSlider;
    private ViewPager sliderpager;

    private ArrayList<Serie> serieList;
    private SerieGridAdapter serieGridAdapter;

    private myDbAdapter helper;
    private Serie filme;

    private long id;
    private Serie serie;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_series);


        getSupportLoaderManager().initLoader(ID_CURSO_LOADER_SERIES, null, this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /*RecyclerView recyclerViewSeries = (RecyclerView) findViewById(R.id.recyclerViewSeries);
        adaptadorSeries = new AdaptadorLVSeries(this);
        recyclerViewSeries.setAdapter(adaptadorSeries);
        recyclerViewSeries.setLayoutManager( new LinearLayoutManager(this) );
        */


        //Slider

        sliderpager = findViewById(R.id.slider_pager_series);
        TabLayout indicator = findViewById(R.id.indicator_series);
        itensSlider = new ArrayList<>();
        itensSlider.add(new Slider(R.drawable.venom, "Venom:", "A não perder"));
        itensSlider.add(new Slider(R.drawable.suicide_squad, "Suicide Squad:","Filmagens da sequela começam já em setembro!"));
        itensSlider.add(new Slider(R.drawable.green_book, "Green Book:", "Uma história que nos inspira"));

        SliderPageAdapter adapter = new SliderPageAdapter(this, itensSlider);
        sliderpager.setAdapter(adapter);

        //Temporizador do Slider
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new SliderTime(), 4000, 6000);

        indicator.setupWithViewPager(sliderpager, true);

        //Grid View
        GridView gridSerieList = (GridView) findViewById(R.id.grid_view_series);
        serieList = new ArrayList<>();
        serieGridAdapter = new SerieGridAdapter(this, R.layout.item_serie_grid_view, serieList);
        gridSerieList.setAdapter(serieGridAdapter);

        helper = new myDbAdapter(this);
        cursor_series = helper.getSeries();

        while(cursor_series.moveToNext()){
            id = cursor_series.getLong(0);

            @SuppressLint("Range") String nome = cursor_series.getString(
                    cursor_series.getColumnIndex(BdTableSeries.CAMPO_NOME)
            );

            @SuppressLint("Range") long categoria = cursor_series.getLong(
                    cursor_series.getColumnIndex(BdTableSeries.CAMPO_CATEGORIA)
            );

            @SuppressLint("Range") long autor = cursor_series.getLong(
                    cursor_series.getColumnIndex(BdTableSeries.CAMPO_AUTOR)
            );

            @SuppressLint("Range") double classificacao = cursor_series.getDouble(
                    cursor_series.getColumnIndex(BdTableSeries.CAMPO_CLASSIFICACAO)
            );

            @SuppressLint("Range") int ano = cursor_series.getInt(
                    cursor_series.getColumnIndex(BdTableSeries.CAMPO_ANO)
            );

            @SuppressLint("Range") String descricao = cursor_series.getString(
                    cursor_series.getColumnIndex(BdTableSeries.CAMPO_DESCRICAO)
            );
            @SuppressLint("Range") byte[] foto_capa = cursor_series.getBlob(
                    cursor_series.getColumnIndex(BdTableSeries.CAMPO_CAPA)
            );

            @SuppressLint("Range") byte[] foto_fundo = cursor_series.getBlob(
                    cursor_series.getColumnIndex(BdTableSeries.CAMPO_FUNDO)
            );
            @SuppressLint("Range") String link = cursor_series.getString(
                    cursor_series.getColumnIndex(BdTableSeries.CAMPO_LINK)
            );

            @SuppressLint("Range") String nomeAutor = cursor_series.getString(
                    cursor_series.getColumnIndex(BdTableSeries.ALIAS_NOME_AUTOR)
            );

            @SuppressLint("Range") String nomeCategoria = cursor_series.getString(
                    cursor_series.getColumnIndex(BdTableSeries.ALIAS_NOME_CATEGORIA)
            );

            @SuppressLint("Range") boolean visto = Boolean.parseBoolean(cursor_series.getString(cursor_series.getColumnIndex(BdTableSeries.CAMPO_VISTO)));

            @SuppressLint("Range") boolean favorito = Boolean.parseBoolean(cursor_series.getString(cursor_series.getColumnIndex(BdTableSeries.CAMPO_FAVORITO)));

            serie = new Serie(nome,foto_capa, id);
            serieList.add(serie);

        }

        serieGridAdapter.notifyDataSetChanged();




        gridSerieList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id1) {

                //Toast.makeText(getApplicationContext(), "You click on position:"+position, Toast.LENGTH_SHORT).show();

                long idSerie = id1 + 1;//Ter atenção aos ids dos filmes;
                System.out.println("ID Da serie: " + idSerie);
                System.out.println("POSICAO CLICKADA: " + position);
                Context context = view.getContext();

                Intent intent = new Intent();
                intent.setClass(context, DetailActivitySerie.class);
                intent.putExtra(ID_SERIE, idSerie);
                context.startActivity(intent);
            }
        });
    }
    @Override
    protected void onResume() {
        getSupportLoaderManager().restartLoader(ID_CURSO_LOADER_SERIES, null, this);
        super.onResume();
    }

    //Menu

    private Menu menu;

    public void atualizaOpcoesMenu() {
        if(adaptadorSeries.isSelecao()){
            menu.findItem(R.id.itemEditar).setVisible(true);
            menu.findItem(R.id.itemEliminar).setVisible(true);
            menu.findItem(R.id.itemDetalhe).setVisible(true);
            menu.findItem(R.id.itemAdicionar).setVisible(false);
            menu.findItem(R.id.itemFavorito).setVisible(false);
            menu.findItem(R.id.itemVisto).setVisible(false);
        }else{
            menu.findItem(R.id.itemEditar).setVisible(false);
            menu.findItem(R.id.itemEliminar).setVisible(false);
            menu.findItem(R.id.itemDetalhe).setVisible(false);
            menu.findItem(R.id.itemAdicionar).setVisible(true);
            menu.findItem(R.id.itemFavorito).setVisible(true);
            menu.findItem(R.id.itemVisto).setVisible(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tabelas, menu);
        this.menu = menu;
        return true;
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
                Intent intent4 = new Intent(this, DetailActivitySerie.class);
                intent4.putExtra(ID_SERIE, adaptadorSeries.getSerieSelecionada().getId_serie());
                startActivity(intent4);
                return true;

            case R.id.itemFavorito:
                Intent intent5 = new Intent(this, FavoritosSeries.class);
               // intent5.putExtra(ID_SERIE, adaptadorSeries.getSerieSelecionada().getId_serie());
                startActivity(intent5);
                return true;

            case R.id.itemVisto:
                Intent intent6 = new Intent(this, VistosSeries.class);
                //intent6.putExtra(ID_SERIE, adaptadorSeries.getSerieSelecionada().getId_serie());
                startActivity(intent6);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle bundle) {
        CursorLoader cursorLoader = new CursorLoader(this, ContentProviderPopcorn.ENDERECO_SERIES, BdTableSeries.TODAS_COLUNAS, null, null, BdTableSeries.CAMPO_NOME);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
//        adaptadorSeries.setCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
    //    adaptadorSeries.setCursor(null);

    }

    class SliderTime extends TimerTask {
        @Override
        public void run() {
            Series.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (sliderpager.getCurrentItem() < itensSlider.size() - 1) {
                        sliderpager.setCurrentItem(sliderpager.getCurrentItem() + 1);
                    } else {
                        sliderpager.setCurrentItem(0);
                    }
                }
            });
        }
    }
}