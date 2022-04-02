package com.example.masterproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.RequestParams;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.codepath.asynchttpclient.callback.TextHttpResponseHandler;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Headers;

public class MainActivity extends AppCompatActivity {

    public static final String api_URL = "http://10.0.2.2:8000/openapi.json";
    public static final String TAG = "MainActivity";

    Button btnRequest;
    Button btnCurrent;
    Button btnLog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnRequest = findViewById(R.id.btnRequest);
        btnCurrent = findViewById(R.id.btnCurrent);
        btnLog = findViewById(R.id.btnLog);

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