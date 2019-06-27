package com.example.popcornsoda.adapters;

public class Slider {

    private int Image;
    private String Title;
    private String Descricao;

    public Slider(int image, String title, String descricao){
        Image = image;
        Title = title;
        Descricao = descricao;
    }

    public int getImage() {
        return Image;
    }

    public String getTitle() {
        return Title;
    }

    public String getDescricao() {
        return Descricao;
    }
}
