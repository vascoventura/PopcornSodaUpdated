package com.example.popcornsoda.models;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.popcornsoda.BdPopcorn.BdTableSeries;

public class Serie {

    private long id_serie;
    private String nome_serie;
    private String tipo_serie;
    private long autor_serie;
    private double classificacao_serie;
    private int ano_serie;
    private int temporadas;
    private String descricao_serie;
    private int capa_serie;
    private boolean favorito_serie;
    private boolean visto_serie;
    private int imagem_fundo_serie;
    private String link_trailer_serie;
    private String nomeAutor;


    public Serie(long id_serie, String nome_serie, double classificacao_serie, int capa_serie, boolean favorito_serie) {
        this.id_serie = id_serie;
        this.nome_serie = nome_serie;
        this.classificacao_serie = classificacao_serie;
        this.capa_serie = capa_serie;
        this.favorito_serie = favorito_serie;
    }

    public Serie(long id_serie, String nome_serie, String tipo_serie, long autor_serie, double classificacao_serie, int ano_serie, String descricao_serie, int capa_serie, int imagem_fundo_serie) {
        this.id_serie = id_serie;
        this.nome_serie = nome_serie;
        this.tipo_serie = tipo_serie;
        this.autor_serie = autor_serie;
        this.classificacao_serie = classificacao_serie;
        this.ano_serie = ano_serie;
        this.descricao_serie = descricao_serie;
        this.capa_serie = capa_serie;
        this.imagem_fundo_serie = imagem_fundo_serie;
    }

    public Serie(String nome_serie, int capa_serie, int imagem_fundo_serie) {
        this.nome_serie = nome_serie;
        this.capa_serie = capa_serie;
        this.imagem_fundo_serie = imagem_fundo_serie;
    }

    public Serie(String nome_serie, int capa_serie) {
        this.nome_serie = nome_serie;
        this.capa_serie = capa_serie;
    }

    public Serie(String nome_serie, double classificacao_serie, String descricao, int capa_serie, String link_trailer_serie) {
        this.nome_serie = nome_serie;
        this.classificacao_serie = classificacao_serie;
        this.descricao_serie = descricao;
        this.capa_serie = capa_serie;

        this.link_trailer_serie = link_trailer_serie;
    }

    public Serie() {

    }

    //Getters e Setters


    public String getNomeAutor() {
        return nomeAutor;
    }

    public void setNomeAutor(String nomeAutor) {
        this.nomeAutor = nomeAutor;
    }

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

    public String getTipo_serie() {
        return tipo_serie;
    }

    public void setTipo_serie(String tipo_serie) {
        this.tipo_serie = tipo_serie;
    }

    public long getAutor_serie() {
        return autor_serie;
    }

    public void setAutor_serie(long autor_serie) {
        this.autor_serie = autor_serie;
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

    public int getCapa_serie() {
        return capa_serie;
    }

    public void setCapa_serie(int capa_serie) {
        this.capa_serie = capa_serie;
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

    public int getImagem_fundo_serie() {
        return imagem_fundo_serie;
    }

    public void setImagem_fundo_serie(int imagem_fundo_serie) {
        this.imagem_fundo_serie = imagem_fundo_serie;
    }

    public String getLink_trailer_serie() {
        return link_trailer_serie;
    }

    public void setLink_trailer_serie(String link_trailer_serie) {
        this.link_trailer_serie = link_trailer_serie;
    }

    public ContentValues getContentValues() {
        ContentValues valores = new ContentValues();

        valores.put(BdTableSeries.CAMPO_NOME, nome_serie);
        valores.put(BdTableSeries.CAMPO_TIPO, tipo_serie);
        valores.put(BdTableSeries.CAMPO_AUTOR, autor_serie);
        valores.put(BdTableSeries.CAMPO_CLASSIFICACAO, classificacao_serie);
        valores.put(BdTableSeries.CAMPO_ANO, ano_serie);
        valores.put(BdTableSeries.CAMPO_TEMPORADAS, temporadas);
        valores.put(BdTableSeries.CAMPO_DESCRICAO, descricao_serie);

        return valores;
    }

    public static Serie fromCursor(Cursor cursor) {
        long id = cursor.getLong(
                cursor.getColumnIndex(BdTableSeries._ID)
        );

        String nome = cursor.getString(
                cursor.getColumnIndex(BdTableSeries.CAMPO_NOME)
        );

        String tipo = cursor.getString(
                cursor.getColumnIndex(BdTableSeries.CAMPO_TIPO)
        );

        long autor = cursor.getLong(
                cursor.getColumnIndex(BdTableSeries.CAMPO_AUTOR)
        );

        double classificacao = cursor.getDouble(
                cursor.getColumnIndex(BdTableSeries.CAMPO_CLASSIFICACAO)
        );

        int ano = cursor.getInt(
                cursor.getColumnIndex(BdTableSeries.CAMPO_ANO)
        );

        int temporadas = cursor.getInt(
                cursor.getColumnIndex(BdTableSeries.CAMPO_TEMPORADAS)
        );

        String descricao = cursor.getString(
                cursor.getColumnIndex(BdTableSeries.CAMPO_DESCRICAO)
        );

        /*int foto_capa_serie = cursor.getInt(
                cursor.getColumnIndex(BdTableSeries.CAMPO_CAPA)
        );

        int foto_fundo_serie = cursor.getInt(
                cursor.getColumnIndex(BdTableSeries.CAMPO_FUNDO)
        );*/
        String nomeAutor = cursor.getString(
                cursor.getColumnIndex(BdTableSeries.ALIAS_NOME_AUTOR)
        );

        Serie serie = new Serie();

        serie.setId_serie(id);
        serie.setNome_serie(nome);
        serie.setTipo_serie(tipo);
        serie.setAutor_serie(autor);
        serie.setClassificacao_serie(classificacao);
        serie.setAno_serie(ano);
        serie.setTemporadas(temporadas);
        serie.setDescricao_serie(descricao);
        /*serie.setCapa_serie(foto_capa_serie);
        serie.setImagem_fundo_serie(foto_fundo_serie);*/
        serie.nomeAutor = nomeAutor;

        return serie;
    }
}
