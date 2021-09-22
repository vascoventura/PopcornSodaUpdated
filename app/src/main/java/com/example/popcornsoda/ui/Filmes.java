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
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.SimpleCursorAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.popcornsoda.BdPopcorn.BdTableAutores;
import com.example.popcornsoda.BdPopcorn.BdTableFilmes;
import com.example.popcornsoda.BdPopcorn.ContentProviderPopcorn;
import com.example.popcornsoda.R;
import com.example.popcornsoda.adapters.AdaptadorLVFilmes;
import com.example.popcornsoda.adapters.MovieGridAdapter;
import com.example.popcornsoda.adapters.Slider;
import com.example.popcornsoda.adapters.SliderPageAdapter;
import com.example.popcornsoda.adapters.myDbAdapter;
import com.example.popcornsoda.models.Movie;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class Filmes extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final String ID_FILME = "ID_FILME";
    private static final int ID_CURSO_LOADER_FILMES = 0;

    private Cursor cursor_filmes;


    private List<Slider> itensSlider;
    private ViewPager sliderpager;


    private ArrayList<Movie> movieList;
    private MovieGridAdapter movieGridAdapter;

    private myDbAdapter helper;
    private Movie filme;

    private long id;


    //Iniciar a atividade
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filmes);


        getSupportLoaderManager().initLoader(ID_CURSO_LOADER_FILMES, null, this);


        //Botao Voltar

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //Slider
        sliderpager = findViewById(R.id.slider_pager);
        TabLayout indicator = findViewById(R.id.indicator);
       //RecyclerView moviesRV = findViewById(R.id.Rv_movieList);


        //SLIDER
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

        /*//Lista Horizontal
        List<Movie> itensMovies = new ArrayList<>();
        itensMovies.add(new Movie("Glass", R.drawable.glass, R.drawable.glass_cover));
        itensMovies.add(new Movie("Venom", R.drawable.venom, R.drawable.venom_cover));
        itensMovies.add(new Movie("The Upside", R.drawable.the_upside, R.drawable.the_upside_cover));
        itensMovies.add(new Movie("Green Book", R.drawable.green_book, R.drawable.green_book_cover));
        itensMovies.add(new Movie("The Prodigy", R.drawable.the_prodigy, R.drawable.the_prodigy_cover));
        //MovieAdapter movieAdapter = new MovieAdapter(this, itensMovies, (MovieItensClickListener) this);
        //moviesRV.setAdapter(movieAdapter);
        //moviesRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        //Lista Vertical*/

        //Grid View
        GridView gridMovieList = (GridView) findViewById(R.id.grid_view_filmes);
        movieList = new ArrayList<>();
        movieGridAdapter = new MovieGridAdapter(this, R.layout.item_movie_grid_view, movieList);
        gridMovieList.setAdapter(movieGridAdapter);

        helper = new myDbAdapter(this);
        cursor_filmes = helper.getFilmes();
        while(cursor_filmes.moveToNext()){
            id = cursor_filmes.getLong(cursor_filmes.getColumnIndex(BdTableFilmes._ID)
            );

            @SuppressLint("Range") String nome = cursor_filmes.getString(
                    cursor_filmes.getColumnIndex(BdTableFilmes.CAMPO_NOME)
            );

            @SuppressLint("Range") long categoria = cursor_filmes.getLong(
                    cursor_filmes.getColumnIndex(BdTableFilmes.CAMPO_CATEGORIA)
            );

            @SuppressLint("Range") long autor = cursor_filmes.getLong(
                    cursor_filmes.getColumnIndex(BdTableFilmes.CAMPO_AUTOR)
            );

            @SuppressLint("Range") double classificacao = cursor_filmes.getDouble(
                    cursor_filmes.getColumnIndex(BdTableFilmes.CAMPO_CLASSIFICACAO)
            );

            @SuppressLint("Range") int ano = cursor_filmes.getInt(
                    cursor_filmes.getColumnIndex(BdTableFilmes.CAMPO_ANO)
            );

            @SuppressLint("Range") String descricao = cursor_filmes.getString(
                    cursor_filmes.getColumnIndex(BdTableFilmes.CAMPO_DESCRICAO)
            );
            @SuppressLint("Range") byte[] foto_capa = cursor_filmes.getBlob(
                    cursor_filmes.getColumnIndex(BdTableFilmes.CAMPO_CAPA)
            );

            @SuppressLint("Range") byte[] foto_fundo = cursor_filmes.getBlob(
                    cursor_filmes.getColumnIndex(BdTableFilmes.CAMPO_FUNDO)
            );
            @SuppressLint("Range") String link = cursor_filmes.getString(
                    cursor_filmes.getColumnIndex(BdTableFilmes.CAMPO_LINK)
            );

            @SuppressLint("Range") String nomeAutor = cursor_filmes.getString(
                    cursor_filmes.getColumnIndex(BdTableFilmes.ALIAS_NOME_AUTOR)
            );

            @SuppressLint("Range") String nomeCategoria = cursor_filmes.getString(
                    cursor_filmes.getColumnIndex(BdTableFilmes.ALIAS_NOME_CATEGORIA)
            );

            @SuppressLint("Range") boolean visto = Boolean.parseBoolean(cursor_filmes.getString(cursor_filmes.getColumnIndex(BdTableFilmes.CAMPO_VISTO)));

            @SuppressLint("Range") boolean favorito = Boolean.parseBoolean(cursor_filmes.getString(cursor_filmes.getColumnIndex(BdTableFilmes.CAMPO_FAVORITO)));

            filme = new Movie(nome,foto_capa, id);
            movieList.add(filme);

        }

        movieGridAdapter.notifyDataSetChanged();




        gridMovieList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id1) {

                //Toast.makeText(getApplicationContext(), "You click on position:"+position, Toast.LENGTH_SHORT).show();

                long idFilme = id1 + 1;//Ter atenção aos ids dos filmes;
                System.out.println("ID DO FILME: " + idFilme);
                System.out.println("POSICAO CLICKADA: " + position);
                Context context = view.getContext();

                Intent intent = new Intent();
                intent.setClass(context, DetailActivityMovie.class);
                intent.putExtra(ID_FILME, idFilme);
                context.startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        getSupportLoaderManager().restartLoader(ID_CURSO_LOADER_FILMES, null, this);
        super.onResume();
    }

    private Menu menu;

    //Menu
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
                Intent intent = new Intent (this, AddMovie.class);
                startActivity(intent);
                return true;

            case R.id.itemFavorito:
                Intent intent5 = new Intent(this, FavoritosFilmes.class);
                startActivity(intent5);
                return true;

            case R.id.itemVisto:
                Intent intent6 = new Intent(this, VistosFilmes.class);
                startActivity(intent6);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
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
         //adaptadorFilmes.setCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader){
        //adaptadorFilmes.setCursor(null);
    }

    class SliderTime extends TimerTask {
        @Override
        public void run() {
            Filmes.this.runOnUiThread(new Runnable() {
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