package com.example.uscdoordrinkteam65;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        List<Drink> menu = new ArrayList<>();
        menu.add(new Drink(100, "Red Bull", 2.99));
        Location l = new Location("Village Dining Hall", 34.025737, -118.286393,menu);
        GlobalVars.selectedLocation = l;
        GlobalVars.currCustomer = new Customer("testing","NK7KOarSfVg4fWMmt9wbzZZlTpP2", null, null);
        Intent i = new Intent(TestActivity.this, MenuActivity.class);
        startActivity(i);

    }
}