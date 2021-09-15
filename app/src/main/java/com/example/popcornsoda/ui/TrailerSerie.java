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

import com.example.popcornsoda.BdPopcorn.BdTableSeries;
import com.example.popcornsoda.BdPopcorn.ContentProviderPopcorn;
import com.example.popcornsoda.R;
import com.example.popcornsoda.models.Serie;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

public class TrailerSerie extends AppCompatActivity {

    private TextView textViewNome;
    private TextView textViewCategoria;
    private TextView textViewAutorFilme;
    private TextView textViewClassificacao;
    private TextView textViewAno;
    private TextView textViewDescricao;

    private ImageView imagemCapa;

    private Uri enderecoSerie;

    private Serie serie;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trailer_serie);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        YouTubePlayerView youTubePlayerView = findViewById(R.id.YoutubePlayer_trailer_serie);
        getLifecycle().addObserver(youTubePlayerView);

        textViewNome = findViewById(R.id.detail_serie_autor_nome_trailer);
        textViewCategoria = findViewById(R.id.detail_serie_tipo_trailer);
        textViewAutorFilme = findViewById(R.id.detail_serie_autor_trailer);
        textViewClassificacao = findViewById(R.id.detail_serie_classificacao_trailer);
        textViewAno = findViewById(R.id.detail_movie_ano_trailer);
        textViewDescricao = findViewById(R.id.detail_serie_descricao_trailer);
        imagemCapa = findViewById(R.id.imageViewCapaSerie_trailer);


        Intent intent = getIntent();
        final long idSerie = intent.getLongExtra(DetailActivitySerie.ID_SERIE, -1);
        if (idSerie == -1) {
            Toast.makeText(this, "Erro: não foi possível abrir a página do conteúdo", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        enderecoSerie = Uri.withAppendedPath(ContentProviderPopcorn.ENDERECO_SERIES, String.valueOf(idSerie));

        Cursor cursor = getContentResolver().query(enderecoSerie, BdTableSeries.TODAS_COLUNAS, null, null, null);

        if (!cursor.moveToNext()) {
            Toast.makeText(this, "Erro: não foi possível abrir a página do conteúdo", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        serie = serie.fromCursor(cursor);

        textViewNome.setText(serie.getNome_serie());
        textViewCategoria.setText(serie.getNomeCategoria());
        textViewAutorFilme.setText(serie.getNomeAutor());
        textViewClassificacao.setText(String.valueOf(serie.getClassificacao_serie()));
        textViewAno.setText(String.valueOf(serie.getAno_serie()));
        textViewDescricao.setText(serie.getDescricao_serie());

        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(YouTubePlayer youTubePlayer) {
                String videoId = serie.getLink_trailer_serie();
                youTubePlayer.loadVideo(videoId, 0);
            }
        });


        //Conversoes de imagens

        byte[] filmeImageCapa = serie.getFoto_capa_serie();
        Bitmap bitmap_filmeImage = BitmapFactory.decodeByteArray(filmeImageCapa, 0, filmeImageCapa.length);
        imagemCapa.setImageBitmap(bitmap_filmeImage);

        getSupportActionBar().setTitle(serie.getNome_serie());
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