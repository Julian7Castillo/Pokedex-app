package com.example.proyecto_pokemon.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.proyecto_pokemon.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import kotlin.jvm.internal.Ref;

public class LoginActivity extends AppCompatActivity {

    EditText edCorreo, edPass;
    Button btnRegistrarse, btnOlvidar, btnIniciar;
    private FirebaseAuth mAuth;
    private final String TAG = "Autenticacion";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        edCorreo = findViewById(R.id.edCorreo);
        edPass = findViewById(R.id.edPass);
        btnRegistrarse = findViewById(R.id.btnRegistrarse);
        btnOlvidar = findViewById(R.id.btnOlvidar);
        btnIniciar = findViewById(R.id.btnIniciar);

        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { Registro(); }
        });

        btnOlvidar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { olvidarPass(); }
        });

        btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { iniciarSesion(); }
        });
    }
    private void Registro(){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
    private void olvidarPass(){
        Intent intent = new Intent(this, ForgotPasswordActivity.class);
        startActivity(intent);
    }

    //validacion de inicio de secion con firebase
    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            updateUI(currentUser);
        }
    }
    private void iniciarSesion() {
        String email = edCorreo.getText().toString();
        String password = edPass.getText().toString();
        if(!email.isEmpty()){
            if(!password.isEmpty()){
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "createUserWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Toast.makeText(LoginActivity.this,"Correo: "+ user.getEmail(),Toast.LENGTH_SHORT).show();
                                    updateUI(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(LoginActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                    updateUI(null);
                                }
                            }
                        });
            }else{
                edPass.setError("Campo obligatorio");
            }
        }else{
            edCorreo.setError("Campo obligatorio");
        }
    }

    private void updateUI(FirebaseUser user) {
        if (user != null){
            System.out.println(user.toString());
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        }
    }
}