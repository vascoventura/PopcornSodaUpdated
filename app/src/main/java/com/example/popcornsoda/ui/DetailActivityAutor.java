package com.example.popcornsoda.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.popcornsoda.BdPopcorn.BdTableAutores;
import com.example.popcornsoda.BdPopcorn.ContentProviderPopcorn;
import com.example.popcornsoda.R;
import com.example.popcornsoda.models.Autor;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DetailActivityAutor extends AppCompatActivity{


    private Uri enderecoAutor;
    ContentProviderPopcorn db;

    private Autor autor = null;
    private Menu menu;

    private boolean favAutor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_autor);

        //Botao Voltar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        TextView textViewNomeAutor = (TextView) findViewById(R.id.detail_autor_nome);
        TextView textViewAnoAutor = (TextView) findViewById(R.id.detail_autor_ano);
        TextView textViewNacionalidadeAutor = (TextView) findViewById(R.id.detail_autor_nacionalidade);
        TextView textViewDescricaoAutor = (TextView) findViewById(R.id.detail_autor_descricao);
        ImageView imageViewCapaAutor = (ImageView) findViewById(R.id.detail_autor_capa);
        ImageView imageViewFundoAutor = (ImageView) findViewById(R.id.detail_autor_fundo);
        FloatingActionButton favoritoAutor = (FloatingActionButton) findViewById(R.id.botao_favorito_apagar_autor);

        textViewNomeAutor.setAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_animation));
        textViewAnoAutor.setAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_animation));
        textViewNacionalidadeAutor.setAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_animation));
        textViewDescricaoAutor.setAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_animation));
        imageViewCapaAutor.setAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_animation));
        imageViewFundoAutor.setAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_animation));
        favoritoAutor.setAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_animation));

        Intent intent = getIntent();
        final long idAutor = intent.getLongExtra(Autores.ID_AUTOR, -1);
        if(idAutor == -1) {
            Toast.makeText(this, "Erro: não foi possível abrir a página do conteúdo", Toast.LENGTH_LONG).show();
            return;
        }

        enderecoAutor = Uri.withAppendedPath(ContentProviderPopcorn.ENDERECO_AUTORES, String.valueOf(idAutor));


        Cursor cursor = getContentResolver().query(enderecoAutor, BdTableAutores.TODAS_COLUNAS, null, null, null);

        if (!cursor.moveToNext()) {
            Toast.makeText(this, "Erro: não foi possível abrir a página do conteúdo", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        autor = Autor.fromCursor(cursor);

        textViewNomeAutor.setText(autor.getNome_autor());
        textViewAnoAutor.setText(String.valueOf(autor.getAno_nascimento()));
        textViewNacionalidadeAutor.setText(autor.getNacionalidade());
        textViewDescricaoAutor.setText(autor.getDescricao_autor());


        getSupportActionBar().setTitle(autor.getNome_autor());

        byte[] autorImageCapa = autor.getFoto_capa_autor();
        Bitmap bitmap_autorImage = BitmapFactory.decodeByteArray(autorImageCapa, 0, autorImageCapa.length);
        //imageViewCapaAutor.setImageBitmap(Bitmap.createScaledBitmap(bitmap_autorImage, imageViewCapaAutor.getWidth(), imageViewCapaAutor.getHeight(), false));
        imageViewCapaAutor.setImageBitmap(bitmap_autorImage);

        byte[] autorImageFundo = autor.getFoto_fundo_autor();
        Bitmap bitmap_autorImageFundo = BitmapFactory.decodeByteArray(autorImageFundo, 0, autorImageFundo.length);
        //imageViewFundoAutor.setImageBitmap(Bitmap.createScaledBitmap(bitmap_autorImageFundo, imageViewFundoAutor.getWidth(), imageViewFundoAutor.getHeight(), false));
        imageViewFundoAutor.setImageBitmap(bitmap_autorImageFundo);


        favoritoAutor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favAutor = autor.isFavorito_autor();
                System.out.println("estado Favorito: " + favAutor);
                System.out.println("id_autor: " + idAutor );

                atualizaFavorito(favAutor);
            }

            private int atualizaFavorito(boolean estadoAtual) {
                autor.setFavorito_autor(!estadoAtual);
                    try {
                        ContentValues values = new ContentValues();
                        values.put(BdTableAutores.CAMPO_FAVORITO, !estadoAtual);
                        getContentResolver().update(enderecoAutor, autor.getContentValues(), null, null );

                        if(favAutor == false) {
                            Toast.makeText(DetailActivityAutor.this, "Adicionado aos Favoritos", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(DetailActivityAutor.this, "Removido dos Favoritos", Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
                        Toast.makeText(DetailActivityAutor.this, "Não Foi Possível Realizar a Operação", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                return 0;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detalhes, menu);
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemEditarDetalhes:
                Intent intent1 = new Intent(this, AlterarAutor.class);
                //intent1.putExtra(ID_AUTOR, autor.getId());
                //startActivity(intent1);
                return true;

            case R.id.itemEliminarDetalhes:
                Intent intent2 = new Intent(this, ApagarAutor.class);
                //intent2.putExtra(ID_AUTOR, adaptadorAutores.getAutorSelecionado().getId());
                //startActivity(intent2);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}