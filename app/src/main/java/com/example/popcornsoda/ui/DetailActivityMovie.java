package com.example.popcornsoda.ui;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.example.popcornsoda.R;

public class DetailActivityMovie extends AppCompatActivity {

    private Button botaoVisto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //busca os dados





        iniViews();

        /*Button btn2 = findViewById(R.id.botao_favorito);

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Adicionado aos Favoritos", Toast.LENGTH_LONG).show();
            }
        });*/


        /*botaoVisto = (Button) findViewById(R.id.botao_visto);

        botaoVisto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Marcado como Visto", Toast.LENGTH_LONG).show();
            }
        });*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater_menu = getMenuInflater();
        inflater_menu.inflate(R.menu.menu_conteudo,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //todo: criar opcoes de alterar e apagar
        switch(item.getItemId()){
            case R.id.itemEditarConteudo:

                return true;

            case R.id.itemEliminarConteudo:

                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }



    void iniViews () {



        String movieTitle = getIntent().getExtras().getString("title");
        int imageResourceId = getIntent().getExtras().getInt("imgURL");
        int imagecover = getIntent().getExtras().getInt("imgCover");
        String movieDescription = getIntent().getExtras().getString("descricao_filme");

        FloatingActionButton play_fab = findViewById(R.id.play_fab);


        ImageView movieThumbnailImg = findViewById(R.id.detailMovie_img);
        ImageView movieCoverImg = findViewById(R.id.detail_movie_cover);
        movieCoverImg = findViewById(R.id.detail_movie_cover);
        TextView tv_title = findViewById(R.id.detail_movie_title);
        TextView tv_description = findViewById(R.id.detail_movie_desc);


        movieThumbnailImg.setImageResource(imageResourceId);






        //configurar animaçao
        movieCoverImg.setAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_animation));
        play_fab.setAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_animation));

        getSupportActionBar().setTitle(movieTitle);

        Glide.with(this).load(imageResourceId).into(movieThumbnailImg);
        Glide.with(this).load(imagecover).into(movieCoverImg);

        tv_title.setText(movieTitle);
        tv_description.setText(movieDescription);



    }
}