package com.example.popcornsoda.BdPopcorn;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class BdPopcornOpenHelper extends SQLiteOpenHelper {

    public static final String NOME_BASE_DADOS = "Popcorn.db";
    public static final int VERSAO_BASE_DADOS = 1;

    public BdPopcornOpenHelper(@Nullable Context context) {
        super(context, NOME_BASE_DADOS, null, VERSAO_BASE_DADOS);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        new BdTableAutores(db).cria();
        new BdTableFilmes(db).cria();
        new BdTableSeries(db).cria();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
