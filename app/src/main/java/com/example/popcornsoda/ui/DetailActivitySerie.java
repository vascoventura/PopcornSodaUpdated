package com.example.popcornsoda.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.example.popcornsoda.BdPopcorn.BdTableSeries;
import com.example.popcornsoda.BdPopcorn.ContentProviderPopcorn;
import com.example.popcornsoda.R;
import com.example.popcornsoda.models.Serie;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DetailActivitySerie extends AppCompatActivity {

    public static final String ID_SERIE = "ID_SERIE";

    private Uri enderecoSerie;
    private Serie serie = null;
    private int favSerie;
    private int vistoSerie;

    boolean click_botao_visto;
    boolean click_botao_favorito;
    ContentValues values;

    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_serie);

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

        values = new ContentValues();

        favorito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                atualizaFavorito();
            }

            private boolean atualizaFavorito() {
                favSerie = serie.getFavorito_numerico();
                if(favSerie == 1){
                    try {
                        values.put(BdTableSeries.CAMPO_FAVORITO, 0);
                        getContentResolver().update(enderecoSerie, values, BdTableSeries.CAMPO_FAVORITO + "=?", null);
                        Toast.makeText(DetailActivitySerie.this, "Removido dos Favoritos", Toast.LENGTH_SHORT).show();
                        serie.setFavorito_numerico(0);
                    }catch (Exception e) {
                        Toast.makeText(DetailActivitySerie.this, "Não Foi Possível Realizar a Operação", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                } else if(favSerie == 0){
                    try {
                        values.put(BdTableSeries.CAMPO_FAVORITO, 1);
                        getContentResolver().update(enderecoSerie, values, BdTableSeries.CAMPO_FAVORITO + "=?", null);
                        Toast.makeText(DetailActivitySerie.this, "Adicionado aos Favoritos", Toast.LENGTH_SHORT).show();
                        serie.setFavorito_numerico(1);
                    }catch (Exception e) {
                        Toast.makeText(DetailActivitySerie.this, "Não Foi Possível Realizar a Operação", Toast.LENGTH_SHORT).show();
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
                vistoSerie = serie.getVisto_numerico();
                if(vistoSerie == 1){
                    try {
                        values.put(BdTableSeries.CAMPO_VISTO, 0);
                        getContentResolver().update(enderecoSerie, values, BdTableSeries.CAMPO_VISTO + "=?", null);
                        Toast.makeText(DetailActivitySerie.this, "Removido dos Vistos", Toast.LENGTH_SHORT).show();
                        serie.setVisto_numerico(0);
                    }catch (Exception e) {
                        Toast.makeText(DetailActivitySerie.this, "Não Foi Possível Realizar a Operação", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }else if(vistoSerie == 0){
                    try {
                        values.put(BdTableSeries.CAMPO_VISTO, 1);
                        getContentResolver().update(enderecoSerie, values, BdTableSeries.CAMPO_VISTO + "=?", null);
                        Toast.makeText(DetailActivitySerie.this, "Adicionado aos Vistos", Toast.LENGTH_SHORT).show();
                        System.out.println("Estado quando false:" + vistoSerie);
                        serie.setVisto_numerico(1);

                    }catch (Exception e) {
                        Toast.makeText(DetailActivitySerie.this, "Não Foi Possível Realizar a Operação", Toast.LENGTH_SHORT).show();
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
                    Intent intent = new Intent(DetailActivitySerie.this, TrailerSerie.class);
                    intent.putExtra(ID_SERIE, serie.getId_serie());
                    startActivity(intent);

                } catch (Exception e) {
                    Toast.makeText(DetailActivitySerie.this, "Não Foi Possível Realizar a Operação", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });


        getSupportActionBar().setTitle(serie.getNome_serie());
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
                Intent intent1 = new Intent(this, AlterarSerie.class);
                intent1.putExtra(ID_SERIE, serie.getId_serie());
                startActivity(intent1);
                return true;

            case R.id.itemEliminarConteudo:
                Intent intent2 = new Intent(this, ApagarSerie.class);
                intent2.putExtra(ID_SERIE, serie.getId_serie());
                startActivity(intent2);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}