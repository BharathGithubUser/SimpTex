package com.belivnat.simptex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {
    Handler mhandler;
    Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mhandler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                Intent chatListActivity = new Intent(SplashActivity.this, ChatListActivity.class);
                startActivity(chatListActivity);
                finish();
            }
        };
        mhandler.postDelayed(runnable, 2000);
    }
}
