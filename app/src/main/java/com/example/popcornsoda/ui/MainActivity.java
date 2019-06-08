package com.example.popcornsoda.ui;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.popcornsoda.R;

public class MainActivity extends AppCompatActivity {

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
                Toast.makeText(getApplicationContext(), "Secção de Autores", Toast.LENGTH_SHORT).show();
                openAutores();
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
        }
        return super.onOptionsItemSelected(item);
    }

    //Calling Activities

    private void openFilmes() {
        Intent intent1 = new Intent(this, Filmes.class);
        startActivity(intent1);
    }

    private void openSeries() {
        Intent intent2 = new Intent(this, Series.class);
        startActivity(intent2);
    }
    private void openAutores() {
        Intent intent4 = new Intent(this, Autores.class);
        startActivity(intent4);
    }
}
