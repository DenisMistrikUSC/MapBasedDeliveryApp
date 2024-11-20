package com.example.uscdoordrinkteam65;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class UpdatePriceActivity extends AppCompatActivity {
    private EditText updatePrice, updateStore, updateDrink;
    private Button UpdatePriceButton;
    String storeName = GlobalVars.currSeller.getStoreName();
    Location storeLoc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updateprice);
        UpdatePriceButton = findViewById(R.id.button2);
        updatePrice = findViewById(R.id.updatePriceSeller);
        //Log.d("tag", String.valueOf(updatePrice));
        updateStore = findViewById(R.id.updatePriceName);
        updateDrink = findViewById(R.id.updatePriceDrink);
        DatabaseReference reference = FirebaseDatabase.getInstance("https://usc-doordrink-default-rtdb.firebaseio.com").getReference().child("Stores");
        reference.addValueEventListener(new ValueEventListener()  {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (!storeName.equals("")) {
                    Location s = (Location) snapshot.child(storeName).getValue(Location.class);
//                    double lat = (double) snapshot.child(storeName).child("latitude").getValue();
//                    double lng = (double) snapshot.child(storeName).child("longitude").getValue();
//                    String name = (String) snapshot.child(storeName).child("name").getValue();
//                    List<Drink> drinks = new ArrayList<>();
//                    for (DataSnapshot drinkSnap : snapshot.child(storeName).child("menu").getChildren()) {
//                        Drink dr = (Drink) drinkSnap.getValue(Drink.class);
//                        drinks.add(dr);
//                    }
//
//                    if (name != null) {
//                        storeLoc = new Location(name, lat, lng, drinks);
//                    }
                    if (s !=null) {
                        storeLoc = s;
                    }
                }
//                System.out.println("1234USERNAME " + u.getName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        UpdatePriceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (updateStore.getText().toString().equals("")) {
                    updateStore.setError("Cannot be empty.");
                }
                else if (updateDrink.getText().toString().equals("")) {
                    updateDrink.setError("Cannot be empty.");
                }
                else if (updatePrice.getText().toString().equals("")) {
                    updatePrice.setError("Cannot be empty.");
                }

//                DatabaseReference store = reference.child(updateStore.getText().toString());
//                DatabaseReference menu = store.child("menu");
                for (Drink dr: storeLoc.getMenu()) {
                    if (dr.getName().equals(updateDrink.getText().toString())) {
                        dr.setPrice(Double.parseDouble(updatePrice.getText().toString()));
                    }
                }
                reference.child(storeLoc.getName()).setValue(storeLoc);
                //DatabaseReference drinksNeeded = menu.child("price");
//                menu.child(updateDrink.getText().toString()).child("price").setValue(Double.parseDouble(updatePrice.getText().toString()));
                Intent i = new Intent( UpdatePriceActivity.this, SellerActivity.class);
                startActivity(i);
                Toast.makeText(UpdatePriceActivity.this, "Successfully updated drink price", Toast.LENGTH_SHORT).show();

            }
        });
    }
}

