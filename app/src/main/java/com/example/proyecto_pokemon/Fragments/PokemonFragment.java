package com.example.proyecto_pokemon.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyecto_pokemon.Activities.RegisterActivity;
import com.example.proyecto_pokemon.Models.Pokemon;
import com.example.proyecto_pokemon.Models.Sprites;
import com.example.proyecto_pokemon.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PokemonFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PokemonFragment extends Fragment {

    TextView tvId, tvName, tvTotal, tvVida, tvAtaque, tvDefensa, tvVelocidad, tvEspcialAttack, tvEspcialDefense;
    Context context;
    ImageView iwPoke;
    FloatingActionButton fabFavorite, fabAdd;
    Button btnTipo1, btnTipo2;

    //Variables globales
    private boolean isIconChanged = false;
    private boolean isInTeam = false;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PokemonFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PokemonFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PokemonFragment newInstance(String param1, String param2) {
        PokemonFragment fragment = new PokemonFragment();
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

    @SuppressLint({"MissingInflatedId", "ResourceAsColor"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pokemon, container, false);

        tvId = view.findViewById(R.id.tvId);
        tvName = view.findViewById(R.id.tvName);
        iwPoke = view.findViewById(R.id.iwPoke);
        tvTotal = view.findViewById(R.id.tvTotal);
        tvVida = view.findViewById(R.id.tvVida);
        tvAtaque = view.findViewById(R.id.tvAtaque);
        tvDefensa = view.findViewById(R.id.tvDefensa);
        tvVelocidad = view.findViewById(R.id.tvVelocidad);
        tvEspcialAttack = view.findViewById(R.id.tvEspcialAttack);
        tvEspcialDefense = view.findViewById(R.id.tvEspcialDefense);

        btnTipo1 = view.findViewById(R.id.btnTipo1);
        btnTipo2 = view.findViewById(R.id.btnTipo2);

        fabFavorite = view.findViewById(R.id.fabFavorite);
        fabAdd = view.findViewById(R.id.fabAdd);

        // Obtener los datos del Bundle
        Bundle bundle = getArguments();
        if (bundle != null) {
            final String number = bundle.getString("number");
            final String name = bundle.getString("name");
            final String sprite = bundle.getString("sprite");
            final String[] type = bundle.getStringArray("type");
            final String total = bundle.getString("total");
            final String vida = bundle.getString("hp");
            final String attack = bundle.getString("attack");
            final String defense = bundle.getString("defense");
            final String sp_atk = bundle.getString("sp_atk");
            final String sp_def = bundle.getString("sp_def");
            final String speed = bundle.getString("speed");

            // Cargar imagen predeterminada si getProfilePicture es nulo o vacío
            if (iwPoke != null) {
                if (!sprite.isEmpty()) {
                    Picasso.with(context)
                            .load(sprite)
                            .resize(650, 650) // Especifica las dimensiones deseadas
                            .centerInside()
                            .into(iwPoke);
                }
            }else {
                Toast.makeText(getContext(), "imgPoke is null. Check the layout ID or the inflation process.", Toast.LENGTH_SHORT).show();
            }

            // Usa los datos recibidos
            tvId.setText(number);
            tvName.setText(name);
            tvTotal.setText(total);
            tvVida.setText(vida);
            tvAtaque.setText(attack);
            tvDefensa.setText(defense);
            tvVelocidad.setText(speed);
            tvEspcialAttack.setText(sp_atk);
            tvEspcialDefense.setText(sp_def);
            btnTipo1.setText(type[0]);
            System.out.println(type.length);
            if ((type.length == 2)){
                btnTipo2.setText(type[1]);
            }else{
                btnTipo2.setVisibility(View.GONE);
            }

            if (type[0] == "Grass"){
                btnTipo1.setBackgroundColor(R.color.blue);
            }

        }else{
            Toast.makeText(getContext(), "No se enontro la informacion del Pokemon", Toast.LENGTH_SHORT).show();
        }

        fabFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {favorito();}
        });

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {AgregarEquipo();}
        });

        return view;
    }

    private void favorito(){

        String number = "";
        String name = "" ;
        //Sprites sprite ="";
        String[] type = new String[]{""};
        String total = "";
        String vida = "";
        String attack = "";
        String defense = "";
        String sp_atk = "";
        String sp_def = "";
        String speed = "";

        Boolean estado = false;

        Bundle bundle = getArguments();
        if (bundle != null) {
            number = bundle.getString("number");
            name = bundle.getString("name");
            //sprite = bundle.getString("spritecomplete");
            type = bundle.getStringArray("type");
            total = bundle.getString("total");
            vida = bundle.getString("hp");
            attack = bundle.getString("attack");
            defense = bundle.getString("defense");
            sp_atk = bundle.getString("sp_atk");
            sp_def = bundle.getString("sp_def");
            speed = bundle.getString("speed");
        }else{
            Toast.makeText(getContext(), "No se enontro la informacion del Pokemon", Toast.LENGTH_SHORT).show();
        }

        if (estado == false) {
            if (isIconChanged) {
                fabFavorite.setImageResource(R.drawable.icon_favorite_border_24);
                Toast.makeText(getContext(), "Pokemon a dejado de ser favorito", Toast.LENGTH_SHORT).show();
            } else {
                fabFavorite.setImageResource(R.drawable.icon_favorite_24);

                //Pokemon favorito = new Pokemon(number,name,type,sprite,total,vida,attack,defense,sp_atk,sp_def,speed);

                Toast.makeText(getContext(), "Pokemon seleccionado como favoritn", Toast.LENGTH_SHORT).show();
            }
            isIconChanged = !isIconChanged;

        }else{
            AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
            alert.setTitle("Confirmar").setMessage("Ya tienes un pokemon favorito ¿Estas seguro de remplazarlo? ").setPositiveButton("Si", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(getContext(), "Pokemn seleccionado como favoriton", Toast.LENGTH_SHORT).show();
                }
            })
                    .setNegativeButton("No", null)
                    .setCancelable(false).show();
        }
    }

    private void AgregarEquipo() {
        Boolean estado = false;

        if (estado == false) {
            if (isInTeam) {
                fabAdd.getDrawable().setColorFilter(null);
                Toast.makeText(getContext(), "El Pokemon a dejado el  equipo", Toast.LENGTH_SHORT).show();

            } else {
                fabAdd.getDrawable().setColorFilter(getResources().getColor(R.color.blue), PorterDuff.Mode.SRC_IN); // Cambiar el color del ícono
                Toast.makeText(getContext(), "Pokemon seleccionado para equipo", Toast.LENGTH_SHORT).show();
            }
            isInTeam = !isInTeam;

        }else{
            AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
            alert.setTitle("Confirmar").setMessage("Ya tienes un equipo de 6 ¿Quieres sustituir uno de tus 6 Pokemón en tu equipo? ").setPositiveButton("Si", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(getContext(), "Selecciona el pokemon a cambial", Toast.LENGTH_SHORT).show();
                }
            })
                    .setNegativeButton("No", null)
                    .setCancelable(false).show();
        }
    }
}