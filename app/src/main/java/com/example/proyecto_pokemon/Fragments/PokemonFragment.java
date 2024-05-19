package com.example.proyecto_pokemon.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyecto_pokemon.R;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PokemonFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PokemonFragment extends Fragment {

    TextView tvId, tvName,tvAtaque, tvDefensa, tvVelocidad, tvEspcialAttack, tvEspcialDefense;
    Context context;
    ImageView iwPoke;

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

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pokemon, container, false);

        tvId = view.findViewById(R.id.tvId);
        tvName = view.findViewById(R.id.tvName);
        iwPoke = view.findViewById(R.id.iwPoke);
        tvAtaque = view.findViewById(R.id.tvAtaque);
        tvDefensa = view.findViewById(R.id.tvDefensa);
        tvVelocidad = view.findViewById(R.id.tvVelocidad);
        tvEspcialAttack = view.findViewById(R.id.tvEspcialAttack);
        tvEspcialDefense = view.findViewById(R.id.tvEspcialDefense);

        // Obtener los datos del Bundle
        Bundle bundle = getArguments();
        if (bundle != null) {
            final String number = bundle.getString("number");
            final String name = bundle.getString("name");
            final String sprite = bundle.getString("sprite");
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
                            .resize(750, 750) // Especifica las dimensiones deseadas
                            .centerInside()
                            .into(iwPoke);
                }
            }else {
                Toast.makeText(getContext(), "imgPoke is null. Check the layout ID or the inflation process.", Toast.LENGTH_SHORT).show();

            }

            // Usa los datos recibidos
            tvId.setText(number);
            tvName.setText(name);
            tvAtaque.setText(attack);
            tvDefensa.setText(defense);
            tvVelocidad.setText(speed);
            tvEspcialAttack.setText(sp_atk);
            tvEspcialDefense.setText(sp_def);

        }else{
            Toast.makeText(getContext(), "No se enontro la informacion del Pokemon", Toast.LENGTH_SHORT).show();
        }
        return view;
    }
}