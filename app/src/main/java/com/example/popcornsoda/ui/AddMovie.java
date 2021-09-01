package com.example.popcornsoda.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.popcornsoda.BdPopcorn.BdTableAutores;
import com.example.popcornsoda.BdPopcorn.BdTableCategorias;
import com.example.popcornsoda.BdPopcorn.ContentProviderPopcorn;
import com.example.popcornsoda.R;
import com.example.popcornsoda.models.Movie;
import com.google.android.material.snackbar.Snackbar;

public class AddMovie extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>  {

    private static final int ID_CURSO_LOADER_AUTORES = 0;

    private EditText editTextNomeFilme;
    private EditText editTextClassificacaoFilme;
    private EditText editTextAnoFilme;
    private EditText editTextDescricaoFilme;
    private Spinner spinnerAutor;
    private Spinner spinnerCategoria;
    private EditText editTextLinkFilme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_movie);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editTextNomeFilme = findViewById(R.id.editText_nome_filme_inserir);
        editTextClassificacaoFilme = findViewById(R.id.editText_classificacao_filme_inserir);
        editTextAnoFilme = findViewById(R.id.editText_ano_filme_inserir);
        editTextDescricaoFilme = findViewById(R.id.editText_descricao_filme_inserir);
        editTextLinkFilme = findViewById(R.id.editTextLink_inserir);

        spinnerAutor = findViewById(R.id.spinnerCategorias_series_inserir);
        spinnerCategoria = findViewById(R.id.spinnerCategorias);

        getSupportLoaderManager().initLoader(ID_CURSO_LOADER_AUTORES, null, this);
    }


    @Override
    protected void onResume() {
        getSupportLoaderManager().restartLoader(ID_CURSO_LOADER_AUTORES, null, this);

        super.onResume();
    }

    private void mostraAutoresSpinner(Cursor cursorAutores) {
        SimpleCursorAdapter adaptadorAutores = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_1,
                cursorAutores,
                new String[]{BdTableAutores.CAMPO_NOME},
                new int[]{android.R.id.text1}
        );
        spinnerAutor.setAdapter(adaptadorAutores);
    }

    private void mostraCategoriasSpinner(Cursor cursorCategorias) {
        SimpleCursorAdapter adaptadorCategorias = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_1,
                cursorCategorias,
                new String[]{BdTableCategorias.CAMPO_NOME},
                new int[]{android.R.id.text1}
        );
        spinnerAutor.setAdapter(adaptadorCategorias);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_guardar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.item_guardar) {
            guardar();
            return true;
        } else if (id == R.id.item_cancelar) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void guardar() {

        String nome = editTextNomeFilme.getText().toString();

        if (nome.trim().isEmpty()) {
            editTextNomeFilme.setError("O campo não pode estar vazio!");
            return;
        }



        String strClassificacao = editTextClassificacaoFilme.getText().toString();

        if (strClassificacao.trim().isEmpty()) {
            editTextClassificacaoFilme.setError("O campo não pode estar vazio!");
            return;
        }

        double classificacao;
        try {
            classificacao = Double.parseDouble(strClassificacao);
        } catch (NumberFormatException e) {
            editTextClassificacaoFilme.setError("Campo Inválido");
            return;
        }

        int ano;

        String strAno = editTextAnoFilme.getText().toString();

        if (strAno.trim().isEmpty()) {
            editTextAnoFilme.setError("O campo não pode estar vazio!");
            return;
        }

        try {
            ano = Integer.parseInt(strAno);
        } catch (NumberFormatException e) {
            editTextAnoFilme.setError("Campo Inválido");
            return;
        }



        String descricao = editTextDescricaoFilme.getText().toString();

        if (descricao.trim().isEmpty()) {
            editTextDescricaoFilme.setError("O campo não pode estar vazio!");
            return;
        }

        String link = editTextLinkFilme.getText().toString();

        if (link.trim().isEmpty()) {
            editTextLinkFilme.setError("O campo não pode estar vazio!");
            return;
        }

        long idAutor = spinnerAutor.getSelectedItemId();
        long idCategoria = spinnerCategoria.getSelectedItemId();

        // guardar os dados
        Movie filme = new Movie();

        filme.setNome_filme(nome);
        filme.setCategoria_filme(idCategoria);
        filme.setAutor_filme(idAutor);
        filme.setClassificacao_filme(classificacao);
        filme.setAno_filme(ano);
        filme.setDescricao_filme(descricao);
        filme.setFoto_capa_filme(null);
        filme.setFoto_fundo_filme(null);
        filme.setFavorito_filme(false);
        filme.setVisto_filme(false);
        filme.setLink_trailer_filme(link);

        try {
            getContentResolver().insert(ContentProviderPopcorn.ENDERECO_FILMES, filme.getContentValues());

            Toast.makeText(this, "Filme guardado com sucesso", Toast.LENGTH_SHORT).show();
            finish();
        } catch (Exception e) {
            Snackbar.make(
                    editTextNomeFilme,
                    "Erro: Não foi possível criar o filme",
                    Snackbar.LENGTH_LONG)
                    .show();

            e.printStackTrace();
        }
    }


    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        androidx.loader.content.CursorLoader cursorLoader = new androidx.loader.content.CursorLoader(this, ContentProviderPopcorn.ENDERECO_AUTORES, BdTableAutores.TODAS_COLUNAS, null, null, BdTableAutores.CAMPO_NOME
        );
        //androidx.loader.content.CursorLoader cursorLoader1 = new androidx.loader.content.CursorLoader(this, ContentProviderPopcorn.ENDERECO_CATEGORIAS, BdTableCategorias.TODAS_COLUNAS, null, null, BdTableCategorias.CAMPO_NOME);


        return cursorLoader;

    }




    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        mostraAutoresSpinner(data);
        //mostraCategoriasSpinner(data);
    }


    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        mostraAutoresSpinner(null);
        mostraCategoriasSpinner(null);
    }

}
