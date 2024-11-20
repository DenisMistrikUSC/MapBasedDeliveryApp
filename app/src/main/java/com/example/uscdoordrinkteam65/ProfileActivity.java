package com.example.uscdoordrinkteam65;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class ProfileActivity extends AppCompatActivity {
    Button goMap;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private User u;
    private String uid;
    ProgressDialog progressDialog;
    DatabaseReference reference;
    TextView currName, currUserType, currUserID;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        goMap = findViewById(R.id.goToMap);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        progressDialog = new ProgressDialog(this);
        reference = FirebaseDatabase.getInstance("https://usc-doordrink-default-rtdb.firebaseio.com").getReference().child("Users");
        reference.addValueEventListener(new ValueEventListener()  {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                uid = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
                u = (User) snapshot.child(uid).getValue(User.class);
               if (u != null) {
                   //Log.d("tag", u.getName());
                   currName = (TextView) findViewById(R.id.currName);
                   String set ="Name: " + u.getName();
                   currName.setText(set);
                   if(u.isSeller())
                   {
                       currUserType = (TextView) findViewById(R.id.currUserType);
                       currUserType.setText("Type: Seller");
                   }
                   else
                   {
                       currUserType = (TextView) findViewById(R.id.currUserType);
                       currUserType.setText("Type: Customer");
                   }
                   currUserID = (TextView) findViewById(R.id.currUserID);
                   String set2 ="User ID: " + u.getUserID();
                   currUserID.setText(set2);
               }
           }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        goMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                if(!GlobalVars.currentIsSeller) {
                    intent = new Intent(ProfileActivity.this, MainActivity.class);
                }
                else
                {
                    intent = new Intent(ProfileActivity.this, SellerActivity.class);
                }
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }
}
