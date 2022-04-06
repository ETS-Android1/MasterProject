package com.example.masterproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.masterproject.ui.login.LoginActivityCsuf;
import com.example.masterproject.ui.login.LoginActivityDph;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class RequestCred extends AppCompatActivity {

    Button btnCsuf;
    Button btnDph;
    Button btnDmv;
    BottomNavigationView bnv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_cred);

        btnCsuf = findViewById(R.id.btnCsuf);
        btnDph = findViewById(R.id.btnDph);
        btnDmv = findViewById(R.id.btnDmv);

        bnv = findViewById(R.id.bnv_main);

        bnv.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigation_home:
                        Intent intent4 = new Intent(RequestCred.this, MainActivity.class);
                        startActivity(intent4);
                        return true;

                    case R.id.navigation_request:
                        Intent intent = new Intent(RequestCred.this, RequestCred.class);
                        startActivity(intent);
                        return true;

                    case R.id.navigation_log:
                        Intent intent2 = new Intent(RequestCred.this, LogActivity.class);
                        startActivity(intent2);
                        return true;

                    case R.id.navigation_documents:
                        Intent intent3 = new Intent(RequestCred.this, CurrentDocuments.class);
                        startActivity(intent3);
                        return true;

                }
                return false;
            }
        });

        btnCsuf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RequestCred.this, LoginActivityCsuf.class);
                startActivity(intent);
            }
        });

        btnDph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RequestCred.this, LoginActivityDph.class);
                intent.putExtra("key", "dph");
                startActivity(intent);
            }
        });

        btnDmv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RequestCred.this, LoginActivityDph.class);
                intent.putExtra("key", "dmv");
                startActivity(intent);
            }
        });

    }
}