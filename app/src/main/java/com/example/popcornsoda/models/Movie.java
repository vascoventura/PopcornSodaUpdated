package com.example.popcornsoda.models;

import android.content.ContentValues;
import android.database.Cursor;
import com.example.popcornsoda.BdPopcorn.BdTableFilmes;


public class Movie {

    //Variaveis

    private long id_filme;
    private String nome_filme;
    private double classificacao_filme;
    private int ano_filme;
    private String descricao_filme;
    private int foto_capa_filme;
    private int foto_fundo_filme;
    private boolean favorito_filme;
    private boolean visto_filme;
    private String link_trailer_filme;

    private long categoria_filme;
    private String nomeCategoria;
    private long autor_filme;
    private String nomeAutor;



    //construtores

    public Movie(long id, String nome_filme, double classificacao_filme, int foto_capa_filme, boolean favorito_filme) {
        this.id_filme = id;
        this.nome_filme = nome_filme;
        this.classificacao_filme = classificacao_filme;
        this.foto_capa_filme = foto_capa_filme;
        this.favorito_filme = favorito_filme;
    }

    public Movie(String nome_filme, int foto_capa_filme, int foto_fundo_filme, String descricao_filme) {
        this.nome_filme = nome_filme;
        this.foto_capa_filme = foto_capa_filme;
        this.foto_fundo_filme = foto_fundo_filme;
        this.descricao_filme = descricao_filme;
    }

    public Movie(String nome_filme, int foto_capa_filme, int foto_fundo_filme){
        this.nome_filme = nome_filme;
        this.foto_capa_filme = foto_capa_filme;
        this.foto_fundo_filme = foto_fundo_filme;
    }

    public Movie(String nome_filme, long autor, int ano, double classificacao_filme) {
        this.nome_filme = nome_filme;
        this.autor_filme = autor;
        this.ano_filme = ano;
        this.classificacao_filme = classificacao_filme;

    }


    public Movie(String nome_filme, double classificacao_filme, int foto_capa_filme) {
        this.nome_filme = nome_filme;
        this.classificacao_filme = classificacao_filme;
        this.foto_capa_filme = foto_capa_filme;
    }

    public Movie() {
    }

    //Setters e Getters


    public String getNomeAutor() {
        return nomeAutor;
    }

    public void setNomeAutor(String nomeAutor) {
        this.nomeAutor = nomeAutor;
    }

    public long getId_filme() {
        return id_filme;
    }

    public void setId_filme(long id) {
        this.id_filme = id;
    }

    public String getNome_filme() {
        return nome_filme;
    }

    public void setNome_filme(String nome_filme) {
        this.nome_filme = nome_filme;
    }

    public long getCategoria_filme() {
        return categoria_filme;
    }

    public void setCategoria_filme(long categoria_filme) {
        this.categoria_filme = categoria_filme;
    }

    public String getNomeCategoria() {
        return nomeCategoria;
    }

    public void setNomeCategoria(String nomeCategoria) {
        this.nomeCategoria = nomeCategoria;
    }

    public long getAutor_filme() {
        return autor_filme;
    }

    public void setAutor_filme(long autor_filme) {
        this.autor_filme = autor_filme;
    }

    public double getClassificacao_filme() {
        return classificacao_filme;
    }

    public void setClassificacao_filme(double classificacao_filme) {
        this.classificacao_filme = classificacao_filme;
    }

    public int getAno_filme() {
        return ano_filme;
    }

    public void setAno_filme(int ano_filme) {
        this.ano_filme = ano_filme;
    }

    public String getDescricao_filme() {
        return descricao_filme;
    }

    public void setDescricao_filme(String descricao_filme) {
        this.descricao_filme = descricao_filme;
    }

    public int getFoto_capa_filme() {
        return foto_capa_filme;
    }

    public void setFoto_capa_filme(int foto_capa_filme) {
        this.foto_capa_filme = foto_capa_filme;
    }

    public int getFoto_fundo_filme() {
        return foto_fundo_filme;
    }

    public void setFoto_fundo_filme(int foto_fundo_filme) {
        this.foto_fundo_filme = foto_fundo_filme;
    }

    public boolean isFavorito_filme() {
        return favorito_filme;
    }

    public void setFavorito_filme(boolean favorito_filme) {
        this.favorito_filme = favorito_filme;
    }

    public boolean isVisto_filme() {
        return visto_filme;
    }

    public void setVisto_filme(boolean visto_filme) {
        this.visto_filme = visto_filme;
    }

    public String getLink_trailer_filme() {
        return link_trailer_filme;
    }

    public void setLink_trailer_filme(String link_trailer_filme) {
        this.link_trailer_filme = link_trailer_filme;
    }

    //ContentValues

    public ContentValues getContentValues() {
        ContentValues valores = new ContentValues();

        valores.put(BdTableFilmes.CAMPO_NOME, nome_filme);
        valores.put(BdTableFilmes.CAMPO_CATEGORIA, categoria_filme);
        valores.put(BdTableFilmes.CAMPO_AUTOR, autor_filme);
        valores.put(BdTableFilmes.CAMPO_CLASSIFICACAO, classificacao_filme);
        valores.put(BdTableFilmes.CAMPO_ANO, ano_filme);
        valores.put(BdTableFilmes.CAMPO_DESCRICAO, descricao_filme);


        return valores;
    }

    public static Movie fromCursor(Cursor cursor) {
        long id = cursor.getLong(
                cursor.getColumnIndex(BdTableFilmes._ID)
        );

        String nome = cursor.getString(
                cursor.getColumnIndex(BdTableFilmes.CAMPO_NOME)
        );

        long categoria = cursor.getLong(
                cursor.getColumnIndex(BdTableFilmes.CAMPO_CATEGORIA)
        );

        long autor = cursor.getLong(
                cursor.getColumnIndex(BdTableFilmes.CAMPO_AUTOR)
        );

        double classificacao = cursor.getDouble(
                cursor.getColumnIndex(BdTableFilmes.CAMPO_CLASSIFICACAO)
        );

        int ano = cursor.getInt(
                cursor.getColumnIndex(BdTableFilmes.CAMPO_ANO)
        );

        String descricao = cursor.getString(
                cursor.getColumnIndex(BdTableFilmes.CAMPO_DESCRICAO)
        );

        String nomeAutor = cursor.getString(
                cursor.getColumnIndex(BdTableFilmes.ALIAS_NOME_AUTOR)
        );

        Movie movie = new Movie();

        movie.setId_filme(id);
        movie.setNome_filme(nome);
        movie.setCategoria_filme(categoria);
        movie.setAutor_filme(autor);
        movie.setClassificacao_filme(classificacao);
        movie.setAno_filme(ano);
        movie.setDescricao_filme(descricao);
        movie.nomeAutor = nomeAutor;


        return movie;
    }
}







