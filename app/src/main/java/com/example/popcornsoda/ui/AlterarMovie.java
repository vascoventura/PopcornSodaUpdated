package com.example.popcornsoda.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.popcornsoda.BdPopcorn.BdTableAutores;
import com.example.popcornsoda.BdPopcorn.BdTableFilmes;
import com.example.popcornsoda.BdPopcorn.ContentProviderPopcorn;
import com.example.popcornsoda.R;
import com.example.popcornsoda.models.Movie;
import com.google.android.material.snackbar.Snackbar;

public class AlterarMovie extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int ID_CURSO_LOADER_AUTORES = 0;

    private EditText editTextNomeFilme;
    private Spinner spinnerAutores, spinnerCategorias;
    private EditText editTextClassificacaoFilme;
    private EditText editTextAnoFilme;
    private EditText editTextDescricaoFilme, editTextLink;


    private Movie filme = null;

    private boolean autoresCarregados = false;
    private boolean autorAtualizado = false;

    private Uri enderecoFilmeEditar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alterar_movie);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editTextNomeFilme = findViewById(R.id.editText_nome_filme_alterar);
        spinnerAutores = findViewById(R.id.spinnerCategorias_series_inserir);
        editTextClassificacaoFilme = findViewById(R.id.editText_classificacao_filme_alterar);
        editTextAnoFilme =  findViewById(R.id.editText_ano_filme_alterar);
        editTextDescricaoFilme = findViewById(R.id.editText_descricao_filme_alterar);

        getSupportLoaderManager().initLoader(ID_CURSO_LOADER_AUTORES, null, this);


        Intent intent = getIntent();

        long idFilme = intent.getLongExtra(Filmes.ID_FILME, -1);

        if (idFilme == -1){
        Toast.makeText(this, "Erro: Não foi possível reconhecer o Filme", Toast.LENGTH_LONG).show();
        finish();
        return;
        }

        enderecoFilmeEditar = Uri.withAppendedPath(ContentProviderPopcorn.ENDERECO_FILMES, String.valueOf(idFilme));

        Cursor cursor = getContentResolver().query(enderecoFilmeEditar, BdTableFilmes.TODAS_COLUNAS, null, null, null);

        if(!cursor.moveToNext()){
            Toast.makeText(this, "Erro: Não foi possivel reconhecer o filme", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        filme = Movie.fromCursor(cursor);

        editTextNomeFilme.setText(filme.getNome_filme());
        editTextClassificacaoFilme.setText(String.valueOf(filme.getClassificacao_filme()));
        editTextAnoFilme.setText(String.valueOf(filme.getAno_filme()));
        editTextDescricaoFilme.setText(filme.getDescricao_filme());

        atualizaAutorSelecionado();

    }

    private void atualizaAutorSelecionado() {
        if(!autoresCarregados) return;
        if(autorAtualizado) return;

        for (int i = 0; i < spinnerAutores.getCount(); i++){
            if(spinnerAutores.getItemIdAtPosition(i) == filme.getAutor_filme()){
                spinnerAutores.setSelection(i);
                break;
            }
        }

        autorAtualizado = true;
    }

    @Override
    protected void onResume() {
        getSupportLoaderManager().restartLoader(ID_CURSO_LOADER_AUTORES, null, this);

        super.onResume();
    }

    private void mostraAutoresSpinner(Cursor cursorCategorias) {
        SimpleCursorAdapter adaptadorAutores = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_1,
                cursorCategorias,
                new String[]{BdTableAutores.CAMPO_NOME},
                new int[]{android.R.id.text1}
        );
        spinnerAutores.setAdapter(adaptadorAutores);
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

        String link = editTextLink.getText().toString();

        if (link.trim().isEmpty()) {
            editTextLink.setError("O campo não pode estar vazio!");
            return;
        }

        long idAutor = spinnerAutores.getSelectedItemId();
        long idCategoria = spinnerCategorias.getSelectedItemId();

        // guardar os dados
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
            getContentResolver().update(enderecoFilmeEditar, filme.getContentValues(), null, null);

            Toast.makeText(this, "Filme guardado com sucesso", Toast.LENGTH_SHORT).show();
            finish();
        } catch (Exception e) {
            Snackbar.make(
                    editTextNomeFilme,
                    "Erro ao guardar filme",
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

        return cursorLoader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        mostraAutoresSpinner(data);
        autoresCarregados = true;
        atualizaAutorSelecionado();

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        autoresCarregados = false;
        autorAtualizado = false;
        mostraAutoresSpinner(null);

    }
}
