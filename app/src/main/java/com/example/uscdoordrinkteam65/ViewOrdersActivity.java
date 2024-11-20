package com.example.uscdoordrinkteam65;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class ViewOrdersActivity extends AppCompatActivity {
    private Button viewOrdersButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vieworders);
        viewOrdersButton = findViewById(R.id.viewOrdersButton);
    }
}
