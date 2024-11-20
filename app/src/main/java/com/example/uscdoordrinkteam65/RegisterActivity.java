package com.example.uscdoordrinkteam65;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {
    EditText inputName, inputEmail, inputPassword;
    Button btnRegister;
    ProgressDialog progressDialog;
    Switch isSeller;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        inputName = findViewById(R.id.registerName);
        inputEmail = findViewById(R.id.registerEmail);
        inputPassword = findViewById(R.id.registerPassword);
        btnRegister = findViewById(R.id.buttonRegister);
        isSeller = findViewById(R.id.switch1);
        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance("https://usc-doordrink-default-rtdb.firebaseio.com").getReference();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performAuthentication();
            }
        });

    }
    private void performAuthentication() {
        String name = inputName.getText().toString();
        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();
        if (name.equals("")) {
            inputName.setError("Name cannot be empty");
        }
        else if (password.length() < 6) {
            inputPassword.setError("Password must be at least 6 characters");
        }
        else {
            progressDialog.setMessage("Please wait, registering your account...");
            progressDialog.setTitle("Registration:");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        progressDialog.dismiss();
                        boolean switchState = isSeller.isChecked();


                        // get the current user ID

                        String uid = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
                        // create a new user and add to database
                        User u;
                        if (!switchState) {
                            // create a customer

                            u = new Customer(name, uid, new Order(), new ArrayList<>());
                        }
                        else {
                            // create a Seller
                            u = new Seller(name, uid, "");
//                            System.out.println("Make seller here");
                        }
                        mDatabase.child("Users").child(uid).setValue(u);

                        sendToNextActivity();
//                        System.out.println("Registration Successful");
                        Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        progressDialog.dismiss();
                        Toast.makeText(RegisterActivity.this, "Registration failed. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    private void sendToNextActivity() {

        if (mAuth.getCurrentUser() != null) {
            System.out.println("USER LOGGED IN");
        }
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}
