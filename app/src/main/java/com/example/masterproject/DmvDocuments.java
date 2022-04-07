package com.example.masterproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DmvDocuments extends AppCompatActivity {

    ImageView ivDmvRecord;
    Spinner spinDetailsDmv;
    WebView wvDetailsDmv;
    BottomNavigationView bnv;
    private String TAG = "dmvDocuments";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dmv_documents);

        bnv = findViewById(R.id.bnv_dmv);

        bnv.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigation_home:
                        Intent intent4 = new Intent(DmvDocuments.this, MainActivity.class);
                        startActivity(intent4);
                        return true;

                    case R.id.navigation_request:
                        Intent intent = new Intent(DmvDocuments.this, RequestCred.class);
                        startActivity(intent);
                        return true;

                    case R.id.navigation_log:
                        Intent intent2 = new Intent(DmvDocuments.this, LogActivity.class);
                        startActivity(intent2);
                        return true;

                    case R.id.navigation_documents:
                        Intent intent3 = new Intent(DmvDocuments.this, CurrentDocuments.class);
                        startActivity(intent3);
                        return true;
                }
                return false;
            }
        });

        spinDetailsDmv = findViewById(R.id.spinDetailsDmv);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.dmv_info, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinDetailsDmv.setAdapter(adapter);

        spinDetailsDmv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> adapterView, View view, int i, long l) {
                switch(i) {
                    case 0:
                        wvDetailsDmv.loadUrl("about:blank");
                        break;
                    case 1:
                        loadDmvRecord();
                        break;
                    case 2:
                        popAlert();
                        break;
                }
            }

            @Override
            public void onNothingSelected(android.widget.AdapterView<?> adapterView) {

            }
        });


        wvDetailsDmv = findViewById(R.id.wvDetailsDmv);

        ivDmvRecord = findViewById(R.id.ivDmvRecord);

        //check this url
        //String img_url = "https://raw.githubusercontent.com/briankptan/MasterProject/master/IDR_DMV.png";
        String img_url = "https://raw.githubusercontent.com/briankptan/MasterProject/master/IDR_DL_SAMPLE.png";

        Picasso.get().load(img_url).resize(1500,800).into(ivDmvRecord);
    }

    private void popAlert() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(DmvDocuments.this);
        builder1.setMessage("Are you sure?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String path = DmvDocuments.this.getFilesDir().getAbsolutePath() + "/dmv_KEY.txt";
                        new File(path).delete();
                        writeToLog();
                        dialog.cancel();
                        backToMain();
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    private void backToMain() {
        Toast.makeText(DmvDocuments.this, "DMV Credential Revoked", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(DmvDocuments.this, MainActivity.class);
        startActivity(intent);
    }

    private void writeToLog() {
        try{
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(DmvDocuments.this.openFileOutput("logFile.txt", Context.MODE_APPEND));
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
            String currentDateTime = dateFormat.format(new Date());
            outputStreamWriter.write(currentDateTime + " DMV Credential Revoked\n");

            outputStreamWriter.close();
        } catch (IOException e) {
            Log.d(TAG, "FAILED TO WRITE TO Log");
        }
    }

    void loadDmvRecord(){
        String transcriptURL = "https://www.briankptan.com/dmv-record";
        wvDetailsDmv.loadUrl(transcriptURL);
    }
}