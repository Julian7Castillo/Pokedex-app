package com.example.proyecto_pokemon.Fragments;

import static android.content.Context.LOCATION_SERVICE;
import static androidx.constraintlayout.motion.widget.Debug.getLocation;
import static androidx.core.content.ContextCompat.getSystemService;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyecto_pokemon.Activities.HomeActivity;
import com.example.proyecto_pokemon.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.zip.Inflater;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PerfilFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PerfilFragment extends Fragment implements OnMapReadyCallback {

    //variables de interfaz
    Button btnCambiar;
    TextView tvUser, tvEmail, tvTelefono, tvCumple;

    //variables de la base de datos
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();
    private DatabaseReference mDatabase;

    //variables del map
    private final int RESULT_PERMITION_GPS = 100;
    private GoogleMap mMap;
    private LocationManager locationManager;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PerfilFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PerfilFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PerfilFragment newInstance(String param1, String param2) {
        PerfilFragment fragment = new PerfilFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        tvUser = view.findViewById(R.id.tvUser);
        tvEmail = view.findViewById(R.id.tvEmail);
        tvTelefono = view.findViewById(R.id.tvTelefono);
        tvCumple = view.findViewById(R.id.tvCumple);

        btnCambiar = view.findViewById(R.id.btnCambiar);

        //obtener uy mostrar informacion de usuario que inicio sesion
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUser.getUid());
            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String username = dataSnapshot.child("Name").getValue(String.class);
                    String email = dataSnapshot.child("Email").getValue(String.class);
                    String phone = dataSnapshot.child("Phone").getValue(String.class);
                    String birday = dataSnapshot.child("Cumple").getValue(String.class);

                    // Ahora puedes utilizar los datos guardados en SharedPreferences según sea necesario
                    tvUser.setText(username);
                    tvEmail.setText(email);
                    tvTelefono.setText(phone);
                    tvCumple.setText(birday);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Maneja el error de lectura de datos
                }
            });
        }

        btnCambiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                CambioDatosFragment cambiodatosfragment = new CambioDatosFragment();
                fragmentTransaction.replace(R.id.frame_layout_Pokedex, cambiodatosfragment);
                fragmentTransaction.addToBackStack(null); // Esto permite volver al fragmento anterior al presionar el botón de retroceso
                fragmentTransaction.commit();
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);//getActivity().getSupportFragmentManager()
        mapFragment.getMapAsync(this);
        getLocation();

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions()
                .position(sydney)
                .title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 12));
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, RESULT_PERMITION_GPS);
        } else {
            mMap.setMyLocationEnabled(true);
        }
    }
    
    private void getLocation () {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, RESULT_PERMITION_GPS);
        } else {
            LocationManager locationManager = (LocationManager) requireContext().getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, new LocationListener() {
                @Override
                public void onLocationChanged(@NonNull Location location) {
                    double longitud = location.getLongitude();
                    double latitud = location.getLatitude();
                    LatLng mylocation = new LatLng(latitud, longitud);
                    mMap.addMarker(new MarkerOptions()
                            .position(mylocation)
                            .title("yo"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mylocation, 12));
                }
            }, null);
        }
    }
}