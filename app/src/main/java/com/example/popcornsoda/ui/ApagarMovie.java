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

import com.example.popcornsoda.BdPopcorn.BdTableFilmes;
import com.example.popcornsoda.BdPopcorn.ContentProviderPopcorn;
import com.example.popcornsoda.R;
import com.example.popcornsoda.models.Movie;

public class ApagarMovie extends AppCompatActivity {
    private Uri enderecoFilmeApagar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apagar_movie);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView textViewNome = (TextView) findViewById(R.id.textViewNomeFilme_eliminar);
        TextView textViewAutorFilme = (TextView) findViewById(R.id.textViewAutorFilme_eliminar);
        TextView textViewCategoria = (TextView) findViewById(R.id.textViewCategoriaFilme_eliminar);
        TextView textViewClassificacao = (TextView) findViewById(R.id.textViewClassificacaoFilme_eliminar);
        TextView textViewAno = (TextView) findViewById(R.id.textViewAnoFilme_eliminar);
        TextView textViewDescricao = (TextView) findViewById(R.id.textViewDescricaoFilme_eliminar);
        TextView textViewTrailer = (TextView) findViewById(R.id.textViewTrailerFilme_eliminar);
        ImageView imageCapaFilme = (ImageView) findViewById(R.id.foto_capa_eliminar_filme);
        ImageView imageFundoFilme = (ImageView) findViewById(R.id.foto_fundo_eliminar_filme);
        Switch botaoFavorito = (Switch) findViewById(R.id.botao_favorito_eliminar_filme);
        Switch botaoVisto = (Switch) findViewById(R.id.botao_visto_eliminar_filme);


        Intent intent = getIntent();
        long idFilme = intent.getLongExtra(Filmes.ID_FILME, -1);
        if (idFilme == -1) {
            Toast.makeText(this, "Erro: não foi possível excluir o filme", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        enderecoFilmeApagar = Uri.withAppendedPath(ContentProviderPopcorn.ENDERECO_FILMES, String.valueOf(idFilme));

        Cursor cursor = getContentResolver().query(enderecoFilmeApagar, BdTableFilmes.TODAS_COLUNAS, null, null, null);

        if (!cursor.moveToNext()) {
            Toast.makeText(this, "Erro: não foi possível excluir o filme", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        Movie movie = Movie.fromCursor(cursor);

        textViewNome.setText(movie.getNome_filme());
        textViewAutorFilme.setText(movie.getNomeAutor());
        textViewCategoria.setText(movie.getNomeCategoria());
        textViewClassificacao.setText(String.valueOf(movie.getClassificacao_filme()));
        textViewAno.setText(String.valueOf(movie.getAno_filme()));
        textViewDescricao.setText(movie.getDescricao_filme());
        textViewTrailer.setText(movie.getLink_trailer_filme());
        botaoFavorito.setChecked(movie.isFavorito_filme());
        botaoVisto.setChecked(movie.isVisto_filme());


        byte[] filmeImageCapaByte = movie.getFoto_capa_filme();
        Bitmap bitmap_filmeImage = BitmapFactory.decodeByteArray(filmeImageCapaByte, 0, filmeImageCapaByte.length);
        imageCapaFilme.setImageBitmap(bitmap_filmeImage);

        byte[] filmeImageFundoByte = movie.getFoto_fundo_filme();
        Bitmap bitmap_filmeImageFundo = BitmapFactory.decodeByteArray(filmeImageFundoByte, 0, filmeImageFundoByte.length);
        imageFundoFilme.setImageBitmap(bitmap_filmeImageFundo);

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
        int filmesApagados = getContentResolver().delete(enderecoFilmeApagar, null, null);

        if (filmesApagados == 1) {
            Toast.makeText(this, "Filme excluido com sucesso", Toast.LENGTH_LONG).show();
            finish();
        } else {
            Toast.makeText(this, "Erro: Não foi possível excluir o filme", Toast.LENGTH_LONG).show();
        }
    }
}
