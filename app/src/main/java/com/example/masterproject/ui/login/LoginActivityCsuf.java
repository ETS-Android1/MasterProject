package com.example.masterproject.ui.login;

import android.app.Activity;

import androidx.annotation.NonNull;
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
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.masterproject.CurrentDocuments;
import com.example.masterproject.LogActivity;
import com.example.masterproject.MainActivity;
import com.example.masterproject.R;
import com.example.masterproject.RequestCred;
import com.example.masterproject.RequestedPage;
import com.example.masterproject.ui.login.LoginViewModel;
import com.example.masterproject.ui.login.LoginViewModelFactory;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.example.masterproject.MainActivity.TAG;

public class LoginActivityCsuf extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    private String TAG = "LoginActivity";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_csuf);
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

                loadingProgressBar.setVisibility(View.VISIBLE);
                setResult(Activity.RESULT_OK);

                //Complete and destroy login activity once successful
                //finish();
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

                String check_ID = "http://10.0.2.2:8000/verify/?credential=" + addData;
                //String check_ID = "http://127.0.0.1:8000/verify/?credential=" + addData;
                Log.d(TAG, "1: " + check_ID);

                writeLogFile();

                RequestQueue queue = Volley.newRequestQueue(LoginActivityCsuf.this);

                StringRequest stringRequestVerify = new StringRequest(Request.Method.GET, check_ID,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                //Toast.makeText(LoginActivityCsuf.this, response, Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "2 " + response);
                                if (response.equals("null")){
                                    postData(addData, queue);
                                    nextActivity();
                                }
                                else{
                                    String path = LoginActivityCsuf.this.getFilesDir().getAbsolutePath() + "/csuf_KEY.txt";
                                    File file = new File(path);
                                    if (!file.exists()){
                                        Log.d(TAG, "FILE NOT EXISTS " + response);
                                        getKey(addData, queue);
                                        nextActivity();
                                    }
                                    else{
                                        dupData();
                                        getKey(addData, queue);
                                    }
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "FAILED HERE " + "ON ERROR");
                        Toast.makeText(LoginActivityCsuf.this, "FAILED_verify", Toast.LENGTH_LONG).show();
                        }
                    });
                queue.add(stringRequestVerify);
            }
        });
    }

    private void writeLogFile() {
        try{
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(LoginActivityCsuf.this.openFileOutput("logFile.txt", Context.MODE_APPEND));
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
            String currentDateTime = dateFormat.format(new Date());
            outputStreamWriter.write(currentDateTime + " CSUF Credential Requested\n");
            Log.d(TAG, "CSUF Log created");
            outputStreamWriter.close();
        } catch (IOException e) {
            Log.d(TAG, "FAILED TO WRITE TO csuf_Log");
        }
    }

    void postData(String addData, RequestQueue queue){
        //String post_url = "http://127.0.0.1:8000/add_block/" + "?data=" + addData;
        String post_url = "http://10.0.2.2:8000/add_block/" + "?data=" + addData;

        Log.d(TAG, "3 " + post_url);
        Log.d(TAG, "4 " + addData);


        StringRequest stringRequest = new StringRequest(Request.Method.POST, post_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(LoginActivityCsuf.this, "DATA POSTED", Toast.LENGTH_LONG).show();
                        Log.d(TAG, "5 "+ response);
                        getKey(addData, queue);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivityCsuf.this, "FAILED", Toast.LENGTH_LONG).show();
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
        Intent intent = new Intent(LoginActivityCsuf.this, RequestedPage.class);
        startActivity(intent);
        Log.d(TAG, "DUPLICATE DATA. NO POST");
    }

    void getKey(String addData, RequestQueue queue){
        String get_url = "http://10.0.2.2:8000/publicKey/?credential=" + addData;
        //String get_url = "http://127.0.0.1:8000/publicKey/?credential=" + addData;

        Log.d(TAG, get_url);

        StringRequest stringRequestKey = new StringRequest(Request.Method.GET, get_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(LoginActivityCsuf.this, response, Toast.LENGTH_LONG).show();
                        Log.d(TAG, "6 pubKey: " + response);
                        writeToFile(response, LoginActivityCsuf.this);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(LoginActivityCsuf.this, "FAILED_pubkey", Toast.LENGTH_LONG).show();
            }
        });
        queue.add(stringRequestKey);
    }

    void writeToFile(String data, Context context){
        try{
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("csuf_KEY.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            Log.d(TAG, "CSUF File created");
            String path = context.getFilesDir().getAbsolutePath();
            outputStreamWriter.close();
        } catch (IOException e) {
            Log.d(TAG, "FAILED TO WRITE TO csuf_KEY");
        }
    }

    void nextActivity(){
        Intent intent = new Intent(LoginActivityCsuf.this, RequestedPage.class);
        intent.putExtra("key", "csuf");
        startActivity(intent);
        Log.d(TAG, "7 POST DATA");
    }
}