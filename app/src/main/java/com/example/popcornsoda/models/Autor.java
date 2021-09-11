package com.example.popcornsoda.models;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.popcornsoda.BdPopcorn.BdTableAutores;
import com.example.popcornsoda.BdPopcorn.BdTableFilmes;

public class Autor {

    private long id;
    private String nome_autor;
    private int ano_nascimento;
    private String nacionalidade;
    private String descricao_autor;
    private byte[] foto_capa_autor;
    private byte[] foto_fundo_autor;
    private boolean favorito_autor;


    //Construtores

    public Autor(){}


    public Autor(long id, String nome_autor, int ano_nascimento, String nacionalidade) {
        this.id = id;
        this.nome_autor = nome_autor;
        this.ano_nascimento = ano_nascimento;
        this.nacionalidade = nacionalidade;
    }

    public Autor(long id, String nome_autor, int ano_nascimento, String nacionalidade, String descricao_autor, byte[] foto_capa_autor, byte[] foto_fundo_autor, boolean favorito_autor) {
        this.id = id;
        this.nome_autor = nome_autor;
        this.ano_nascimento = ano_nascimento;
        this.nacionalidade = nacionalidade;
        this.descricao_autor = descricao_autor;
        this.foto_capa_autor = foto_capa_autor;
        this.foto_fundo_autor = foto_fundo_autor;
        this.favorito_autor = favorito_autor;
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

    public String getDescricao_autor() {
        return descricao_autor;
    }

    public void setDescricao_autor(String descricao_autor) {
        this.descricao_autor = descricao_autor;
    }

    public byte[] getFoto_capa_autor() {
        return foto_capa_autor;
    }

    public void setFoto_capa_autor(byte[] foto_capa_autor) {
        this.foto_capa_autor = foto_capa_autor;
    }

    public byte[] getFoto_fundo_autor() {
        return foto_fundo_autor;
    }

    public void setFoto_fundo_autor(byte[] foto_fundo_autor) {
        this.foto_fundo_autor = foto_fundo_autor;
    }

    public boolean isFavorito_autor() {
        return favorito_autor;
    }

    public void setFavorito_autor(boolean favorito_autor) {
        this.favorito_autor = favorito_autor;
    }

    //Content Values

    public ContentValues getContentValues(){
        ContentValues valores = new ContentValues();

        valores.put(BdTableAutores.CAMPO_NOME, nome_autor);
        valores.put(BdTableAutores.CAMPO_ANONASCIMENTO, ano_nascimento);
        valores.put(BdTableAutores.CAMPO_NACIONALIDADE, nacionalidade);
        valores.put(BdTableAutores.CAMPO_DESCRICAO, descricao_autor);
        valores.put(BdTableAutores.CAMPO_FOTO_CAPA, foto_capa_autor);
        valores.put(BdTableAutores.CAMPO_FOTO_FUNDO, foto_fundo_autor);
        valores.put(BdTableAutores.CAMPO_FAVORITO, favorito_autor);

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

        String descricao = cursor.getString(
                cursor.getColumnIndex(BdTableAutores.CAMPO_DESCRICAO)
        );

        byte[] foto_capa = cursor.getBlob(
                cursor.getColumnIndex(BdTableAutores.CAMPO_FOTO_CAPA)
        );

        byte[] foto_fundo = cursor.getBlob(
                cursor.getColumnIndex(BdTableAutores.CAMPO_FOTO_FUNDO)
        );

        int favorito = ((int) cursor.getColumnIndex(BdTableAutores.CAMPO_FAVORITO));

        boolean favorito_bool;

        if(favorito == 0){
         favorito_bool = false;
        } else{
         favorito_bool = true;
        }



        Autor autor = new Autor();

        autor.setId(id);
        autor.setNome_autor(nome);
        autor.setAno_nascimento(ano);
        autor.setNacionalidade(nacionalidade);
        autor.setDescricao_autor(descricao);
        autor.setFoto_capa_autor(foto_capa);
        autor.setFoto_fundo_autor(foto_fundo);
        autor.setFavorito_autor(favorito_bool);

        return autor;
    }

}
