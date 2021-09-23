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
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.example.popcornsoda.BdPopcorn.BdTableAutores;
import com.example.popcornsoda.BdPopcorn.BdTableCategorias;
import com.example.popcornsoda.BdPopcorn.ContentProviderPopcorn;
import com.example.popcornsoda.R;
import com.example.popcornsoda.adapters.myDbAdapter;
import com.example.popcornsoda.models.Movie;
import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Calendar;

public class AddMovie extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>, AdapterView.OnItemSelectedListener {

    private static final int ID_CURSO_LOADER_AUTORES = 0;
    private static final int ID_CURSO_LOADER_CATEGORIAS = 0;
    final int REQUEST_CODE_GALLERY = 399;


    private EditText editTextNomeFilme;
    private EditText editTextClassificacaoFilme;
    private EditText editTextAnoFilme;
    private EditText editTextDescricaoFilme;
    private EditText editTextLinkFilme;
    private ImageView imagemCapaFilme;
    private ImageView imagemFundoFilme;
    private Button botaoCapaFilme;
    private Button botaoFundoFilme;


    private Switch switchFavoritoFilme;
    private Switch switchVistoFilme;

    private Spinner spinnerAutor;
    private Spinner spinnerCategoria;

    private myDbAdapter helper;

    private boolean estadoSwitchFavoritos;
    private boolean estadoSwitchVistos;

    private int acao_botao = 0;

    private double classificacao;
    private int ano;



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
                    imagemCapaFilme.setImageBitmap(bitmap);
                    setAcao_botao(0);
                } else if(getAcao_botao() == 2){
                    imagemFundoFilme.setImageBitmap(bitmap);
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
        setContentView(R.layout.activity_add_movie);

        //Botao Voltar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editTextNomeFilme = findViewById(R.id.editText_nome_filme_alterar);
        editTextClassificacaoFilme = findViewById(R.id.editText_classificacao_filme_alterar);
        editTextAnoFilme = findViewById(R.id.editText_ano_filme_alterar);
        editTextDescricaoFilme = findViewById(R.id.editText_descricao_filme_alterar);
        editTextLinkFilme = findViewById(R.id.editTextLink_filme_alterar);
        imagemCapaFilme = findViewById(R.id.foto_capa_alterar_filme);
        imagemFundoFilme = findViewById(R.id.foto_fundo_add_filme);
        botaoFundoFilme = findViewById(R.id.botao_fundo_alterar_filme);
        botaoCapaFilme = findViewById(R.id.botao_capa_alterar_filme);


        spinnerAutor = findViewById(R.id.spinnerAutores_filmes_alterar);
        spinnerCategoria = findViewById(R.id.spinnerCategorias_filmes_alterar);

        switchFavoritoFilme = findViewById(R.id.botao_favorito_alterar_filme);
        switchVistoFilme = findViewById(R.id.botao_visto_alterar_filme);

        estadoSwitchFavoritos = switchFavoritoFilme.isChecked();
        estadoSwitchVistos = switchVistoFilme.isChecked();


        getSupportLoaderManager().initLoader(ID_CURSO_LOADER_AUTORES, null, this);
        getSupportLoaderManager().initLoader(ID_CURSO_LOADER_CATEGORIAS, null, this);

        botaoCapaFilme.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(AddMovie.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_GALLERY);
                setAcao_botao(1);
            }
        });

        botaoFundoFilme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(AddMovie.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_GALLERY);
                setAcao_botao(2);
            }
        });

        helper = new myDbAdapter(this);

        Cursor cursor_categorias = helper.getCategorias();
        mostraCategoriasSpinner(cursor_categorias);


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

    private void mostraCategoriasSpinner(Cursor cursorCategorias){
        SimpleCursorAdapter adaptadorCategorias = new SimpleCursorAdapter(getApplicationContext(),
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

        String nome = editTextNomeFilme.getText().toString();

        if (nome.trim().isEmpty()) {
            editTextNomeFilme.setError("O campo não pode estar vazio!");
            return;
        }



        String strClassificacao = editTextClassificacaoFilme.getText().toString();

        if (strClassificacao.trim().isEmpty()) {
            editTextClassificacaoFilme.setError("O campo Não Pode Estar Vazio!");
            return;
        }

        double classificacao1 = Double.parseDouble(strClassificacao);
        try {
            if(classificacao1<0.0 ||  classificacao1>10.0){
                editTextClassificacaoFilme.setError("Classificação Não Aceitável");
                return;
            }else{
                classificacao = classificacao1;
            }
        } catch (NumberFormatException e) {
            editTextClassificacaoFilme.setError("Campo Inválido");
            return;
        }

        String strAno = editTextAnoFilme.getText().toString();

        if (strAno.trim().isEmpty()) {
            editTextAnoFilme.setError("O campo Não Pode Estar Vazio!");
            return;
        }

        try {
            int ano_atual = Calendar.getInstance().get(Calendar.YEAR);
            int ano1 = Integer.parseInt(strAno);
            if(ano1<1850 || ano1>ano_atual){
                editTextAnoFilme.setError("Ano Introduzido Não Aceitável");
                return;
            }else{
                ano = ano1;
            }

        } catch (NumberFormatException e) {
            editTextAnoFilme.setError("Campo Inválido");
            return;
        }

        String descricao = editTextDescricaoFilme.getText().toString();

        if (descricao.trim().isEmpty()) {
            editTextDescricaoFilme.setError("O campo não pode estar vazio!");
            return;
        }

        String link = editTextLinkFilme.getText().toString();

        if (link.trim().isEmpty()) {
            editTextLinkFilme.setError("O campo não pode estar vazio!");
            return;
        }

        long idAutor = spinnerAutor.getSelectedItemId();

        long idCategoria = spinnerCategoria.getSelectedItemId();


        System.out.println("switch favorito: " + estadoSwitchFavoritos);
        System.out.println("switch visto: " + estadoSwitchVistos);


        // guardar os dados
        Movie filme = new Movie();

        filme.setNome_filme(nome);
        filme.setCategoria_filme(idCategoria);
        filme.setAutor_filme(idAutor);
        filme.setClassificacao_filme(classificacao);
        filme.setAno_filme(ano);
        filme.setDescricao_filme(descricao);

        byte[] imagem_capa = ImagemParaByte(imagemCapaFilme);
        if(imagem_capa != null){
            filme.setFoto_capa_filme(imagem_capa);
        } else{
            imagemCapaFilme.setBackgroundColor(Color.RED);
            Toast.makeText(this, "Insira uma imagem para a capa", Toast.LENGTH_SHORT).show();
        }

        byte[] imagem_fundo = ImagemParaByte(imagemFundoFilme);
        if(imagem_fundo != null){
            filme.setFoto_fundo_filme(imagem_fundo);
        } else{
            Toast.makeText(this, "Insira uma imagem para o fundo", Toast.LENGTH_SHORT).show();
        }


        filme.setFavorito_filme(switchFavoritoFilme.isChecked());
        filme.setVisto_filme(switchVistoFilme.isChecked());

        filme.setLink_trailer_filme(link);

        try {

            getContentResolver().insert(ContentProviderPopcorn.ENDERECO_FILMES, filme.getContentValues());
            Toast.makeText(this, "Filme Guardado Com Sucesso", Toast.LENGTH_SHORT).show();

            //Toast.makeText(this, "Id Categoria" + spinnerCategoria.getSelectedItemId(), Toast.LENGTH_SHORT).show();


            Intent intent = new Intent(this, Filmes.class);
            finish();
            startActivity(intent);
        } catch (Exception e) {
            Snackbar.make(
                    editTextNomeFilme,
                    "Erro: Filme Não Guardado",
                    Snackbar.LENGTH_LONG)
                    .show();

            e.printStackTrace();
        }
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {

        androidx.loader.content.CursorLoader cursorLoader = new androidx.loader.content.CursorLoader(this, ContentProviderPopcorn.ENDERECO_AUTORES, BdTableAutores.TODAS_COLUNAS, null, null, BdTableAutores.CAMPO_NOME);

        return cursorLoader;

    }




    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        mostraAutoresSpinner(data);
    }


    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        mostraAutoresSpinner(null);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        //Toast.makeText(getApplicationContext(), categorias.indexOf(i), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private byte[] ImagemParaByte(ImageView image){
        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        return byteArray;
    }
}
