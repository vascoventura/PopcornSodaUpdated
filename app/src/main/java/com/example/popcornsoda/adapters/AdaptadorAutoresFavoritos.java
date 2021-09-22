package com.example.popcornsoda.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.popcornsoda.R;
import com.example.popcornsoda.models.Autor;
import com.example.popcornsoda.ui.DetailActivityAutor;

import java.util.ArrayList;

public class AdaptadorAutoresFavoritos extends BaseAdapter implements View.OnClickListener {

    private static final String ID_AUTOR = "ID_AUTOR";
    private Context context;
    private int layout;
    private ArrayList<Autor> autoresList;
    private View row;
    private ViewHolder holder;


    LayoutInflater inflater;

    public AdaptadorAutoresFavoritos(Context context, int layout, ArrayList<Autor> autoresList) {
        this.context = context;
        this.layout = layout;
        this.autoresList = autoresList;

    }


    @Override
    public int getCount() {
        return autoresList.size();
    }

    @Override
    public Object getItem(int position) {
        return autoresList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void onClick(View view) {

        long idAutor = row.getId();
        System.out.println("ID DO AUTOR (favoritos): " + idAutor);

        Context context = view.getContext();

        Intent intent = new Intent();
        intent.setClass(context, DetailActivityAutor.class);
        intent.putExtra(ID_AUTOR, idAutor);
        context.startActivity(intent);
    }

    private class ViewHolder{
        private TextView textViewNome;
        private TextView textViewAno;
        private TextView textViewNacionalidade;

        private ImageView imageCapaFilme;

    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {


        row = view;
        holder = new ViewHolder();


        if(row==null){
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);

            holder.textViewNome = (TextView) row.findViewById(R.id.item_autor_title_favorito);
            holder.textViewAno = (TextView) row.findViewById(R.id.item_autor_ano_favorito);
            holder.textViewNacionalidade = (TextView) row.findViewById(R.id.item_autor_nacionalidade_favorito);

            //holder.imageCapaFilme = (ImageView) row.findViewById(R.id.imageViewCapaFilme);


            row.setTag(holder);


        }else{
            holder = (ViewHolder) row.getTag();
        }

        Autor autor = autoresList.get(position);

        holder.textViewNome.setText(autor.getNome_autor());
        holder.textViewAno.setText(String.valueOf(autor.getAno_nascimento()));
        holder.textViewNacionalidade.setText(autor.getNacionalidade());


        return row;
    }
}
