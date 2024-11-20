package com.example.uscdoordrinkteam65;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OrderConfirmedActivity extends AppCompatActivity {
    Button backToMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmed);
        backToMap = (Button) findViewById(R.id.backToMap);
        backToMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(OrderConfirmedActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
    }
}