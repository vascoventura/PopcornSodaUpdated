package com.example.popcornsoda.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.popcornsoda.BdPopcorn.BdTableAutores;
import com.example.popcornsoda.BdPopcorn.BdTableSeries;
import com.example.popcornsoda.BdPopcorn.ContentProviderPopcorn;
import com.example.popcornsoda.R;
import com.example.popcornsoda.models.Autor;
import com.example.popcornsoda.models.Serie;
import com.example.popcornsoda.ui.Autores;
import com.example.popcornsoda.ui.Series;

public class ApagarSerie extends AppCompatActivity {
    private Uri enderecoSerieApagar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apagar_serie);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView textViewNome = (TextView) findViewById(R.id.textViewNomeSerie);
        TextView textViewTipo = (TextView) findViewById(R.id.textViewTipoSerie);
        TextView textViewAutorSerie = (TextView) findViewById(R.id.textViewAutorSerie);
        TextView textViewClassificacao = (TextView) findViewById(R.id.textViewClassificacaoSerie);
        TextView textViewAno = (TextView) findViewById(R.id.textViewAnoSerie);
        TextView textViewTemporadas = (TextView) findViewById(R.id.textViewTemporadasSerie);
        TextView textViewDescricao = (TextView) findViewById(R.id.textViewDescricaoSerie);

        Intent intent = getIntent();
        long idSerie = intent.getLongExtra(Series.ID_SERIE, -1);
        if (idSerie == -1) {
            Toast.makeText(this, "Erro: não foi possível excluir a série", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        enderecoSerieApagar = Uri.withAppendedPath(ContentProviderPopcorn.ENDERECO_SERIES, String.valueOf(idSerie));

        Cursor cursor = getContentResolver().query(enderecoSerieApagar, BdTableSeries.TODAS_COLUNAS, null, null, null);

        if (!cursor.moveToNext()) {
            Toast.makeText(this, "Erro: não foi possível excluir a série", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        Serie serie = Serie.fromCursor(cursor);


        textViewNome.setText(serie.getNome_serie());
        textViewTipo.setText(serie.getTipo_serie());
        textViewAutorSerie.setText(serie.getNomeAutor());
        textViewClassificacao.setText(String.valueOf(serie.getClassificacao_serie()));
        textViewAno.setText(String.valueOf(serie.getAno_serie()));
        textViewTemporadas.setText(String.valueOf(serie.getTemporadas()));
        textViewDescricao.setText(serie.getDescricao_serie());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_eliminar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_eliminar) {
            eliminar();
            return true;
        } else if (id == R.id.action_cancelar) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void eliminar() {
        int seriesApagadas = getContentResolver().delete(enderecoSerieApagar, null, null);

        if (seriesApagadas == 1) {
            Toast.makeText(this, "Serie excluida com sucesso", Toast.LENGTH_LONG).show();
            finish();
        } else {
            Toast.makeText(this, "Erro: Não foi possível excluir a série", Toast.LENGTH_LONG).show();
        }
    }
}