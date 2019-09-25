package com.belivnat.simptex;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChatListActivity extends AppCompatActivity {
    private static final String TAG = "ChatListActivity";
    RecyclerView chatRecyclerView;
    Users users;
    List<Users> usersList;
    UserChats userChats;
    UserProfile userProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);
        users = new Users();
        getDataFromFireStore();
        usersList = new ArrayList<>();
        chatRecyclerView = findViewById(R.id.rv_chat_list);
        chatRecyclerView.setLayoutManager(
                new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Chats");
        FloatingActionButton fab = findViewById(R.id.fab);
        Log.d(TAG, "Current Milli Seconds" + System.currentTimeMillis() / 1000L);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });
    }
    private void getDataFromFireStore() {
      FirebaseDatabase.getInstance().getReference("Users/mobileNumber")
              .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
               for (DataSnapshot user: dataSnapshot.getChildren()) {
                   Log.d(TAG, "Realtime DB User Mobile: " + user.getKey());
                   for (DataSnapshot currentUser: user.getChildren()) {
                       if (currentUser.getKey() !=null && currentUser.getKey().equals("recent_chat")) {
                           userChats = currentUser.getValue(UserChats.class);
                           if (userChats != null && userChats.getMessage() != null) {
                               users.setRecentChat(userChats.getMessage());
                               Log.d(TAG, "Realtime DB RecentChat: " + userChats.getMessage());
                           }
                       } else if (currentUser.getKey() !=null && currentUser.getKey().equals("user_profile")) {
                           userProfile = currentUser.getValue(UserProfile.class);
                           if (userProfile != null && userProfile.getProfileImage() != null) {
                               users.setUserImageUrl(userProfile.getProfileImage());
                               users.setUserName(userProfile.getName());
                               Log.d(TAG, "Realtime DB UserProfileImage: " + userProfile.getProfileImage());
                           }
                       }
                   }
               }
               usersList.add(users);
               chatRecyclerView.setAdapter(new ChatListAdapter(usersList, ChatListActivity.this));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    private void getRecentChat(String path) {

    }

}
