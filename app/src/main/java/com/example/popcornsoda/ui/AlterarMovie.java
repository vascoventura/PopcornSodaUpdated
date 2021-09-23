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
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;
import com.example.popcornsoda.BdPopcorn.BdTableAutores;
import com.example.popcornsoda.BdPopcorn.BdTableCategorias;
import com.example.popcornsoda.BdPopcorn.BdTableFilmes;
import com.example.popcornsoda.BdPopcorn.ContentProviderPopcorn;
import com.example.popcornsoda.R;
import com.example.popcornsoda.adapters.myDbAdapter;
import com.example.popcornsoda.models.Movie;
import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Calendar;

public class AlterarMovie extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int ID_CURSO_LOADER_AUTORES = 0;
    private static final int ID_CURSO_LOADER_CATEGORIAS = 0;
    private static final int REQUEST_CODE_GALLERY = 399;

    private EditText editTextNomeFilme;
    private EditText editTextClassificacaoFilme;
    private EditText editTextAnoFilme;
    private EditText editTextDescricaoFilme;
    private EditText editTextLink;

    private Spinner spinnerAutores;
    private Spinner spinnerCategorias;


    private Switch switchFavoritoFilme;
    private Switch switchVistoFilme;

    private ImageView imageViewCapaFilme;
    private ImageView imageViewFundoFilme;

    private Button botaoCapaAlterarFilme;
    private Button botaoFundoAlterarFilme;

    private Cursor cursor_categorias;

    private Movie filme = null;

    private boolean autoresCarregados = false;
    private boolean autorAtualizado = false;

    private myDbAdapter helper;
    private boolean categoriasCarregadas = false;
    private boolean categoriaAtualizada = false;

    private Uri enderecoFilmeEditar;

    private double classificacao;
    private int ano;

    private int acao_botao = 0;

    public int getAcao_botao() {
        return acao_botao;
    }

    public void setAcao_botao(int acao_botao) {
        this.acao_botao = acao_botao;
    }




    private boolean favorito;
    private boolean visto;

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
                    imageViewCapaFilme.setImageBitmap(bitmap);
                    setAcao_botao(0);
                } else if(getAcao_botao() == 2){
                    imageViewFundoFilme.setImageBitmap(bitmap);
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
        setContentView(R.layout.activity_alterar_movie);


        editTextNomeFilme = (EditText) findViewById(R.id.editText_nome_filme_alterar);
        editTextClassificacaoFilme = (EditText) findViewById(R.id.editText_classificacao_filme_alterar);
        editTextAnoFilme = (EditText) findViewById(R.id.editText_ano_filme_alterar);
        editTextDescricaoFilme = (EditText) findViewById(R.id.editText_descricao_filme_alterar);
        editTextLink = (EditText) findViewById(R.id.editTextLink_filme_alterar);

        spinnerAutores = (Spinner) findViewById(R.id.spinnerAutores_filmes_alterar);
        spinnerCategorias = (Spinner) findViewById(R.id.spinnerCategorias_filmes_alterar);

        imageViewCapaFilme = (ImageView) findViewById(R.id.foto_capa_alterar_filme);
        imageViewFundoFilme = (ImageView) findViewById(R.id.foto_fundo_add_filme);

        switchFavoritoFilme = (Switch) findViewById(R.id.botao_favorito_alterar_filme);
        switchVistoFilme =  (Switch) findViewById(R.id.botao_visto_alterar_filme);


        botaoCapaAlterarFilme = (Button) findViewById(R.id.botao_capa_alterar_filme);
        botaoFundoAlterarFilme = (Button) findViewById(R.id.botao_fundo_alterar_filme);



        getSupportLoaderManager().initLoader(ID_CURSO_LOADER_CATEGORIAS, null, this);
        getSupportLoaderManager().initLoader(ID_CURSO_LOADER_AUTORES, null, this);

        botaoCapaAlterarFilme.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(AlterarMovie.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_GALLERY);
                setAcao_botao(1);
            }
        });

        botaoFundoAlterarFilme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(AlterarMovie.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_GALLERY);
                setAcao_botao(2);
            }
        });

        Intent intent = getIntent();

        long idFilme = intent.getLongExtra(Filmes.ID_FILME, -1);

        if (idFilme == -1){
        Toast.makeText(this, "Erro: Não foi possível reconhecer o Filme", Toast.LENGTH_LONG).show();
        finish();
        return;
        }

        enderecoFilmeEditar = Uri.withAppendedPath(ContentProviderPopcorn.ENDERECO_FILMES, String.valueOf(idFilme));

        Cursor cursor = getContentResolver().query(enderecoFilmeEditar, BdTableFilmes.TODAS_COLUNAS, null, null, null);

        if(!cursor.moveToNext()){
            Toast.makeText(this, "Erro: Não foi possivel reconhecer o filme", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        filme = filme.fromCursor(cursor);

        editTextNomeFilme.setText(filme.getNome_filme());
        editTextClassificacaoFilme.setText(String.valueOf(filme.getClassificacao_filme()));
        editTextAnoFilme.setText(String.valueOf(filme.getAno_filme()));
        editTextDescricaoFilme.setText(filme.getDescricao_filme());
        editTextLink.setText(filme.getLink_trailer_filme());


        int favorito_num = filme.getFavorito_numerico();
        if(favorito_num == 1){
            favorito = true;
        } else if (favorito_num == 0){
            favorito = false;
        }
        int visto_num = filme.getVisto_numerico();
        if(visto_num == 0){
            visto = false;
        } else if(visto_num == 1){
            visto = true;
        }

        switchFavoritoFilme.setChecked(favorito);
        switchVistoFilme.setChecked(visto);

        switchFavoritoFilme.setChecked(favorito);
        switchVistoFilme.setChecked(visto);

        byte[] filmeImageCapaByte = filme.getFoto_capa_filme();
        Bitmap bitmap_filmeImage = BitmapFactory.decodeByteArray(filmeImageCapaByte, 0, filmeImageCapaByte.length);
        imageViewCapaFilme.setImageBitmap(bitmap_filmeImage);

        byte[] filmeImageFundoByte = filme.getFoto_fundo_filme();
        Bitmap bitmap_filmeImageFundo = BitmapFactory.decodeByteArray(filmeImageFundoByte, 0, filmeImageFundoByte.length);
        imageViewFundoFilme.setImageBitmap(bitmap_filmeImageFundo);

        helper = new myDbAdapter(this);

        cursor_categorias = helper.getCategorias();

        atualizaCategoriaSelecionada();
        atualizaAutorSelecionado();

    }

    private void mostraCategoriasSpinner(Cursor cursorCategorias) {
        SimpleCursorAdapter adaptadorCategorias = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_1,
                cursorCategorias,
                new String[]{BdTableCategorias.CAMPO_NOME},
                new int[]{android.R.id.text1}
        );
        spinnerCategorias.setAdapter(adaptadorCategorias);
    }
    private void atualizaCategoriaSelecionada() {
        if(!categoriasCarregadas) return;
        if(categoriaAtualizada) return;

        for (int i = 0; i < spinnerCategorias.getCount(); i++){
            if(spinnerCategorias.getItemIdAtPosition(i) == filme.getCategoria_filme()){
                spinnerCategorias.setSelection(i);
                break;
            }
        }

        categoriaAtualizada = true;
    }


    private void atualizaAutorSelecionado() {
        if(!autoresCarregados) return;
        if(autorAtualizado) return;

        for(int i = 0; i < spinnerAutores.getCount(); i++){
            if(spinnerAutores.getItemIdAtPosition(i) == filme.getAutor_filme()){
                spinnerAutores.setSelection(i);
                break;
            }
        }

        autorAtualizado = true;
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
        spinnerAutores.setAdapter(adaptadorAutores);
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

        String link = editTextLink.getText().toString();

        if (link.trim().isEmpty()) {
            editTextLink.setError("O campo não pode estar vazio!");
            return;
        }

        filme.setFoto_capa_filme(null);
        byte[] imagem_capa = ImagemParaByte(imageViewCapaFilme);
        if(imagem_capa !=null){
            filme.setFoto_capa_filme(imagem_capa);
        } else{
            Toast.makeText(this, "Insira uma imagem para a capa", Toast.LENGTH_SHORT).show();
        }


        filme.setFoto_fundo_filme(null);
        byte[] imagem_fundo = ImagemParaByte(imageViewFundoFilme);
        if(imagem_fundo !=null){
            filme.setFoto_fundo_filme(imagem_fundo);
        } else{
            Toast.makeText(this, "Insira uma imagem para o fundo", Toast.LENGTH_SHORT).show();
        }


        long idAutor = spinnerAutores.getSelectedItemId();
        long idCategoria = spinnerCategorias.getSelectedItemId();

        // guardar os dados
        filme.setNome_filme(nome);
        filme.setCategoria_filme(idCategoria);
        filme.setAutor_filme(idAutor);
        filme.setClassificacao_filme(classificacao);
        filme.setAno_filme(ano);
        filme.setDescricao_filme(descricao);
        filme.setVisto_filme(switchVistoFilme.isChecked());
        filme.setFavorito_filme(switchFavoritoFilme.isChecked());
        filme.setFoto_capa_filme(imagem_capa);
        filme.setFoto_fundo_filme(imagem_fundo);
        filme.setLink_trailer_filme(link);


        try {
            getContentResolver().update(enderecoFilmeEditar, filme.getContentValues(), null, null);

            Toast.makeText(this, "Filme guardado com sucesso", Toast.LENGTH_SHORT).show();
            finish();
            recreate();
        } catch (Exception e) {
            Snackbar.make(
                    editTextNomeFilme,
                    "Erro ao guardar filme",
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
        autoresCarregados = true;
        atualizaAutorSelecionado();

        mostraCategoriasSpinner(cursor_categorias);
        categoriasCarregadas = true;
        atualizaCategoriaSelecionada();

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        autoresCarregados = false;
        autorAtualizado = false;
        mostraAutoresSpinner(null);

        categoriasCarregadas = false;
        categoriaAtualizada = false;
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
