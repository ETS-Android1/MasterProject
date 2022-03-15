package com.example.masterproject.ui.login;

import android.app.Activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.masterproject.MainActivity;
import com.example.masterproject.R;
import com.example.masterproject.RequestedPage;
import com.example.masterproject.ui.login.LoginViewModel;
import com.example.masterproject.ui.login.LoginViewModelFactory;

import java.util.HashMap;
import java.util.Map;

import static com.example.masterproject.MainActivity.TAG;

public class LoginActivityCsuf extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    private String TAG = "LoginActivity";
    private boolean isVerified;

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

                String check_ID = "http://10.0.2.2:8000/verify/?credential=" + addData;
                Log.d(TAG, "HEREEEE: " + check_ID);


                RequestQueue queue = Volley.newRequestQueue(LoginActivityCsuf.this);

                //RequestQueue queue1 = Volley.newRequestQueue(LoginActivityCsuf.this);

                StringRequest stringRequestVerify = new StringRequest(Request.Method.GET, check_ID,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(LoginActivityCsuf.this, response, Toast.LENGTH_LONG).show();
                                Log.d(TAG, "yeyee " + response);
                                if (response != "null"){
                                    isVerified = true;
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivityCsuf.this, "FAILED_verify", Toast.LENGTH_LONG).show();
                        }
                    });
                queue.add(stringRequestVerify);

                if (isVerified){

                    //String post_url = "http://127.0.0.1:8000/add_block/" + "?data=" + addData;
                    String post_url = "http://10.0.2.2:8000/add_block/" + "?data=" + addData;
                    Log.d(TAG, post_url);
                    Log.d(TAG, addData);


                    StringRequest stringRequest = new StringRequest(Request.Method.POST, post_url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Toast.makeText(LoginActivityCsuf.this, "DATA POSTED", Toast.LENGTH_LONG).show();
                                    Log.d(TAG, response);
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
//                    }
                    };

                    queue.add(stringRequest);
                    Intent intent = new Intent(LoginActivityCsuf.this, RequestedPage.class);
                    startActivity(intent);
                    Log.d(TAG, "POST DATA");

                }

                else{
                    Intent intent = new Intent(LoginActivityCsuf.this, RequestedPage.class);
                    startActivity(intent);
                    Log.d(TAG, "DUPLICATE DATA. NO POST");
                }


            }
        });
    }
}