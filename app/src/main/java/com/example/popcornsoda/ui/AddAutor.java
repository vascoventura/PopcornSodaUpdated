package com.example.popcornsoda.ui;

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import com.example.popcornsoda.BdPopcorn.BdTableAutores;
import com.example.popcornsoda.BdPopcorn.ContentProviderPopcorn;
import com.example.popcornsoda.R;
import com.example.popcornsoda.models.Autor;
import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class AddAutor extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int ID_CURSO_LOADER_AUTORES = 0;
    final int REQUEST_CODE_GALLERY = 399;

    private EditText editTextNomeAutor;
    private EditText editTextAnoAutor;
    private EditText editTextNacionalidadeAutor;
    private EditText editTextDescricaoAutor;
    private Switch switchFavoritoAddAutor;
    private ImageView imageViewFotoCapa;
    private Button botaoImagemCapa;
    private ImageView imageViewFotoFundo;
    private Button botaoImagemFundo;

    private boolean estadoSwitchFavoritos;

    private int acao_botao = 0;

    public void setAcao_botao(int acao_botao) {
        this.acao_botao = acao_botao;
    }

    public int getAcao_botao() {
        return acao_botao;
    }

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
                        imageViewFotoCapa.setImageBitmap(bitmap);
                        setAcao_botao(0);
                    } else if(getAcao_botao() == 2){
                        imageViewFotoFundo.setImageBitmap(bitmap);
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
        setContentView(R.layout.activity_add_autor);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editTextNomeAutor = findViewById(R.id.editTextNome_autor);
        editTextAnoAutor = findViewById(R.id.editTextAno_autor);
        editTextNacionalidadeAutor = findViewById(R.id.editTextNacionalidade_autor);
        imageViewFotoCapa = findViewById(R.id.foto_capa_add_autor);
        botaoImagemCapa = findViewById(R.id.botao_capa_add_autor);
        editTextDescricaoAutor = findViewById(R.id.editTextDescricao_autor);
        switchFavoritoAddAutor = (Switch) findViewById(R.id.botao_favorito_add_autor);
        imageViewFotoFundo = findViewById(R.id.foto_fundo_add_autor);
        botaoImagemFundo = findViewById(R.id.botao_fundo_add_autor);

        //Estado do Switch dos Favoritos
        estadoSwitchFavoritos = switchFavoritoAddAutor.isChecked();




        getSupportLoaderManager().initLoader(ID_CURSO_LOADER_AUTORES, null, this);

        botaoImagemCapa.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(AddAutor.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_GALLERY);
                setAcao_botao(1);
            }
        });

        botaoImagemFundo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(AddAutor.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_GALLERY);
                setAcao_botao(2);
            }
        });



    }

    @Override
    protected void onResume(){
        getSupportLoaderManager().restartLoader(ID_CURSO_LOADER_AUTORES, null, this);

        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_guardar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

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

        if (descricao.trim().isEmpty()) {
            editTextDescricaoAutor.setError("O campo não pode estar vazio!");
            return;
        }



        Autor autor = new Autor();


        autor.setNome_autor(nome);
        autor.setAno_nascimento(ano);
        autor.setNacionalidade(nacionalidade);
        autor.setDescricao_autor(descricao);

        byte[] imagem_capa = ImagemParaByte(imageViewFotoCapa);
        if(imagem_capa != null){
            autor.setFoto_capa_autor(imagem_capa);
        } else{
            Toast.makeText(this, "Insira uma imagem para a capa", Toast.LENGTH_SHORT).show();
        }

        byte[] imagem_fundo = ImagemParaByte(imageViewFotoFundo);
        if(imagem_fundo != null){
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


        try {
            getContentResolver().insert(ContentProviderPopcorn.ENDERECO_AUTORES, autor.getContentValues());

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
