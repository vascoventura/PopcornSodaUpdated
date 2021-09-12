package com.example.popcornsoda.ui;

import androidx.appcompat.app.AppCompatActivity;

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

import com.example.popcornsoda.BdPopcorn.BdTableSeries;
import com.example.popcornsoda.BdPopcorn.ContentProviderPopcorn;
import com.example.popcornsoda.R;
import com.example.popcornsoda.models.Serie;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DetailActivtySerie extends AppCompatActivity {

    private Uri enderecoSerie;
    private Serie serie = null;
    private boolean favSerie;
    private boolean vistoSerie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_activty_serie);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        TextView textViewNomeSerie = (TextView) findViewById(R.id.detail_serie_nome);
        TextView textViewTipoSerie = (TextView) findViewById(R.id.detail_serie_tipo);
        TextView textViewAutorSerie = (TextView) findViewById(R.id.detail_serie_autor);
        TextView textViewClassificacaoSerie = (TextView) findViewById(R.id.detail_serie_classificacao);
        TextView textViewAnoSerie = (TextView) findViewById(R.id.detail_serie_ano);
        TextView textViewTemporadasSerie = (TextView) findViewById(R.id.detail_serie_temporadas);
        TextView textView1 = (TextView) findViewById(R.id.textView9);
        TextView textViewDescricaoSerie = (TextView) findViewById(R.id.detail_serie_descricao);
        FloatingActionButton favorito = findViewById(R.id.botao_favorito);
        FloatingActionButton visto = findViewById(R.id.botao_visto);
        FloatingActionButton trailer = findViewById(R.id.detail_serie_trailer);
        ImageView imageViewCapa = findViewById(R.id.imageViewCapaSerie);
        ImageView imageViewFundo = findViewById(R.id.imageViewFundoSerie);


        textViewNomeSerie.setAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_animation));
        textViewTipoSerie.setAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_animation));
        textViewAutorSerie.setAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_animation));
        textViewClassificacaoSerie.setAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_animation));
        textViewAnoSerie.setAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_animation));
        textViewDescricaoSerie.setAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_animation));
        favorito.setAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_animation));
        visto.setAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_animation));
        textViewTemporadasSerie.setAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_animation));
        textView1.setAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_animation));
        trailer.setAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_animation));


        //Botoes

        favorito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Adicionado aos Favoritos", Toast.LENGTH_LONG).show();
            }
        });

        visto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Marcado como Visto", Toast.LENGTH_LONG).show();
            }
        });



        Intent intent = getIntent();
        long idSerie = intent.getLongExtra(Series.ID_SERIE, -1);
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

        serie = Serie.fromCursor(cursor);

        textViewNomeSerie.setText(serie.getNome_serie());
        textViewTipoSerie.setText(serie.getNomeCategoria());
        textViewAutorSerie.setText(serie.getNomeAutor());
        textViewClassificacaoSerie.setText(String.valueOf(serie.getClassificacao_serie()));
        textViewAnoSerie.setText(String.valueOf(serie.getAno_serie()));
        textViewDescricaoSerie.setText(serie.getDescricao_serie());
        textViewTemporadasSerie.setText(String.valueOf(serie.getTemporadas()));

        //Conversoes de imagens
        byte[] serieImageCapa = serie.getFoto_capa_serie();
        Bitmap bitmap_serieImageCapa = BitmapFactory.decodeByteArray(serieImageCapa, 0, serieImageCapa.length);
        //imageViewCapaAutor.setImageBitmap(Bitmap.createScaledBitmap(bitmap_autorImage, imageViewCapaAutor.getWidth(), imageViewCapaAutor.getHeight(), false));
        imageViewCapa.setImageBitmap(bitmap_serieImageCapa);

        byte[] serieImageFundo = serie.getFoto_fundo_serie();
        Bitmap bitmap_serieImageFundo = BitmapFactory.decodeByteArray(serieImageFundo, 0, serieImageFundo.length);
        //imageViewFundoAutor.setImageBitmap(Bitmap.createScaledBitmap(bitmap_autorImageFundo, imageViewFundoAutor.getWidth(), imageViewFundoAutor.getHeight(), false));
        imageViewFundo.setImageBitmap(bitmap_serieImageFundo);

        favorito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favSerie = serie.isFavorito_serie();
                long id = serie.getId_serie();
                System.out.println("estado Favorito: " + favSerie);
                System.out.println("id_autor: " + id);

                atualizaFavorito(favSerie);
            }

            private int atualizaFavorito(boolean estadoAtual) {
                serie.setFavorito_serie(!estadoAtual);
                try {
                    ContentValues values = new ContentValues();
                    values.put(BdTableSeries.CAMPO_FAVORITO, !estadoAtual);
                    getContentResolver().update(enderecoSerie, serie.getContentValues(), null, null);

                    if (favSerie == false) {
                        Toast.makeText(DetailActivtySerie.this, "Adicionado aos Favoritos", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(DetailActivtySerie.this, "Removido dos Favoritos", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    Toast.makeText(DetailActivtySerie.this, "Não Foi Possível Realizar a Operação", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                return 0;
            }
        });

        visto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vistoSerie = serie.isVisto_serie();
                long id = serie.getId_serie();
                System.out.println("estado Visto: " + vistoSerie);
                System.out.println("id_autor: " + id);

                atualizaVisto(vistoSerie);
            }

            private int atualizaVisto(boolean estadoAtual) {
                serie.setVisto_serie(!estadoAtual);
                try {
                    ContentValues values = new ContentValues();
                    values.put(BdTableSeries.CAMPO_VISTO, !estadoAtual);
                    getContentResolver().update(enderecoSerie, serie.getContentValues(), null, null);

                    if (vistoSerie == false) {
                        Toast.makeText(DetailActivtySerie.this, "Adicionado aos Vistos", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(DetailActivtySerie.this, "Removido dos Vistos", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    Toast.makeText(DetailActivtySerie.this, "Não Foi Possível Realizar a Operação", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                return 0;
            }
        });

        getSupportActionBar().setTitle(serie.getNome_serie());
    }
}