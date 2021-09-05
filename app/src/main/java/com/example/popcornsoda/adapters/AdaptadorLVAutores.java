package com.example.popcornsoda.adapters;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popcornsoda.BdPopcorn.BdTableAutores;
import com.example.popcornsoda.R;
import com.example.popcornsoda.models.Autor;
import com.example.popcornsoda.ui.Autores;

public class AdaptadorLVAutores extends RecyclerView.Adapter<AdaptadorLVAutores.ViewHolderAutor> {
    private Cursor cursor;
    private Context context;
    private boolean selecao = false;
    private boolean click = false;

    public AdaptadorLVAutores(Context context){
        this.context = context;
    }

    public void setCursor(Cursor cursor){
        if (this.cursor != cursor){
            this.cursor = cursor;
            notifyDataSetChanged();
        }
    }

    public boolean isSelecao(){
        return selecao;
    }

    @NonNull
    @Override
    public ViewHolderAutor onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemAutor = LayoutInflater.from(context).inflate(R.layout.item_autor, parent, false);
        return new ViewHolderAutor(itemAutor);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderAutor holderAutor, int position) {
        cursor.moveToPosition(position);
        Autor autor = Autor.fromCursor(cursor);
        holderAutor.setAutor(autor);
    }

    @Override
    public int getItemCount() {
        if (cursor == null) {
            System.out.println("Nao tem nada!");
            return 0;
        } else{
            System.out.println("Tem tudo!");
            return cursor.getCount();
        }
    }

    private static ViewHolderAutor viewHolderAutorSelecionado = null;

    public Autor getAutorSelecionado() {
        if(viewHolderAutorSelecionado == null) return null;

        return viewHolderAutorSelecionado.autor;
    }

    public class ViewHolderAutor extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView textViewNome;
        private TextView textViewNacionalidade;
        private TextView textViewAno;
        private ImageView imageViewImagemCapa;


        private Autor autor;

        public ViewHolderAutor(@NonNull View itemView) {
            super(itemView);

            textViewNome = (TextView) itemView.findViewById(R.id.item_autor_title);
            textViewAno = (TextView) itemView.findViewById(R.id.item_autor_ano);
            textViewNacionalidade = (TextView) itemView.findViewById(R.id.item_autor_nacionalidade);
            imageViewImagemCapa = (ImageView) itemView.findViewById(R.id.item_autor_foto_capa);

            itemView.setOnClickListener(this);

        }

        public void setAutor(Autor autor) {
            this.autor = autor;

            textViewNome.setText(autor.getNome_autor());
            textViewAno.setText(String.valueOf(autor.getAno_nascimento()));
            textViewNacionalidade.setText(autor.getNacionalidade());

            byte[] autorImage = autor.getFoto_capa_autor();
            Bitmap bitmap_autorImage = BitmapFactory.decodeByteArray(autorImage, 0, autorImage.length);
            imageViewImagemCapa.setImageBitmap(bitmap_autorImage);

        }


        @Override
        public void onClick(View v) {
            if (viewHolderAutorSelecionado != null) {
                viewHolderAutorSelecionado.desSeleciona();
            }

            viewHolderAutorSelecionado = this;

            ((Autores) context).atualizaOpcoesMenu();

            seleciona();
        }


        private void desSeleciona() {
            itemView.setBackgroundResource(R.color.colorPrimary);
        }

        private void seleciona() {
            itemView.setBackgroundResource(R.color.colorAccent);
        }

    }
}