package com.onlyforadventure.DocApp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.onlyforadventure.DocApp.appointment.AppointmentBooking;

import java.util.ArrayList;

public class userAdapter extends RecyclerView.Adapter<userAdapter.userViewHolder> {

    Context context;
    ArrayList<userModel> list;
    SelectListener selectListener;

    public userAdapter(Context context, ArrayList<userModel> list,SelectListener selectListener) {
        this.context = context;
        this.list = list;
        this.selectListener=selectListener;
    }

    @NonNull
    @Override
    public userViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.items,parent,false);

        return new userViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull userViewHolder holder, @SuppressLint("RecyclerView") int position) {
        userModel userModel=list.get(position);
        holder.name.setText(userModel.getName());
        holder.email.setText(userModel.getEmail());
        holder.specialization.setText(userModel.getSpecialization());
        holder.phone.setText(userModel.getPhone());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(context, AppointmentBooking.class);
                intent.putExtra("Duid", userModel.getUid());
                intent.putExtra("Dname", userModel.getName());
                intent.putExtra("Demail", userModel.getEmail());
                intent.putExtra("Dphone", userModel.getPhone());
                intent.putExtra("Dtype", userModel.getSpecialization());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class userViewHolder extends RecyclerView.ViewHolder{

        TextView name,email,specialization,phone;
        CardView cardView;

        public userViewHolder(@NonNull View itemView) {
            super(itemView);

            name=itemView.findViewById(R.id.doctorName);
            email=itemView.findViewById(R.id.doctorEmail);
            specialization=itemView.findViewById(R.id.specialization);
            phone=itemView.findViewById(R.id.phone);
            cardView=itemView.findViewById(R.id.recyclerLayout);
        }
    }

}
