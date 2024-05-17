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

import com.example.proyecto_pokemon.Activities.HomeActivity;
import com.example.proyecto_pokemon.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CambioDatosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CambioDatosFragment extends Fragment {

    Button btnVolverConformarDatos, btnConformarDatos;

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

        btnVolverConformarDatos = view.findViewById(R.id.btnVolverConformarDatos);
        btnConformarDatos = view.findViewById(R.id.btnConformarDatos);

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
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                PerfilFragment perfilfragment = new PerfilFragment();
                fragmentTransaction.replace(R.id.frame_layout_Pokedex, perfilfragment);
                fragmentTransaction.addToBackStack(null); // Esto permite volver al fragmento anterior al presionar el botón de retroceso
                fragmentTransaction.commit();
            }
        });

        return view;
    }
}