package com.example.gazownia;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    LayoutInflater inflater;
    List<Entries> entries;


    public Adapter(Context ctx, List<Entries> entries){
        this.inflater = LayoutInflater.from(ctx);
        this.entries = entries;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_list_layout,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.nameTV.setText(entries.get(position).getName() +" " + entries.get(position).getSurname());

        holder.peselTV.setText(entries.get(position).getPesel());
        holder.adresTV.setText(entries.get(position).getAdres());
        holder.entryTV.setText(entries.get(position).getEntry());
        holder.dateTV.setText(entries.get(position).getDate());

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)view.getContext()).OpenDeleteDialog(entries.get(holder.getPosition()).getHistoryid());
            }
        });

        holder.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)view.getContext()).OpenEditDialog(entries.get(holder.getPosition()).getHistoryid());
            }
        });
    }

    @Override
    public int getItemCount() {
        return entries.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView nameTV, surnameTV, peselTV, adresTV, entryTV, dateTV;
        Button deleteBtn,editBtn;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTV= itemView.findViewById(R.id.listName);

            peselTV= itemView.findViewById(R.id.listPesel);
            adresTV= itemView.findViewById(R.id.listAdres);
            entryTV= itemView.findViewById(R.id.listEntry);
            dateTV= itemView.findViewById(R.id.listDate);
            deleteBtn = itemView.findViewById(R.id.listDelete);
            editBtn = itemView.findViewById(R.id.listEdit);
        }
    }

}
