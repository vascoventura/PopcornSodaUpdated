package com.example.popcornsoda.ui;


import com.example.popcornsoda.BdPopcorn.BdTableFilmes;
import com.example.popcornsoda.BdPopcorn.ContentProviderPopcorn;
import com.example.popcornsoda.models.Movie;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.example.popcornsoda.R;

public class DetailActivityMovie extends AppCompatActivity {


    private Uri enderecoFilme;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        TextView textViewNome = (TextView) findViewById(R.id.detail_movie_nome);
        TextView textViewTipo = (TextView) findViewById(R.id.detail_movie_tipo);
        TextView textViewAutorFilme = (TextView) findViewById(R.id.detail_movie_autor);
        TextView textViewClassificacao = (TextView) findViewById(R.id.detail_movie_classificacao);
        TextView textViewAno = (TextView) findViewById(R.id.detail_movie_ano);
        TextView textViewDescricao = (TextView) findViewById(R.id.detail_movie_descricao);
        FloatingActionButton favorito = findViewById(R.id.botao_favorito);
        FloatingActionButton visto = findViewById(R.id.botao_visto);
        FloatingActionButton trailer = findViewById(R.id.detail_movie_trailer);

        textViewNome.setAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_animation));
        textViewTipo.setAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_animation));
        textViewAutorFilme.setAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_animation));
        textViewClassificacao.setAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_animation));
        textViewAno.setAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_animation));
        textViewDescricao.setAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_animation));
        favorito.setAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_animation));
        visto.setAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_animation));
        trailer.setAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_animation));







        //Botoes

        favorito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adicionaFavorito();
            }
        });

        visto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Marcado como Visto", Toast.LENGTH_LONG).show();
            }
        });



        Intent intent = getIntent();
        long idFilme = intent.getLongExtra(Filmes.ID_FILME, -1);
        if (idFilme == -1) {
            Toast.makeText(this, "Erro: não foi possível abrir a página do conteúdo", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        enderecoFilme = Uri.withAppendedPath(ContentProviderPopcorn.ENDERECO_FILMES, String.valueOf(idFilme));

        Cursor cursor = getContentResolver().query(enderecoFilme, BdTableFilmes.TODAS_COLUNAS, null, null, null);

        if (!cursor.moveToNext()) {
            Toast.makeText(this, "Erro: não foi possível abrir a página do conteúdo", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        Movie movie = Movie.fromCursor(cursor);

        textViewNome.setText(movie.getNome_filme());
        textViewTipo.setText(movie.getTipo_filme());
        textViewAutorFilme.setText(movie.getNomeAutor());
        textViewClassificacao.setText(String.valueOf(movie.getClassificacao_filme()));
        textViewAno.setText(String.valueOf(movie.getAno_filme()));
        textViewDescricao.setText(movie.getDescricao_filme());

        getSupportActionBar().setTitle(movie.getNome_filme());

    }

    private boolean adicionaFavorito() {
        boolean estado_favorito = true;

        if(estado_favorito = false){
            Toast.makeText(this, "Adicionado aos favoritos!", Toast.LENGTH_SHORT).show();
            estado_favorito = true;
            return true;
        }else{
            Toast.makeText(this, "Removido dos favoritos", Toast.LENGTH_SHORT).show();
            estado_favorito = false;
            return false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_conteudo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.itemEditarConteudo) {
            //todo:alterar;
            return true;
        } else if (id == R.id.itemEliminarConteudo) {
            //todo: eliminar
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}