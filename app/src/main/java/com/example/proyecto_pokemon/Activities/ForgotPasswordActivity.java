package com.example.proyecto_pokemon.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.proyecto_pokemon.R;

public class ForgotPasswordActivity extends AppCompatActivity {

    EditText edCorreoFor;
    Button btnVolver1, btnRecuperar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);

        edCorreoFor = findViewById(R.id.edCorreoFor);
        btnVolver1 = findViewById(R.id.btnVolver1);
        btnRecuperar = findViewById(R.id.btnRecuperar);

        btnVolver1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { volver1(); }
        });

        btnRecuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { recuperar(); }
        });
    }

    private void volver1 (){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void recuperar (){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}