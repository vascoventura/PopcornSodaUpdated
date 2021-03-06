package com.example.popcornsoda.models;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.widget.ImageView;

import com.example.popcornsoda.BdPopcorn.BdTableAutores;
import com.example.popcornsoda.BdPopcorn.BdTableFilmes;
import com.example.popcornsoda.BdPopcorn.BdTableSeries;


public class Movie {

    //Atributos
    private long id_filme;
    private String nome_filme;
    private double classificacao_filme;
    private int ano_filme;
    private String descricao_filme;
    private byte[] foto_capa_filme;
    private byte[] foto_fundo_filme;
    private boolean favorito_filme;
    private boolean visto_filme;
    private String link_trailer_filme;

    private int favorito_numerico;
    private int visto_numerico;

    private long categoria_filme;
    private String nomeCategoria;
    private long autor_filme;
    private String nomeAutor;

    Cursor cursor;



    //construtores
    public Movie() {}

    public Movie(long id_filme, String nome_filme, double classificacao_filme, int ano_filme, String descricao_filme, byte[] foto_capa_filme, byte[] foto_fundo_filme, boolean favorito_filme, boolean visto_filme, String link_trailer_filme, long categoria_filme, String nomeCategoria, long autor_filme, String nomeAutor) {
        this.id_filme = id_filme;
        this.nome_filme = nome_filme;
        this.classificacao_filme = classificacao_filme;
        this.ano_filme = ano_filme;
        this.descricao_filme = descricao_filme;
        this.foto_capa_filme = foto_capa_filme;
        this.foto_fundo_filme = foto_fundo_filme;
        this.favorito_filme = favorito_filme;
        this.visto_filme = visto_filme;
        this.link_trailer_filme = link_trailer_filme;
        this.categoria_filme = categoria_filme;
        this.nomeCategoria = nomeCategoria;
        this.autor_filme = autor_filme;
        this.nomeAutor = nomeAutor;
    }



    public Movie(String nome, String nomeCategoria, double classificacao_filme, int ano_filme, String nomeAutor, long id_filme) {
        this.nome_filme = nome;
        this.nomeCategoria = nomeCategoria;
        this.classificacao_filme = classificacao_filme;
        this.ano_filme = ano_filme;
        this.nomeAutor = nomeAutor;
        this.id_filme = id_filme;

    }

    public Movie(String nome, byte[] foto_capa, long id) {
        this.nome_filme = nome;
        this.foto_capa_filme = foto_capa;
        this.id_filme = id;
    }

    //Setters e Getters

    public long getId_filme() {
        return id_filme;
    }

    public void setId_filme(long id_filme) {
        this.id_filme = id_filme;
    }

    public String getNome_filme() {
        return nome_filme;
    }

    public void setNome_filme(String nome_filme) {
        this.nome_filme = nome_filme;
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

    public byte[] getFoto_capa_filme() {
        return foto_capa_filme;
    }

    public void setFoto_capa_filme(byte[] foto_capa_filme) {
        this.foto_capa_filme = foto_capa_filme;
    }

    public byte[] getFoto_fundo_filme() {
        return foto_fundo_filme;
    }

    public void setFoto_fundo_filme(byte[] foto_fundo_filme) {
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

    public String getNomeAutor() {
        return nomeAutor;
    }

    public void setNomeAutor(String nomeAutor) {
        this.nomeAutor = nomeAutor;
    }

    public int getFavorito_numerico() {
        return favorito_numerico;
    }

    public void setFavorito_numerico(int favorito_numerico) {
        this.favorito_numerico = favorito_numerico;
    }

    public int getVisto_numerico() {
        return visto_numerico;
    }

    public void setVisto_numerico(int visto_numerico) {
        this.visto_numerico = visto_numerico;
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
        valores.put(BdTableFilmes.CAMPO_CAPA, foto_capa_filme);
        valores.put(BdTableFilmes.CAMPO_FUNDO, foto_fundo_filme);
        valores.put(BdTableFilmes.CAMPO_FAVORITO, favorito_filme);
        valores.put(BdTableFilmes.CAMPO_VISTO, visto_filme);
        valores.put(BdTableFilmes.CAMPO_LINK, link_trailer_filme);


        return valores;
    }

    public static Movie fromCursor(Cursor cursor) {
        @SuppressLint("Range") long id = cursor.getLong(cursor.getColumnIndex(BdTableFilmes._ID)
        );

        @SuppressLint("Range") String nome = cursor.getString(
                cursor.getColumnIndex(BdTableFilmes.CAMPO_NOME)
        );

        @SuppressLint("Range") long categoria = cursor.getLong(
                cursor.getColumnIndex(BdTableFilmes.CAMPO_CATEGORIA)
        );

        @SuppressLint("Range") long autor = cursor.getLong(
                cursor.getColumnIndex(BdTableFilmes.CAMPO_AUTOR)
        );

        @SuppressLint("Range") double classificacao = cursor.getDouble(
                cursor.getColumnIndex(BdTableFilmes.CAMPO_CLASSIFICACAO)
        );

        @SuppressLint("Range") int ano = cursor.getInt(
                cursor.getColumnIndex(BdTableFilmes.CAMPO_ANO)
        );

        @SuppressLint("Range") String descricao = cursor.getString(
                cursor.getColumnIndex(BdTableFilmes.CAMPO_DESCRICAO)
        );
        @SuppressLint("Range") byte[] foto_capa = cursor.getBlob(
                cursor.getColumnIndex(BdTableFilmes.CAMPO_CAPA)
        );

        @SuppressLint("Range") byte[] foto_fundo = cursor.getBlob(
                cursor.getColumnIndex(BdTableFilmes.CAMPO_FUNDO)
        );
        @SuppressLint("Range") String link = cursor.getString(
                cursor.getColumnIndex(BdTableFilmes.CAMPO_LINK)
        );

        @SuppressLint("Range") String nomeAutor = cursor.getString(
                cursor.getColumnIndex(BdTableFilmes.ALIAS_NOME_AUTOR)
        );

        @SuppressLint("Range") String nomeCategoria = cursor.getString(
                cursor.getColumnIndex(BdTableFilmes.ALIAS_NOME_CATEGORIA)
        );

        @SuppressLint("Range") boolean visto = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(BdTableFilmes.CAMPO_VISTO)));

        @SuppressLint("Range") boolean favorito = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(BdTableFilmes.CAMPO_FAVORITO)));

        int visto1 = cursor.getInt(cursor.getColumnIndex(BdTableFilmes.CAMPO_VISTO));
        int favorito1 = cursor.getInt(cursor.getColumnIndex(BdTableFilmes.CAMPO_FAVORITO));


        Movie movie = new Movie();

        movie.setId_filme(id);

        movie.setNome_filme(nome);
        movie.setCategoria_filme(categoria);
        movie.setAutor_filme(autor);
        movie.setClassificacao_filme(classificacao);
        movie.setAno_filme(ano);
        movie.setDescricao_filme(descricao);
        movie.setFoto_capa_filme(foto_capa);
        movie.setFoto_fundo_filme(foto_fundo);
        movie.setFavorito_filme(favorito);
        movie.setVisto_filme(visto);
        movie.setLink_trailer_filme(link);

        movie.setNomeAutor(nomeAutor);
        movie.setNomeCategoria(nomeCategoria);

        movie.setFavorito_numerico(favorito1);
        movie.setVisto_numerico(visto1);


        return movie;
    }
}







