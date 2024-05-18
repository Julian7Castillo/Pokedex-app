package com.example.proyecto_pokemon.Adapters;

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_pokemon.Fragments.PokemonFragment;
import com.example.proyecto_pokemon.Models.Pokemon;
import com.example.proyecto_pokemon.R;
import com.squareup.picasso.Picasso;

import java.util.LinkedList;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.ViewHolder> {
    LinkedList<Pokemon> pokemones;
    RecyclerView rvPokemon;
    Context context;
    CardView cardPoke;
    public PokemonAdapter(LinkedList<Pokemon> pokemones){ this.pokemones = pokemones;}

    @NonNull
    @Override
    public PokemonAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_pokemon, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonAdapter.ViewHolder holder, int position) {

        Pokemon pokemonList  = pokemones.get(position);

        Picasso.with(holder.itemView.getContext())
                .load(pokemonList.getSprites().getLarge().toString())
                .resize(750, 750) // Especifica las dimensiones deseadas
                .centerInside()
                .into(holder.imgPoke);
        /*Picasso.with(holder.itemView.getContext())
                .load(pokemon.getSprites().getAnimated().toString())
                .resize(300, 300) // Especifica las dimensiones deseadas
                .centerInside() // Puedes usar centerCrop(), centerInside() o fit() según tus necesidades
                .into(holder.imgPoke);*/
        holder.tvId.setText(pokemonList.getNational_number());
        holder.tvName.setText(String.valueOf(pokemonList.getName()));

        holder.layout_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickPerson(pokemonList);
            }
        });
    }

    @Override
    public int getItemCount() {
        return pokemones.size();
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
    private void onClickPerson(Pokemon pokemonList) {
        Intent intent = new Intent(context, PokemonFragment.class);

        intent.putExtra("number", pokemonList.getNational_number());
        intent.putExtra("name", pokemonList.getName());
        intent.putExtra("type", pokemonList.getType());
        //intent.putExtra("sprite", pokemonList.getSprites());
        intent.putExtra("total", pokemonList.getTotal());
        intent.putExtra("hp", pokemonList.getHp());
        intent.putExtra("attack", pokemonList.getAttack());
        intent.putExtra("defense", pokemonList.getDefense());
        intent.putExtra("sp_atk", pokemonList.getSp_atk());
        intent.putExtra("sp_def", pokemonList.getSp_def());
        intent.putExtra("speed", pokemonList.getSpeed());

        context.startActivity(intent);
    }
}
