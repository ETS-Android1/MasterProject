package com.example.masterproject;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class SchoolDocuments extends AppCompatActivity {

    ImageView ivSchoolRecord;
    Spinner spinDetails;
    WebView wvDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_documents);

        spinDetails = findViewById(R.id.spinDetails);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.school_info, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinDetails.setAdapter(adapter);

        spinDetails.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> adapterView, View view, int i, long l) {
                switch(i) {
                    case 0:
                        wvDetails.loadUrl("about:blank");
                        break;
                    case 1:
                        loadTranscript();
                        break;
                    case 2:
                        wvDetails.loadUrl("about:blank");
                        break;
                }
            }

            @Override
            public void onNothingSelected(android.widget.AdapterView<?> adapterView) {

            }
        });


        wvDetails = findViewById(R.id.wvDetails);
        ivSchoolRecord = findViewById(R.id.ivSchoolRecord);

        //check this url
        String img_url = "https://raw.githubusercontent.com/briankptan/MasterProject/master/IDR_CSUF.png";

        Picasso.get().load(img_url).resize(1500,800).into(ivSchoolRecord);
    }

    void loadTranscript(){
        String transcriptURL = "https://www.briankptan.com/transcript";
        wvDetails.loadUrl(transcriptURL);
    }
}