package com.example.masterproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class RequestedPage extends AppCompatActivity {

    TextView tv_requestMade;
    Button btn_home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requested_page);

        tv_requestMade = findViewById(R.id.tv_requestMade);
        tv_requestMade.setText("Your request has been submitted.\n" +
                " View Request Log for status of requests.\n" +
                " Successful requests will populate in your Current Documents.");

        btn_home = findViewById(R.id.btnReturn);

        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RequestedPage.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}