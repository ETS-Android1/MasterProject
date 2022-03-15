package com.example.masterproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

public class CurrentDocuments extends AppCompatActivity {

    ImageButton imgBtnCsuf;
    TextView tvEmpty;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_documents);

        tvEmpty = findViewById(R.id.tvEmpty);
        tvEmpty.setVisibility(View.INVISIBLE);



        imgBtnCsuf = findViewById(R.id.imgBtnCsuf);
        //imgBtnCsuf.setVisibility(View.INVISIBLE);
        //imgBtnCsuf.setBackground(null);

        imgBtnCsuf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CurrentDocuments.this, SchoolDocuments.class);
                startActivity(intent);
            }
        });


    }
}