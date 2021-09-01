package com.example.popcornsoda.models;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.popcornsoda.BdPopcorn.BdTableAutores;
import com.example.popcornsoda.BdPopcorn.BdTableCategorias;

public class Categoria {
    private long id_categoria;
    private String nome_categoria;

    //Construtores
    public Categoria(){

    }

    public Categoria(int id_categoria, String nome_categoria) {
        this.id_categoria = id_categoria;
        this.nome_categoria = nome_categoria;
    }

    //Getters e Setters

    public long getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(long id_categoria) {
        this.id_categoria = id_categoria;
    }

    public String getNome_categoria() {
        return nome_categoria;
    }

    public void setNome_categoria(String nome_categoria) {
        this.nome_categoria = nome_categoria;
    }

    //Content Values

    public ContentValues getContentValues(){
        ContentValues valores = new ContentValues();

        valores.put(BdTableCategorias.CAMPO_NOME, nome_categoria);

        return valores;
    }

    public static Categoria fromCursor(Cursor cursor) {
        long id = cursor.getLong(
                cursor.getColumnIndex(BdTableCategorias._ID)
        );

        String nome = cursor.getString(
                cursor.getColumnIndex(BdTableCategorias.CAMPO_NOME)
        );

        Categoria categoria = new Categoria();

        categoria.setId_categoria(id);
        categoria.setNome_categoria(nome);

        return categoria;
    }
}
