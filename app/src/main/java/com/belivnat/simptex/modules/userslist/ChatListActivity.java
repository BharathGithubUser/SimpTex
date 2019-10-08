package com.belivnat.simptex.modules.userslist;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.belivnat.simptex.modules.chats.ChatsAdapter;
import com.belivnat.simptex.utils.Constants;
import com.belivnat.simptex.application.MyApplication;
import com.belivnat.simptex.R;
import com.belivnat.simptex.utils.RecyclerItemClickListener;
import com.belivnat.simptex.model.Users;
import com.belivnat.simptex.model.Chats;
import com.belivnat.simptex.utils.Utils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ChatListActivity extends AppCompatActivity {
    private static final String TAG = "ChatListActivity";
    RecyclerView chatUsersRecyclerView;
    ArrayList<Users> usersList;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);
        usersList = new ArrayList<>();
        intent = getIntent();
        usersList = intent.getParcelableArrayListExtra(Constants.USER_LIST_BUNDLE);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Chats");
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });
        if (usersList != null && usersList.size() > 0) {
            configureUsersList();
        } else {
            Utils.makeToast(this, "No Users Found!");
        }
    }
    private void configureUsersList() {
        chatUsersRecyclerView = findViewById(R.id.rv_chat_list);
        chatUsersRecyclerView.setLayoutManager(
                new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        Log.d(TAG, "Current Milli Seconds" + System.currentTimeMillis() / 1000L);
        chatUsersRecyclerView.setAdapter(new ChatListAdapter(usersList, ChatListActivity.this));
        chatUsersRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Log.d(TAG, "chatUsersRecyclerView Item Clicked At: " + position);
                        Intent chatActivity = new Intent(ChatListActivity.this, ChatActivity.class);
                        chatActivity.putExtra(Constants.USER_DETAILS_BUNDLE, usersList.get(position));
                        startActivity(chatActivity);
                    }
                })
        );
    }

    public static class ChatActivity extends AppCompatActivity implements ChildEventListener {
        Intent intent;
        Button sendMessage;
        EditText messageText;
        RecyclerView chatRecyclerView;
        Users user;
        Chats chats;
        ArrayList<Chats> chatshistoryList;
        ChatsAdapter chatsAdapter;
        DatabaseReference databaseReference;
        String TAG = "ChatActivity";

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
            chatsAdapter = new ChatsAdapter(ChatActivity.this, chatshistoryList, user.getUserName());
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
                        Utils.makeToast(ChatActivity.this,"Cannot Send Empty Message!");
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
}
