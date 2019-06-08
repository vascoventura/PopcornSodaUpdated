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
import android.widget.Spinner;
import android.widget.Toast;

import com.example.popcornsoda.BdPopcorn.BdTableAutores;
import com.example.popcornsoda.BdPopcorn.ContentProviderPopcorn;
import com.example.popcornsoda.R;
import com.example.popcornsoda.models.Autor;
import com.example.popcornsoda.models.Movie;
import com.google.android.material.snackbar.Snackbar;

public class AlterarAutor extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int ID_CURSO_LOADER_AUTORES = 0;

    private EditText editTextNomeAutor;
    private EditText editTextAnoAutor;
    private EditText editTextNacionalidadeAutor;

    private Autor autor = null;

    private Uri enderecoAutorEditar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alterar_autor);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editTextNomeAutor = (EditText) findViewById(R.id.editTextNome_autor_alterar);
        editTextAnoAutor = (EditText) findViewById(R.id.editTextAno_autor_alterar);
        editTextNacionalidadeAutor = (EditText) findViewById(R.id.editTextNacionalidade_autor_alterar);

        getSupportLoaderManager().initLoader(ID_CURSO_LOADER_AUTORES, null, this);


        Intent intent = getIntent();

        long idAutor = intent.getLongExtra(Autores.ID_AUTOR, -1);

        if (idAutor == -1) {
            Toast.makeText(this, "Erro: não foi possível reconhcer o autor", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        enderecoAutorEditar = Uri.withAppendedPath(ContentProviderPopcorn.ENDERECO_AUTORES, String.valueOf(idAutor));

        Cursor cursor = getContentResolver().query(enderecoAutorEditar, BdTableAutores.TODAS_COLUNAS, null, null, null);

        if (!cursor.moveToNext()) {
            Toast.makeText(this, "Erro: não foi possível reconhecer o autor", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        autor = autor.fromCursor(cursor);

        editTextNomeAutor.setText(autor.getNome_autor());
        editTextAnoAutor.setText(String.valueOf(autor.getAno_nascimento()));
        editTextNacionalidadeAutor.setText(autor.getNacionalidade());


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
        String nome = editTextNomeAutor.getText().toString();

        if(nome.trim().isEmpty()){
            editTextNomeAutor.setError("O campo não pode estar vazio");
            return;
        }

        int ano;

        String strAno = editTextAnoAutor.getText().toString();

        if (strAno.trim().isEmpty()){
            editTextAnoAutor.setError("O campo não pode estar vazio");
            return;
        }

        try {
            ano = Integer.parseInt(strAno);
        } catch (NumberFormatException e) {
            editTextAnoAutor.setError("Ano Inválido");
            return;
        }

        String nacionalidade = editTextNacionalidadeAutor.getText().toString();

        if(nome.trim().isEmpty()){
            editTextNacionalidadeAutor.setError("O campo não pode estar vazio");
            return;
        }

        autor.setNome_autor(nome);
        autor.setAno_nascimento(ano);
        autor.setNacionalidade(nacionalidade);

        try {
            getContentResolver().update(enderecoAutorEditar, autor.getContentValues(), null, null);

            Toast.makeText(this, "Autor guardado com sucesso", Toast.LENGTH_SHORT).show();
            finish();
        } catch (Exception e) {
            Snackbar.make(
                    editTextNomeAutor,"Erro: Não foi possível criar o autor", Snackbar.LENGTH_LONG).show();


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

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }
}
