package com.example.uscdoordrinkteam65;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalTime;

public class CheckoutActivity extends AppCompatActivity {
    private Button confirmCheckout;
    private TextView orderTotal;
    Delivery currentDelivery;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        confirmCheckout = (Button) findViewById(R.id.confirmCheckout);
        orderTotal = (TextView) findViewById(R.id.orderTotalDisplay);
        LocalTime currTime = LocalTime.now();
        orderTotal.setText("Your total is: $" + GlobalVars.currCustomer.getCurrentOrder().getTotalCost() +
                "\nDelivery ETA is " + GlobalVars.currentEta + " minutes." +
                "\nEstimated delivery time: " + currTime.plusMinutes(GlobalVars.currentEta));
        confirmCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentDelivery = new Delivery(GlobalVars.currCustomer.getCurrentOrder(), GlobalVars.currentEta, GlobalVars.selectedLocation.getName());
                addDelivery();
                Intent i = new Intent(CheckoutActivity.this, OrderConfirmedActivity.class);
                startActivity(i);
            }
        });
    }
    private void addDelivery() {
        // add the current checked out order to DB
        DatabaseReference reference2 = FirebaseDatabase.getInstance("https://usc-doordrink-default-rtdb.firebaseio.com").getReference().child("Deliveries").child(GlobalVars.currCustomer.getUserID());
        // get the delivery number
        String deliveryNum = "" + GlobalVars.currCustomer.getNumOrders();
        // make a delivery obj
        reference2.child(deliveryNum).setValue(currentDelivery);
        // update the customer
        GlobalVars.currCustomer.checkOutCurrentOrder();
        DatabaseReference reference = FirebaseDatabase.getInstance("https://usc-doordrink-default-rtdb.firebaseio.com").getReference().child("Users");
        reference.child(GlobalVars.currCustomer.getUserID()).setValue(GlobalVars.currCustomer);
        reference.child(GlobalVars.currCustomer.getUserID()).child("currentOrder").removeValue();


    }

    // TODO: method to add a delivery to the database
}