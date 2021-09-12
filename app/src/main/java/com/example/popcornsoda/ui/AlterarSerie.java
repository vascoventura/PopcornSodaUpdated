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
import com.example.popcornsoda.BdPopcorn.BdTableSeries;
import com.example.popcornsoda.BdPopcorn.ContentProviderPopcorn;
import com.example.popcornsoda.R;
import com.example.popcornsoda.adapters.myDbAdapter;
import com.example.popcornsoda.models.Serie;
import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Calendar;

public class AlterarSerie extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int ID_CURSO_LOADER_AUTORES = 0;
    private static final int REQUEST_CODE_GALLERY = 399;

    private EditText editTextNomeSerie;
    private EditText editTextLink;
    private EditText editTextClassificacaoSerie;
    private EditText editTextAnoSerie;
    private EditText editTextTemporadas;
    private EditText editTextDescricaoSerie;

    private Spinner spinnerAutores;
    private Spinner spinnerCategorias;

    private Switch switchFavoritoSerie;
    private Switch switchVistoSerie;


    private ImageView imageViewCapaSerie;
    private ImageView imageViewFundoSerie;
    private Button botaoCapaAlterarSerie;
    private Button botaoFundoAlterarSerie;

    private boolean estadoSwitchFavoritos;
    private boolean estadoSwitchVistos;


    private Serie serie = null;

    private boolean autoresCarregados = false;
    private boolean autorAtualizado = false;

    private myDbAdapter helper;
    private boolean categoriasCarregadas = false;
    private boolean categoriaAtualizada = false;

    private Uri enderecoSerieEditar;

    private double classificacao;
    private int ano;

    private int acao_botao = 0;

    public int getAcao_botao() {
        return acao_botao;
    }

    public void setAcao_botao(int acao_botao) {
        this.acao_botao = acao_botao;
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
                    imageViewCapaSerie.setImageBitmap(bitmap);
                    setAcao_botao(0);
                } else if(getAcao_botao() == 2){
                    imageViewFundoSerie.setImageBitmap(bitmap);
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
        setContentView(R.layout.activity_alterar_serie);

        editTextNomeSerie = (EditText) findViewById(R.id.editText_nome_serie_alterar);
        editTextClassificacaoSerie = (EditText) findViewById(R.id.editText_classificacao_serie_alterar);
        editTextAnoSerie = (EditText) findViewById(R.id.editText_ano_serie_alterar);
        editTextTemporadas = (EditText) findViewById(R.id.editText_temporadas_serie_alterar);
        editTextDescricaoSerie = (EditText) findViewById(R.id.editText_descricao_serie_alterar);
        editTextLink = (EditText) findViewById(R.id.editTextLink_ser_alterar) ;

        spinnerAutores = (Spinner) findViewById(R.id.spinnerAutores_series_alterar);
        spinnerCategorias = (Spinner) findViewById(R.id.spinnerCategorias_series_alterar);

        imageViewCapaSerie = (ImageView) findViewById(R.id.foto_capa_alterar_serie);
        imageViewFundoSerie = (ImageView) findViewById(R.id.foto_fundo_alterar_serie);

        switchFavoritoSerie = (Switch) findViewById(R.id.botao_favorito_alterar_serie);
        switchVistoSerie = (Switch) findViewById(R.id.botao_visto_alterar_serie);

        botaoCapaAlterarSerie = (Button) findViewById(R.id.botao_capa_alterar_serie);
        botaoFundoAlterarSerie = (Button) findViewById(R.id.botao_fundo_alterar_serie);

        getSupportLoaderManager().initLoader(ID_CURSO_LOADER_AUTORES, null, this);

        botaoCapaAlterarSerie.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(AlterarSerie.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_GALLERY);
                setAcao_botao(1);
            }
        });

        botaoFundoAlterarSerie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(AlterarSerie.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_GALLERY);
                setAcao_botao(2);
            }
        });

        Intent intent = getIntent();

        long idSerie = intent.getLongExtra(Series.ID_SERIE, -1);

        if (idSerie == -1) {
            Toast.makeText(this, "Erro: não foi possível reconhcer a série", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        enderecoSerieEditar = Uri.withAppendedPath(ContentProviderPopcorn.ENDERECO_SERIES, String.valueOf(idSerie));

        Cursor cursor = getContentResolver().query(enderecoSerieEditar, BdTableSeries.TODAS_COLUNAS, null, null, null);

        if (!cursor.moveToNext()) {
            Toast.makeText(this, "Erro: não foi possível reconhecer a série", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        serie = serie.fromCursor(cursor);

        editTextNomeSerie.setText(serie.getNome_serie());
        editTextClassificacaoSerie.setText(String.valueOf(serie.getClassificacao_serie()));
        editTextAnoSerie.setText(String.valueOf(serie.getAno_serie()));
        editTextTemporadas.setText(String.valueOf(serie.getTemporadas()));
        editTextDescricaoSerie.setText(serie.getDescricao_serie());
        editTextLink.setText(serie.getLink_trailer_serie());

        switchFavoritoSerie.setChecked(serie.isFavorito_serie());
        switchVistoSerie.setChecked(serie.isVisto_serie());

        byte[] serieImageCapaByte = serie.getFoto_capa_serie();
        Bitmap bitmap_serieImage = BitmapFactory.decodeByteArray(serieImageCapaByte, 0, serieImageCapaByte.length);
        imageViewCapaSerie.setImageBitmap(bitmap_serieImage);

        byte[] serieImageFundoByte = serie.getFoto_fundo_serie();
        Bitmap bitmap_serieImageFundo = BitmapFactory.decodeByteArray(serieImageFundoByte, 0, serieImageFundoByte.length);
        imageViewFundoSerie.setImageBitmap(bitmap_serieImageFundo);


        helper = new myDbAdapter(this);

        Cursor cursor_categorias = helper.getCategorias();
        mostraCategoriasSpinner(cursor_categorias);
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
            if(spinnerCategorias.getItemIdAtPosition(i) == serie.getCategoria_serie()){
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
            if(spinnerAutores.getItemIdAtPosition(i) == serie.getAutor_serie()){
                spinnerAutores.setSelection(i);
                break;
            }
        }

        autorAtualizado = true;
    }

    @Override
    protected void onResume() {
        getSupportLoaderManager().restartLoader(ID_CURSO_LOADER_AUTORES, null, this);

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

        String nome = editTextNomeSerie.getText().toString();

        if (nome.trim().isEmpty()) {
            editTextNomeSerie.setError("O campo não pode estar vazio!");
            return;
        }

        String strClassificacao = editTextClassificacaoSerie.getText().toString();

        if (strClassificacao.trim().isEmpty()) {
            editTextClassificacaoSerie.setError("O campo Não Pode Estar Vazio!");
            return;
        }

        double classificacao1 = Double.parseDouble(strClassificacao);
        try {
            if(classificacao1<0.0 ||  classificacao1>10.0){
                editTextClassificacaoSerie.setError("Classificação Não Aceitável");
                return;
            }else{
                classificacao = classificacao1;
            }
        } catch (NumberFormatException e) {
            editTextClassificacaoSerie.setError("Campo Inválido");
            return;
        }

        String strAno = editTextAnoSerie.getText().toString();

        if (strAno.trim().isEmpty()) {
            editTextAnoSerie.setError("O campo Não Pode Estar Vazio!");
            return;
        }

        try {
            int ano_atual = Calendar.getInstance().get(Calendar.YEAR);
            int ano1 = Integer.parseInt(strAno);
            if(ano1<1850 || ano1>ano_atual){
                editTextAnoSerie.setError("Ano Introduzido Não Aceitável");
                return;
            }else{
                ano = ano1;
            }

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

        String link = editTextLink.getText().toString();

        if (link.trim().isEmpty()) {
            editTextLink.setError("O campo não pode estar vazio!");
            return;
        }

        serie.setFoto_capa_serie(null);
        byte[] imagem_capa = ImagemParaByte(imageViewCapaSerie);
        if(imagem_capa !=null){
            serie.setFoto_capa_serie(imagem_capa);
        } else{
            Toast.makeText(this, "Insira uma imagem para a capa", Toast.LENGTH_SHORT).show();
        }


        serie.setFoto_fundo_serie(null);
        byte[] imagem_fundo = ImagemParaByte(imageViewFundoSerie);
        if(imagem_fundo !=null){
            serie.setFoto_fundo_serie(imagem_fundo);
        } else{
            Toast.makeText(this, "Insira uma imagem para o fundo", Toast.LENGTH_SHORT).show();
        }

        if(estadoSwitchFavoritos){
            serie.setFavorito_serie(true);
        } else{
            serie.setFavorito_serie(false);
        }

        if(estadoSwitchVistos){
            serie.setVisto_serie(true);
        } else{
            serie.setVisto_serie(false);
        }

        long idAutor = spinnerAutores.getSelectedItemId();
        long idCategoria = spinnerCategorias.getSelectedItemId();


        // guardar os dados

        serie.setNome_serie(nome);
        serie.setAutor_serie(idAutor);
        serie.setCategoria_serie(idCategoria);
        serie.setClassificacao_serie(classificacao);
        serie.setAno_serie(ano);
        serie.setTemporadas(temporada);
        serie.setDescricao_serie(descricao);
        serie.setLink_trailer_serie(link);


        try {
            getContentResolver().update(enderecoSerieEditar, serie.getContentValues(), null, null);

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
        autoresCarregados = true;
        atualizaAutorSelecionado();

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        autoresCarregados = false;
        autorAtualizado = false;
        mostraAutoresSpinner(null);

    }

    private byte[] ImagemParaByte(ImageView image){
        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        return byteArray;
    }
}