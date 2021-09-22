package com.example.popcornsoda.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.popcornsoda.BdPopcorn.BdPopcornOpenHelper;
import com.example.popcornsoda.BdPopcorn.BdTableCategorias;
import com.example.popcornsoda.BdPopcorn.BdTableFilmes;
import com.example.popcornsoda.models.Categoria;

import java.util.ArrayList;
import java.util.List;

public class myDbAdapter {
    private BdPopcornOpenHelper myhelper;

    public myDbAdapter(Context context) {
        myhelper = new BdPopcornOpenHelper(context);
    }


    public Cursor getCategorias() {
        SQLiteDatabase db = myhelper.getReadableDatabase();
        String query = "Select * from categorias";
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    public Cursor getFilmes(){
        SQLiteDatabase db = myhelper.getReadableDatabase();
        String query = "Select * from filmes, autores, categorias where filmes.autor_filme = autores._id and filmes.categoria_filme = categorias._id;";
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    public Cursor getFilmesFavoritos(){
        SQLiteDatabase db = myhelper.getReadableDatabase();
        String query = "Select * \n" +
                "from filmes, autores, categorias \n" +
                "where categoria_filme = categorias._id\n" +
                "and autor_filme = autores._id\n" +
                "and favorito_filme = 1;";
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }
}
