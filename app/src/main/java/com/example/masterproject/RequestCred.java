package com.example.masterproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.masterproject.ui.login.LoginActivityCsuf;
import com.example.masterproject.ui.login.LoginActivityDph;

public class RequestCred extends AppCompatActivity {

    Button btnCsuf;
    Button btnDph;
    Button btnDmv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_cred);

        btnCsuf = findViewById(R.id.btnCsuf);
        btnDph = findViewById(R.id.btnDph);
        btnDmv = findViewById(R.id.btnDmv);


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