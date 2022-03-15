package com.example.masterproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.masterproject.ui.login.LoginActivityCsuf;

public class RequestCred extends AppCompatActivity {

    Button btnCsuf;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_cred);

        btnCsuf = findViewById(R.id.btnCsuf);

        btnCsuf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //String csuf_url = "https://www.briankptan.com/csuf";
                //Uri uri = Uri.parse(csuf_url);
                //Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                Intent intent = new Intent(RequestCred.this, LoginActivityCsuf.class);
                startActivity(intent);
            }
        });
    }
}