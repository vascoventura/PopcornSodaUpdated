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
import androidx.viewpager.widget.ViewPager;

import com.example.popcornsoda.BdPopcorn.BdTableFilmes;
import com.example.popcornsoda.BdPopcorn.ContentProviderPopcorn;
import com.example.popcornsoda.R;
import com.example.popcornsoda.adapters.AdaptadorLVFilmes;
import com.example.popcornsoda.adapters.AdaptadorLVSeries;
import com.example.popcornsoda.adapters.Slider;
import com.example.popcornsoda.adapters.SliderPageAdapter;
import com.example.popcornsoda.models.Movie;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class Filmes extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final String ID_FILME = "ID_FILME" ;
    private static final int ID_CURSO_LOADER_FILMES = 0;


    private AdaptadorLVFilmes adaptadorFilmes;

    private List<Slider> itensSlider;
    private ViewPager sliderpager;


    //Iniciar a atividade
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filmes);
        Toolbar toolbar = findViewById(R.id.toolbar);

        getSupportLoaderManager().initLoader(ID_CURSO_LOADER_FILMES, null, this);


        //Botao Voltar

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //Slider
        sliderpager = findViewById(R.id.slider_pager);
        TabLayout indicator = findViewById(R.id.indicator);
        RecyclerView moviesRV = findViewById(R.id.Rv_movieList);

        //Lista Vertical
        RecyclerView recyclerViewFilmes = (RecyclerView) findViewById(R.id.lista_filmes_vertical);
        adaptadorFilmes = new AdaptadorLVFilmes(this);
        recyclerViewFilmes.setAdapter(adaptadorFilmes);
        recyclerViewFilmes.setLayoutManager( new LinearLayoutManager(this) );

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

       /* //Lista Horizontal
        List<Movie> itensMovies = new ArrayList<>();
        itensMovies.add(new Movie("Glass", R.drawable.glass, R.drawable.glass_cover));
        itensMovies.add(new Movie("Venom", R.drawable.venom, R.drawable.venom_cover));
        itensMovies.add(new Movie("The Upside", R.drawable.the_upside, R.drawable.the_upside_cover));
        itensMovies.add(new Movie("Green Book", R.drawable.green_book, R.drawable.green_book_cover));
        itensMovies.add(new Movie("The Prodigy", R.drawable.the_prodigy, R.drawable.the_prodigy_cover));
        MovieAdapter movieAdapter = new MovieAdapter(this, itensMovies, (MovieItensClickListener) this);
        moviesRV.setAdapter(movieAdapter);
        moviesRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        //Lista Vertical*/
    }

    @Override
    protected void onResume() {
        getSupportLoaderManager().restartLoader(ID_CURSO_LOADER_FILMES, null, this);
        super.onResume();
    }

    private Menu menu;

    public void atualizaOpcoesMenu() {
        Movie filme = adaptadorFilmes.getFilmeSelecionada();

        boolean mostraAlterarEliminar = (filme != null);

        menu.findItem(R.id.itemEditar).setVisible(mostraAlterarEliminar);
        menu.findItem(R.id.itemEliminar).setVisible(mostraAlterarEliminar);
        menu.findItem(R.id.itemDetalhe).setVisible(mostraAlterarEliminar);
        menu.findItem(R.id.itemAdicionar).setVisible(false);
    }

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



    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader (int id, @Nullable Bundle args){
        CursorLoader cursorLoader = new CursorLoader(this, ContentProviderPopcorn.ENDERECO_FILMES, BdTableFilmes.TODAS_COLUNAS, null, null, BdTableFilmes.CAMPO_NOME);
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

    /*@SuppressLint("NewApi")
    @Override
    public void onMovieClick(Movie filme, TextView nomeFilme, TextView tipoFilme, TextView autorFilme, TextView classificacaoFilme, TextView anoFilme) {
        // aqui vamos mandaremos a descriçao do conteudo com detalhe noutra atividade
        Intent intent = new Intent(this, DetailActivityMovie.class);
        //send movie information to detailActivity
        intent.putExtra("title", filme.getNome_filme());
        intent.putExtra(, filme.getNome_filme());
        intent.putExtra("imgURL", filme.getFoto_capa_filme());
        intent.putExtra("imgCover", filme.getFoto_fundo_filme());
        intent.putExtra("decricao_filme", filme.getDescricao_filme());
        //tambem criamos a animaçao entre duas atividades
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Filmes.this, movieImageView, "sharedElement");
        startActivity(intent, options.toBundle());
        Toast.makeText(this, filme.getNome_filme(),Toast.LENGTH_SHORT).show();
    }*/

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