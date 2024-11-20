package com.example.uscdoordrinkteam65;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {
    ArrayAdapter<String> adapter;
    ArrayList<Drink> cart = new ArrayList<>();
    Button checkoutButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if(GlobalVars.currCustomer != null){
            if(GlobalVars.currCustomer.getCurrentOrder() != null){
                cart = GlobalVars.currCustomer.getCurrentOrder().getDrinks();
            }
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        ListView listView = (ListView) findViewById(R.id.list);
        checkoutButton = (Button) findViewById(R.id.checkout);
        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CartActivity.this, CheckoutActivity.class);
                startActivity(i);
            }
        });
        ArrayList<String> items = new ArrayList<>();

        for(Drink d: cart){
            double pricetotal = d.getCount() * d.getPrice();
            items.add(d.getName() + ", x" + d.getCount() + "\n" + "Price: $" + pricetotal);
        }
        adapter = new ArrayAdapter(this, R.layout.listitem, R.id.itemTextView,items);
        listView.setAdapter(adapter);
    }
}