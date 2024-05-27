package com.example.proyecto_pokemon.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.proyecto_pokemon.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    EditText edName,edCorreo, ed_Cumpleanios,edPass1, edPass2, edPhone;
    Button btnVolver2, btnRegistro;
    CheckBox cbTerminos;
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        edName = findViewById(R.id.edName);
        edCorreo = findViewById(R.id.edCorreo);
        ed_Cumpleanios = findViewById(R.id.ed_Cumpleanios);
        edPass1 = findViewById(R.id.edPass1);
        edPass2 = findViewById(R.id.edPass2);
        btnVolver2 = findViewById(R.id.btnVolver2);
        btnRegistro = findViewById(R.id.btnRegistro);
        cbTerminos = findViewById(R.id.cbTerminos);
        edPhone = findViewById(R.id.edPhone);

        btnVolver2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { volver2(); }
        });
        ed_Cumpleanios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {showCalendar();}
        });
        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { registro(); }
        });
        cbTerminos.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) { TerminosYCondiciones(); }
        });
    }

    private void volver2 (){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private  void registro() {

        //Obtener texto de los campos
        String nombre = edName.getText().toString();
        String correo = edCorreo.getText().toString();
        String Phone = edPhone.getText().toString();
        String Cumple = ed_Cumpleanios.getText().toString();
        String clave1 = edPass1.getText().toString();
        String clave2 = edPass2.getText().toString();

        if (nombre.isEmpty()) {
            edName.setError("Campo de Nombre vacio");

        } else if (Phone.isEmpty()) {
            edPhone.setError("Campo de Telefono vacio");

        } else if (correo.isEmpty()) {
            edCorreo.setError("Campo de correo vacio");

        } else if (Cumple.isEmpty()) {
            ed_Cumpleanios.setError("Campo de cumpleaños vacio");

        } else if (clave1.isEmpty()) {
            edPass1.setError("Campo de contraseña vacio");

        } else if (clave2.isEmpty()) {
            edPass2.setError("Campo de confirmacion de contraseña vacio");

        } else if (!cbTerminos.isChecked()) {
            cbTerminos.setError("Campo de confirmacion de contraseña vacio");

        } else if (clave2.length() <= 6){
            Toast.makeText(this, "Las contraseñas deben tener almenos 7 caracteres", Toast.LENGTH_SHORT).show();

        }else if (!clave1.equals(clave2)) {
            //mensaje de validacion de ocntraseñas igualales
            Toast.makeText(this, "Las contraseñas deben ser iguales", Toast.LENGTH_SHORT).show();
        } else {
            AlertDialog.Builder alert = new AlertDialog.Builder(RegisterActivity.this);
            alert.setTitle("Confirmar").setMessage("¿Sus datos son Correctos? ").setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mAuth.createUserWithEmailAndPassword(correo, clave2).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){

                                        Map<String, Object> map = new HashMap<>();
                                        map.put("Name", nombre);
                                        map.put("Email", correo);
                                        map.put("Phone", Phone);
                                        map.put("Birthday", Cumple);
                                        map.put("Password", clave2);
                                        map.put("Favotite", new ArrayList<String>());
                                        map.put("Team", new ArrayList<Map<String, Object>>());

                                        String id = mAuth.getCurrentUser().getUid();

                                        mDatabase.child("Users").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task2) {
                                                if (task2.isSuccessful()){
                                                    Toast.makeText(RegisterActivity.this, "Datos Guardados correctamente", Toast.LENGTH_SHORT).show();
                                                    //intent.putExtra("correo", correo);
                                                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                                    finish();
                                                }
                                                else {
                                                    Toast.makeText(RegisterActivity.this, "No se pudieron crear los datos correctamente", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    }else{
                                        Toast.makeText(RegisterActivity.this, "No se pudo registrar este usuario", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    })
                    .setNegativeButton("No", null)
                    .setCancelable(false).show();
        }
    }
    private void showCalendar(){
        Calendar calendario = Calendar.getInstance();
        int year = calendario.get(Calendar.YEAR);
        int month = calendario.get(Calendar.MONTH);
        int dayOfMonth = calendario.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(RegisterActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                ed_Cumpleanios.setText(year+" - "+(month+1)+" - "+dayOfMonth);
            }
        },year, month, dayOfMonth);
        dialog.show();
    }

    private void  TerminosYCondiciones(){
        Toast.makeText(RegisterActivity.this, "Acepto terminos y condiciones", Toast.LENGTH_SHORT).show();
    }
}