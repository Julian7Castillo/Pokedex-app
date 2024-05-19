package com.example.proyecto_pokemon.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyecto_pokemon.Activities.HomeActivity;
import com.example.proyecto_pokemon.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.zip.Inflater;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PerfilFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PerfilFragment extends Fragment {

    Button btnCambiar;
    TextView tvUser, tvEmail, tvTelefono,tvCumple;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();
    private DatabaseReference mDatabase;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PerfilFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PerfilFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PerfilFragment newInstance(String param1, String param2) {
        PerfilFragment fragment = new PerfilFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        tvUser = view.findViewById(R.id.tvUser);
        tvEmail = view.findViewById(R.id.tvEmail);
        tvTelefono= view.findViewById(R.id.tvTelefono);
        tvCumple= view.findViewById(R.id.tvCumple);

        btnCambiar = view.findViewById(R.id.btnCambiar);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DataSnapshot dataSnapshot = snapshot.child("Users");

                final String getUserName = dataSnapshot.child("Name").getValue(String.class);
                final String getUserEmail = dataSnapshot.child("Email").getValue(String.class);
                final String getUserPhone = dataSnapshot.child("Phone").getValue(String.class);
                final String getUserBirday = dataSnapshot.child("Cumple").getValue(String.class);

                Toast.makeText(getContext(), "Nombre de usuario"+getUserEmail, Toast.LENGTH_LONG).show();

                tvUser.setText(getUserName);
                tvEmail.setText(getUserEmail);
                tvTelefono.setText(getUserPhone);
                tvCumple.setText(getUserBirday);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnCambiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                CambioDatosFragment cambiodatosfragment = new CambioDatosFragment();
                fragmentTransaction.replace(R.id.frame_layout_Pokedex, cambiodatosfragment);
                fragmentTransaction.addToBackStack(null); // Esto permite volver al fragmento anterior al presionar el botón de retroceso
                fragmentTransaction.commit();
            }
        });
        // Inflate the layout for this fragment
        return view;
    }
}