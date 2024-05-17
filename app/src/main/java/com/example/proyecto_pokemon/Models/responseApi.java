package com.example.proyecto_pokemon.Models;

import com.google.gson.annotations.SerializedName;

import java.util.LinkedList;

public class responseApi {
    @SerializedName("results")
    private LinkedList<Pokemon> pokemones;
    public LinkedList<Pokemon> getPokemones(){return pokemones;}
    public void setPokemones(LinkedList<Pokemon> pokemones){this.pokemones = pokemones;}
}
