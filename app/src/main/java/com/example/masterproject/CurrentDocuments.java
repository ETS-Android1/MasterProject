package com.example.masterproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.io.File;

public class CurrentDocuments extends AppCompatActivity {

    ImageButton imgBtnCsuf;
    ImageButton imgBtnDph;
    ImageButton imgBtnDmv;
    TextView tvEmpty;
    private String TAG = "CurrentDocActivity";
    BottomNavigationView bnv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_documents);

        tvEmpty = findViewById(R.id.tvEmpty);
        tvEmpty.setVisibility(View.INVISIBLE);

        imgBtnCsuf = findViewById(R.id.imgBtnCsuf);
        imgBtnCsuf.setVisibility(View.INVISIBLE);

        imgBtnDph = findViewById(R.id.imgBtnDph);
        imgBtnDph.setVisibility(View.INVISIBLE);

        imgBtnDmv = findViewById(R.id.imgBtnDmv);
        imgBtnDmv.setVisibility(View.INVISIBLE);

        bnv = findViewById(R.id.bnv_current);

        bnv.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigation_home:
                        Intent intent4 = new Intent(CurrentDocuments.this, MainActivity.class);
                        startActivity(intent4);
                        return true;

                    case R.id.navigation_request:
                        Intent intent = new Intent(CurrentDocuments.this, RequestCred.class);
                        startActivity(intent);
                        return true;

                    case R.id.navigation_log:
                        Intent intent2 = new Intent(CurrentDocuments.this, LogActivity.class);
                        startActivity(intent2);
                        return true;

                    case R.id.navigation_documents:
                        Intent intent3 = new Intent(CurrentDocuments.this, CurrentDocuments.class);
                        startActivity(intent3);
                        return true;
                }
                return false;
            }
        });

        //imgBtnCsuf.setBackground(null);

        String path_csuf = CurrentDocuments.this.getFilesDir().getAbsolutePath() + "/csuf_KEY.txt";
        String path_dph = CurrentDocuments.this.getFilesDir().getAbsolutePath() + "/dph_KEY.txt";
        String path_dmv = CurrentDocuments.this.getFilesDir().getAbsolutePath() + "/dmv_KEY.txt";

        File file = new File(path_csuf);
        File file_dmv = new File(path_dmv);
        File file_dph = new File(path_dph);

        if (file.exists() || file_dmv.exists() || file_dph.exists())
            tvEmpty.setVisibility(View.INVISIBLE);
        else if (!file.exists() && !file_dmv.exists() && !file_dph.exists())
            tvEmpty.setVisibility(View.VISIBLE);

        if (file.exists()){
            Log.d(TAG, "file exists");
            imgBtnCsuf.setVisibility(View.VISIBLE);
        }

        if (file_dph.exists()){
            Log.d(TAG, "file exists");
            imgBtnDph.setVisibility(View.VISIBLE);
        }

        if (file_dmv.exists()){
            Log.d(TAG, "file exists");
            imgBtnDmv.setVisibility(View.VISIBLE);
        }


        imgBtnCsuf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CurrentDocuments.this, SchoolDocuments.class);
                startActivity(intent);
            }
        });

        imgBtnDph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CurrentDocuments.this, CdphDocuments.class);
                startActivity(intent);
            }
        });

        imgBtnDmv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CurrentDocuments.this, DmvDocuments.class);
                startActivity(intent);
            }
        });
    }
}