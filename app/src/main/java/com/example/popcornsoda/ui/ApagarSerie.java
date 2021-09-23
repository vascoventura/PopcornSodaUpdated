package com.example.popcornsoda.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.popcornsoda.BdPopcorn.BdTableSeries;
import com.example.popcornsoda.BdPopcorn.ContentProviderPopcorn;
import com.example.popcornsoda.R;
import com.example.popcornsoda.models.Serie;

public class ApagarSerie extends AppCompatActivity {
    private Uri enderecoSerieApagar;
    private boolean favorito;
    private boolean visto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apagar_serie);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView textViewNome = (TextView) findViewById(R.id.textViewNomeSerie_eliminar);
        TextView textViewCategoria = (TextView) findViewById(R.id.textViewCategoriaSerie_eliminar);
        TextView textViewAutorSerie = (TextView) findViewById(R.id.textViewAutorSerie_eliminar);
        TextView textViewClassificacao = (TextView) findViewById(R.id.textViewClassificacaoSerie_eliminar);
        TextView textViewAno = (TextView) findViewById(R.id.textViewAnoSerie_eliminar);
        TextView textViewTemporadas = (TextView) findViewById(R.id.textViewTemporadasSerie_eliminar);
        TextView textViewDescricao = (TextView) findViewById(R.id.textViewDescricaoSerie_eliminar);
        TextView textViewTrailer = (TextView) findViewById(R.id.textViewTrailerSerie_eliminar);
        ImageView imageCapaFilme = (ImageView) findViewById(R.id.foto_capa_eliminar_serie);
        ImageView imageFundoFilme = (ImageView) findViewById(R.id.foto_fundo_eliminar_serie);
        Switch botaoFavorito = (Switch) findViewById(R.id.botao_favorito_eliminar_serie);
        Switch botaoVisto = (Switch) findViewById(R.id.botao_visto_eliminar_filme);

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
        textViewAutorSerie.setText(serie.getNomeAutor());
        textViewCategoria.setText(serie.getNomeCategoria());
        textViewClassificacao.setText(String.valueOf(serie.getClassificacao_serie()));
        textViewAno.setText(String.valueOf(serie.getAno_serie()));
        textViewTemporadas.setText(String.valueOf(serie.getTemporadas()));
        textViewDescricao.setText(serie.getDescricao_serie());
        textViewTrailer.setText(serie.getLink_trailer_serie());
        botaoFavorito.setChecked(serie.isFavorito_serie());
        botaoVisto.setChecked(serie.isVisto_serie());

        int favorito_num = serie.getFavorito_numerico();
        if(favorito_num == 1){
            favorito = true;
        } else if (favorito_num == 0){
            favorito = false;
        }
        int visto_num = serie.getVisto_numerico();
        if(visto_num == 0){
            visto = false;
        } else if(visto_num == 1){
            visto = true;
        }

        botaoFavorito.setChecked(favorito);
        botaoVisto.setChecked(visto);



        byte[] serieImageCapaByte = serie.getFoto_capa_serie();
        Bitmap bitmap_serieImage = BitmapFactory.decodeByteArray(serieImageCapaByte, 0, serieImageCapaByte.length);
        imageCapaFilme.setImageBitmap(bitmap_serieImage);

        byte[] serieImageFundoByte = serie.getFoto_fundo_serie();
        Bitmap bitmap_serieImageFundo = BitmapFactory.decodeByteArray(serieImageFundoByte, 0, serieImageFundoByte.length);
        imageFundoFilme.setImageBitmap(bitmap_serieImageFundo);


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