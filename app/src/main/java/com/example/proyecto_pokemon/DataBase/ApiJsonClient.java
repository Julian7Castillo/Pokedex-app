package com.example.proyecto_pokemon.DataBase;

import com.example.proyecto_pokemon.Utils.Server;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiJsonClient {
    private static Retrofit retrofitPokemon;

    public static Retrofit getClient(){
        if (retrofitPokemon == null){
            retrofitPokemon = new Retrofit.Builder()
                    .baseUrl(Server.url_Pokemones)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofitPokemon;
    }
}
