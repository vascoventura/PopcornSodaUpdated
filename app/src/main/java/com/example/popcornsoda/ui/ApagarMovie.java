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

        TextView textViewNome = (TextView) findViewById(R.id.textViewNomeFilme);
        TextView textViewTipo = (TextView) findViewById(R.id.textViewCategoriaFilme);
        TextView textViewAutorFilme = (TextView) findViewById(R.id.textViewAutorFilme);
        TextView textViewClassificacao = (TextView) findViewById(R.id.textViewClassificacaoFilme);
        TextView textViewAno = (TextView) findViewById(R.id.textViewAnoFilme);
        TextView textViewDescricao = (TextView) findViewById(R.id.textViewDescricaoFilme);

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
        textViewClassificacao.setText(String.valueOf(movie.getClassificacao_filme()));
        textViewAno.setText(String.valueOf(movie.getAno_filme()));
        textViewDescricao.setText(movie.getDescricao_filme());
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
