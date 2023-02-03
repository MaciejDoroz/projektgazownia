package com.example.gazownia;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gazownia.databinding.FragmentProfileBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class Profile extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private Context globalContext = null;

    SharedPreferences sharedPreferences;


    EditText loginET, passwordET, nameET, surnameET, peselET, emailET, phoneET, adresET;
    Button updateData;
    Button logoutBtn;


    public Profile() {
        // Required empty public constructor
    }
    public static Profile newInstance(String param1, String param2) {
        Profile fragment = new Profile();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            globalContext = this.getActivity();
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        loginET = v.findViewById(R.id.profileLoginEv);
        passwordET = v.findViewById(R.id.profilePasswordEv);
        nameET = v.findViewById(R.id.profileNameEv);
        surnameET = v.findViewById(R.id.profileSurnameEv);
        peselET = v.findViewById(R.id.profilePeselEv);
        emailET = v.findViewById(R.id.profileEmailEv);
        phoneET = v.findViewById(R.id.profilePhoneEv);
        adresET = v.findViewById(R.id.profileAdresEv);

        updateData = v.findViewById(R.id.profileUpdateBtn);
        logoutBtn= v.findViewById(R.id.logoutBtn);


        sharedPreferences = getActivity().getSharedPreferences("Gazownia", Context.MODE_PRIVATE);

        if (sharedPreferences.getString("logged", "").equals("false")) {;
            OpenLoginPage();
        }

        FetchAndSetDataFromDB();

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Logout();
                }
        });

        updateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateData();
            }
        });


        return v;
    }

    void FetchAndSetDataFromDB(){
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        String url = "https://testsite12345012345.000webhostapp.com/APPfetchprofile.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            loginET.setText(jsonObject.getString("login"));
                            passwordET.setText(jsonObject.getString("password"));
                            nameET.setText(jsonObject.getString("name"));
                            surnameET.setText(jsonObject.getString("surname"));
                            peselET.setText(jsonObject.getString("pesel"));
                            emailET.setText(jsonObject.getString("email"));
                            phoneET.setText(jsonObject.getString("phonenumber"));
                            adresET.setText(jsonObject.getString("adres"));


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> paramV = new HashMap<>();
                paramV.put("login", sharedPreferences.getString("login",""));
                return paramV;
            }
        };
        queue.add(stringRequest);
    }

    void UpdateData(){
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        String url = "https://testsite12345012345.000webhostapp.com/APPupdatedata.php";


        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("###",response);
                        if(response.equals("success")){
                            Toast.makeText(getActivity(), "Data changed successfully!", Toast.LENGTH_SHORT).show();
                            FetchAndSetDataFromDB();
                        }
                        else if(response.equals("fail")){
                            Toast.makeText(getActivity(), "Fail", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> paramV = new HashMap<>();
                paramV.put("login", sharedPreferences.getString("login",""));
                paramV.put("password", passwordET.getText().toString());
                paramV.put("name", nameET.getText().toString());
                paramV.put("surname", surnameET.getText().toString());
                paramV.put("pesel", peselET.getText().toString());
                paramV.put("email", emailET.getText().toString());
                paramV.put("phone", phoneET.getText().toString());
                paramV.put("adres", adresET.getText().toString());
                return paramV;
            }
        };
        queue.add(stringRequest);
    }

    void Logout(){

        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        String url = "https://testsite12345012345.000webhostapp.com/APPlogout.php";

        Log.d("#REG"," 1 STOP");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if(response.equals("success")){

                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("logged", "");
                            editor.putString("login", "");
                            editor.putString("apiKey", "");
                            editor.apply();


                            OpenLoginPage();
                        }
                        else{
                            Toast.makeText(globalContext, response.toString(), Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> paramV = new HashMap<>();
                paramV.put("login", sharedPreferences.getString("login",""));
                paramV.put("apiKey", sharedPreferences.getString("apiKey",""));
                return paramV;
            }
        };
        queue.add(stringRequest);
    }

    void OpenLoginPage() {
        Intent intent = new Intent(getActivity(), LoginPage.class);
        startActivity(intent);

    }
}