package com.example.gazownia;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Registration extends AppCompatActivity {
    Button registerBtn;
    Button loginSiteBtn;
    TextView loginTV, passwordTV;
    TextView errorTV;

    String loginS, passwordS;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        loginTV = (TextView) findViewById(R.id.editTextLoginR);
        passwordTV = (TextView) findViewById(R.id.editTextPasswordR);
        errorTV = (TextView)findViewById(R.id.errorTV);
        registerBtn = (Button) findViewById(R.id.registerBtn);
        loginSiteBtn = (Button) findViewById(R.id.backToLoginBtn);

        loginSiteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenLoginPage();
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginS = String.valueOf(loginTV.getText());
                passwordS = String.valueOf(passwordTV.getText());

                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url ="http://192.168.1.184/gazownia/APPregister.php";

                Log.d("#REG"," 1 STOP");
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("#REG"," 2 STOP");
                            if(response.equals("success")){
                                Log.d("CON"," success ");
                                Toast.makeText(Registration.this, "Registration successful.", Toast.LENGTH_SHORT).show();
                                OpenLoginPage();
                            }
                            else {
                                errorTV.setText(response);
                                Log.d("CON"," fail ");
                            }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("#REG"," 3 STOP");
                        errorTV.setText(error.toString());
                    }
                }){
                    protected Map<String, String> getParams(){
                        Map<String, String> paramV = new HashMap<>();
                        paramV.put("login", loginS);
                        paramV.put("password", passwordS);
                        return paramV;
                    }
                };
                queue.add(stringRequest);
            }
        });
    }

    void OpenLoginPage(){
        Intent intent = new Intent(this, LoginPage.class);
        startActivity(intent);
        finish();
    }
}