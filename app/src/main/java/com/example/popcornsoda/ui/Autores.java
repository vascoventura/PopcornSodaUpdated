package com.example.popcornsoda.ui;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popcornsoda.BdPopcorn.BdTableAutores;
import com.example.popcornsoda.BdPopcorn.ContentProviderPopcorn;
import com.example.popcornsoda.R;
import com.example.popcornsoda.adapters.AdaptadorLVAutores;
import com.example.popcornsoda.models.Autor;

public class Autores extends AppCompatActivity  implements LoaderManager.LoaderCallbacks<Cursor>{

    public static final String ID_AUTOR = "ID_AUTOR";
    private  static  final int ID_CURSO_LOADER_AUTORES = 0;

    private AdaptadorLVAutores adaptadorAutores;
    private RecyclerView recyclerViewAutores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autores);
        Toolbar toolbar = findViewById(R.id.toolbar);


        getSupportLoaderManager().initLoader(ID_CURSO_LOADER_AUTORES, null, this);

        recyclerViewAutores = findViewById(R.id.recyclerViewAutores);
        adaptadorAutores = new AdaptadorLVAutores(this);
        recyclerViewAutores.setAdapter(adaptadorAutores);
        recyclerViewAutores.setLayoutManager(new LinearLayoutManager(this));

        //Botao Voltar

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onResume() {
        getSupportLoaderManager().restartLoader(ID_CURSO_LOADER_AUTORES, null, this);
        super.onResume();
    }

    private Menu menu;

    public void atualizaOpcoesMenu() {
        Autor autor = adaptadorAutores.getAutorSelecionado();

        boolean mostraAlterarEliminar = (autor != null);

        menu.findItem(R.id.itemEditar).setVisible(mostraAlterarEliminar);
        menu.findItem(R.id.itemEliminar).setVisible(mostraAlterarEliminar);
        menu.findItem(R.id.itemAdicionar).setVisible(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tabelas, menu);

        this.menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.itemAdicionar:
                Intent intent = new Intent (this, AddAutor.class);
                startActivity(intent);
                return true;

            case R.id.itemEditar:
                Intent intent1 = new Intent (this, AlterarAutor.class);
                intent1.putExtra(ID_AUTOR, adaptadorAutores.getAutorSelecionado().getId());
                startActivity(intent1);
                return true;

            case R.id.itemEliminar:
                Intent intent2 = new Intent (this, ApagarAutor.class);
                intent2.putExtra(ID_AUTOR, adaptadorAutores.getAutorSelecionado().getId());
                startActivity(intent2);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }



    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {
        CursorLoader cursorLoader = new CursorLoader(this, ContentProviderPopcorn.ENDERECO_AUTORES, BdTableAutores.TODAS_COLUNAS, null, null, BdTableAutores.CAMPO_NOME);

        return cursorLoader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        adaptadorAutores.setCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        adaptadorAutores.setCursor(null);

    }


}
