package com.example.popcornsoda.models;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;

import com.example.popcornsoda.BdPopcorn.BdTableFilmes;
import com.example.popcornsoda.BdPopcorn.BdTableSeries;

public class Serie {

    private long id_serie;

    private String nome_serie;
    private double classificacao_serie;
    private int ano_serie;
    private int temporadas;
    private String descricao_serie;
    private byte[] foto_capa_serie;
    private byte[] foto_fundo_serie;
    private boolean favorito_serie;
    private boolean visto_serie;
    private String link_trailer_serie;

    private long categoria_serie;
    private String nomeCategoria;
    private long autor_serie;
    private String nomeAutor;

    //Construtores

    public Serie() {
    }

    public Serie(long id_serie, String nome_serie, double classificacao_serie, int ano_serie, int temporadas, String descricao_serie, byte[] foto_capa_serie, byte[] foto_fundo_serie, boolean favorito_serie, boolean visto_serie, String link_trailer_serie, long categoria_serie, String nomeCategoria, long autor_serie, String nomeAutor) {
        this.id_serie = id_serie;
        this.nome_serie = nome_serie;
        this.classificacao_serie = classificacao_serie;
        this.ano_serie = ano_serie;
        this.temporadas = temporadas;
        this.descricao_serie = descricao_serie;
        this.foto_capa_serie = foto_capa_serie;
        this.foto_fundo_serie = foto_fundo_serie;
        this.favorito_serie = favorito_serie;
        this.visto_serie = visto_serie;
        this.link_trailer_serie = link_trailer_serie;
        this.categoria_serie = categoria_serie;
        this.nomeCategoria = nomeCategoria;
        this.autor_serie = autor_serie;
        this.nomeAutor = nomeAutor;
    }

    //Getters e Setters


    public long getId_serie() {
        return id_serie;
    }

    public void setId_serie(long id_serie) {
        this.id_serie = id_serie;
    }

    public String getNome_serie() {
        return nome_serie;
    }

    public void setNome_serie(String nome_serie) {
        this.nome_serie = nome_serie;
    }

    public double getClassificacao_serie() {
        return classificacao_serie;
    }

    public void setClassificacao_serie(double classificacao_serie) {
        this.classificacao_serie = classificacao_serie;
    }

    public int getAno_serie() {
        return ano_serie;
    }

    public void setAno_serie(int ano_serie) {
        this.ano_serie = ano_serie;
    }

    public int getTemporadas() {
        return temporadas;
    }

    public void setTemporadas(int temporadas) {
        this.temporadas = temporadas;
    }

    public String getDescricao_serie() {
        return descricao_serie;
    }

    public void setDescricao_serie(String descricao_serie) {
        this.descricao_serie = descricao_serie;
    }

    public byte[] getFoto_capa_serie() {
        return foto_capa_serie;
    }

    public void setFoto_capa_serie(byte[] foto_capa_serie) {
        this.foto_capa_serie = foto_capa_serie;
    }

    public byte[] getFoto_fundo_serie() {
        return foto_fundo_serie;
    }

    public void setFoto_fundo_serie(byte[] foto_fundo_serie) {
        this.foto_fundo_serie = foto_fundo_serie;
    }

    public boolean isFavorito_serie() {
        return favorito_serie;
    }

    public void setFavorito_serie(boolean favorito_serie) {
        this.favorito_serie = favorito_serie;
    }

    public boolean isVisto_serie() {
        return visto_serie;
    }

    public void setVisto_serie(boolean visto_serie) {
        this.visto_serie = visto_serie;
    }

    public String getLink_trailer_serie() {
        return link_trailer_serie;
    }

    public void setLink_trailer_serie(String link_trailer_serie) {
        this.link_trailer_serie = link_trailer_serie;
    }

    public long getCategoria_serie() {
        return categoria_serie;
    }

    public void setCategoria_serie(long categoria_serie) {
        this.categoria_serie = categoria_serie;
    }

    public String getNomeCategoria() {
        return nomeCategoria;
    }

    public void setNomeCategoria(String nomeCategoria) {
        this.nomeCategoria = nomeCategoria;
    }

    public long getAutor_serie() {
        return autor_serie;
    }

    public void setAutor_serie(long autor_serie) {
        this.autor_serie = autor_serie;
    }

    public String getNomeAutor() {
        return nomeAutor;
    }

    public void setNomeAutor(String nomeAutor) {
        this.nomeAutor = nomeAutor;
    }

    //Content Values

    public ContentValues getContentValues() {
        ContentValues valores = new ContentValues();

        valores.put(BdTableSeries.CAMPO_NOME, nome_serie);
        valores.put(BdTableSeries.CAMPO_CATEGORIA, categoria_serie);
        valores.put(BdTableSeries.CAMPO_AUTOR, autor_serie);
        valores.put(BdTableSeries.CAMPO_CLASSIFICACAO, classificacao_serie);
        valores.put(BdTableSeries.CAMPO_ANO, ano_serie);
        valores.put(BdTableSeries.CAMPO_TEMPORADAS, temporadas);
        valores.put(BdTableSeries.CAMPO_DESCRICAO, descricao_serie);
        valores.put(BdTableSeries.CAMPO_CAPA, foto_capa_serie);
        valores.put(BdTableSeries.CAMPO_FUNDO, foto_fundo_serie);
        valores.put(BdTableSeries.CAMPO_FAVORITO, favorito_serie);
        valores.put(BdTableSeries.CAMPO_VISTO, visto_serie);
        valores.put(BdTableSeries.CAMPO_LINK, link_trailer_serie);

        return valores;
    }

    public static Serie fromCursor(Cursor cursor) {
        @SuppressLint("Range") long id = cursor.getLong(
                cursor.getColumnIndex(BdTableSeries._ID)
        );

        @SuppressLint("Range") String nome = cursor.getString(
                cursor.getColumnIndex(BdTableSeries.CAMPO_NOME)
        );

        @SuppressLint("Range") long categoria = cursor.getLong(
                cursor.getColumnIndex(BdTableSeries.CAMPO_CATEGORIA)
        );

        @SuppressLint("Range") long autor = cursor.getLong(
                cursor.getColumnIndex(BdTableSeries.CAMPO_AUTOR)
        );

        @SuppressLint("Range") double classificacao = cursor.getDouble(
                cursor.getColumnIndex(BdTableSeries.CAMPO_CLASSIFICACAO)
        );

        @SuppressLint("Range") int ano = cursor.getInt(
                cursor.getColumnIndex(BdTableSeries.CAMPO_ANO)
        );

        @SuppressLint("Range") int temporadas = cursor.getInt(
                cursor.getColumnIndex(BdTableSeries.CAMPO_TEMPORADAS)
        );

        @SuppressLint("Range") String descricao = cursor.getString(
                cursor.getColumnIndex(BdTableSeries.CAMPO_DESCRICAO)
        );

        @SuppressLint("Range") byte[] foto_capa = cursor.getBlob(
                cursor.getColumnIndex(BdTableSeries.CAMPO_CAPA)
        );

        @SuppressLint("Range") byte[] foto_fundo = cursor.getBlob(
                cursor.getColumnIndex(BdTableSeries.CAMPO_FUNDO)
        );
        @SuppressLint("Range") String link = cursor.getString(
                cursor.getColumnIndex(BdTableSeries.CAMPO_LINK)
        );

        @SuppressLint("Range") String nomeAutor = cursor.getString(
                cursor.getColumnIndex(BdTableSeries.ALIAS_NOME_AUTOR)
        );

        @SuppressLint("Range") String nomeCategoria = cursor.getString(
                cursor.getColumnIndex(BdTableSeries.ALIAS_NOME_CATEGORIA)
        );

        @SuppressLint("Range") boolean visto = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(BdTableSeries.CAMPO_VISTO)));

        @SuppressLint("Range") boolean favorito = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(BdTableSeries.CAMPO_FAVORITO)));


        Serie serie = new Serie();

        serie.setId_serie(id);
        serie.setNome_serie(nome);
        serie.setCategoria_serie(categoria);
        serie.setAutor_serie(autor);
        serie.setClassificacao_serie(classificacao);
        serie.setAno_serie(ano);
        serie.setTemporadas(temporadas);
        serie.setDescricao_serie(descricao);
        serie.setFoto_capa_serie(foto_capa);
        serie.setFoto_fundo_serie(foto_fundo);
        serie.setFavorito_serie(favorito);
        serie.setVisto_serie(visto);
        serie.setLink_trailer_serie(link);

        serie.nomeAutor = nomeAutor;
        serie.nomeCategoria = nomeCategoria;


        System.out.println("FAVORITO: " + favorito);
        System.out.println("VISTO: " + visto);

        return serie;
    }
}
