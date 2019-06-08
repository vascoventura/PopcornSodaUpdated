package com.example.popcornsoda.ui;

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

import com.example.popcornsoda.BdPopcorn.BdTableFilmes;
import com.example.popcornsoda.BdPopcorn.BdTableSeries;
import com.example.popcornsoda.BdPopcorn.ContentProviderPopcorn;
import com.example.popcornsoda.R;
import com.example.popcornsoda.models.Movie;
import com.example.popcornsoda.models.Serie;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DetailActivtySerie extends AppCompatActivity {

    private Uri enderecoSerie;

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

        Serie serie = Serie.fromCursor(cursor);

        textViewNomeSerie.setText(serie.getNome_serie());
        textViewTipoSerie.setText(serie.getTipo_serie());
        textViewAutorSerie.setText(serie.getNomeAutor());
        textViewClassificacaoSerie.setText(String.valueOf(serie.getClassificacao_serie()));
        textViewAnoSerie.setText(String.valueOf(serie.getAno_serie()));
        textViewDescricaoSerie.setText(serie.getDescricao_serie());
        textViewTemporadasSerie.setText(String.valueOf(serie.getTemporadas()));

        getSupportActionBar().setTitle(serie.getNome_serie());
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