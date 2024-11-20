package com.example.uscdoordrinkteam65;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uscdoordrinkteam65.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;

import java.util.ArrayList;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword;
    Button btnLogin;
    ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private User u;
    private String uid;
    DatabaseReference reference;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        inputEmail = findViewById(R.id.username);
        inputPassword = findViewById(R.id.password);
        btnLogin = findViewById(R.id.login);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        progressDialog = new ProgressDialog(this);
        reference = FirebaseDatabase.getInstance("https://usc-doordrink-default-rtdb.firebaseio.com").getReference().child("Users");
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performAuthentication();
            }
        });
        reference.addValueEventListener(new ValueEventListener()  {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(mAuth.getCurrentUser() != null){
                    uid = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
                    u = (User) snapshot.child(uid).getValue(User.class);
                }
                else
                {
                    u = null;
                }
                //uid = mAuth.getCurrentUser().getUid();
                //u = (User) snapshot.child(uid).getValue(User.class);
                if (u != null) {
                    boolean isSeller;
                    String isSellerString = snapshot.child(uid).child("seller").getValue().toString();
                    if(isSellerString.trim().toLowerCase() == "false")
                    {
                         isSeller = false;
                    }
                    else
                    {
                        isSeller = true;
                    }
                    GlobalVars.currentIsSeller = isSeller;
                    if (isSeller) {
                        GlobalVars.currSeller = (Seller) snapshot.child(uid).getValue(Seller.class);

                    } else {
                        GlobalVars.currCustomer = (Customer) snapshot.child(uid).getValue(Customer.class);
                    }
                }
//                System.out.println("1234USERNAME " + u.getName());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
    void performAuthentication() {
        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();
        progressDialog.setMessage("Attempting to sign in...");
        progressDialog.setTitle("Login Status");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    progressDialog.dismiss();
                    sendToNextActivity();
//                    System.out.println("Registration Successful");
                    Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                }
                else {
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "Your email or password is incorrect", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void sendToNextActivity() {

        Intent intent;
        // check if the current user is a seller or a customer
//        DatabaseReference reference = FirebaseDatabase.getInstance("https://usc-doordrink-default-rtdb.firebaseio.com").getReference().child("Users");
        if (u == null) {
            System.out.println("1234major error");
        }
        // take to seller UI
        if (GlobalVars.currentIsSeller) {
            // TODO: add the seller activity here instead of Map
            intent = new Intent(LoginActivity.this, SellerActivity.class);
//            System.out.println("Current seller name " + GlobalVars.currSeller.getName());
        }
        else {
            intent = new Intent(LoginActivity.this, ProfileActivity.class);
//            System.out.println("Current customer name " + GlobalVars.currCustomer.getName());
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}