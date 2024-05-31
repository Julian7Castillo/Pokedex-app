package com.example.proyecto_pokemon.Fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyecto_pokemon.Activities.HomeActivity;
import com.example.proyecto_pokemon.Activities.LoginActivity;
import com.example.proyecto_pokemon.Activities.RegisterActivity;
import com.example.proyecto_pokemon.Models.Evolution;
import com.example.proyecto_pokemon.Models.Pokemon;
import com.example.proyecto_pokemon.Models.Sprites;
import com.example.proyecto_pokemon.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PokemonFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PokemonFragment extends Fragment {

    TextView tvId, tvName, tvTotal, tvVida, tvAtaque, tvDefensa, tvVelocidad, tvEspcialAttack, tvEspcialDefense;
    TextView twPokeTeam1, twPokeTeam2, twPokeTeam3, twPokeTeam4, twPokeTeam5, twPokeTeam6;
    Context context;
    ImageView iwPoke;
    FloatingActionButton fabFavorite, fabAdd;
    Button btnTipo1, btnTipo2;
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    //Variables globales
    private boolean isFavorite = false;
    private boolean isInTeam = false;
    private boolean therAreFavorite = false;
    boolean newisInTeam = false;
    String poke = "";
    int indice;

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

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

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
            final String evolution = bundle.getString("Evolution");
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
            if (evolution == null){
                tvName.setText(name);
                //System.out.println("Es nulo "+evolution);
            }else{
                tvName.setText(evolution);
                //System.out.println("No Es nulo "+evolution);
            }
            tvTotal.setText(total);
            tvVida.setText(vida);
            tvAtaque.setText(attack);
            tvDefensa.setText(defense);
            tvVelocidad.setText(speed);
            tvEspcialAttack.setText(sp_atk);
            tvEspcialDefense.setText(sp_def);
            btnTipo1.setText(type[0]);
            //System.out.println(type.length);
            if ((type.length == 2)){
                btnTipo2.setText(type[1]);

                if (type[1].equals("Grass")){
                    btnTipo2.setBackgroundColor(getContext().getColor(R.color.grass));

                }else if (type[1].equals("Poison")){
                    btnTipo2.setBackgroundColor(getContext().getColor(R.color.poison));

                }else if (type[1].equals("Fire")){
                    btnTipo2.setBackgroundColor(getContext().getColor(R.color.fire));

                }else if (type[1].equals("Dragon")){
                    btnTipo2.setBackgroundColor(getContext().getColor(R.color.dragon));

                }else if (type[1].equals("Flying")){
                    btnTipo2.setBackgroundColor(getContext().getColor(R.color.flying));

                }else if (type[1].equals("Water")){
                    btnTipo2.setBackgroundColor(getContext().getColor(R.color.water));

                }else if (type[1].equals("Bug")){
                    btnTipo2.setBackgroundColor(getContext().getColor(R.color.bug));

                }else if (type[1].equals("Normal")){
                    btnTipo2.setBackgroundColor(getContext().getColor(R.color.normal));

                }else if (type[1].equals("Dark")){
                    btnTipo2.setBackgroundColor(getContext().getColor(R.color.dark));

                }else if (type[1].equals("Electric")){
                    btnTipo2.setBackgroundColor(getContext().getColor(R.color.electric));

                }else if (type[1].equals("Psychic")){
                    btnTipo2.setBackgroundColor(getContext().getColor(R.color.psychic));

                }else if (type[1].equals("Ice")){
                    btnTipo2.setBackgroundColor(getContext().getColor(R.color.ice));

                }else if (type[1].equals("Steel")){
                    btnTipo2.setBackgroundColor(getContext().getColor(R.color.steel));

                }else if (type[1].equals("Fairy")){
                    btnTipo2.setBackgroundColor(getContext().getColor(R.color.fairy));

                }else if (type[1].equals("Fighting")){
                    btnTipo2.setBackgroundColor(getContext().getColor(R.color.fighting));

                }else if (type[1].equals("Rock")){
                    btnTipo2.setBackgroundColor(getContext().getColor(R.color.rock));

                }else if (type[1].equals("Ghost")){
                    btnTipo2.setBackgroundColor(getContext().getColor(R.color.ghost));

                }else if (type[1].equals("Ground")){
                    btnTipo2.setBackgroundColor(getContext().getColor(R.color.ground));
                }
            }else{
                btnTipo2.setVisibility(View.GONE);
            }

            //ajuste de colores de fondo de tipos
            if (type[0].equals("Grass")){
                btnTipo1.setBackgroundColor(getContext().getColor(R.color.grass));

            }else if (type[0].equals("Poison")){
                btnTipo1.setBackgroundColor(getContext().getColor(R.color.poison));

            }else if (type[0].equals("Fire")){
                btnTipo1.setBackgroundColor(getContext().getColor(R.color.fire));

            }else if (type[0].equals("Dragon")){
                btnTipo1.setBackgroundColor(getContext().getColor(R.color.dragon));

            }else if (type[0].equals("Flying")){
                btnTipo1.setBackgroundColor(getContext().getColor(R.color.flying));

            }else if (type[0].equals("Water")){
                btnTipo1.setBackgroundColor(getContext().getColor(R.color.water));

            }else if (type[0].equals("Bug")){
                btnTipo1.setBackgroundColor(getContext().getColor(R.color.bug));

            }else if (type[0].equals("Normal")){
                btnTipo1.setBackgroundColor(getContext().getColor(R.color.normal));

            }else if (type[0].equals("Dark")){
                btnTipo1.setBackgroundColor(getContext().getColor(R.color.dark));

            }else if (type[0].equals("Electric")){
                btnTipo1.setBackgroundColor(getContext().getColor(R.color.electric));

            }else if (type[0].equals("Psychic")){
                btnTipo1.setBackgroundColor(getContext().getColor(R.color.psychic));

            }else if (type[0].equals("Ice")){
                btnTipo1.setBackgroundColor(getContext().getColor(R.color.ice));

            }else if (type[0].equals("Steel")){
                btnTipo1.setBackgroundColor(getContext().getColor(R.color.steel));

            }else if (type[0].equals("Fairy")){
                btnTipo1.setBackgroundColor(getContext().getColor(R.color.fairy));

            }else if (type[0].equals("Fighting")){
                btnTipo1.setBackgroundColor(getContext().getColor(R.color.fighting));

            }else if (type[0].equals("Rock")){
                btnTipo1.setBackgroundColor(getContext().getColor(R.color.rock));

            }else if (type[0].equals("Ghost")){
                btnTipo1.setBackgroundColor(getContext().getColor(R.color.ghost));

            }else if (type[0].equals("Ground")){
                btnTipo1.setBackgroundColor(getContext().getColor(R.color.ground));
            }

            //obtener uy mostrar informacion de usuario que inicio sesion
            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            if (currentUser != null) {
                DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUser.getUid());
                userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String favorito = dataSnapshot.child("Favorite").getValue(String.class);
                        String poke1 = dataSnapshot.child("Equipo").child("0").getValue(String.class);
                        String poke2 = dataSnapshot.child("Equipo").child("1").getValue(String.class);
                        String poke3 = dataSnapshot.child("Equipo").child("2").getValue(String.class);
                        String poke4 = dataSnapshot.child("Equipo").child("3").getValue(String.class);
                        String poke5 = dataSnapshot.child("Equipo").child("4").getValue(String.class);
                        String poke6 = dataSnapshot.child("Equipo").child("5").getValue(String.class);

                        if (!(favorito == null || favorito.equals(""))){
                            therAreFavorite = true;
                            if (favorito.equals(tvName.getText())){
                                fabFavorite.setImageResource(R.drawable.icon_favorite_24);
                                isFavorite = true;
                            }
                        }

                        if (!(poke1 == null)){
                            if (poke1.equals(tvName.getText())) {
                                fabAdd.setImageResource(R.drawable.icon_catching_pokemon_24);
                                fabAdd.getDrawable().setColorFilter(getResources().getColor(R.color.blue), PorterDuff.Mode.SRC_IN); // Cambiar el color del ícono
                                isInTeam = true;
                            }
                        }
                        if (!(poke2 == null)){
                            if (poke2.equals(tvName.getText())) {
                                fabAdd.setImageResource(R.drawable.icon_catching_pokemon_24);
                                fabAdd.getDrawable().setColorFilter(getResources().getColor(R.color.blue), PorterDuff.Mode.SRC_IN); // Cambiar el color del ícono
                                isInTeam = true;
                            }
                        }
                        if (!(poke3 == null)){
                            if (poke3.equals(tvName.getText())) {
                                fabAdd.setImageResource(R.drawable.icon_catching_pokemon_24);
                                fabAdd.getDrawable().setColorFilter(getResources().getColor(R.color.blue), PorterDuff.Mode.SRC_IN); // Cambiar el color del ícono
                                isInTeam = true;
                            }
                        }
                        if (!(poke4 == null)){
                            if (poke4.equals(tvName.getText())) {
                                fabAdd.setImageResource(R.drawable.icon_catching_pokemon_24);
                                fabAdd.getDrawable().setColorFilter(getResources().getColor(R.color.blue), PorterDuff.Mode.SRC_IN); // Cambiar el color del ícono
                                isInTeam = true;
                            }
                        }
                        if (!(poke5 == null)){
                            if (poke5.equals(tvName.getText())) {
                                fabAdd.setImageResource(R.drawable.icon_catching_pokemon_24);
                                fabAdd.getDrawable().setColorFilter(getResources().getColor(R.color.blue), PorterDuff.Mode.SRC_IN); // Cambiar el color del ícono
                                isInTeam = true;
                            }
                        }
                        if (!(poke6 == null)){
                            if (poke6.equals(tvName.getText())) {
                                fabAdd.setImageResource(R.drawable.icon_catching_pokemon_24);
                                fabAdd.getDrawable().setColorFilter(getResources().getColor(R.color.blue), PorterDuff.Mode.SRC_IN); // Cambiar el color del ícono
                                isInTeam = true;
                            }
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Maneja el error de lectura de datos
                    }
                });
            }

        }else{
            Toast.makeText(getContext(), "No se enontro la informacion del Pokemon", Toast.LENGTH_SHORT).show();
        }

        //seleccion de corazon en el caso de que el poquyemon se pfavotrito

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

        String name = "";
        String evolution;

        Bundle bundle = getArguments();

        if (bundle != null) {
            name = bundle.getString("name");
            evolution = bundle.getString("Evolution");
            if (evolution == null){
                tvName.setText(name);
            }else{
                tvName.setText(evolution);
                name = evolution;
            }

        }else{
            Toast.makeText(getContext(), "No se enontro la informacion del Pokemon", Toast.LENGTH_SHORT).show();
        }

        if (therAreFavorite) {
            if (isFavorite) {

                final String pokeToRemove = name;
                String id = mAuth.getCurrentUser().getUid();
                DatabaseReference userRef = mDatabase.child("Users").child(id).child("Favorite");

                // Lee el array actual de "Equipo"
                userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String pokeFavya = pokeToRemove;

                        // Elimina el elemento si existe en la lista
                        if (pokeFavya.equals(pokeToRemove)) {
                            pokeFavya = "";

                            // Actualiza el campo "Equipo" en la base de datos
                            userRef.setValue(pokeFavya).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        // Operación exitosa
                                        fabFavorite.setImageResource(R.drawable.icon_favorite_border_24);
                                        Toast.makeText(getContext(), "Pokemon a dejado de ser favorito", Toast.LENGTH_SHORT).show();
                                    } else {
                                        // Manejar errores
                                        Toast.makeText(getContext(), "Error al eliminar el elemento.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } else {
                            // Manejar caso cuando el elemento no está en la lista
                            Toast.makeText(getContext(), "El Pokemon no está en favoritos.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Manejar errores de lectura de datos
                        Toast.makeText(getContext(), "Error de lectura de datos: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            } else {
                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                String finalNumber = name;
                alert.setTitle("Confirmar").setMessage("Ya tienes un pokemon favorito ¿Estas seguro de remplazarlo? ").setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String id = mAuth.getCurrentUser().getUid();
                        mDatabase.child("Users").child(id).child("Favorite").setValue(finalNumber).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task2) {
                                if (task2.isSuccessful()){
                                    fabFavorite.setImageResource(R.drawable.icon_favorite_24);
                                    Toast.makeText(getContext(), "Pokemon seleccionado como favorito", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(getContext(), "Pokemon No sea seleccionado como favoritn", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                })
                .setNegativeButton("No", null)
                .setCancelable(false).show();
            }
            isFavorite = !isFavorite;
        }else{
            String id = mAuth.getCurrentUser().getUid();

            mDatabase.child("Users").child(id).child("Favorite").setValue(name).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task2) {
                    if (task2.isSuccessful()){
                        fabFavorite.setImageResource(R.drawable.icon_favorite_24);
                        Toast.makeText(getContext(), "Pokemon seleccionado como favorito", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getContext(), "Pokemon No sea seleccionado como favoritn", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void AgregarEquipo() {
        String name = "";
        String evolution;

        Bundle bundle = getArguments();

        if (bundle != null) {
            name = bundle.getString("name");
            evolution = bundle.getString("Evolution");
            if (evolution == null){
                tvName.setText(name);
            }else{
                tvName.setText(evolution);
                name = evolution;
            }

        }else{
            Toast.makeText(getContext(), "No se enontro la informacion del Pokemon", Toast.LENGTH_SHORT).show();
        }

        if (isInTeam) {
            final String pokeToRemove = name;
            String id = mAuth.getCurrentUser().getUid();
            DatabaseReference userRef = mDatabase.child("Users").child(id).child("Equipo");

            // Lee el array actual de "Equipo"
            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    List<String> equipoList = new ArrayList<>();

                    // Si el campo "Equipo" existe, agrégalo a la lista
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            equipoList.add(snapshot.getValue(String.class));
                        }
                    }

                    // Elimina el elemento si existe en la lista
                    if (equipoList.contains(pokeToRemove)) {
                        equipoList.remove(pokeToRemove);

                        // Actualiza el campo "Equipo" en la base de datos
                        userRef.setValue(equipoList).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    // Operación exitosa
                                    Toast.makeText(getContext(), "El Pokemon a dejado el equipo", Toast.LENGTH_SHORT).show();
                                    fabAdd.setImageResource(R.drawable.icon_add_24);
                                    fabAdd.getDrawable().setColorFilter(null);
                                } else {
                                    // Manejar errores
                                    Toast.makeText(getContext(), "Error al eliminar el elemento.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } else {
                        // Manejar caso cuando el elemento no está en la lista
                        Toast.makeText(getContext(), "El Pokemon no está en el equipo.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Manejar errores de lectura de datos
                    Toast.makeText(getContext(), "Error de lectura de datos: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            String id = mAuth.getCurrentUser().getUid();
            DatabaseReference userRef = mDatabase.child("Users").child(id).child("Equipo");

            // Lee el array actual de "Equipo"
            String finalNumber = name;
            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    List<String> equipoList = new ArrayList<>();

                    // Si el campo "Equipo" existe, agrégalo a la lista
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            equipoList.add(snapshot.getValue(String.class));
                        }
                    }

                    // Verifica si la lista tiene menos de 6 elementos
                    if (equipoList.size() < 6) {
                        equipoList.add(finalNumber);

                        // Actualiza el campo "Equipo" en la base de datos
                        userRef.setValue(equipoList).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @SuppressLint("ResourceAsColor")
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    fabAdd.setImageResource(R.drawable.icon_catching_pokemon_24);
                                    fabAdd.getDrawable().setColorFilter(getResources().getColor(R.color.blue), PorterDuff.Mode.SRC_IN); // Cambiar el color del ícono
                                    Toast.makeText(getContext(), "Pokemon seleccionado para equipo", Toast.LENGTH_SHORT).show();
                                    isInTeam = !isInTeam;
                                } else {
                                    // Manejar errores
                                }
                            }
                        });
                    } else {
                        // Manejar caso cuando el array tiene 6 elementos
                        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                        alert.setTitle("Confirmar").setMessage("Ya tienes un equipo de 6 ¿Quieres sustituir uno de tus 6 Pokemón en tu equipo? ").setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(getContext(), "Selecciona el pokemon a cambiar", Toast.LENGTH_SHORT).show();
                                        boolean resultado1 = false;

                                        resultado1 = showBottomDialog();

                                        if (resultado1 == true){
                                            isInTeam = !isInTeam;
                                        }
                                    }
                                })
                                .setNegativeButton("No", null)
                                .setCancelable(false).show();
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Manejar errores de lectura de datos
                }
            });
        }
    }
    private boolean showBottomDialog() {

        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.button_sheetlayout_pokeomon_team);

        twPokeTeam1 = dialog.findViewById(R.id.twPokeTeam1);
        twPokeTeam2 = dialog.findViewById(R.id.twPokeTeam2);
        twPokeTeam3 = dialog.findViewById(R.id.twPokeTeam3);
        twPokeTeam4 = dialog.findViewById(R.id.twPokeTeam4);
        twPokeTeam5 = dialog.findViewById(R.id.twPokeTeam5);
        twPokeTeam6 = dialog.findViewById(R.id.twPokeTeam6);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUser.getUid());
            mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String poke1 = dataSnapshot.child("Equipo").child("0").getValue(String.class);
                    String poke2 = dataSnapshot.child("Equipo").child("1").getValue(String.class);
                    String poke3 = dataSnapshot.child("Equipo").child("2").getValue(String.class);
                    String poke4 = dataSnapshot.child("Equipo").child("3").getValue(String.class);
                    String poke5 = dataSnapshot.child("Equipo").child("4").getValue(String.class);
                    String poke6 = dataSnapshot.child("Equipo").child("5").getValue(String.class);

                    twPokeTeam1.setText(poke1);
                    twPokeTeam2.setText(poke2);
                    twPokeTeam3.setText(poke3);
                    twPokeTeam4.setText(poke4);
                    twPokeTeam5.setText(poke5);
                    twPokeTeam6.setText(poke6);
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Maneja el error de lectura de datos
                }
            });
        }

        LinearLayout layoutPoke1 = dialog.findViewById(R.id.layoutPoke1);
        LinearLayout layoutPoke2 = dialog.findViewById(R.id.layoutPoke2);
        LinearLayout layoutPoke3 = dialog.findViewById(R.id.layoutPoke3);
        LinearLayout layoutPoke4 = dialog.findViewById(R.id.layoutPoke4);
        LinearLayout layoutPoke5 = dialog.findViewById(R.id.layoutPoke5);
        LinearLayout layoutPoke6 = dialog.findViewById(R.id.layoutPoke6);
        ImageView cancelButton = dialog.findViewById(R.id.cancelButtonUSer);

        layoutPoke1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                newisInTeam = true;
                cambairPoke(0);
            }
        });

        layoutPoke2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                newisInTeam = true;
                cambairPoke(1);
            }
        });

        layoutPoke3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                newisInTeam = true;
                cambairPoke(2);
            }
        });

        layoutPoke4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                newisInTeam = true;
                cambairPoke(3);
            }
        });

        layoutPoke5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                newisInTeam = true;
                cambairPoke(4);
            }
        });

        layoutPoke6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                newisInTeam = true;
                cambairPoke(5);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);

        return newisInTeam;
    }

    private void cambairPoke(int indice){

        String name = "";
        String evolution = "";
        Bundle bundle = getArguments();
        if (bundle != null) {
            name = bundle.getString("name");
            evolution = bundle.getString("Evolution");
            if (evolution == null){
                tvName.setText(name);
            }else{
                tvName.setText(evolution);
                name = evolution;
            }

        }else{
            Toast.makeText(getContext(), "No se enontro la informacion del Pokemon", Toast.LENGTH_SHORT).show();
        }

        String id = mAuth.getCurrentUser().getUid();
        DatabaseReference userRef = mDatabase.child("Users").child(id).child("Equipo");

        String finalNumber = name;
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> equipoList = new ArrayList<>();

                // Si el campo "Equipo" existe, agrégalo a la lista
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        equipoList.add(snapshot.getValue(String.class));
                    }
                }
                equipoList.set(indice, finalNumber);

                // Actualiza el campo "Equipo" en la base de datos
                userRef.setValue(equipoList).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            fabAdd.setImageResource(R.drawable.icon_catching_pokemon_24);
                            fabAdd.getDrawable().setColorFilter(getResources().getColor(R.color.blue), PorterDuff.Mode.SRC_IN); // Cambiar el color del ícono
                            Toast.makeText(getContext(), "Pokemon seleccionado para equipo", Toast.LENGTH_SHORT).show();
                        } else {
                            // Manejar errores
                        }
                    }
                });
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Manejar errores de lectura de datos
            }
        });
    }
}