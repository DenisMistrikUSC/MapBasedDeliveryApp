package com.example.uscdoordrinkteam65;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SellerActivity extends AppCompatActivity {
    private Button addMenuButton;
    private Button updatePriceButton;
    //private Button viewOrdersButton;
    private Button addStoreButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller);
        addMenuButton = findViewById(R.id.addItemMenu);
        updatePriceButton = findViewById(R.id.updatePrice);
        //viewOrdersButton = findViewById(R.id.viewOrders);
        addStoreButton = findViewById(R.id.addStore);

        addMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SellerActivity.this, AddItemActivity.class);
                startActivity(i);
            }
        });
        updatePriceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SellerActivity.this, UpdatePriceActivity.class);
                startActivity(i);
            }
        });
        /*viewOrdersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SellerActivity.this, ViewOrdersActivity.class);
                startActivity(i);
            }
        });*/
        addStoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SellerActivity.this, AddStoreActivity.class);
                startActivity(i);
            }
        });
    }
}
