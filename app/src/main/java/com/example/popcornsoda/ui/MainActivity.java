package com.example.popcornsoda.ui;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.popcornsoda.BdPopcorn.ContentProviderPopcorn;
import com.example.popcornsoda.R;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    private Uri enderecoAutorApagar;
    private Uri enderecoFilmeApagar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Botoes
        Button btn = findViewById(R.id.botaofilmes);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(), "Secção de Filmes", Toast.LENGTH_SHORT).show();
                openFilmes();
                //ApagarFilme();
            }
        });

        Button btn2 = findViewById(R.id.botaoseries);

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Secção de Séries", Toast.LENGTH_SHORT).show();
                openSeries();
            }
        });

        Button btn3 = findViewById(R.id.botaoautores);

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Secção de Realizadores", Toast.LENGTH_SHORT).show();
                openAutores();
                //ApagarAutor(1);
            }
        });

    }

    //Menu

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater_menu = getMenuInflater();
        inflater_menu.inflate(R.menu.menu_principal,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.itemSair){
            finish();
        } else if(item.getItemId() == R.id.adicionarCategoria){
            Intent intent5 = new Intent(this, AddCategory.class);
            startActivity(intent5);
        }
        return super.onOptionsItemSelected(item);
    }

    //Calling Activities
    Intent intent;
    private void openFilmes() {
        intent = new Intent(this, Filmes.class);
        startActivity(intent);
    }

    private void openSeries() {
        intent = new Intent(this, Series.class);
        startActivity(intent);
    }
    private void openAutores() {
        intent = new Intent(this, Autores.class);
        startActivity(intent);
    }

    private void ApagarFilme(){
        try {
            enderecoFilmeApagar = Uri.withAppendedPath(ContentProviderPopcorn.ENDERECO_FILMES, String.valueOf(1));
            int filmesapagados = getContentResolver().delete(enderecoFilmeApagar, null, null);

            if (filmesapagados == 1) {
                Toast.makeText(this, "Filme excluido com sucesso", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Erro: Não foi possível excluir o filme", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void ApagarAutor(int i){
        try {

            enderecoAutorApagar = Uri.withAppendedPath(ContentProviderPopcorn.ENDERECO_AUTORES, String.valueOf(i));


            int autoresApagados = getContentResolver().delete(enderecoAutorApagar, null, null);

            if (autoresApagados == 1) {
                Toast.makeText(this, "Autor excluido com sucesso", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Erro: Não foi possível excluir o autor", Toast.LENGTH_LONG).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
