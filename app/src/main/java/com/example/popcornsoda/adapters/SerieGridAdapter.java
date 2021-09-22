package com.example.popcornsoda.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.popcornsoda.R;
import com.example.popcornsoda.models.Serie;
import com.example.popcornsoda.ui.DetailActivitySerie;

import java.util.ArrayList;

public class SerieGridAdapter extends BaseAdapter implements View.OnClickListener {

    private static final String ID_SERIE = "ID_SERIE";

    Context context;
    private int layout;
    private ArrayList<Serie> seriesList;
    View row;


    LayoutInflater inflater;

    public SerieGridAdapter(Context context, int layout, ArrayList<Serie> seriesList) {
        this.context = context;
        this.layout = layout;
        this.seriesList = seriesList;

    }


    @Override
    public int getCount() {
        return seriesList.size();
    }

    @Override
    public Object getItem(int position) {
        return seriesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void onClick(View view) {

        long idSerie = row.getId();
        System.out.println("ID DA SERIE: " + idSerie);

        Context context = view.getContext();

        Intent intent = new Intent();
        intent.setClass(context, DetailActivitySerie.class);
        intent.putExtra(ID_SERIE, idSerie);
        context.startActivity(intent);
    }

    private class ViewHolder{
        ImageView imageCapa;
        TextView nomeSerie;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        row = view;
        ViewHolder holder = new ViewHolder();

        if(row==null){
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);

            holder.nomeSerie = (TextView) row.findViewById(R.id.item_grid_serie_name);
            holder.imageCapa = (ImageView) row.findViewById(R.id.item_grid_serie_img);

            row.setTag(holder);


        }else{
            holder = (ViewHolder) row.getTag();
        }

        Serie serie = seriesList.get(position);

        holder.nomeSerie.setText(serie.getNome_serie());

        byte[] capaImage = serie.getFoto_capa_serie();
        Bitmap bitmap = BitmapFactory.decodeByteArray(capaImage, 0, capaImage.length);
        holder.imageCapa.setImageBitmap(bitmap);

        return row;
    }
}