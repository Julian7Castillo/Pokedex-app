package com.example.proyecto_pokemon.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.proyecto_pokemon.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

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

        String email = edCorreoFor.getText().toString().trim();

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            edCorreoFor.setError("Correo Invalido");
            return;
        }

        sendEmail(email);

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void sendEmail(String email){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String emailAddress = email;

        auth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(ForgotPasswordActivity.this, "Correo Enviado Correctamente", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();

                        }else{
                            Toast.makeText(ForgotPasswordActivity.this, "Correo Invalido", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}