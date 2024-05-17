package com.example.proyecto_pokemon.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.proyecto_pokemon.Adapters.PokemonAdapter;
import com.example.proyecto_pokemon.DataBase.ApiJsonClient;
import com.example.proyecto_pokemon.Fragments.BlogFragment;
import com.example.proyecto_pokemon.Fragments.PerfilFragment;
import com.example.proyecto_pokemon.Fragments.PokedexFragment;
import com.example.proyecto_pokemon.Models.Pokemon;
import com.example.proyecto_pokemon.Models.responseApi;
import com.example.proyecto_pokemon.R;
import com.example.proyecto_pokemon.Services.API;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.LinkedList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    //FloatingActionButton fab;
    private DrawerLayout containerPokedex;
    NavigationView drawer_view_pokedex;
    BottomNavigationView bottomNavigationViewPokedex;

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNavigationViewPokedex = findViewById(R.id.bottomNavigationViewPokedex);
        //fab = findViewById(R.id.fab);
        containerPokedex = findViewById(R.id.containerPokedex);
        drawer_view_pokedex = findViewById(R.id.drawer_view_pokedex);
        Toolbar toolbar = findViewById(R.id.toolbarPokedex);
        setSupportActionBar(toolbar);

        containerPokedex = findViewById(R.id.containerPokedex);
        NavigationView navigationView = findViewById(R.id.drawer_view_pokedex);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, containerPokedex, toolbar, R.string.open_nav,
                R.string.open_nav);
        containerPokedex.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_Pokedex, new PokedexFragment()).commit();
            drawer_view_pokedex.setCheckedItem(R.id.idInicio);
        }
        bottomNavigationViewPokedex.setSelectedItemId(R.id.itemInicio);
        replaceFragment(new PokedexFragment());

        bottomNavigationViewPokedex.setBackground(null);
        bottomNavigationViewPokedex.setOnItemSelectedListener(item -> {

            if (item.getItemId() == R.id.itemInicio){
                replaceFragment(new PokedexFragment());

            }else if (item.getItemId() == R.id.itemPerfil){
                replaceFragment(new PerfilFragment());

            }else if (item.getItemId() == R.id.itemBlog){
                replaceFragment(new BlogFragment());
            }

            return true;
        });

        /*fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomDialog();
            }
        });*/
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.idInicio) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_Pokedex, new PokedexFragment()).commit();
            bottomNavigationViewPokedex.setSelectedItemId(R.id.itemInicio);

        } else if (item.getItemId() == R.id.idUsuario) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_Pokedex, new PerfilFragment()).commit();
            bottomNavigationViewPokedex.setSelectedItemId(R.id.itemPerfil);

        } else if (item.getItemId() == R.id.idBlog) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_Pokedex, new BlogFragment()).commit();
            bottomNavigationViewPokedex.setSelectedItemId(R.id.itemBlog);

        } else if (item.getItemId() == R.id.idLogout) {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(intent);
        }
        containerPokedex.closeDrawer(GravityCompat.START);
        return true;
    }

    private  void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout_Pokedex, fragment);
        fragmentTransaction.commit();
    }

    /*private void showBottomDialog() {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottonsheetlayout_user);

        LinearLayout enviarLayout = dialog.findViewById(R.id.layoutEnviarUser);
        ImageView cancelButton = dialog.findViewById(R.id.cancelButtonUSer);

        enviarLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                Toast.makeText(HomeUSerActivity.this,"Se a seleccionado Enviar Paquete",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(HomeUSerActivity.this, HomeUSerActivity.class);
                startActivity(intent);
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
    }*/
}