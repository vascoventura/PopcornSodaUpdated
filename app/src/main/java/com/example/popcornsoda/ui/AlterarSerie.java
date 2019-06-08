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
import com.example.popcornsoda.BdPopcorn.BdTableSeries;
import com.example.popcornsoda.BdPopcorn.ContentProviderPopcorn;
import com.example.popcornsoda.R;
import com.example.popcornsoda.models.Autor;
import com.example.popcornsoda.models.Movie;
import com.example.popcornsoda.models.Serie;
import com.google.android.material.snackbar.Snackbar;

public class AlterarSerie extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int ID_CURSO_LOADER_AUTORES = 0;

    private EditText editTextNomeSerie;
    private EditText editTextTipoSerie;
    private Spinner spinnerAutores;
    private EditText editTextClassificacaoSerie;
    private EditText editTextAnoSerie;
    private EditText editTextTemporadas;
    private EditText editTextDescricaoSerie;

    private Serie serie = null;

    private boolean autoresCarregados = false;
    private boolean autorAtualizado = false;

    private Uri enderecoSerieEditar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alterar_serie);

        editTextNomeSerie = (EditText) findViewById(R.id.editText_nome_serie_alterar);
        editTextTipoSerie = (EditText) findViewById(R.id.editText_tipo_serie_alterar);
        spinnerAutores = (Spinner) findViewById(R.id.spinnerAutores);
        editTextClassificacaoSerie = (EditText) findViewById(R.id.editText_classificacao_serie_alterar);
        editTextAnoSerie = (EditText) findViewById(R.id.editText_ano_serie_alterar);
        editTextTemporadas = (EditText) findViewById(R.id.editText_temporadas_serie_alterar);
        editTextDescricaoSerie = (EditText) findViewById(R.id.editText_descricao_serie_alterar);

        getSupportLoaderManager().initLoader(ID_CURSO_LOADER_AUTORES, null, this);

        Intent intent = getIntent();

        long idSerie = intent.getLongExtra(Series.ID_SERIE, -1);

        if (idSerie == -1) {
            Toast.makeText(this, "Erro: não foi possível reconhcer a série", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        enderecoSerieEditar = Uri.withAppendedPath(ContentProviderPopcorn.ENDERECO_SERIES, String.valueOf(idSerie));

        Cursor cursor = getContentResolver().query(enderecoSerieEditar, BdTableSeries.TODAS_COLUNAS, null, null, null);

        if (!cursor.moveToNext()) {
            Toast.makeText(this, "Erro: não foi possível reconhecer a série", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        serie = serie.fromCursor(cursor);

        editTextNomeSerie.setText(serie.getNome_serie());
        editTextTipoSerie.setText(serie.getTipo_serie());
        editTextClassificacaoSerie.setText(String.valueOf(serie.getClassificacao_serie()));
        editTextAnoSerie.setText(String.valueOf(serie.getAno_serie()));
        editTextTemporadas.setText(String.valueOf(serie.getTemporadas()));
        editTextDescricaoSerie.setText(serie.getDescricao_serie());

        atualizaAutorSelecionado();

    }

    private void atualizaAutorSelecionado() {
        if(!autoresCarregados) return;
        if(autorAtualizado) return;

        for(int i = 0; i < spinnerAutores.getCount(); i++){
            if(spinnerAutores.getItemIdAtPosition(i) == serie.getAutor_serie()){
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

    private void mostraAutoresSpinner(Cursor cursorAutores) {
        SimpleCursorAdapter adaptadorAutores = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_1,
                cursorAutores,
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

        long idAutor = spinnerAutores.getSelectedItemId();

        // guardar os dados

        serie.setNome_serie(nome);
        serie.setTipo_serie(tipo);
        serie.setAutor_serie(idAutor);
        serie.setClassificacao_serie(classificacao);
        serie.setAno_serie(ano);
        serie.setTemporadas(temporada);
        serie.setDescricao_serie(descricao);


        try {
            getContentResolver().update(enderecoSerieEditar, serie.getContentValues(), null, null);

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