package com.example.popcornsoda.ui;


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

import com.example.popcornsoda.BdPopcorn.BdTableFilmes;
import com.example.popcornsoda.BdPopcorn.ContentProviderPopcorn;
import com.example.popcornsoda.R;
import com.example.popcornsoda.models.Movie;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DetailActivityMovie extends AppCompatActivity {

    public static final String ID_FILME = "ID_FILME";

    private Uri enderecoFilme;
    private Movie movie = null;
    private boolean favFilme;
    private boolean vistoFilme;
    boolean click_botao_visto;
    boolean click_botao_favorito;
    ContentValues values;
    private Menu menu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

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
                favFilme = movie.isFavorito_filme();
                click_botao_favorito = true;
                if(click_botao_favorito) {
                    atualizaFavorito(favFilme);
                }
            }

            private boolean atualizaFavorito(boolean estadoAtual) {
                boolean estadoContrario = !estadoAtual;
                if(estadoAtual == true){
                    try {
                        values.put(BdTableFilmes.CAMPO_FAVORITO, estadoContrario);
                        String[] whereArgs = {"1"};
                        getContentResolver().update(enderecoFilme, values, BdTableFilmes.CAMPO_FAVORITO + "=?", whereArgs);
                        Toast.makeText(DetailActivityMovie.this, "Removido dos Favoritos", Toast.LENGTH_SHORT).show();
                        System.out.println("Estado quando true:" + favFilme);
                        movie.setFavorito_filme(false);
                        click_botao_favorito = false;
                    }catch (Exception e) {
                        Toast.makeText(DetailActivityMovie.this, "Não Foi Possível Realizar a Operação", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                } else if(estadoAtual == false){
                    try {
                        values.put(BdTableFilmes.CAMPO_FAVORITO, estadoContrario);
                        String[] whereArgs = {"0"};
                        getContentResolver().update(enderecoFilme, values, BdTableFilmes.CAMPO_FAVORITO + "=?", whereArgs);
                        Toast.makeText(DetailActivityMovie.this, "Adicionado aos Favoritos", Toast.LENGTH_SHORT).show();
                        System.out.println("Estado quando false:" + favFilme);
                        movie.setFavorito_filme(true);
                        click_botao_favorito = false;
                    }catch (Exception e) {
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
                vistoFilme = movie.isVisto_filme();
                long id = movie.getId_filme();
                System.out.println("estado Visto: " + vistoFilme);
                System.out.println("id_autor: " + id);

                vistoFilme = movie.isVisto_filme();
                click_botao_visto = true;
                if(click_botao_visto){
                    atualizaVisto(vistoFilme);
                }

            }

            private boolean atualizaVisto(boolean estadoAtual) {
                boolean estadoContrario = !estadoAtual;
                if(estadoAtual == true){
                    try {
                        values.put(BdTableFilmes.CAMPO_VISTO, estadoContrario);
                        String[] whereArgs = {"1"};
                        getContentResolver().update(enderecoFilme, values, BdTableFilmes.CAMPO_VISTO + "=?", whereArgs);
                        Toast.makeText(DetailActivityMovie.this, "Adicionado aos Vistos", Toast.LENGTH_SHORT).show();
                        System.out.println("Estado quando true:" + favFilme);
                        movie.setVisto_filme(estadoContrario);
                        click_botao_visto = false;
                    }catch (Exception e) {
                        Toast.makeText(DetailActivityMovie.this, "Não Foi Possível Realizar a Operação", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }else if(estadoAtual == false){
                    try {
                        values.put(BdTableFilmes.CAMPO_VISTO, estadoContrario);
                        String[] whereArgs = {"0"};
                        getContentResolver().update(enderecoFilme, values, BdTableFilmes.CAMPO_VISTO + "=?", whereArgs);
                        Toast.makeText(DetailActivityMovie.this, "Removido dos Vistos", Toast.LENGTH_SHORT).show();
                        System.out.println("Estado quando false:" + vistoFilme);
                        movie.setVisto_filme(estadoContrario);
                        click_botao_visto = false;

                    }catch (Exception e) {
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

