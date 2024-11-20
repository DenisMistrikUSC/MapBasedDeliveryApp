package com.example.uscdoordrinkteam65;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.FirebaseApp;
//import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
public class FirebaseFuncs {

    public static Location getStore(@NonNull DataSnapshot snapshot) {
        Location out = (Location) snapshot.getValue(Location.class);
        return out;
    }
}
