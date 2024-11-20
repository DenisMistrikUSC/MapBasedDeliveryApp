package com.example.uscdoordrinkteam65;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class    AddItemActivity extends AppCompatActivity {
    private EditText addName, addPrice, addStore, addCaffeine;
    private Button submitButton;
    DatabaseReference reference;
    Location storeLoc;
    String storeName = GlobalVars.currSeller.getStoreName();
    Drink d;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_additem);
            addName = findViewById(R.id.addItemName);
            addPrice = findViewById(R.id.addItemPrice);
            addStore = findViewById(R.id.storeNameInfo);
            addCaffeine = findViewById(R.id.addCaffeineAmount);
            submitButton = findViewById(R.id.submitButton1);
            // read the current store from the DB
            reference = FirebaseDatabase.getInstance("https://usc-doordrink-default-rtdb.firebaseio.com").getReference();
            reference.addValueEventListener(new ValueEventListener()  {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if (!storeName.equals("")) {
                        double lat = (double) snapshot.child("Stores").child(storeName).child("latitude").getValue();
                        double lng = (double) snapshot.child("Stores").child(storeName).child("longitude").getValue();
                        String name = (String) snapshot.child("Stores").child(storeName).child("name").getValue();
                        List<Drink> drinks = new ArrayList<>();
                        for (DataSnapshot drinkSnap : snapshot.child("Stores").child(storeName).child("menu").getChildren()) {
                            Drink dr = (Drink) drinkSnap.getValue(Drink.class);
                            drinks.add(dr);
                        }

                        if (name != null) {
                            storeLoc = new Location(name, lat, lng, drinks);
                        }
                    }
//                System.out.println("1234USERNAME " + u.getName());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            submitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // check for empty on any of the locations
                    String storeNameInput = addStore.getText().toString();
                    String itemName = addName.getText().toString();
                    if (itemName.equals("")) {
                        addName.setError("Cannot be empty.");
                    }
                    else if (storeName.equals("")) {
                        addStore.setError("Cannot be empty.");
                    }
                    else if (addPrice.getText().toString().equals("")) {
                        addPrice.setError("Cannot be empty.");
                    }
                    else if (addCaffeine.getText().toString().equals("")) {
                        addCaffeine.setError("Cannot be empty.");
                    }
                    else {
                        double price = Double.parseDouble(addPrice.getText().toString());
                        int caffeine =Integer.parseInt(addCaffeine.getText().toString());
//                        store = new Location(storeName, lat, lon, null);
                        d = new Drink(caffeine, itemName, price);
//                        reference.child("dummy").setValue("non");
                        addDrinkToDB();
                        Intent i = new Intent( AddItemActivity.this, SellerActivity.class);
                        startActivity(i);
                        Toast.makeText(AddItemActivity.this, "Successfully added a drink", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    private void addDrinkToDB() {
        // set the value of the store in the database table Stores
        storeLoc.addToMenu(d);
//        DatabaseReference reference = FirebaseDatabase.getInstance("https://usc-doordrink-default-rtdb.firebaseio.com").getReference().child("Stores");

        reference.child("Stores").child(storeLoc.getName()).setValue(storeLoc);
        // add the store to the current seller and write to DB
//        GlobalVars.currSeller.setStoreName(store.getName());
//        GlobalVars.currSeller.setSeller();
//        DatabaseReference mDatabase = FirebaseDatabase.getInstance("https://usc-doordrink-default-rtdb.firebaseio.com").getReference().child("Users");
//        mDatabase.child(GlobalVars.currSeller.getUserID()).setValue(GlobalVars.currSeller);

    }
}

