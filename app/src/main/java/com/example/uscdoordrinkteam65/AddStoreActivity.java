package com.example.uscdoordrinkteam65;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddStoreActivity extends AppCompatActivity {
    private EditText addStore, addLat, addLon;
    private Button submit;
    Location store;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addstore);
        addStore = findViewById(R.id.addStoreName);
        addLat = findViewById(R.id.setLat);
        addLon = findViewById(R.id.setLon);
        submit = findViewById(R.id.submitButton2);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // check for empty on any of the locations
                String storeName = addStore.getText().toString();


                if (storeName.equals("")) {
                    addStore.setError("Cannot be empty.");
                }
                else if (addLat.getText().toString().equals("")) {
                    addLat.setError("Cannot be empty.");
                }
                else if (addLon.getText().toString().equals("")) {
                    addLon.setError("Cannot be empty.");
                }
                else {
                    double lat = Double.parseDouble(addLat.getText().toString());
                    double lon = Double.parseDouble(addLon.getText().toString());
                    store = new Location(storeName, lat, lon, null);
                    addStoreToDB();
                    Intent i = new Intent( AddStoreActivity.this, SellerActivity.class);
                    startActivity(i);
                    Toast.makeText(AddStoreActivity.this, "Succesfully added a store", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    private void addStoreToDB() {
        // set the value of the store in the database table Stores
        DatabaseReference reference = FirebaseDatabase.getInstance("https://usc-doordrink-default-rtdb.firebaseio.com").getReference().child("Stores");
        reference.child(store.getName()).setValue(store);
        // add the store to the current seller and write to DB
        GlobalVars.currSeller.setStoreName(store.getName());
        GlobalVars.currSeller.setSeller();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance("https://usc-doordrink-default-rtdb.firebaseio.com").getReference().child("Users");
        mDatabase.child(GlobalVars.currSeller.getUserID()).setValue(GlobalVars.currSeller);

    }
}
