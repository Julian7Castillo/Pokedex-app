package com.example.proyecto_pokemon.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_pokemon.Models.Pokemon;
import com.example.proyecto_pokemon.R;
import com.squareup.picasso.Picasso;

import java.util.LinkedList;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.ViewHolder> {
    LinkedList<Pokemon> pokemones;
    RecyclerView rvPokemon;
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
        Pokemon pokemon  = pokemones.get(position);

        //Pokemon currentItem = dataList.get(position);

        // Asigna los valores a las vistas dentro del CardView

        /*holder.cardPoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Acción al hacer clic en el CardView
                pokemones selectedItem = dataList.get(position);
                // Aquí puedes hacer lo que necesites con el objeto seleccionado

                // Por ejemplo, puedes abrir una nueva actividad pasando el objeto como parámetro
                Intent intent = new Intent(context, PokemonActivity.class);
                intent.putExtra("selectedItem", selectedItem);
                context.startActivity(intent);
            }
        });*/

        Picasso.with(holder.itemView.getContext())
                .load(pokemon.getSprites().getLarge().toString())
                .resize(750, 750) // Especifica las dimensiones deseadas
                .centerInside()
                .into(holder.imgPoke);
        /*Picasso.with(holder.itemView.getContext())
                .load(pokemon.getSprites().getAnimated().toString())
                .resize(300, 300) // Especifica las dimensiones deseadas
                .centerInside() // Puedes usar centerCrop(), centerInside() o fit() según tus necesidades
                .into(holder.imgPoke);*/
        holder.tvId.setText(pokemon.getNational_number());
        holder.tvName.setText(String.valueOf(pokemon.getName()));
    }

    @Override
    public int getItemCount() {
        return pokemones.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imgPoke;
        TextView tvId, tvName;

        public ViewHolder(@NonNull View itemView){
            super(itemView);

            imgPoke = itemView.findViewById(R.id.imgPoke);
            tvId = itemView.findViewById(R.id.tvId);
            tvName = itemView.findViewById(R.id.tvName);
        }
    }
}
