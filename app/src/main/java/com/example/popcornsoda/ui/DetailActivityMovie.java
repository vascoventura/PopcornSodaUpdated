package com.example.popcornsoda.ui;


import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.popcornsoda.BdPopcorn.BdTableFilmes;
import com.example.popcornsoda.BdPopcorn.ContentProviderPopcorn;
import com.example.popcornsoda.R;
import com.example.popcornsoda.models.Movie;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DetailActivityMovie extends AppCompatActivity {


    private Uri enderecoFilme;
    private boolean estado_favorito;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        TextView textViewNome = findViewById(R.id.detail_movie_nome);
        TextView textViewTipo =  findViewById(R.id.detail_movie_tipo);
        TextView textViewAutorFilme = findViewById(R.id.detail_movie_autor);
        TextView textViewClassificacao = findViewById(R.id.detail_movie_classificacao);
        TextView textViewAno = findViewById(R.id.detail_movie_ano);
        TextView textViewDescricao = findViewById(R.id.detail_movie_descricao);
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
        textViewTipo.setText(movie.getNomeCategoria());
        textViewAutorFilme.setText(movie.getNomeAutor());
        textViewClassificacao.setText(String.valueOf(movie.getClassificacao_filme()));
        textViewAno.setText(String.valueOf(movie.getAno_filme()));
        textViewDescricao.setText(movie.getDescricao_filme());

        getSupportActionBar().setTitle(movie.getNome_filme());

    }


    private boolean adicionaFavorito() {

        if(!estado_favorito){
           /* try {
                getContentResolver().update(enderecoFilme, filme.getContentValues(), null, null);

                Toast.makeText(this, "Filme guardado com sucesso", Toast.LENGTH_SHORT).show();
                finish();
            } catch (Exception e) {
                Snackbar.make(
                        editTextNomeFilme,
                        "Erro ao guardar filme",
                        Snackbar.LENGTH_LONG)
                        .show();
                e.printStackTrace();
            }*/
            Toast.makeText(this, "Adicionado aos favoritos", Toast.LENGTH_SHORT).show();
            estado_favorito = true;
            return true;
        }else{
            Toast.makeText(this, "Removido dos favoritos", Toast.LENGTH_SHORT).show();
            estado_favorito = false;
            return false;
        }
    }


}