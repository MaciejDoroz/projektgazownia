package com.example.gazownia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class LoginPage extends AppCompatActivity {

    EditText login;
    EditText password;
    Button loginBtn;
    Button registerBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        login = (EditText)findViewById(R.id.editTextLogin);
        password = (EditText)findViewById(R.id.editTextPassword);
        loginBtn = (Button) findViewById(R.id.loginBtn);
        registerBtn = (Button) findViewById(R.id.registerBtn);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenRegisterPage();
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //OpenMainPage();



            }
        });
    }
    void OpenMainPage(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    void OpenRegisterPage(){
        Intent intent = new Intent(this, Registration.class);
        startActivity(intent);
        finish();
    }
}