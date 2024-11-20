package com.example.uscdoordrinkteam65;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.ListViewCompat;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

//import com.google.firebase.auth.FirebaseAuth;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class MenuActivity extends AppCompatActivity {
   // ArrayAdapter<String> adapter;
    List<Drink> menu = GlobalVars.selectedLocation.getMenu();
    ArrayAdapter<String> adapter;
    String drinkName;
    Button cartbutton;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        mAuth = FirebaseAuth.getInstance();
        ListView listView = (ListView) findViewById(R.id.list);
        Button addToCart = (Button) findViewById(R.id.addcart);
        cartbutton = findViewById(R.id.cart2);
        cartbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MenuActivity.this, CartActivity.class);
                startActivity(i);
            }
        });
        ArrayList<String> items = new ArrayList<>();
        System.out.println(menu.size());
        for(Drink d: menu){
            items.add(d.getName() + "," + "\n" + "Price: $" + d.getPrice() + "\n Caffeine amount: " + d.getCaffeine() + " mg");
        }
        adapter = new ArrayAdapter(this, R.layout.listitem, R.id.itemTextView,items);
        listView.setAdapter(adapter);

        // Display the item name when the item's row is clicked
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MenuActivity.this, "Item Selected", Toast.LENGTH_SHORT).show();
                String data=(String)parent.getItemAtPosition(position);
                int iend = data.indexOf(",");
                drinkName = data.substring(0 , iend);

            }
        });
        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToCart(drinkName);
            }
        });
    }

    private void loadMenu(){

    }
    private void addToCart(String d){
        GlobalVars.currCustomer.updateCaffeineIntake();
        String uid = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        Drink orderDrink = new Drink();
        for(Drink dr: menu){
            if(dr.getName().equals(d)){
                orderDrink = dr;
            }
        }
        if(orderDrink.getName() == null){
            Toast.makeText(MenuActivity.this, "No item selected", Toast.LENGTH_SHORT).show();

            return;
        }
        if (!GlobalVars.currCustomer.checkCanHaveDrink(orderDrink)) {
//            Toast.makeText(MenuActivity.this, "Cannot have this drink, exceeded total caffeine limit of 400mg.", Toast.LENGTH_SHORT).show();
            View view = findViewById(R.id.addcart);
            Snackbar snackbar = Snackbar.make(view,"Cannot add this drink, it would exceed total daily caffeine limit of 400mg.",Snackbar.LENGTH_SHORT);
            snackbar.show();
            return;

        }
        boolean isInOrder = false;
        for(Drink dr: GlobalVars.currCustomer.getCurrentOrder().getDrinks()){
            if(dr.getName().equals(orderDrink.getName())){
//                orderDrink.addCount();
                dr.addCount();
                GlobalVars.currCustomer.getCurrentOrder().addSupplementalInfo(dr.getPrice(), dr.getCaffeine());
              //  dr.addCount();
                isInOrder = true;
            }
        }
        if(!isInOrder){
            GlobalVars.currCustomer.addToOrder(orderDrink);
        }
        System.out.println(orderDrink.getName());
//        DatabaseReference reference = FirebaseDatabase.getInstance("https://usc-doordrink-default-rtdb.firebaseio.com").getReference().child("Users")
//                .child(uid).child("order").child(drinkName);


    }
}