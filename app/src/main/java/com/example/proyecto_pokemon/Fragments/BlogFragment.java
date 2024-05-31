package com.example.proyecto_pokemon.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.proyecto_pokemon.Adapters.ChatAdapter;
import com.example.proyecto_pokemon.Adapters.PokemonAdapter;
import com.example.proyecto_pokemon.DataBase.ApiJsonClient;
import com.example.proyecto_pokemon.Models.Chat;
import com.example.proyecto_pokemon.Models.Pokemon;
import com.example.proyecto_pokemon.Models.responseApi;
import com.example.proyecto_pokemon.R;
import com.example.proyecto_pokemon.Services.API;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BlogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BlogFragment extends Fragment {
    //objetos de la iterfaz
    EditText edMessage;
    ImageView sendBtn;
    ChatAdapter chatAdapter;
    LinkedList<Chat> chats;
    RecyclerView rvChat;
    private DatabaseReference mDatabase;

    private String getChatKey;

    //base de datos
    //private final LinkedList<Chats> chats = new LinkedList<>();
    //private DatabaseReference mDatabase;
    //private ChatsAdapter chatsAdapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BlogFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlogFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BlogFragment newInstance(String param1, String param2) {
        BlogFragment fragment = new BlogFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_blog, container, false);

        sendBtn = view.findViewById(R.id.sendBtn);
        edMessage = view.findViewById(R.id.edMessage);
        rvChat= view.findViewById(R.id.rvChat);

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

                    obtenerChat(email);

                    sendBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            SendMessiges(username, email);
                        }
                    });
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Maneja el error de lectura de datos
                }
            });
        }
        return view;
    }

    LinkedList<Chat> obtenerChat(String email){
        chats = new LinkedList<>();

        chatAdapter = new ChatAdapter(getActivity(), email);
        rvChat.setAdapter(chatAdapter);

        return chats;
    }

    private void SendMessiges(String username, String email){
        final String getMessageTxt = edMessage.getText().toString();

        // Validate message text
        if (!getMessageTxt.isEmpty()) {
            // Get current Timestamp
            final String currentTimestamp = String.valueOf(System.currentTimeMillis()).substring(0, 10);

            // Check if chat exists for the two users
            mDatabase.child("chats").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    boolean chatExists = false;
                    for (DataSnapshot chatSnapshot : snapshot.getChildren()) {
                        String user1 = chatSnapshot.child("user_1").getValue(String.class);
                        String user2 = chatSnapshot.child("user_2").getValue(String.class);
                        /*if ((user1.equals(getPhoneUser) && user2.equals(email)) || (user1.equals(email) && user2.equals(getPhoneUser))) {
                            // Chat exists
                            chatExists = true;
                            getChatKey = chatSnapshot.getKey();
                            break;
                        }*/
                    }

                    // If chat does not exist, generate a new chatKey
                    if (!chatExists) {
                        getChatKey = String.valueOf(snapshot.getChildrenCount() + 1);
                        // Save new chat details to the database
                        //mDatabase.child("chats").child(getChatKey).child("user_1").setValue(getPhoneUser);
                        mDatabase.child("chats").child(getChatKey).child("user_2").setValue(email);
                    }

                    // Save message to database
                    mDatabase.child("chats").child(getChatKey).child("messages").child(currentTimestamp).child("msg").setValue(getMessageTxt);
                    //mDatabase.child("chats").child(getChatKey).child("messages").child(currentTimestamp).child("phone").setValue(getPhoneUser);

                    // Clear message input
                    edMessage.setText("");
                    LoadChat(username, email);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Handle error
                }
            });
        }
    }

    private void LoadChat(String username, String email){
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(getChatKey.isEmpty()){
                    // Generate chatKey by Default ChatKey is 1
                    getChatKey = "1";
                    if(snapshot.hasChild("chats")){
                        getChatKey = String.valueOf(snapshot.child("chats").getChildrenCount() + 1);
                    }
                }

                if(snapshot.hasChild("chats")){
                    if(snapshot.child("chats").child(getChatKey).hasChild("messages")){
                        chats.clear();
                        for(DataSnapshot messagesSnapshot : snapshot.child("chats").child(getChatKey).child("messages").getChildren()){
                            if(messagesSnapshot.hasChild("msg") && messagesSnapshot.hasChild("Email")){
                                final String messageTimestamps = messagesSnapshot.getKey();
                                final String getemail = messagesSnapshot.child("email").getValue(String.class);
                                final String getMsg   = messagesSnapshot.child("mensaje").getValue(String.class);

                                Timestamp timestamp = new Timestamp(Long.parseLong(messageTimestamps));
                                Date date = new Date(timestamp.getTime());
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yy", Locale.getDefault());
                                SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("hh:mm aa", Locale.getDefault());

                                Chat chatList = new Chat(email, username, getMsg, simpleDateFormat.format(date), simpleTimeFormat.format(date));
                                chats.add(chatList);

                                /*if(loadingFirstTime || Long.parseLong(messageTimestamps) > Long.parseLong(MemoryData.getLastMsgTS(getActivity(), getChatKey))){
                                    loadingFirstTime = false;
                                    // Save last message timestamp
                                    MemoryData.saveLastMsgTS(messageTimestamps, getChatKey,getActivity());
                                    chatsAdapter.updateChat(chats);
                                    chattingRecyclerView.scrollToPosition(chats.size() - 1);
                                }*/
                            }
                        }
                    }
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}