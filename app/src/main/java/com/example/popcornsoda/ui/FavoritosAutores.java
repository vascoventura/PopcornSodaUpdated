package com.example.popcornsoda.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.popcornsoda.BdPopcorn.BdTableAutores;
import com.example.popcornsoda.BdPopcorn.BdTableCategorias;
import com.example.popcornsoda.BdPopcorn.BdTableFilmes;
import com.example.popcornsoda.R;
import com.example.popcornsoda.adapters.AdaptadorAutoresFavoritos;
import com.example.popcornsoda.adapters.AdaptadorFilmesFavoritos;
import com.example.popcornsoda.adapters.myDbAdapter;
import com.example.popcornsoda.models.Autor;
import com.example.popcornsoda.models.Movie;

import java.util.ArrayList;

public class FavoritosAutores extends AppCompatActivity {

    private static final String ID_AUTOR = "ID_AUTOR";


    private Menu menu;
    private AdaptadorAutoresFavoritos adaptadorAutores;
    private myDbAdapter helper;
    private Cursor cursor_autores_favoritos;
    private ArrayList<Autor> autorArrayList;

    private Autor autor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoritos_autores);


        //Botao Voltar

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        autorArrayList = new ArrayList<Autor>();

        helper = new myDbAdapter(this);
        cursor_autores_favoritos = helper.getAutoresFavoritas();


        while(cursor_autores_favoritos.moveToNext()){
            @SuppressLint("Range") long id1  = cursor_autores_favoritos.getLong(0);

            @SuppressLint("Range") String nome = cursor_autores_favoritos.getString(
                    cursor_autores_favoritos.getColumnIndex(BdTableAutores.CAMPO_NOME)
            );

            @SuppressLint("Range") int ano = cursor_autores_favoritos.getInt(
                    cursor_autores_favoritos.getColumnIndex(BdTableAutores.CAMPO_ANONASCIMENTO)
            );

            @SuppressLint("Range") String nacionalidade = cursor_autores_favoritos.getString(
                    cursor_autores_favoritos.getColumnIndex(BdTableAutores.CAMPO_NACIONALIDADE)
            );



            System.out.println("");
            System.out.println("");
            System.out.println("");

            System.out.println("ID_GUARDADO FILME: " + nome  + " ID: " + id1);

            System.out.println("");
            System.out.println("");
            System.out.println("");

            autor = new Autor(id1, nome, ano, nacionalidade);
            autorArrayList.add(autor);

        }





        //Lista Vertical
        ListView listViewFilmes = findViewById(R.id.lista_autores_vertical);
        adaptadorAutores = new AdaptadorAutoresFavoritos(this,R.layout.item_autor_favorito, autorArrayList);
        listViewFilmes.setAdapter((ListAdapter) adaptadorAutores);

        listViewFilmes.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id2) {

                //Toast.makeText(getApplicationContext(), "You click on position:"+position, Toast.LENGTH_SHORT).show();

                Autor autor1 = autorArrayList.get(position);
                long id_autor = autor1.getId();
                System.out.println("ID DO AUTOR FAVORITO clickado: " + id_autor);
                System.out.println("POSICAO CLICKADA: " + position);
                Context context = view.getContext();

                Intent intent = new Intent();
                intent.setClass(context, DetailActivityAutor.class);
                intent.putExtra(ID_AUTOR, id_autor);
                context.startActivity(intent);
            }
        });

    }

    //Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_favoritos, menu);
        this.menu = menu;
        return true;
    }

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemEditar:
                Intent intent2 = new Intent(this, AlterarMovie.class);
                intent2.putExtra(ID_FILME, adaptadorFilmes.getFilmeSelecionada().getId_filme());
                startActivity(intent2);
                return true;

            case R.id.itemEliminar:
                Intent intent3 = new Intent(this, ApagarMovie.class);
                intent3.putExtra(ID_FILME, adaptadorFilmes.getFilmeSelecionada().getId_filme());
                startActivity(intent3);
                return true;

            case R.id.itemDetalhe:
                Intent intent4 = new Intent(this, DetailActivityMovie.class);
                intent4.putExtra(ID_FILME, adaptadorFilmes.getFilmeSelecionada().getId_filme());
                startActivity(intent4);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void atualizaOpcoesMenu() {
        if(adaptadorFilmes.isSelecao()){
            menu.findItem(R.id.itemEditar).setVisible(true);
            menu.findItem(R.id.itemEliminar).setVisible(true);
            menu.findItem(R.id.itemDetalhe).setVisible(true);
            menu.findItem(R.id.itemRemoverFavorito).setVisible(true);
        }else{
            menu.findItem(R.id.itemEditar).setVisible(false);
            menu.findItem(R.id.itemEliminar).setVisible(false);
            menu.findItem(R.id.itemDetalhe).setVisible(false);
            menu.findItem(R.id.itemRemoverFavorito).setVisible(false);
        }
    }*/
}