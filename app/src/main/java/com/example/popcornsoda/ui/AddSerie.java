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
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import com.example.popcornsoda.BdPopcorn.BdTableAutores;
import com.example.popcornsoda.BdPopcorn.BdTableCategorias;
import com.example.popcornsoda.BdPopcorn.BdTableFilmes;
import com.example.popcornsoda.BdPopcorn.ContentProviderPopcorn;
import com.example.popcornsoda.R;
import com.example.popcornsoda.models.Serie;
import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class AddSerie extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int ID_CURSO_LOADER_AUTORES = 0;
    private static final int ID_CURSO_LOADER_CATEGORIAS = 0;
    final int REQUEST_CODE_GALLERY = 399;

    EditText editTextNomeSerie, editTextLinkSerie, editTextClassificacaoSerie, editTextAnoSerie, editTextDescricaoSerie, editTextTemporadas;
    Spinner spinnerAutor, spinnerCategoria;
    ImageView imagemCapaSerie, imagemFundoSerie;
    Switch switchFavoritoSerie, switchVistoSerie;
    Button botaoCapaSerie, botaoFundoSerie;


    private boolean estadoSwitchFavoritos, estadoSwitchVistos;

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
                    imagemCapaSerie.setImageBitmap(bitmap);
                    setAcao_botao(0);
                } else if(getAcao_botao() == 2){
                    imagemFundoSerie.setImageBitmap(bitmap);
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
        setContentView(R.layout.activity_add_serie);


        //Botao Voltar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editTextNomeSerie = findViewById(R.id.editText_nome_serie_inserir);
        editTextClassificacaoSerie = (EditText)findViewById(R.id.editText_classificacao_serie_inserir);
        editTextAnoSerie = (EditText)findViewById(R.id.editText_ano_serie_inserir);
        editTextTemporadas = (EditText) findViewById(R.id.editText_temporadas_serie_inserir);
        editTextDescricaoSerie = (EditText)findViewById(R.id.editText_descricao_serie_inserir);
        editTextLinkSerie = (EditText)findViewById(R.id.editTextLink_ser_inserir);
        botaoCapaSerie = findViewById(R.id.botao_capa_add_serie);
        botaoFundoSerie = findViewById(R.id.botao_fundo_add_serie);


        spinnerAutor = (Spinner) findViewById(R.id.spinnerCategorias_series_inserir);
        spinnerCategoria = (Spinner) findViewById(R.id.spinnerCategorias_series_inserir);

        switchFavoritoSerie = findViewById(R.id.botao_favorito_add_serie);
        switchVistoSerie = findViewById(R.id.botao_visto_add_serie);

        estadoSwitchFavoritos = switchFavoritoSerie.isChecked();
        estadoSwitchVistos = switchVistoSerie.isChecked();

        getSupportLoaderManager().initLoader(ID_CURSO_LOADER_AUTORES, null, this);

        botaoCapaSerie.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(AddSerie.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_GALLERY);
                setAcao_botao(1);
            }
        });

        botaoFundoSerie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(AddSerie.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_GALLERY);
                setAcao_botao(2);
            }
        });


    }

    @Override
    protected void onResume() {
        getSupportLoaderManager().restartLoader(ID_CURSO_LOADER_AUTORES, null, this);
        getSupportLoaderManager().restartLoader(ID_CURSO_LOADER_CATEGORIAS, null, this);

        super.onResume();
    }

    private void mostraAutoresSpinner(Cursor cursorAutores) {
        SimpleCursorAdapter adaptadorAutores = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_1,
                cursorAutores,
                new String[]{BdTableAutores.CAMPO_NOME},
                new int[]{android.R.id.text1}
        );
        spinnerAutor.setAdapter(adaptadorAutores);
    }

    private void mostraCategoriasSpinner(Cursor cursorCategorias) {
        SimpleCursorAdapter adaptadorCategorias = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_1,
                cursorCategorias,
                new String[]{BdTableCategorias.CAMPO_NOME},
                new int[]{android.R.id.text1}
        );
        spinnerCategoria.setAdapter(adaptadorCategorias);
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

        String nome = editTextNomeSerie.getText().toString();

        if (nome.trim().isEmpty()) {
            editTextNomeSerie.setError("O campo não pode estar vazio!");
            return;
        }

        double classificacao;

        String strClassificacao = editTextClassificacaoSerie.getText().toString();

        if (strClassificacao.trim().isEmpty()) {
            editTextClassificacaoSerie.setError("O campo não pode estar vazio!");
            return;
        }

        try {
            classificacao = Double.parseDouble(strClassificacao);
        } catch (NumberFormatException e) {
            editTextClassificacaoSerie.setError("Campo Inválido");
            return;
        }

        int ano;

        String strAno = editTextAnoSerie.getText().toString();

        if (strAno.trim().isEmpty()) {
            editTextAnoSerie.setError("O campo não pode estar vazio!");
            return;
        }

        try {
            ano = Integer.parseInt(strAno);
        } catch (NumberFormatException e) {
            editTextAnoSerie.setError("Campo Inválido");
            return;
        }

        int temporada;

        String strTemporada = editTextTemporadas.getText().toString();

        if (strTemporada.trim().isEmpty()) {
            editTextTemporadas.setError("O campo não pode estar vazio!");
            return;
        }

        try {
            temporada = Integer.parseInt(strTemporada);
        } catch (NumberFormatException e) {
            editTextTemporadas.setError("Campo Inválido");
            return;
        }


        String descricao = editTextDescricaoSerie.getText().toString();

        if (descricao.trim().isEmpty()) {
            editTextDescricaoSerie.setError("O campo não pode estar vazio!");
            return;
        }

        long idAutor = spinnerAutor.getSelectedItemId();
        long idCategoria = spinnerCategoria.getSelectedItemId();

        String link = editTextLinkSerie.getText().toString();

        if (link.trim().isEmpty()) {
            editTextLinkSerie.setError("O campo não pode estar vazio!");
            return;
        }

        // guardar os dados
        Serie serie = new Serie();

        serie.setNome_serie(nome);
        serie.setCategoria_serie(idCategoria);
        serie.setAutor_serie(idAutor);
        serie.setClassificacao_serie(classificacao);
        serie.setAno_serie(ano);
        serie.setTemporadas(temporada);
        serie.setDescricao_serie(descricao);

        byte[] imagem_capa = ImagemParaByte(imagemCapaSerie);
        if(imagem_capa != null){
            serie.setFoto_capa_serie(imagem_capa);
        } else{
            Toast.makeText(this, "Insira uma imagem para a capa", Toast.LENGTH_SHORT).show();
        }

        byte[] imagem_fundo = ImagemParaByte(imagemFundoSerie);
        if(imagem_fundo != null){
            serie.setFoto_fundo_serie(imagem_fundo);
        } else{
            Toast.makeText(this, "Insira uma imagem para o fundo", Toast.LENGTH_SHORT).show();
        }

        if(estadoSwitchFavoritos){
            serie.setFavorito_serie(true);
        }else{
            serie.setFavorito_serie(false);
        }

        if(estadoSwitchVistos){
            serie.setVisto_serie(true);
        }else{
            serie.setVisto_serie(false);
        }

        serie.setLink_trailer_serie(link);

        try {
            getContentResolver().insert(ContentProviderPopcorn.ENDERECO_SERIES, serie.getContentValues());

            Toast.makeText(this, "Serie guardada com sucesso", Toast.LENGTH_SHORT).show();
            finish();
        } catch (Exception e) {
            Snackbar.make(
                    editTextNomeSerie,
                    "Erro ao guardar série",
                    Snackbar.LENGTH_LONG)
                    .show();
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
        mostraAutoresSpinner(data);
        //mostraCategoriasSpinner(data);
    }


    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        mostraAutoresSpinner(null);
        mostraCategoriasSpinner(null);
    }

    private byte[] ImagemParaByte(ImageView image){
        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        return byteArray;
    }

}