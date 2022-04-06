package com.example.masterproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class LogActivity extends AppCompatActivity {

    public final static String TAG = "LogActivity";
    TextView tvLog;
    Button btnBack;
    BottomNavigationView bnv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        bnv = findViewById(R.id.bnv_main);

        bnv.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigation_home:
                        Intent intent4 = new Intent(LogActivity.this, MainActivity.class);
                        startActivity(intent4);
                        return true;

                    case R.id.navigation_request:
                        Intent intent = new Intent(LogActivity.this, RequestCred.class);
                        startActivity(intent);
                        return true;

                    case R.id.navigation_log:
                        Intent intent2 = new Intent(LogActivity.this, LogActivity.class);
                        startActivity(intent2);
                        return true;

                    case R.id.navigation_documents:
                        Intent intent3 = new Intent(LogActivity.this, CurrentDocuments.class);
                        startActivity(intent3);
                        return true;
                }
                return false;
            }
        });

        tvLog = findViewById(R.id.tvLog);

        File file = new File(LogActivity.this.getFilesDir().getAbsolutePath() + "/logFile.txt");
        String myLog = "";
        if (file.exists()){
            try {
                FileInputStream fis = new FileInputStream(file);
                DataInputStream dis = new DataInputStream(fis);
                BufferedReader br = new BufferedReader(new InputStreamReader(dis));

                String eachLine;
                while ((eachLine = br.readLine()) != null){
                    myLog = myLog + eachLine + "\n";
                }
                br.close();
                dis.close();
                fis.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            tvLog.setTextSize(16);
            tvLog.setText(myLog);
        }

        else{
            tvLog.setTextSize(24);
            tvLog.setText("Emptiness...");
        }




    }
}