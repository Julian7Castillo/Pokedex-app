package com.example.proyecto_pokemon.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.proyecto_pokemon.Activities.HomeActivity;
import com.example.proyecto_pokemon.Activities.LoginActivity;
import com.example.proyecto_pokemon.Adapters.PokemonAdapter;
import com.example.proyecto_pokemon.DataBase.ApiJsonClient;
import com.example.proyecto_pokemon.Models.Pokemon;
import com.example.proyecto_pokemon.Models.responseApi;
import com.example.proyecto_pokemon.R;
import com.example.proyecto_pokemon.Services.API;
import com.google.firebase.auth.FirebaseAuth;

import java.util.LinkedList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PokedexFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PokedexFragment extends Fragment {
    //elementos necesarios
    PokemonAdapter pokemonAdapter;
    LinkedList<Pokemon> pokemones;
    RecyclerView rvPokemon;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PokedexFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PokedexFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PokedexFragment newInstance(String param1, String param2) {
        PokedexFragment fragment = new PokedexFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pokedex, container, false);

        // Inicializar el RecyclerView
        rvPokemon = view.findViewById(R.id.rvPokemon);
        rvPokemon.setLayoutManager(new LinearLayoutManager(getContext()));

        // Obtener y mostrar los Pokémon
        obtenerPokemones();

        // Devolver la vista del fragmento
        return view;
    }

    //constructor
    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        rvPokemon = findViewById(R.id.rvPokemon);
        rvPokemon.setLayoutManager(new LinearLayoutManager(getContext()));

        obtenerPokemones();
    }*/

    //adaptador de cardView
    LinkedList<Pokemon> obtenerPokemones(){
        pokemones = new LinkedList<>();
        API api = ApiJsonClient.getClient().create(API.class);

        Call<responseApi> call = api.getAllPokemon();

        call.enqueue(new Callback<responseApi>() {
            @Override
            public void onResponse(Call<responseApi> call, Response<responseApi> response) {
                if(response.isSuccessful()){
                    //Toast.makeText(HomeActivity.this, ""+response, Toast.LENGTH_LONG).show();
                    pokemonAdapter = new PokemonAdapter(response.body().getPokemones());
                    rvPokemon.setAdapter(pokemonAdapter);
                }else{
                    Toast.makeText(getContext(), "Error de usuario. Código: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<responseApi> call, Throwable t) {
                System.out.println("Error "+t.toString());
                Toast.makeText(getContext(), "Error en el servidor ", Toast.LENGTH_LONG).show();
            }
        });

        return pokemones;
    }
}