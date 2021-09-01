package com.example.popcornsoda.models;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.popcornsoda.BdPopcorn.BdTableAutores;

public class Autor {

    private long id;
    private String nome_autor;
    private int ano_nascimento;
    private String nacionalidade;

    //Construtores

    public Autor(){

    }


    public Autor(long id, String nome_autor, int ano_nascimento, String nacionalidade) {
        this.id = id;
        this.nome_autor = nome_autor;
        this.ano_nascimento = ano_nascimento;
        this.nacionalidade = nacionalidade;
    }

    //Getters e Setters


    public long getId(){
        return id;
    }

    public void setId(long id){
        this.id = id;
    }

    public String getNome_autor() {
        return nome_autor;
    }

    public void setNome_autor(String nome_autor) {
        this.nome_autor = nome_autor;
    }

    public int getAno_nascimento() {
        return ano_nascimento;
    }

    public void setAno_nascimento(int ano_nascimento) {
        this.ano_nascimento = ano_nascimento;
    }

    public String getNacionalidade() {
        return nacionalidade;
    }

    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }


    //Content Values

    public ContentValues getContentValues(){
        ContentValues valores = new ContentValues();

        valores.put(BdTableAutores.CAMPO_NOME, nome_autor);
        valores.put(BdTableAutores.CAMPO_ANONASCIMENTO, ano_nascimento);
        valores.put(BdTableAutores.CAMPO_NACIONALIDADE, nacionalidade);

        return valores;
    }

    public static Autor fromCursor(Cursor cursor) {
        long id = cursor.getLong(
                cursor.getColumnIndex(BdTableAutores._ID)
        );

        String nome = cursor.getString(
                cursor.getColumnIndex(BdTableAutores.CAMPO_NOME)
        );

        int ano = cursor.getInt(
                cursor.getColumnIndex(BdTableAutores.CAMPO_ANONASCIMENTO)
        );

        String nacionalidade = cursor.getString(
                cursor.getColumnIndex(BdTableAutores.CAMPO_NACIONALIDADE)
        );

        Autor autor = new Autor();

        autor.setId(id);
        autor.setNome_autor(nome);
        autor.setAno_nascimento(ano);
        autor.setNacionalidade(nacionalidade);

        return autor;
    }

}
