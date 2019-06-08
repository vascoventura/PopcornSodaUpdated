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
import android.widget.Toast;

import com.example.popcornsoda.BdPopcorn.BdTableAutores;
import com.example.popcornsoda.BdPopcorn.ContentProviderPopcorn;
import com.example.popcornsoda.R;
import com.example.popcornsoda.models.Autor;
import com.google.android.material.snackbar.Snackbar;

public class AddAutor extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int ID_CURSO_LOADER_AUTORES = 0;

    private EditText editTextNomeAutor;
    private EditText editTextAnoAutor;
    private EditText editTextNacionalidadeAutor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_autor);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editTextNomeAutor = (EditText) findViewById(R.id.editTextNome_autor);
        editTextAnoAutor = (EditText) findViewById(R.id.editTextAno_autor);
        editTextNacionalidadeAutor = (EditText) findViewById(R.id.editTextNacionalidade_autor);
    }

    @Override
    protected void onResume(){
        getSupportLoaderManager().restartLoader(ID_CURSO_LOADER_AUTORES, null, this);

        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_guardar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if (id == R.id.item_guardar) {
            guardar();
            return true;
        } else if (id == R.id.action_cancelar) {
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

        Autor autor = new Autor();


        autor.setNome_autor(nome);
        autor.setAno_nascimento(ano);
        autor.setNacionalidade(nacionalidade);

        try {
            getContentResolver().insert(ContentProviderPopcorn.ENDERECO_AUTORES, autor.getContentValues());

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
