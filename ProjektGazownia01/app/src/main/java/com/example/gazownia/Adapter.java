package com.example.gazownia;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

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
        holder.nameTV.setText(entries.get(position).getName());
        holder.surnameTV.setText(entries.get(position).getSurname());
        holder.peselTV.setText(entries.get(position).getPesel());
        holder.adresTV.setText(entries.get(position).getAdres());
        holder.entryTV.setText(entries.get(position).getEntry());
        holder.dateTV.setText(entries.get(position).getDate());
    }



    @Override
    public int getItemCount() {
        return entries.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView nameTV, surnameTV, peselTV, adresTV, entryTV, dateTV;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTV= itemView.findViewById(R.id.listName);
            surnameTV= itemView.findViewById(R.id.listSurname);
            peselTV= itemView.findViewById(R.id.listPesel);
            adresTV= itemView.findViewById(R.id.listAdres);
            entryTV= itemView.findViewById(R.id.listEntry);
            dateTV= itemView.findViewById(R.id.listDate);
        }
    }


}
