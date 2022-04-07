package com.example.masterproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class RequestedPage extends AppCompatActivity {

    TextView tv_requestMade;
    Button btn_home;
    ImageView iv_Logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requested_page);

        iv_Logo = findViewById(R.id.ivLogo);

        String value;
        int flag = 0;

        try{
            Bundle extras = getIntent().getExtras();
            value = extras.getString("key");
        } catch (Exception e) {
            e.printStackTrace();
            value = "";
        }


        if (value.equals("dph")){
            iv_Logo.setImageResource(R.mipmap.cadph_logo);
        }
        else if (value.equals("csuf")){
            iv_Logo.setImageResource(R.mipmap.csuf_logo);
        }
        else if (value.equals("dmv")){
            iv_Logo.setImageResource((R.mipmap.dmv_1_logo));
        }
        else{
            iv_Logo.setVisibility(View.INVISIBLE);
            flag = 1;
        }

        tv_requestMade = findViewById(R.id.tv_requestMade);

        if (flag == 1){
            tv_requestMade.setText("Your request has been denied.\nYou already have this credential");
        }
        else{
            tv_requestMade.setText("Your request has been submitted.\n");
        }



        btn_home = findViewById(R.id.btnReturn);

        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RequestedPage.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}