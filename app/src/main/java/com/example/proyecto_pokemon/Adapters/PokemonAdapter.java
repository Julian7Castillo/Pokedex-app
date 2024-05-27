package com.example.proyecto_pokemon.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_pokemon.Fragments.PokemonFragment;
import com.example.proyecto_pokemon.Models.Pokemon;
import com.example.proyecto_pokemon.R;
import com.squareup.picasso.Picasso;

import java.util.LinkedList;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.ViewHolder> {
    private LinkedList<Pokemon> pokemones;
    private LinkedList<Pokemon> filtroPokemones;
    RecyclerView rvPokemon;
    Context context;
    private PokemonAdapter pokemonAdapter;
    CardView cardPoke;
    public PokemonAdapter(LinkedList<Pokemon> pokemones, Context context){
        this.pokemones = pokemones;
        this.context = context;
        this.filtroPokemones = new LinkedList<>(pokemones);
    }

    @NonNull
    @Override
    public PokemonAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_pokemon, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonAdapter.ViewHolder holder, int position) {

        if ( !filtroPokemones.isEmpty() && position < filtroPokemones.size()){

            Pokemon pokemonList  = filtroPokemones.get(position);

            Picasso.with(holder.itemView.getContext())
                    .load(pokemonList.getSprites().getLarge().toString())
                    .resize(750, 750) // Especifica las dimensiones deseadas
                    .centerInside()
                    .into(holder.imgPoke);
            holder.tvId.setText(pokemonList.getNational_number());
            holder.tvName.setText(String.valueOf(pokemonList.getName()));

            holder.layout_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickPokemon(pokemonList);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return filtroPokemones.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imgPoke;
        TextView tvId, tvName;
        LinearLayout layout_card;

        public ViewHolder(@NonNull View itemView){
            super(itemView);

            layout_card = itemView.findViewById(R.id.layout_card);
            imgPoke = itemView.findViewById(R.id.imgPoke);
            tvId = itemView.findViewById(R.id.tvId);
            tvName = itemView.findViewById(R.id.tvName);
        }
    }
    private void onClickPokemon(Pokemon pokemonList) {

        Fragment fragment = new PokemonFragment();
        Bundle bundle = new Bundle();

        bundle.putString("number", pokemonList.getNational_number());
        bundle.putString("name", pokemonList.getName());
        bundle.putStringArray("type", pokemonList.getType());
        bundle.putString("sprite", pokemonList.getSprites().getLarge());
        bundle.putString("total", pokemonList.getTotal());
        bundle.putString("hp", pokemonList.getHp());
        bundle.putString("attack", pokemonList.getAttack());
        bundle.putString("defense", pokemonList.getDefense());
        bundle.putString("sp_atk", pokemonList.getSp_atk());
        bundle.putString("sp_def", pokemonList.getSp_def());
        bundle.putString("speed", pokemonList.getSpeed());

        fragment.setArguments(bundle);

        ((FragmentActivity) context).getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_layout_Pokedex, fragment)
                .addToBackStack(null)
                .commit();
    }

    public void filter(String text) {
        filtroPokemones.clear();
        if (text.isEmpty()) {
            filtroPokemones.addAll(pokemones);
        } else {
            //text = text.toLowerCase();
            for (Pokemon item : pokemones) {
                if (item.getName().toLowerCase().contains(text.toLowerCase()) || item.getNational_number().toLowerCase().contains(text.toLowerCase())) {
                    filtroPokemones.add(item);

                }
            }
        }
        notifyDataSetChanged();
    }
}
