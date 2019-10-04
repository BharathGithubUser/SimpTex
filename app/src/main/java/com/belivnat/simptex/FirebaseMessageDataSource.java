package com.belivnat.simptex;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

abstract class FirebaseMessageDataSource {
    private DatabaseReference mDatabase;
    interface ValueChangeListener {
        void onDataChange(@NonNull DataSnapshot dataSnapshot);
        void onCancelled(@NonNull DatabaseError error);
        void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s);
        void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s);
        void onChildRemoved(@NonNull DataSnapshot dataSnapshot);
        void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s);
    }
    FirebaseMessageDataSource(final ValueChangeListener dataChangedListenable) {
        mDatabase = FirebaseDatabase.getInstance().getReference(Constants.FIRESTORE_BASE_PATH);
        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

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
        });
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataChangedListenable.onDataChange(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                dataChangedListenable.onCancelled(databaseError);
            }
        });
    }
}
