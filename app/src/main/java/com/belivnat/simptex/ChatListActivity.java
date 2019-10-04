package com.belivnat.simptex;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChatListActivity extends AppCompatActivity {
    private static final String TAG = "ChatListActivity";
    RecyclerView chatRecyclerView;
    Users users;
    UserChats userChats;
    UserProfile userProfile;
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
        chatRecyclerView = findViewById(R.id.rv_chat_list);
        chatRecyclerView.setLayoutManager(
                new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        Log.d(TAG, "Current Milli Seconds" + System.currentTimeMillis() / 1000L);
        chatRecyclerView.setAdapter(new ChatListAdapter(usersList, ChatListActivity.this));
        chatRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Log.d(TAG, "chatRecyclerView Item Clicked At: " + position);
                        Intent chatActivity = new Intent(ChatListActivity.this, ChatActivity.class);
                        startActivity(chatActivity);
                    }
                })
        );
    }
}
