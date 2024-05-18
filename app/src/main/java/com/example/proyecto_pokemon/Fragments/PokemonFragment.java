package com.example.proyecto_pokemon.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
    ImageView imgPoke;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pokemon, container, false);

        tvId = view.findViewById(R.id.tvId);
        tvName = view.findViewById(R.id.tvName);
        tvAtaque = view.findViewById(R.id.tvAtaque);
        tvDefensa = view.findViewById(R.id.tvDefensa);
        tvVelocidad = view.findViewById(R.id.tvVelocidad);
        tvEspcialAttack = view.findViewById(R.id.tvEspcialAttack);
        tvEspcialDefense = view.findViewById(R.id.tvEspcialDefense);

        //final String getId = getIntent().getStringExtra("number");
        //final String getName = getIntent().getStringExtra("name");
        //final String getType = getIntent().getStringExtra("type");
        //final String getTotal = getIntent().getStringExtra("total");
        //final String getHp = getIntent().getStringExtra("hp");
        //final String getAttack = getIntent().getStringExtra("attack");
        //final String getDefense = getIntent().getStringExtra("defense");
        //final String getSpAtk = getIntent().getStringExtra("sp_atk");
        //final String getSpDef = getIntent().getStringExtra("sp_def");
        //final String getSpeed = getIntent().getStringExtra("speed");

        //tvId.setText(getId);
        //tvName.setText(getName);
        //tvAtaque.setText(getAttack);
        //tvDefensa.setText(getDefense);
        //tvVelocidad.setText(getSpeed);
        //tvEspcialAttack.setText(getSpAtk);
        //tvEspcialDefense.setText(getSpDef);

        // Cargar imagen predeterminada si getProfilePicture es nulo o vacío
        //if (!FotoUser.isEmpty()) {
            //Picasso.get().load(FotoUser).into(iwPoke);
        //}
        // Inflate the layout for this fragment
        return view;
    }
}