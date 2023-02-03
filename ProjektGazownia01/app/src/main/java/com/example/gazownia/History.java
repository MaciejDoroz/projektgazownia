package com.example.gazownia;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Ref;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class History extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    EditText entryET;
    EditText peselET;
    TextView historyTV;
    Button addEntry;

    RecyclerView recyclerView;
    List<Entries> entries;
    Adapter adapter;

    SharedPreferences sharedPreferences;

    Boolean client = true;



    public History() {
        // Required empty public constructor
    }
    public static History newInstance(String param1, String param2) {
        History fragment = new History();
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
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_history, container, false);

        //historyTV = v.findViewById(R.id.historyTV);
        addEntry = v.findViewById(R.id.addEntryButton);
        //tv = v.findViewById(R.id.historyTextView);

        recyclerView = v.findViewById(R.id.historyList);
        entries = new ArrayList<>();


        sharedPreferences = getActivity().getSharedPreferences("Gazownia", Context.MODE_PRIVATE);

        if (sharedPreferences.getString("logged", "").equals("false")) {;
            OpenLoginPage();
        }

        CheckRole();
        FetchHistoryFromDB();


        addEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String role = client?"client":"admin";
                String login = sharedPreferences.getString("login","");
                OpenAddEntryDialog(role,login);

            }
        });



        return v;
    }

    void OpenAddEntryDialog (String role, String login){
        AddEntryDialog addEntryDialog = new AddEntryDialog(role,login);
        addEntryDialog.show(getParentFragmentManager(),"AddEntry");
    }

    void CheckRole(){
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        String url = "https://testsite12345012345.000webhostapp.com/APPCheckRole.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        if(response.equals("client")){
                            //Toast.makeText(getActivity(), response.toString(), Toast.LENGTH_SHORT).show();
                            ;
                            client=true;
                        }
                        else if(response.equals("admin")){
                            //Toast.makeText(getActivity(), response.toString(), Toast.LENGTH_SHORT).show();
                            client=false;
                        }
                        else{
                            Toast.makeText(getActivity(), response.toString(), Toast.LENGTH_SHORT).show();
                            historyTV.setText(response);
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



    void FetchHistoryFromDB(){
        //historyTV.setText("");
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        String url = "https://testsite12345012345.000webhostapp.com/APPfetchhistory.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        //Log.d("###",response);
                        try {
                            JSONObject jsonObject1 = new JSONObject(response);
                            JSONArray jsonArray = jsonObject1.getJSONArray("entry");
                            for (int i =0;i<jsonArray.length();i++){
                                JSONObject entryObject = jsonArray.getJSONObject(i);

                                Entries entry = new Entries();
                                entry.setName(entryObject.getString("name").toString());
                                entry.setSurname(entryObject.getString("surname").toString());
                                entry.setPesel(entryObject.getString("pesel").toString());
                                entry.setAdres(entryObject.getString("adres").toString());
                                entry.setHistoryid(entryObject.getString("id").toString());
                                entry.setEntry(entryObject.getString("entry").toString());
                                entry.setDate(entryObject.getString("date").toString());

                                entries.add(entry);
                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        // Preview of json
                        //historyTV.setText(response);

                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        adapter = new Adapter(getActivity(),entries);
                        recyclerView.setAdapter(adapter);


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





    void OpenLoginPage() {
        Intent intent = new Intent(getActivity(), LoginPage.class);
        startActivity(intent);
    }
}
