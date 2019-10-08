package com.belivnat.simptex.application;

import android.app.Application;
import android.os.Bundle;

import com.belivnat.simptex.utils.Constants;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MyApplication extends Application {
    static DatabaseReference databaseReference = null;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate();
    }
    public static DatabaseReference firebaseDatabaseReference() {
        if(databaseReference == null) {
            databaseReference = FirebaseDatabase.getInstance().getReference(Constants.FIRESTORE_BASE_PATH);
            return databaseReference;
        } else {
            return  databaseReference;
        }
    }
}
