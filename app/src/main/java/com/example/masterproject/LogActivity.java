package com.example.masterproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class LogActivity extends AppCompatActivity {

    public final static String TAG = "LogActivity";
    List<String> log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        RecyclerView rvList = findViewById(R.id.rvLog);
        log = new ArrayList<>();

        log.add("YEEEEESAAAA");

    }
}