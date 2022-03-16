package com.example.masterproject.ui.login;

import android.app.Activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.masterproject.R;
import com.example.masterproject.RequestedPage;
import com.example.masterproject.ui.login.LoginViewModel;
import com.example.masterproject.ui.login.LoginViewModelFactory;

import java.io.IOException;
import java.io.OutputStreamWriter;

public class LoginActivityDph extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    private String TAG = "LoginActivityDph";
    ImageView ivLogin;
    String value;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_dph);

        ivLogin = findViewById(R.id.iv_login);
        Bundle extras = getIntent().getExtras();
        value = extras.getString("key");

        if (value.equals("dph")){
            ivLogin.setImageResource(R.mipmap.cadph_1_logo);
        }
        else
            ivLogin.setImageResource(R.mipmap.dmv_1_logo);

        loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);

        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                loginButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);

                setResult(Activity.RESULT_OK);

                //Complete and destroy login activity once successful
                finish();
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                return false;
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);
                loginViewModel.login(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
                String addData = usernameEditText.getText().toString();

                //String check_ID = "http://10.0.2.2:8000/verify/?credential=" + addData;
                String check_ID = "http://127.0.0.1:8000/verify/?credential=" + addData;
                Log.d(TAG, "1: " + check_ID);

                RequestQueue queue = Volley.newRequestQueue(LoginActivityDph.this);

                StringRequest stringRequestVerify = new StringRequest(Request.Method.GET, check_ID,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(LoginActivityDph.this, response, Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "2 " + response);
                                if (response.equals("null")){
                                    postData(addData, queue);
                                    nextActivity();

                                    //nextActivity();
                                }
                                else{
                                    dupData();
                                    getKey(addData, queue);
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivityDph.this, "FAILED_verify", Toast.LENGTH_LONG).show();
                    }
                });
                queue.add(stringRequestVerify);

            }
        });
    }

    void postData(String addData, RequestQueue queue){
        String post_url = "http://127.0.0.1:8000/add_block/" + "?data=" + addData;
        //String post_url = "http://10.0.2.2:8000/add_block/" + "?data=" + addData;
        Log.d(TAG, "3 " + post_url);
        Log.d(TAG, "4 " + addData);


        StringRequest stringRequest = new StringRequest(Request.Method.POST, post_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(LoginActivityDph.this, "DATA POSTED", Toast.LENGTH_LONG).show();
                        Log.d(TAG, "5 "+ response);
                        getKey(addData, queue);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivityDph.this, "FAILED", Toast.LENGTH_LONG).show();
            }
        }){
//                    @Override
//                    protected Map<String, String> getParams(){
//                        Map<String, String> params = new HashMap<String, String>();
//
//                        params.put("name", addData);
//                        return params;
//                       }
        };
        queue.add(stringRequest);
    }

    void dupData(){
        Intent intent = new Intent(LoginActivityDph.this, RequestedPage.class);
        startActivity(intent);
        Log.d(TAG, "DUPLICATE DATA. NO POST");
    }

    void getKey(String addData, RequestQueue queue){
        //String get_url = "http://10.0.2.2:8000/publicKey/?credential=" + addData;
        String get_url = "http://127.0.0.1:8000/publicKey/?credential=" + addData;
        Log.d(TAG, get_url);

        StringRequest stringRequestKey = new StringRequest(Request.Method.GET, get_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(LoginActivityCsuf.this, response, Toast.LENGTH_LONG).show();
                        Log.d(TAG, "6 pubKey: " + response);
                        writeToFile(response, LoginActivityDph.this);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivityDph.this, "FAILED_pubkey", Toast.LENGTH_LONG).show();
            }
        });
        queue.add(stringRequestKey);
    }

    void writeToFile(String data, Context context){
        try{
            if (value.equals("dph")){
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("dph_KEY.txt", Context.MODE_PRIVATE));
                outputStreamWriter.write(data);
                Log.d(TAG, "DPH File created");
                //String path = context.getFilesDir().getAbsolutePath();
                outputStreamWriter.close();
            }
            else{
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("dmv_KEY.txt", Context.MODE_PRIVATE));
                outputStreamWriter.write(data);
                Log.d(TAG, "DMV File created");
                //String path = context.getFilesDir().getAbsolutePath();
                outputStreamWriter.close();
            }


        } catch (IOException e) {
            Log.d(TAG, "FAILED TO WRITE TO csuf_KEY");
        }
    }

    void nextActivity(){
        Intent intent = new Intent(LoginActivityDph.this, RequestedPage.class);
        if (value.equals("dph"))
            intent.putExtra("key", "dph");
        else
            intent.putExtra("key", "dmv");
        startActivity(intent);
        Log.d(TAG, "7 POST DATA");
    }
}