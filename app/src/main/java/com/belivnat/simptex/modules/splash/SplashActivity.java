package com.belivnat.simptex.modules.splash;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.belivnat.simptex.application.MyApplication;
import com.belivnat.simptex.R;
import com.belivnat.simptex.model.UserProfile;
import com.belivnat.simptex.model.Users;
import com.belivnat.simptex.model.Chats;
import com.belivnat.simptex.modules.userslist.ChatUsersActivity;
import com.belivnat.simptex.utils.Constants;
import com.belivnat.simptex.utils.Utils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SplashActivity extends AppCompatActivity implements ValueEventListener {
    Handler mhandler;
    Runnable runnable;
    Users users;
    Chats userChats;
    UserProfile userProfile;
    ArrayList<Users> usersList;
    private static final String TAG = "SplashActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        users = new Users();
        userChats = new Chats();
        userProfile = new UserProfile();
        usersList = new ArrayList<>();
        mhandler = new Handler();
        if (Utils.isConnectedToInternet(this)) {
            getDataFromFirebaseRealTimeDB();
        } else {
            Utils.makeToast(this, "This App Requires Internet Connection for Loading Messages. Please Turn ON and Try Again!");
        }
    }

    @Override
    public void onStop() {
        stopFirebaseEventListener();
        super.onStop();
    }

    private void getDataFromFirebaseRealTimeDB() {
        MyApplication.firebaseDatabaseReference().addValueEventListener(this);
    }

    private void stopFirebaseEventListener() {
        MyApplication.firebaseDatabaseReference().removeEventListener(this);
    }

    private void redirectToHomePage() {
        runnable = new Runnable() {
            @Override
            public void run() {
                Intent chatListActivity = new Intent(SplashActivity.this, ChatUsersActivity.class);
                chatListActivity.putParcelableArrayListExtra(Constants.USER_LIST_BUNDLE, usersList);
                startActivity(chatListActivity);
                finish();
            }
        };
        mhandler.postDelayed(runnable, 2000);
    }

    private void getRecentChat(String path) {

    }

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        // This method is called once with the initial value and again
        // whenever data at this location is updated.
        for (DataSnapshot user : dataSnapshot.getChildren()) {
            users = new Users();
            Log.d(TAG, "Realtime DB User Mobile: " + user.getKey());
            users.setMobileNumber(Long.parseLong(user.getKey()));
            for (DataSnapshot currentUser : user.getChildren()) {
                if (currentUser.getKey() != null && currentUser.getKey().equals("recent_chat")) {
                    userChats = currentUser.getValue(Chats.class);
                    if (userChats != null && userChats.getMessage() != null) {
                        users.setRecentChat(userChats.getMessage());
                        Log.d(TAG, "Realtime DB RecentChat: " + userChats.getMessage());
                    }
                } else if (currentUser.getKey() != null && currentUser.getKey().equals("user_profile")) {
                    userProfile = currentUser.getValue(UserProfile.class);
                    if (userProfile != null && userProfile.getProfileImage() != null) {
                        users.setUserImageUrl(userProfile.getProfileImage());
                        users.setUserName(userProfile.getName());
                        Log.d(TAG, "Realtime DB UserProfileImage: " + userProfile.getProfileImage());
                    }
                }
            }
            usersList.add(users);
        }
        redirectToHomePage();
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
}
