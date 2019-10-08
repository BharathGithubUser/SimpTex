package com.belivnat.simptex.modules.userslist;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.belivnat.simptex.R;
import com.belivnat.simptex.model.Users;
import com.belivnat.simptex.modules.chats.ChatsActivity;
import com.belivnat.simptex.utils.Constants;
import com.belivnat.simptex.utils.RecyclerItemClickListener;
import com.belivnat.simptex.utils.Utils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ChatUsersActivity extends AppCompatActivity {
    private static final String TAG = "ChatUsersActivity";
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
        chatUsersRecyclerView.setAdapter(new ChatUsersAdapter(usersList, ChatUsersActivity.this));
        chatUsersRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Log.d(TAG, "chatUsersRecyclerView Item Clicked At: " + position);
                        Intent chatActivity = new Intent(ChatUsersActivity.this, ChatsActivity.class);
                        chatActivity.putExtra(Constants.USER_DETAILS_BUNDLE, usersList.get(position));
                        startActivity(chatActivity);
                    }
                })
        );
    }
}
