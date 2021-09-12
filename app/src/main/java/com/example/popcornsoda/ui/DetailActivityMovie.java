package com.example.popcornsoda.ui;


import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.popcornsoda.BdPopcorn.BdTableAutores;
import com.example.popcornsoda.BdPopcorn.BdTableFilmes;
import com.example.popcornsoda.BdPopcorn.ContentProviderPopcorn;
import com.example.popcornsoda.R;
import com.example.popcornsoda.models.Movie;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DetailActivityMovie extends AppCompatActivity {


    private Uri enderecoFilme;
    private Movie movie = null;
    private boolean favFilme;
    private boolean vistoFilme;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        TextView textViewNome = findViewById(R.id.detail_autor_nome);
        TextView textViewTipo = findViewById(R.id.detail_movie_tipo);
        TextView textViewAutorFilme = findViewById(R.id.detail_movie_autor);
        TextView textViewClassificacao = findViewById(R.id.detail_movie_classificacao);
        TextView textViewAno = findViewById(R.id.detail_movie_ano);
        TextView textViewDescricao = findViewById(R.id.detail_movie_descricao);
        ImageView imageViewCapa = findViewById(R.id.imageViewCapaFilme);
        ImageView imageViewFundo = findViewById(R.id.imageViewFundoFilme);
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
        imageViewCapa.setAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_animation));
        imageViewFundo.setAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_animation));



        Intent intent = getIntent();
        final long idFilme = intent.getLongExtra(Filmes.ID_FILME, -1);
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

        movie = Movie.fromCursor(cursor);

        textViewNome.setText(movie.getNome_filme());
        textViewTipo.setText(movie.getNomeCategoria());
        textViewAutorFilme.setText(movie.getNomeAutor());
        textViewClassificacao.setText(String.valueOf(movie.getClassificacao_filme()));
        textViewAno.setText(String.valueOf(movie.getAno_filme()));
        textViewDescricao.setText(movie.getDescricao_filme());


        //Conversoes de imagens

        byte[] filmeImageCapa = movie.getFoto_capa_filme();
        Bitmap bitmap_filmeImage = BitmapFactory.decodeByteArray(filmeImageCapa, 0, filmeImageCapa.length);
        //imageViewCapaAutor.setImageBitmap(Bitmap.createScaledBitmap(bitmap_autorImage, imageViewCapaAutor.getWidth(), imageViewCapaAutor.getHeight(), false));
        imageViewCapa.setImageBitmap(bitmap_filmeImage);

        byte[] filmeImageFundo = movie.getFoto_fundo_filme();
        Bitmap bitmap_filmeImageFundo = BitmapFactory.decodeByteArray(filmeImageFundo, 0, filmeImageFundo.length);
        //imageViewFundoAutor.setImageBitmap(Bitmap.createScaledBitmap(bitmap_autorImageFundo, imageViewFundoAutor.getWidth(), imageViewFundoAutor.getHeight(), false));
        imageViewFundo.setImageBitmap(bitmap_filmeImageFundo);


        getSupportActionBar().setTitle(movie.getNome_filme());

        favorito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favFilme = movie.isFavorito_filme();
                long id = movie.getId_filme();
                System.out.println("estado Favorito: " + favFilme);
                System.out.println("id_autor: " + id);

                atualizaFavorito(favFilme);
            }

            private int atualizaFavorito(boolean estadoAtual) {
                movie.setFavorito_filme(!estadoAtual);
                try {
                    ContentValues values = new ContentValues();
                    values.put(BdTableFilmes.CAMPO_FAVORITO, !estadoAtual);
                    getContentResolver().update(enderecoFilme, movie.getContentValues(), null, null);

                    if (favFilme == false) {
                        Toast.makeText(DetailActivityMovie.this, "Adicionado aos Favoritos", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(DetailActivityMovie.this, "Removido dos Favoritos", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    Toast.makeText(DetailActivityMovie.this, "Não Foi Possível Realizar a Operação", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                return 0;
            }
        });

        visto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vistoFilme = movie.isVisto_filme();
                long id = movie.getId_filme();
                System.out.println("estado Visto: " + vistoFilme);
                System.out.println("id_autor: " + id);

                atualizaVisto(vistoFilme);
            }

            private int atualizaVisto(boolean estadoAtual) {
                movie.setVisto_filme(!estadoAtual);
                try {
                    ContentValues values = new ContentValues();
                    values.put(BdTableFilmes.CAMPO_VISTO, !estadoAtual);
                    getContentResolver().update(enderecoFilme, movie.getContentValues(), null, null);

                    if (vistoFilme == false) {
                        Toast.makeText(DetailActivityMovie.this, "Adicionado aos Vistos", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(DetailActivityMovie.this, "Removido dos Vistos", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    Toast.makeText(DetailActivityMovie.this, "Não Foi Possível Realizar a Operação", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                return 0;
            }
        });

    }
}

