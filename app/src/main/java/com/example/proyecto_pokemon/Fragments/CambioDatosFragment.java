package com.example.proyecto_pokemon.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.proyecto_pokemon.Activities.HomeActivity;
import com.example.proyecto_pokemon.Activities.LoginActivity;
import com.example.proyecto_pokemon.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CambioDatosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CambioDatosFragment extends Fragment {

    EditText editTextPhone, edName, edPasPass1, edPass1, edPass2;
    Button btnVolverConformarDatos, btnConformarDatos;
    DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CambioDatosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CambioDatosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CambioDatosFragment newInstance(String param1, String param2) {
        CambioDatosFragment fragment = new CambioDatosFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cambio_datos, container, false);

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();


        edName = view.findViewById(R.id.edName);
        editTextPhone = view.findViewById(R.id.editTextPhone);
        edPasPass1 = view.findViewById(R.id.edPasPass1);
        edPass1 = view.findViewById(R.id.edPass1);
        edPass2 = view.findViewById(R.id.edPass2);

        btnVolverConformarDatos = view.findViewById(R.id.btnVolverConformarDatos);
        btnConformarDatos = view.findViewById(R.id.btnConformarDatos);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUser.getUid());
            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String username = dataSnapshot.child("Name").getValue(String.class);
                    String email = dataSnapshot.child("Email").getValue(String.class);
                    String phone = dataSnapshot.child("Phone").getValue(String.class);
                    String birday = dataSnapshot.child("Cumple").getValue(String.class);

                    // Ahora puedes utilizar los datos guardados en SharedPreferences según sea necesario
                    edName.setText(username);
                    editTextPhone.setText(phone);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Maneja el error de lectura de datos
                }
            });
        }

        btnVolverConformarDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                PerfilFragment perfilfragment = new PerfilFragment();
                fragmentTransaction.replace(R.id.frame_layout_Pokedex, perfilfragment);
                fragmentTransaction.addToBackStack(null); // Esto permite volver al fragmento anterior al presionar el botón de retroceso
                fragmentTransaction.commit();
            }
        });

        btnConformarDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DataSnapshot dataSnapshot;

                String newName = String.valueOf(edName.getText());
                String NewPhone = String.valueOf(editTextPhone.getText());
                String PastPassword = String.valueOf(edPasPass1.getText());
                String NewPassword1 = String.valueOf(edPass1.getText());
                String NewPassword2 = String.valueOf(edPass2.getText());

                if (mDatabase != null) {
                    mDatabase.child("Users").child(mAuth.getUid()).child("Name").setValue(newName);
                    mDatabase.child("Users").child(mAuth.getUid()).child("Phone").setValue(NewPhone);

                    String pastpas = String.valueOf(mDatabase.child("Users").child(mAuth.getUid()).child("Password"));
                    Toast.makeText(getContext(),"Las contraseñas era"+pastpas,Toast.LENGTH_SHORT).show();

                    if(PastPassword.length() != 0 /*&& PastPassword.length() == pastpas*/){
                        if (NewPassword1.length() == NewPassword2.length()){

                            mDatabase.child("Users").child(mAuth.getUid()).child("Password").setValue(NewPassword1);

                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            PerfilFragment perfilfragment = new PerfilFragment();
                            fragmentTransaction.replace(R.id.frame_layout_Pokedex, perfilfragment);
                            fragmentTransaction.addToBackStack(null); // Esto permite volver al fragmento anterior al presionar el botón de retroceso
                            fragmentTransaction.commit();
                        }else{
                            Toast.makeText(getContext(),"Las contraseñas deben ser iguales",Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getContext(),"Error de Cambio de datos",Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(),"No se pudo, Usuario: "+ mAuth.getUid(),Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }
}
