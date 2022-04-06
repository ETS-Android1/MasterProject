package com.example.masterproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final String api_URL = "http://10.0.2.2:8000/openapi.json";
    public static final String TAG = "MainActivity";

    Button btnRequest;
    Button btnCurrent;
    Button btnLog;
    BottomNavigationView bnv;
    TextView tvWelcome;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnRequest = findViewById(R.id.btnRequest);
        btnCurrent = findViewById(R.id.btnCurrent);
        btnLog = findViewById(R.id.btnLog);

        btnRequest.setVisibility(View.INVISIBLE);
        btnCurrent.setVisibility(View.INVISIBLE);
        btnLog.setVisibility(View.INVISIBLE);

        tvWelcome = findViewById(R.id.tv_welcome);
        tvWelcome.setText("Self-Sovereign Briefcase");

        bnv = findViewById(R.id.bnv_main);

        bnv.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigation_home:
                        Intent intent4 = new Intent(MainActivity.this, MainActivity.class);
                        startActivity(intent4);
                        return true;

                    case R.id.navigation_request:
                        Intent intent = new Intent(MainActivity.this, RequestCred.class);
                        startActivity(intent);
                        return true;

                    case R.id.navigation_log:
                        Intent intent2 = new Intent(MainActivity.this, LogActivity.class);
                        startActivity(intent2);
                        return true;

                    case R.id.navigation_documents:
                        Intent intent3 = new Intent(MainActivity.this, CurrentDocuments.class);
                        startActivity(intent3);
                        return true;
                }
                return false;
            }
        });

        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //navigate to requestCred page
                Intent intent = new Intent(MainActivity.this, RequestCred.class);
                startActivity(intent);
            }
        });

        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LogActivity.class);
                startActivity(intent);
            }
        });

        btnCurrent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CurrentDocuments.class);
                startActivity(intent);
            }
        });
    }
}