package com.example.popcornsoda.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.popcornsoda.BdPopcorn.BdTableFilmes;
import com.example.popcornsoda.BdPopcorn.ContentProviderPopcorn;
import com.example.popcornsoda.R;
import com.example.popcornsoda.models.Movie;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

public class TrailerFilme extends AppCompatActivity {

    private YouTubePlayerView youTubePlayerView;
    private TextView textViewNome;
    private TextView textViewCategoria;
    private TextView textViewAutorFilme;
    private TextView textViewClassificacao;
    private TextView textViewAno;
    private TextView textViewDescricao;

    private ImageView imagemCapa;

    Uri enderecoFilme;
    Movie movie = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trailer_movie);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        YouTubePlayerView youTubePlayerView = findViewById(R.id.YoutubePlayer_trailer_filme);
        getLifecycle().addObserver(youTubePlayerView);

        textViewNome = findViewById(R.id.detail_autor_nome_trailer);
        textViewCategoria = findViewById(R.id.detail_movie_tipo_trailer);
        textViewAutorFilme = findViewById(R.id.detail_movie_autor_trailer);
        textViewClassificacao = findViewById(R.id.detail_movie_classificacao_trailer);
        textViewAno = findViewById(R.id.detail_movie_ano_trailer);
        textViewDescricao = findViewById(R.id.detail_movie_descricao_trailer);
        imagemCapa = findViewById(R.id.imageViewCapaFilme_trailer);


        Intent intent = getIntent();
        final long idFilme = intent.getLongExtra(DetailActivityMovie.ID_FILME, -1);
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
        textViewCategoria.setText(movie.getNomeCategoria());
        textViewAutorFilme.setText(movie.getNomeAutor());
        textViewClassificacao.setText(String.valueOf(movie.getClassificacao_filme()));
        textViewAno.setText(String.valueOf(movie.getAno_filme()));
        textViewDescricao.setText(movie.getDescricao_filme());

        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(YouTubePlayer youTubePlayer) {
                String videoId = movie.getLink_trailer_filme();
                youTubePlayer.loadVideo(videoId, 0);
            }
        });


        //Conversoes de imagens

        byte[] filmeImageCapa = movie.getFoto_capa_filme();
        Bitmap bitmap_filmeImage = BitmapFactory.decodeByteArray(filmeImageCapa, 0, filmeImageCapa.length);
        //imageViewCapaAutor.setImageBitmap(Bitmap.createScaledBitmap(bitmap_autorImage, imageViewCapaAutor.getWidth(), imageViewCapaAutor.getHeight(), false));
        imagemCapa.setImageBitmap(bitmap_filmeImage);

        getSupportActionBar().setTitle(movie.getNome_filme());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}