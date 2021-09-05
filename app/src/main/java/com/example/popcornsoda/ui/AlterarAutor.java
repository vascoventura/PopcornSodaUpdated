package com.example.popcornsoda.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.example.popcornsoda.BdPopcorn.BdTableAutores;
import com.example.popcornsoda.BdPopcorn.ContentProviderPopcorn;
import com.example.popcornsoda.R;
import com.example.popcornsoda.models.Autor;
import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class AlterarAutor extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int ID_CURSO_LOADER_AUTORES = 0;
    private static final int REQUEST_CODE_GALLERY = 399;

    private EditText editTextNomeAutor;
    private EditText editTextAnoAutor;
    private EditText editTextNacionalidadeAutor;
    private EditText editTextDescricaoAutor;
    private ImageView imageViewCapaAutorEditar;
    private ImageView imageViewFundoAutorEditar;
    private Switch switchFavoritoAutor;
    private boolean estadoSwitchFavoritos;
    private Button buttonCapaAutor;
    private Button buttonFundoAutor;

    private int acao_botao = 0;

    public int getAcao_botao() {
        return acao_botao;
    }

    public void setAcao_botao(int acao_botao) {
        this.acao_botao = acao_botao;
    }

    private Autor autor = null;

    private Uri enderecoAutorEditar;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_CODE_GALLERY){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            } else {
                Toast.makeText(getApplicationContext(), "Sem permissão para aceder ao conteúdo", Toast.LENGTH_LONG).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data !=null){
            Uri uri = data.getData();
            try{
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                if(getAcao_botao() == 1){
                    imageViewCapaAutorEditar.setImageBitmap(bitmap);
                    setAcao_botao(0);
                } else if(getAcao_botao() == 2){
                    imageViewFundoAutorEditar.setImageBitmap(bitmap);
                    setAcao_botao(0);
                }

            } catch(FileNotFoundException e){
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alterar_autor);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editTextNomeAutor = (EditText) findViewById(R.id.editTextNome_autorEditar);
        editTextAnoAutor = (EditText) findViewById(R.id.editTextAno_autorEditar);
        editTextNacionalidadeAutor = (EditText) findViewById(R.id.editTextNacionalidade_autorEditar);
        editTextDescricaoAutor = (EditText) findViewById(R.id.editTextDescricao_autorEditar);
        imageViewCapaAutorEditar = (ImageView) findViewById(R.id.foto_capa_edit_autor);
        imageViewFundoAutorEditar = (ImageView) findViewById(R.id.foto_fundo_edit_autor);
        switchFavoritoAutor = (Switch) findViewById(R.id.botao_favorito_editar_autor);
        buttonCapaAutor = (Button) findViewById(R.id.botao_capa_edit_autor);
        buttonFundoAutor = (Button) findViewById(R.id.botao_fundo_edit_autor);

        getSupportLoaderManager().initLoader(ID_CURSO_LOADER_AUTORES, null, this);

        buttonCapaAutor.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(AlterarAutor.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_GALLERY);
                setAcao_botao(1);
            }
        });

        buttonFundoAutor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(AlterarAutor.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_GALLERY);
                setAcao_botao(2);
            }
        });


        Intent intent = getIntent();

        long idAutor = intent.getLongExtra(Autores.ID_AUTOR, -1);

        if (idAutor == -1) {
            Toast.makeText(this, "Erro: não foi possível reconhcer o autor", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        enderecoAutorEditar = Uri.withAppendedPath(ContentProviderPopcorn.ENDERECO_AUTORES, String.valueOf(idAutor));

        Cursor cursor = getContentResolver().query(enderecoAutorEditar, BdTableAutores.TODAS_COLUNAS, null, null, null);

        if (!cursor.moveToNext()) {
            Toast.makeText(this, "Erro: não foi possível reconhecer o autor", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        autor = autor.fromCursor(cursor);

        editTextNomeAutor.setText(autor.getNome_autor());
        editTextAnoAutor.setText(String.valueOf(autor.getAno_nascimento()));
        editTextNacionalidadeAutor.setText(autor.getNacionalidade());
        editTextDescricaoAutor.setText(autor.getDescricao_autor());

        byte[] autorImageCapaByte = autor.getFoto_capa_autor();
        Bitmap bitmap_autorImage = BitmapFactory.decodeByteArray(autorImageCapaByte, 0, autorImageCapaByte.length);
        imageViewCapaAutorEditar.setImageBitmap(bitmap_autorImage);

        byte[] autorImageFundoByte = autor.getFoto_fundo_autor();
        Bitmap bitmap_autorImageFundo = BitmapFactory.decodeByteArray(autorImageFundoByte, 0, autorImageFundoByte.length);
        imageViewFundoAutorEditar.setImageBitmap(bitmap_autorImageFundo);

        switchFavoritoAutor.setChecked(autor.isFavorito_autor());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_guardar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.item_guardar) {
            guardar();
            return true;
        } else if (id == R.id.item_cancelar) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void guardar() {
        String nome = editTextNomeAutor.getText().toString();

        if(nome.trim().isEmpty()){
            editTextNomeAutor.setError("O campo não pode estar vazio");
            return;
        }

        int ano;

        String strAno = editTextAnoAutor.getText().toString();

        if (strAno.trim().isEmpty()){
            editTextAnoAutor.setError("O campo não pode estar vazio");
            return;
        }

        try {
            ano = Integer.parseInt(strAno);
        } catch (NumberFormatException e) {
            editTextAnoAutor.setError("Ano Inválido");
            return;
        }

        String nacionalidade = editTextNacionalidadeAutor.getText().toString();

        if(nacionalidade.trim().isEmpty()){
            editTextNacionalidadeAutor.setError("O campo não pode estar vazio");
            return;
        }

        String descricao = editTextDescricaoAutor.getText().toString();

        if(descricao.trim().isEmpty()){
            editTextDescricaoAutor.setError("O campo não pode estar vazio");
            return;
        }

        autor.setFoto_capa_autor(null);
        byte[] imagem_capa = ImagemParaByte(imageViewCapaAutorEditar);
        if(imagem_capa !=null){
            autor.setFoto_capa_autor(imagem_capa);
        } else{
            Toast.makeText(this, "Insira uma imagem para a capa", Toast.LENGTH_SHORT).show();
        }


        autor.setFoto_fundo_autor(null);
        byte[] imagem_fundo = ImagemParaByte(imageViewFundoAutorEditar);
        if(imagem_fundo !=null){
            autor.setFoto_fundo_autor(imagem_fundo);
        } else{
            Toast.makeText(this, "Insira uma imagem para o fundo", Toast.LENGTH_SHORT).show();
        }

        if(estadoSwitchFavoritos){
            autor.setFavorito_autor(true);
        } else{
            autor.setFavorito_autor(false);
        }

        System.out.println("Estado do Switch: " + estadoSwitchFavoritos);

        autor.setNome_autor(nome);
        autor.setAno_nascimento(ano);
        autor.setNacionalidade(nacionalidade);
        autor.setDescricao_autor(descricao);
        autor.setFoto_capa_autor(imagem_capa);
        autor.setFoto_fundo_autor(imagem_fundo);

        try {
            getContentResolver().update(enderecoAutorEditar, autor.getContentValues(), null, null);

            Toast.makeText(this, "Autor guardado com sucesso", Toast.LENGTH_SHORT).show();
            finish();
        } catch (Exception e) {
            Snackbar.make(
                    editTextNomeAutor,"Erro: Não foi possível criar o autor", Snackbar.LENGTH_LONG).show();


            e.printStackTrace();
        }
    }


    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        androidx.loader.content.CursorLoader cursorLoader = new androidx.loader.content.CursorLoader(this, ContentProviderPopcorn.ENDERECO_AUTORES, BdTableAutores.TODAS_COLUNAS, null, null, BdTableAutores.CAMPO_NOME
        );

        return cursorLoader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }

    private byte[] ImagemParaByte(ImageView image){
        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        return byteArray;
    }
}
