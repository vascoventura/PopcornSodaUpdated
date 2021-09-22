package com.example.popcornsoda.BdPopcorn;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.text.TextUtils;
import android.util.Log;


public class BdTableFilmes implements BaseColumns {

    public static final String NOME_TABELA = "filmes";

    public static final String ALIAS_NOME_AUTOR = "nome_autor";
    public static final String ALIAS_NOME_CATEGORIA = "nome_categoria";

    public static final String _ID = "_id";
    public static final String CAMPO_NOME = "nome_filme";
    public static final String CAMPO_CATEGORIA = "categoria_filme";
    public static final String CAMPO_AUTOR = "autor_filme";
    public static final String CAMPO_CLASSIFICACAO = "classificacao_filme";
    public static final String CAMPO_ANO = "ano_filme";
    public static final String CAMPO_DESCRICAO = "descricao_filme";
    public static final String CAMPO_CAPA = "capa_filme";
    public static final String CAMPO_FUNDO = "fundo_filme";
    public static final String CAMPO_FAVORITO = "favorito_filme";
    public static final String CAMPO_VISTO = "visto_filme";
    public static final String CAMPO_LINK = "link_filme";




    public static final String CAMPO_NOME_AUTOR = BdTableAutores.NOME_TABELA + "." + BdTableAutores.CAMPO_NOME + " AS " + ALIAS_NOME_AUTOR; // tabela de autores (só de leitura)
    public static final String CAMPO_NOME_CATEGORIA = BdTableCategorias.NOME_TABELA + "." + BdTableCategorias.CAMPO_NOME + " AS " + ALIAS_NOME_CATEGORIA; // tabela de categorias (só de leitura)


    public static final String[] TODAS_COLUNAS = new String[] { NOME_TABELA + "." + _ID, CAMPO_NOME, CAMPO_CATEGORIA,CAMPO_NOME_CATEGORIA, CAMPO_AUTOR, CAMPO_NOME_AUTOR, CAMPO_CLASSIFICACAO, CAMPO_ANO, CAMPO_DESCRICAO, CAMPO_CAPA, CAMPO_FUNDO, CAMPO_FAVORITO, CAMPO_VISTO, CAMPO_LINK };


    private SQLiteDatabase db;

    public BdTableFilmes(SQLiteDatabase db) {
        this.db = db;
    }

    public void cria() {
        db.execSQL(
                "CREATE TABLE " + NOME_TABELA + "(" +
                            _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                            CAMPO_NOME + " TEXT NOT NULL," +
                            CAMPO_CATEGORIA + " TEXT NOT NULL," +
                            CAMPO_AUTOR + " TEXT NOT NULL," +
                            CAMPO_CLASSIFICACAO + " DOUBLE NOT NULL," +
                            CAMPO_ANO + " INTEGER NOT NULL," +
                            CAMPO_DESCRICAO + " TEXT NOT NULL," +
                            CAMPO_CAPA + " BLOB," +
                            CAMPO_FUNDO + " BLOB," +
                            CAMPO_FAVORITO + " BOOLEAN," +
                            CAMPO_VISTO + " BOOLEAN," +
                            CAMPO_LINK + " TEXT NOT NULL," +
                            "FOREIGN KEY (" + CAMPO_AUTOR + ") REFERENCES " + BdTableAutores.NOME_TABELA + "(" + BdTableAutores._ID + ")," +
                            "FOREIGN KEY (" + CAMPO_CATEGORIA + ") REFERENCES " + BdTableCategorias.NOME_TABELA + "(" + BdTableCategorias._ID + ")" +
                            ")"
        );
    }

    public void delete(){
        db.delete(
                NOME_TABELA,null, null
        );
    }


    public Cursor query(String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        String colunasSelect = TextUtils.join(",", columns);

        String sql = "SELECT " + colunasSelect + " FROM " +
                NOME_TABELA +
                "," + BdTableAutores.NOME_TABELA +
                "," + BdTableCategorias.NOME_TABELA +
                " WHERE " + NOME_TABELA + "." + CAMPO_AUTOR + " = " + BdTableAutores.NOME_TABELA + "." + BdTableAutores._ID +
                " AND " + NOME_TABELA + "." + CAMPO_CATEGORIA + " = " + BdTableCategorias.NOME_TABELA + "." + BdTableCategorias._ID;

        if (selection != null) {
            sql += " AND " + selection;
        }

        Log.d("Tabela Filmes", "query: " + sql);

        return db.rawQuery(sql, selectionArgs);


    }


    public long insert(ContentValues values) {
        return db.insert(NOME_TABELA, null, values);
    }

    public int update(ContentValues values, String whereClause, String [] whereArgs) {
        return db.update(NOME_TABELA, values, whereClause, whereArgs);
    }

    public int delete(String whereClause, String[] whereArgs) {
        return db.delete(NOME_TABELA, whereClause, whereArgs);
    }
}