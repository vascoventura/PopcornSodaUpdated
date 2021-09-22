package com.example.popcornsoda.ui;


import static com.example.popcornsoda.ui.DetailActivityAutor.ID_AUTOR;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.popcornsoda.BdPopcorn.BdTableAutores;
import com.example.popcornsoda.BdPopcorn.BdTableCategorias;
import com.example.popcornsoda.BdPopcorn.BdTableFilmes;
import com.example.popcornsoda.BdPopcorn.BdTableSeries;
import com.example.popcornsoda.BdPopcorn.ContentProviderPopcorn;
import com.example.popcornsoda.R;
import com.example.popcornsoda.adapters.myDbAdapter;
import com.example.popcornsoda.models.Autor;
import com.example.popcornsoda.models.Movie;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class DetailActivityMovie extends AppCompatActivity {

    public static final String ID_FILME = "ID_FILME";

    private Uri enderecoFilme;
    private Uri enderecoAutor;

    private Movie movie = null;
    private Autor autor = null;

    ContentValues values;

    private Menu menu;

    private int favFilme;
    private int vistoFilme;

    private Cursor autoresCursor;
    private myDbAdapter myDbAdapter;

    private ArrayList<Autor> autorArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        autorArrayList = new ArrayList<Autor>();


        TextView textViewNome = findViewById(R.id.detail_autor_nome);
        TextView textViewTipo = findViewById(R.id.detail_movie_tipo);
        TextView textViewAutorFilme = findViewById(R.id.detail_movie_autor);
        TextView textViewClassificacao = findViewById(R.id.detail_movie_classificacao);
        TextView textViewAno = findViewById(R.id.detail_movie_ano);
        TextView textViewDescricao = findViewById(R.id.detail_movie_descricao);
        TextView textViewNomeAutor = findViewById(R.id.textViewNomeAutor);
        ImageView imageViewCapaAutor = findViewById(R.id.imageViewAutor);
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
        textViewNomeAutor.setAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_animation));
        imageViewCapaAutor.setAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_animation));


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


        values = new ContentValues();


        //Conversoes de imagens

        byte[] filmeImageCapa = movie.getFoto_capa_filme();
        Bitmap bitmap_filmeImage = BitmapFactory.decodeByteArray(filmeImageCapa, 0, filmeImageCapa.length);
        imageViewCapa.setImageBitmap(bitmap_filmeImage);

        byte[] filmeImageFundo = movie.getFoto_fundo_filme();
        Bitmap bitmap_filmeImageFundo = BitmapFactory.decodeByteArray(filmeImageFundo, 0, filmeImageFundo.length);
        imageViewFundo.setImageBitmap(bitmap_filmeImageFundo);


        favorito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                atualizaFavorito();
            }

            private boolean atualizaFavorito() {
                favFilme = movie.getFavorito_numerico();
                if (favFilme == 1) {
                    try {
                        values.put(BdTableFilmes.CAMPO_FAVORITO, 0);
                        getContentResolver().update(enderecoFilme, values, BdTableFilmes.CAMPO_FAVORITO + "=?", null);
                        Toast.makeText(DetailActivityMovie.this, "Removido dos Favoritos", Toast.LENGTH_SHORT).show();
                        movie.setFavorito_numerico(0);
                    } catch (Exception e) {
                        Toast.makeText(DetailActivityMovie.this, "Não Foi Possível Realizar a Operação", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                } else if (favFilme == 0) {
                    try {
                        values.put(BdTableFilmes.CAMPO_FAVORITO, 1);
                        getContentResolver().update(enderecoFilme, values, BdTableFilmes.CAMPO_FAVORITO + "=?", null);
                        Toast.makeText(DetailActivityMovie.this, "Adicionado aos Favoritos", Toast.LENGTH_SHORT).show();
                        movie.setFavorito_numerico(1);
                    } catch (Exception e) {
                        Toast.makeText(DetailActivityMovie.this, "Não Foi Possível Realizar a Operação", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }


                return true;
            }
        });

        visto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atualizaVisto();
            }

            private boolean atualizaVisto() {
                vistoFilme = movie.getVisto_numerico();
                if (vistoFilme == 1) {
                    try {
                        values.put(BdTableFilmes.CAMPO_VISTO, 0);
                        getContentResolver().update(enderecoFilme, values, BdTableFilmes.CAMPO_VISTO + "=?", null);
                        Toast.makeText(DetailActivityMovie.this, "Removido dos Vistos", Toast.LENGTH_SHORT).show();
                        movie.setVisto_numerico(0);
                    } catch (Exception e) {
                        Toast.makeText(DetailActivityMovie.this, "Não Foi Possível Realizar a Operação", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                } else if (vistoFilme == 0) {
                    try {
                        values.put(BdTableFilmes.CAMPO_VISTO, 1);
                        getContentResolver().update(enderecoFilme, values, BdTableFilmes.CAMPO_VISTO + "=?", null);
                        Toast.makeText(DetailActivityMovie.this, "Adicionado aos Vistos", Toast.LENGTH_SHORT).show();
                        System.out.println("Estado quando false:" + vistoFilme);
                        movie.setVisto_numerico(1);

                    } catch (Exception e) {
                        Toast.makeText(DetailActivityMovie.this, "Não Foi Possível Realizar a Operação", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
                return true;
            }
        });

        trailer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                openTrailer();
            }

            private void openTrailer() {
                try {
                    Intent intent = new Intent(DetailActivityMovie.this, TrailerFilme.class);
                    intent.putExtra(ID_FILME, movie.getId_filme());
                    startActivity(intent);

                } catch (Exception e) {
                    Toast.makeText(DetailActivityMovie.this, "Não Foi Possível Realizar a Operação", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });

        getSupportActionBar().setTitle(movie.getNome_filme());

        myDbAdapter = new myDbAdapter(this);
        autoresCursor = myDbAdapter.getAutor(movie.getAutor_filme());


        while (autoresCursor.moveToNext()) {
            @SuppressLint("Range") long id = autoresCursor.getLong(0);

            @SuppressLint("Range") String nome_autor = autoresCursor.getString(
                    autoresCursor.getColumnIndex(BdTableAutores.CAMPO_NOME)
            );

            int ano = autoresCursor.getInt(
                    autoresCursor.getColumnIndex(BdTableAutores.CAMPO_ANONASCIMENTO)
            );

            @SuppressLint("Range") String nacionalidade = autoresCursor.getString(
                    autoresCursor.getColumnIndex(BdTableAutores.CAMPO_NACIONALIDADE)
            );

            @SuppressLint("Range") String descricao = autoresCursor.getString(
                    autoresCursor.getColumnIndex(BdTableAutores.CAMPO_DESCRICAO)
            );

            @SuppressLint("Range") byte[] foto_capa = autoresCursor.getBlob(
                    autoresCursor.getColumnIndex(BdTableAutores.CAMPO_FOTO_CAPA)
            );

            @SuppressLint("Range") byte[] foto_fundo = autoresCursor.getBlob(
                    autoresCursor.getColumnIndex(BdTableAutores.CAMPO_FOTO_FUNDO)
            );


            @SuppressLint("Range") boolean favorito1 = Boolean.parseBoolean(autoresCursor.getString(autoresCursor.getColumnIndex(BdTableAutores.CAMPO_FAVORITO)));

            int favorito2 = autoresCursor.getInt(autoresCursor.getColumnIndex(BdTableAutores.CAMPO_FAVORITO));



            System.out.println("ID_GUARDADO AUTOR: " + nome_autor + " ID: " + id);

            System.out.println("");
            System.out.println("");
            System.out.println("");

            autor = new Autor(id, nome_autor, ano, nacionalidade, descricao, foto_capa, foto_fundo, favorito1);

            textViewNomeAutor.setText(nome_autor);

            byte[] autorImageCapa = foto_capa;
            Bitmap bitmap_AutorImage = BitmapFactory.decodeByteArray(autorImageCapa, 0, autorImageCapa.length);
            imageViewCapaAutor.setImageBitmap(bitmap_AutorImage);

            imageViewCapaAutor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(DetailActivityMovie.this, DetailActivityAutor.class);
                    intent.putExtra(ID_AUTOR, autor.getId());
                    startActivity(intent);
                }
            });

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_conteudo, menu);
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemEditarConteudo:
                Intent intent1 = new Intent(this, AlterarMovie.class);
                intent1.putExtra(ID_FILME, movie.getId_filme());
                startActivity(intent1);
                return true;

            case R.id.itemEliminarConteudo:
                Intent intent2 = new Intent(this, ApagarMovie.class);
                intent2.putExtra(ID_FILME, movie.getId_filme());
                startActivity(intent2);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

