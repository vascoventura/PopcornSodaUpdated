package com.example.popcornsoda.BdPopcorn;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.popcornsoda.adapters.Message;
import com.example.popcornsoda.models.Categoria;

import java.util.ArrayList;
import java.util.List;

public class BdPopcornOpenHelper extends SQLiteOpenHelper {

    public static final String NOME_BASE_DADOS = "Popcorn.db";

    private static final int VERSAO_BASE_DADOS = 1;

    private static final String DROP_TABLE ="DROP TABLE IF EXISTS "+ BdTableAutores.NOME_TABELA;

    SQLiteOpenHelper dbhandler;
    SQLiteDatabase db;
    Context context;

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
        try {
            Message.message(context,"OnUpgrade");
            db.execSQL(DROP_TABLE);
            onCreate(db);
        }catch (Exception e) {
            Message.message(context,""+e);
        }

    }
}
