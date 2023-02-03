package com.example.gazownia;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class AddEntryDialog extends AppCompatDialogFragment {

    private EditText entryET;
    private EditText peselET;


    String login;
    String role;

    public AddEntryDialog(String role, String login){
        this.login = login;
        this.role = role;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.custom_addentry_dialog, null);

        builder.setView(view)
                .setTitle("Add entry")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Add entry", new DialogInterface.OnClickListener() {
                    @Override

                    public void onClick(DialogInterface dialogInterface, int i) {


                        RequestQueue queue = Volley.newRequestQueue(view.getContext());
                        String url = "https://testsite12345012345.000webhostapp.com/APPhistory.php";

                        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                                new Response.Listener<String>() {

                                    @Override
                                    public void onResponse(String response) {
                                        Log.d("#1","STOP 2");
                                        Log.d("#1",response);

                                        if(response.equals("success")){
                                            Toast.makeText(view.getContext(), "Success", Toast.LENGTH_SHORT).show();
                                            //FetchHistoryFromDB();

                                            ((MainActivity)view.getContext()).ReplaceFragment(new History(),"history");

                                        }
                                        else{
                                            Toast.makeText(view.getContext(), response.toString(), Toast.LENGTH_SHORT).show();
                                            //Log.d("#1",response);
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
                                paramV.put("login", login);
                                paramV.put("entry", entryET.getText().toString());
                                paramV.put("pesel", peselET.getText().toString());
                                return paramV;
                            }
                        };
                        queue.add(stringRequest);


                    }
                });

        entryET = view.findViewById(R.id.dialogEntryET);
        peselET = view.findViewById(R.id.dialogPeselET);
        if(role.equals("client")){peselET.setVisibility(View.GONE);}

        return builder.create();
    }


}
