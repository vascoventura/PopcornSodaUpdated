package com.example.popcornsoda.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.popcornsoda.BdPopcorn.BdTableAutores;
import com.example.popcornsoda.BdPopcorn.ContentProviderPopcorn;
import com.example.popcornsoda.R;
import com.example.popcornsoda.models.Autor;
import com.example.popcornsoda.ui.Autores;

import java.io.ByteArrayOutputStream;

public class ApagarAutor extends AppCompatActivity {
    private Uri enderecoAutorApagar;

    private TextView textViewNome;
    private TextView textViewAno;
    private TextView textViewNacionalidade;
    private TextView textViewDescricao;
    private Switch switchFavorito;
    private ImageView imageViewCapa;
    private ImageView imageViewFundo;

    private Autor autor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apagar_autor);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        textViewNome = (TextView) findViewById(R.id.textViewNomeAutor_eliminar);
        textViewAno = (TextView) findViewById(R.id.textViewAnoAutor_eliminar);
        textViewNacionalidade = (TextView) findViewById(R.id.textViewNacionalidadeAutor_eliminar);
        textViewDescricao = (TextView) findViewById(R.id.textViewDescricaoAutor_eliminar);
        switchFavorito = (Switch) findViewById(R.id.botao_favorito_apagar_autor);
        imageViewCapa = (ImageView) findViewById(R.id.foto_capa_apagar_autor);
        imageViewFundo = (ImageView) findViewById(R.id.foto_fundo_apagar_autor);


        Intent intent = getIntent();
        long idAutor = intent.getLongExtra(Autores.ID_AUTOR, -1);
        if (idAutor == -1) {
            Toast.makeText(this, "Erro: não foi possível excluir o autor", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

            enderecoAutorApagar = Uri.withAppendedPath(ContentProviderPopcorn.ENDERECO_AUTORES, String.valueOf(idAutor));

        Cursor cursor = getContentResolver().query(enderecoAutorApagar, BdTableAutores.TODAS_COLUNAS, null, null, null);

        if (!cursor.moveToNext()) {
            Toast.makeText(this, "Erro: não foi possível excluir o autor", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        autor = autor.fromCursor(cursor);

        textViewNome.setText(autor.getNome_autor());
        textViewAno.setText(String.valueOf(autor.getAno_nascimento()));
        textViewNacionalidade.setText(autor.getNacionalidade());
        textViewDescricao.setText(autor.getDescricao_autor());
        switchFavorito.setChecked(autor.isFavorito_autor());



        byte[] autorImageCapaByte = autor.getFoto_capa_autor();
        Bitmap bitmap_filmeImage = BitmapFactory.decodeByteArray(autorImageCapaByte, 0, autorImageCapaByte.length);
        imageViewCapa.setImageBitmap(bitmap_filmeImage);

        byte[] autorImageFundoByte = autor.getFoto_fundo_autor();
        Bitmap bitmap_filmeImageFundo = BitmapFactory.decodeByteArray(autorImageFundoByte, 0, autorImageFundoByte.length);
        imageViewFundo.setImageBitmap(bitmap_filmeImageFundo);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_eliminar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_eliminar) {
            eliminar();
            return true;
        } else if (id == R.id.action_cancelar) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void eliminar() {
        int autoresApagados = getContentResolver().delete(enderecoAutorApagar, null, null);

        if (autoresApagados == 1) {
            Toast.makeText(this, "Autor excluido com sucesso", Toast.LENGTH_LONG).show();
            finish();
        } else {
            Toast.makeText(this, "Erro: Não foi possível excluir o autor", Toast.LENGTH_LONG).show();
        }
    }
}
