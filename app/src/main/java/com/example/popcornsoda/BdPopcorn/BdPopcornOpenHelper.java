package com.example.popcornsoda.BdPopcorn;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.popcornsoda.models.Categoria;

import java.util.ArrayList;
import java.util.List;

public class BdPopcornOpenHelper extends SQLiteOpenHelper {

    public static final String NOME_BASE_DADOS = "Popcorn.db";

    private static final int VERSAO_BASE_DADOS = 1;

    public static final String LOGTAG = "FAVORITE";

    SQLiteOpenHelper dbhandler;
    SQLiteDatabase db;
    private Context context;

    public BdPopcornOpenHelper(@Nullable Context context) {
        super(context, NOME_BASE_DADOS, null, VERSAO_BASE_DADOS);
        this.context = context;
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        new BdTableAutores(db).cria();
        new BdTableFilmes(db).cria();
        new BdTableSeries(db).cria();
        new BdTableCategorias(db).cria();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }



}
