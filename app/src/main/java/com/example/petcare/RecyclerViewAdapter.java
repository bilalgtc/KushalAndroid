package com.example.petcare;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.helper.widget.Layer;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private int lastPosition = -1;
    Context context;
    ArrayList<RecyclerViewModel> details;

    RecyclerViewAdapter(Context context,ArrayList<RecyclerViewModel> details){
        this.context = context;
        this.details = details;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_model,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.pet_img.setImageResource(details.get(position).image);
        holder.name.setText(details.get(position).name);
        holder.pet_type.setText(details.get(position).pet_type);
        holder.pet_verity.setText(details.get(position).pet_verity);
        holder.pet_gender.setText(details.get(position).pet_gender);
        holder.pet_size.setText(details.get(position).pet_size);
        holder.quality1.setText(details.get(position).quality1);
        holder.quality2.setText(details.get(position).quality2);
        holder.quality3.setText(details.get(position).quality3);
        holder.quality4.setText(details.get(position).quality4);


    }

    @Override
    public int getItemCount() {
        return details.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,pet_type,pet_verity,pet_gender,pet_size,quality1,quality2,quality3,quality4;
        ImageView pet_img, edit_petDetails, delete_pet;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            pet_img = itemView.findViewById(R.id.pet_img);
            name =itemView.findViewById(R.id.pet_name);
            pet_type = itemView.findViewById(R.id.pet_type);
            pet_verity = itemView.findViewById(R.id.pet_verity);
            pet_gender = itemView.findViewById(R.id.pet_gender);
            pet_size = itemView.findViewById(R.id.pet_size);
            quality1 = itemView.findViewById(R.id.pet_quality1);
            quality2 = itemView.findViewById(R.id.pet_quality2);
            quality3 = itemView.findViewById(R.id.pet_quality3);
            quality4 = itemView.findViewById(R.id.pet_quality4);
            edit_petDetails = itemView.findViewById(R.id.edit_pet_details);
            delete_pet = itemView.findViewById(R.id.delete_pet);


        }
    }
}
