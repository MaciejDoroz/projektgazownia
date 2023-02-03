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

public class DeleteDialog extends AppCompatDialogFragment {

    private EditText entryET;
    String entryid;

    public DeleteDialog(String entryid){
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
                .setTitle("Are you sure you want to delete the entry? (Action permanent)")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        RequestQueue queue = Volley.newRequestQueue(view.getContext());
                        String url = "https://testsite12345012345.000webhostapp.com/APPdeleteentry.php";

                        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                                new Response.Listener<String>() {

                                    @Override
                                    public void onResponse(String response) {
                                        if(response.equals("success")){
                                            Toast.makeText(view.getContext(), "Entry Deleted", Toast.LENGTH_SHORT).show();
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
                                paramV.put("id", entryid);
                                return paramV;
                            }
                        };
                        queue.add(stringRequest);

                    }
                });

        entryET = view.findViewById(R.id.editEntryDialog);
        entryET.setVisibility(View.GONE);
        return builder.create();
    }
}
