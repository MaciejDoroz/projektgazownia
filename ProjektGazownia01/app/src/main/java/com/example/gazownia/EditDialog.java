package com.example.gazownia;

import android.app.Dialog;
import android.content.Context;
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

public class EditDialog extends AppCompatDialogFragment {
    private EditText entryET;


    String entryid;
    public EditDialog(String entryid){
        this.entryid = entryid;
    }

    public String getEntryid() {
        return entryid;
    }

    public void setEntryid(String entryid) {
        this.entryid = entryid;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.custom_edit_dialog,null);

        builder.setView(view)
                .setTitle("Edit entry")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        })
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String updatedEntry = entryET.getText().toString();
                        //Toast.makeText(view.getContext(), updatedEntry, Toast.LENGTH_SHORT).show();

                        //Log.d("#DIALOG",entryid);


                        RequestQueue queue = Volley.newRequestQueue(view.getContext());
                        String url = "https://projektgazownia.000webhostapp.com/APPeditentry.php";

                        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                                new Response.Listener<String>() {

                                    @Override
                                    public void onResponse(String response) {
                                        Log.d("#EDIT",response);
                                        if(response.equals("success")){
                                            Toast.makeText(view.getContext(), "Entry edited", Toast.LENGTH_SHORT).show();
                                            ((MainActivity)view.getContext()).ReplaceFragment(new History(),"history");
                                        }
                                        else{
                                            Log.d("#DEL",response);
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
                                paramV.put("id",entryid);
                                paramV.put("entry",entryET.getText().toString());
                                return paramV;
                            }
                        };
                        queue.add(stringRequest);

                    }
                });

        entryET = view.findViewById(R.id.editEntryDialog);
        return builder.create();
    }



}
