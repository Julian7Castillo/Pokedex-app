package com.example.proyecto_pokemon.Services;

import com.example.proyecto_pokemon.Models.responseApi;

import retrofit2.Call;
import retrofit2.http.GET;

public interface API {
    @GET("pokemons")
    Call<responseApi> getAllPokemon();
}
