package com.example.proyecto_pokemon.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.proyecto_pokemon.R;

public class LoadScreenActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //no visualizar la barra superior
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_load_screen);

        //traemos los elementos del xml
        TextView tvBy = findViewById(R.id.tvBy);
        TextView tvCompany = findViewById(R.id.tvCompany);
        ImageView imgLogo= findViewById(R.id.imgLogo);

        //animations
        Animation animacion1 = AnimationUtils.loadAnimation(this, R.anim.desplazamiento_arriba);
        Animation animacion2 = AnimationUtils.loadAnimation(this, R.anim.desplazamiento_abajo);

        //asignar animaciones
        tvBy.setAnimation(animacion2); ;
        tvCompany.setAnimation(animacion2);
        imgLogo.setAnimation(animacion1);

        //cantidad de tiempo que debe demorar la animacion y screem a la cual debe pasar
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(LoadScreenActivity.this, IntroductionActivity.class);
                startActivity(intent);
                finish();
            }

        }, 2000);
    }
}