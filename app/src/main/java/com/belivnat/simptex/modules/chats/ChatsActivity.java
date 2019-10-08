package com.belivnat.simptex.modules.chats;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.belivnat.simptex.R;
import com.belivnat.simptex.application.MyApplication;
import com.belivnat.simptex.model.Chats;
import com.belivnat.simptex.model.Users;
import com.belivnat.simptex.utils.Constants;
import com.belivnat.simptex.utils.Utils;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ChatsActivity extends AppCompatActivity implements ChildEventListener {
    Intent intent;
    Button sendMessage;
    EditText messageText;
    RecyclerView chatRecyclerView;
    Users user;
    Chats chats;
    ArrayList<Chats> chatshistoryList;
    ChatsAdapter chatsAdapter;
    DatabaseReference databaseReference;
    String TAG = "ChatsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent = getIntent();
        user = intent.getParcelableExtra(Constants.USER_DETAILS_BUNDLE);
        setContentView(R.layout.activity_chat);
        sendMessage = findViewById(R.id.btn_send_msg);
        messageText = findViewById(R.id.et_msg_to_send);
        chatRecyclerView = findViewById(R.id.rv_chats);
        chatshistoryList = new ArrayList<>();
        chatRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        if (user != null) {
            databaseReference = FirebaseDatabase.getInstance().getReference(Constants.FIRESTORE_BASE_PATH + "/" + user.getMobileNumber() + "/chats");
            databaseReference.addChildEventListener(this);
        } else {
            Utils.makeToast(this, "Something Went Wrong!");
        }
        chatsAdapter = new ChatsAdapter(ChatsActivity.this, chatshistoryList, user.getUserName());
        chatRecyclerView.setAdapter(chatsAdapter);
        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!messageText.getText().toString().trim().equals("")) {
                    Chats chats = new Chats();
                    String currentTime = new SimpleDateFormat("h:mm a", Locale.getDefault()).format(new Date());
                    chats.setMessage(messageText.getText().toString());
                    chats.setSender(user.getUserName());
                    chats.setMsg_type("text");
                    chats.setSent_on(currentTime);
                    databaseReference.child(Long.toString(System.currentTimeMillis() / 1000L)).setValue(chats);
                    messageText.setText("");
                } else {
                    Utils.makeToast(ChatsActivity.this, "Cannot Send Empty Message!");
                }
            }
        });
    }

    @Override
    public void onStop() {
        MyApplication.firebaseDatabaseReference().removeEventListener(this);
        super.onStop();
    }

    @Override
    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
        chats = new Chats();
        try {
            chats = dataSnapshot.getValue(Chats.class);
        } catch (ClassCastException exception) {
            if (exception.getMessage() != null) {
                Log.d(TAG, exception.getMessage());
            }
        }
        chatshistoryList.add(chats);
        chatsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

    }

    @Override
    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

    }

    @Override
    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
}