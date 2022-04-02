package com.example.masterproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DmvDocuments extends AppCompatActivity {

    ImageView ivDmvRecord;
    Spinner spinDetailsDmv;
    WebView wvDetailsDmv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dmv_documents);

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
                        wvDetailsDmv.loadUrl("about:blank");
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
        String img_url = "https://raw.githubusercontent.com/briankptan/MasterProject/master/IDR_DMV.png";

        Picasso.get().load(img_url).resize(1500,800).into(ivDmvRecord);
    }

    void loadDmvRecord(){
        String transcriptURL = "https://www.briankptan.com/dmv-record";
        wvDetailsDmv.loadUrl(transcriptURL);
    }
}