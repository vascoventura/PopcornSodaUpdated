package com.example.popcornsoda.ui;

import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import com.example.popcornsoda.BdPopcorn.BdTableAutores;
import com.example.popcornsoda.BdPopcorn.ContentProviderPopcorn;
import com.example.popcornsoda.R;
import com.example.popcornsoda.models.Serie;
import com.google.android.material.snackbar.Snackbar;

public class AddSerie extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int ID_CURSO_LOADER_AUTORES = 0;


    EditText editTextNomeSerie, editTextTipoSerie, editTextClassificacaoSerie, editTextAnoSerie, editTextDescricaoSerie, editTextTemporadas;
    Spinner spinnerAutor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_serie);


        //Botao Voltar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editTextNomeSerie = findViewById(R.id.editText_nome_serie_inserir);
        editTextTipoSerie = (EditText)findViewById(R.id.editText_tipo_serie_inserir);
        spinnerAutor = (Spinner) findViewById(R.id.spinnerAutores);
        editTextClassificacaoSerie = (EditText)findViewById(R.id.editText_classificacao_serie_inserir);
        editTextAnoSerie = (EditText)findViewById(R.id.editText_ano_serie_inserir);
        editTextTemporadas = (EditText) findViewById(R.id.editText_temporadas_serie_inserir);
        editTextDescricaoSerie = (EditText)findViewById(R.id.editText_descricao_serie_inserir);

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

        String nome = editTextNomeSerie.getText().toString();

        if (nome.trim().isEmpty()) {
            editTextNomeSerie.setError("O campo não pode estar vazio!");
            return;
        }

        String tipo = editTextTipoSerie.getText().toString();

        if (tipo.trim().isEmpty()) {
            editTextTipoSerie.setError("O campo não pode estar vazio!");
            return;
        }


        double classificacao;

        String strClassificacao = editTextClassificacaoSerie.getText().toString();

        if (strClassificacao.trim().isEmpty()) {
            editTextClassificacaoSerie.setError("O campo não pode estar vazio!");
            return;
        }

        try {
            classificacao = Double.parseDouble(strClassificacao);
        } catch (NumberFormatException e) {
            editTextClassificacaoSerie.setError("Campo Inválido");
            return;
        }

        int ano;

        String strAno = editTextAnoSerie.getText().toString();

        if (strAno.trim().isEmpty()) {
            editTextAnoSerie.setError("O campo não pode estar vazio!");
            return;
        }

        try {
            ano = Integer.parseInt(strAno);
        } catch (NumberFormatException e) {
            editTextAnoSerie.setError("Campo Inválido");
            return;
        }

        int temporada;

        String strTemporada = editTextTemporadas.getText().toString();

        if (strTemporada.trim().isEmpty()) {
            editTextTemporadas.setError("O campo não pode estar vazio!");
            return;
        }

        try {
            temporada = Integer.parseInt(strTemporada);
        } catch (NumberFormatException e) {
            editTextTemporadas.setError("Campo Inválido");
            return;
        }


        String descricao = editTextDescricaoSerie.getText().toString();

        if (tipo.trim().isEmpty()) {
            editTextDescricaoSerie.setError("O campo não pode estar vazio!");
            return;
        }

        long idAutor = spinnerAutor.getSelectedItemId();

        // guardar os dados
        Serie serie = new Serie();

        serie.setNome_serie(nome);
        serie.setTipo_serie(tipo);
        serie.setAutor_serie(idAutor);
        serie.setClassificacao_serie(classificacao);
        serie.setAno_serie(ano);
        serie.setTemporadas(temporada);
        serie.setDescricao_serie(descricao);


        try {
            getContentResolver().insert(ContentProviderPopcorn.ENDERECO_SERIES, serie.getContentValues());

            Toast.makeText(this, "Serie guardada com sucesso", Toast.LENGTH_SHORT).show();
            finish();
        } catch (Exception e) {
            Snackbar.make(
                    editTextNomeSerie,
                    "Erro ao guardar série",
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
    }


    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        mostraAutoresSpinner(null);
    }

}