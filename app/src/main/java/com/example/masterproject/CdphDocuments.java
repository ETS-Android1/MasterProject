package com.example.masterproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.squareup.picasso.Picasso;

public class CdphDocuments extends AppCompatActivity {

    ImageView ivCdphRecord;
    Spinner spinDetailsCdph;
    WebView wvDetailsCdph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cdph_documents);

        spinDetailsCdph = findViewById(R.id.spinDetailsCdph);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.cdph_info, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinDetailsCdph.setAdapter(adapter);

        spinDetailsCdph.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> adapterView, View view, int i, long l) {
                switch(i) {
                    case 0:
                        wvDetailsCdph.loadUrl("about:blank");
                        break;
                    case 1:
                        loadVaccine();
                        break;
                    case 2:
                        wvDetailsCdph.loadUrl("about:blank");
                        break;
                }
            }

            @Override
            public void onNothingSelected(android.widget.AdapterView<?> adapterView) {

            }
        });


        wvDetailsCdph = findViewById(R.id.wvDetailsCdph);
        ivCdphRecord = findViewById(R.id.ivCdphRecord);

        //check this url
        String img_url = "https://raw.githubusercontent.com/briankptan/MasterProject/master/IDR_DPH.png";

        Picasso.get().load(img_url).resize(1500,800).into(ivCdphRecord);
    }

    void loadVaccine(){
        String vaccineURL = "https://www.briankptan.com/vaccination-record";
        wvDetailsCdph.loadUrl(vaccineURL);
    }
}