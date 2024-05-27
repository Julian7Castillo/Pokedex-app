package com.example.proyecto_pokemon.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_pokemon.Models.Chat;
import com.example.proyecto_pokemon.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyHolder> {
    private LinkedList<Chat> chats;
    private final Context context;
    private String email;
    ImageView sendBtn;

    public ChatAdapter(Context context, String email) {
        this.chats   = chats;
        this.context = context;
        this.email = email;
    }

    public void updateChat(LinkedList<Chat> chats){
        this.chats = chats;
    }

    @NonNull
    @Override
    public ChatAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_messages, null);
        return new ChatAdapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        Chat chatsList = chats.get(position);

        if(chatsList.getUsername().equals(email)) {
            holder.myLayout.setVisibility(View.VISIBLE);
            holder.oppoLayout.setVisibility(View.GONE);

            holder.myMessage.setText(chatsList.getMesage());
            holder.twTimeUser.setText(chatsList.getDate()+" "+chatsList.getTime());
        }else{
            holder.myLayout.setVisibility(View.GONE);
            holder.oppoLayout.setVisibility(View.VISIBLE);

            holder.oppoMessage.setText(chatsList.getMesage());
            holder.twTimeOtherUser.setText(chatsList.getDate()+" "+chatsList.getTime());
        }
    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        private LinearLayout oppoLayout, myLayout;
        private TextView oppoMessage, myMessage, twTimeUser, twTimeOtherUser;
        public MyHolder(@NonNull View itemView) {
            super(itemView);

            oppoLayout      = itemView.findViewById(R.id.oppoLayout);
            myLayout        = itemView.findViewById(R.id.myLayout);
            oppoMessage     = itemView.findViewById(R.id.oppoMessage);
            myMessage       = itemView.findViewById(R.id.myMessage);
            twTimeUser      = itemView.findViewById(R.id.twTimeUser);
            twTimeOtherUser = itemView.findViewById(R.id.twTimeOtherUser);
        }
    }
}