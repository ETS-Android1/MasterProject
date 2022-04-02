package com.example.masterproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class LogActivity extends AppCompatActivity {

    public final static String TAG = "LogActivity";
    TextView tvLog;
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

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
        }

        tvLog.setText(myLog);

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LogActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });




    }
}