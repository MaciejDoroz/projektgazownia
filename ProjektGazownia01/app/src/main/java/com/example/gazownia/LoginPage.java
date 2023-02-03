package com.example.gazownia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginPage extends AppCompatActivity {

    EditText loginET;
    EditText passwordET;
    Button loginBtn;
    Button registerBtn;
    TextView errorTV;

    String loginS, passwordS, apiKeyS;

    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        loginET = (EditText) findViewById(R.id.editTextLogin);
        passwordET = (EditText) findViewById(R.id.editTextPassword);
        loginBtn = (Button) findViewById(R.id.loginBtn);
        registerBtn = (Button) findViewById(R.id.registerBtn);
        errorTV = (TextView) findViewById(R.id.loginErrorTV);

        sharedPreferences = getSharedPreferences("Gazownia", MODE_PRIVATE);

        if (sharedPreferences.getString("logged", "").equals("true")) {
            OpenMainPage();
        }

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenRegisterPage();
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loginS = String.valueOf(loginET.getText());
                passwordS = String.valueOf(passwordET.getText());

                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url = "https://projektgazownia.000webhostapp.com/APPlogin.php";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    JSONObject jsonObject = new JSONObject(response);

                                    String status = jsonObject.getString("status");
                                    String message = jsonObject.getString("message");

                                    if (status.equals("success")) {
                                        loginS = jsonObject.getString("login");
                                        apiKeyS = jsonObject.getString("apiKey");

                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("logged", "true");
                                        editor.putString("login", loginS);
                                        editor.putString("apiKey", apiKeyS);
                                        editor.apply();

                                        OpenMainPage();
                                    } else {
                                        errorTV.setText(message);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        errorTV.setText(error.toString());
                    }
                }) {
                    protected Map<String, String> getParams() {
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

    void OpenMainPage() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }

    void OpenRegisterPage() {
        Intent intent = new Intent(this, Registration.class);
        startActivity(intent);
        finish();
    }
}