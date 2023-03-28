package com.example.petcare.adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petcare.AddPetDetails;
import com.example.petcare.DBOpreation;
import com.example.petcare.R;
import com.example.petcare.RecyclerViewModel;
import com.example.petcare.VeterinaryCard;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    Context context;
    private ArrayList<RecyclerViewModel> details = new ArrayList<>();

    public RecyclerViewAdapter(Context context/*, ArrayList<RecyclerViewModel> details*/) {
        this.context = context;
//        this.details = details;
    }

    public void setitems(ArrayList<RecyclerViewModel> data) {
        details.addAll(data);
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_model, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        RecyclerViewModel item = details.get(position);

        String  imageBytes = item.getPet_img();
        if (imageBytes != null){
            Picasso.get().load(imageBytes).into(holder.pet_img);
        }
        else {
            holder.pet_img.setImageResource(R.drawable.dog_img);
        }

        holder.pet_name.setText(item.getPet_name());
        holder.pet_type.setText(item.getPet_breed());
        holder.pet_verity.setText(item.getPet_species());
        holder.pet_size.setText(item.getPet_size());

        if (item.getPet_gender() == true){
            holder.pet_gender.setText("Male");
        }
        else if (item.getPet_gender() == false){
            holder.pet_gender.setText("Female");
        }

        if (item.getNeutered() == true) {
            holder.quality1.setText("Neutered");
        }else if (item.getNeutered() == false){
            holder.quality1.setVisibility(View.GONE);
        }
        if (item.getVaccinated() == true) {
            holder.quality2.setText("Vaccinated");
        }else if (item.getVaccinated() == false){
            holder.quality2.setVisibility(View.GONE);
        }
        if (item.getFriendly_with_dogs() == true) {
            holder.quality3.setText("Friendly with dogs");
        }else if (item.getFriendly_with_dogs() == false) {
            holder.quality3.setVisibility(View.GONE);
        }
        if (item.getFriendly_with_cats()== true) {
            holder.quality4.setText("Friendly with cats");
        }else if (item.getFriendly_with_cats()== false) {
            holder.quality4.setVisibility(View.GONE);
        }

      /*  if (item.getQuality5() == true) {
            holder.quality5.setText("Friendly with kids <10 year");
        }else {
            holder.quality5.setText("");
        }
        if (item.getQuality6() == true) {
            holder.quality6.setText("Friendly with kids >10 year");
        }else {
            holder.quality6.setText("");
        }*/
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, VeterinaryCard.class);

                i.putExtra("EDIT", item);
                v.getContext().startActivity(i);

            }
        });

        holder.edit_petDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, AddPetDetails.class);
                intent.putExtra("EDIT", item);
                v.getContext().startActivity(intent);
            }
        });

        holder.delete_pet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View modelBottomSheet = LayoutInflater.from(context).inflate(R.layout.delete_dialoge_box, null);
                BottomSheetDialog dialog = new BottomSheetDialog(context, R.style.SheetDialog);
                dialog.setContentView(modelBottomSheet);
                ImageView close_btn = dialog.findViewById(R.id.close_btn);
                Button del_yes = dialog.findViewById(R.id.delete_yes);
                Button del_no = dialog.findViewById(R.id.delete_no);

                close_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                del_yes.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void onClick(View v) {
                        DBOpreation dao = new DBOpreation();
                        dao.remove(item.getId()).addOnSuccessListener(suc ->
                        {
                            Toast.makeText(context, "Record is removed", Toast.LENGTH_SHORT).show();
                            notifyItemRemoved(position);
                            details.remove(item);
                            del_yes.setBackgroundResource(R.drawable.yes_no_btn_selector);
                            dialog.dismiss();

                        }).addOnFailureListener(er ->
                        {
                            Toast.makeText(context, "" + er.getMessage(), Toast.LENGTH_SHORT).show();
                        });
                    }
                });

                del_no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
                Window window = dialog.getWindow();
                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                WindowManager.LayoutParams wlp = window.getAttributes();
                wlp.gravity = Gravity.BOTTOM;
                wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
                window.setAttributes(wlp);
            }
        });
    }

    @Override
    public int getItemCount() {
        return details.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView pet_name, pet_type, pet_verity, pet_gender, pet_size, quality1, quality2, quality3, quality4,quality5,quality6;
        ImageView pet_img, edit_petDetails, delete_pet;
        LinearLayout card;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            pet_img = itemView.findViewById(R.id.pet_img);
            pet_name = itemView.findViewById(R.id.pet_name);
            pet_type = itemView.findViewById(R.id.pet_type);
            pet_verity = itemView.findViewById(R.id.pet_verity);
            pet_gender = itemView.findViewById(R.id.pet_gender);
            pet_size = itemView.findViewById(R.id.pet_size);
            quality1 = itemView.findViewById(R.id.pet_quality1);
            quality2 = itemView.findViewById(R.id.pet_quality2);
            quality3 = itemView.findViewById(R.id.pet_quality3);
            quality4 = itemView.findViewById(R.id.pet_quality4);
//            quality5 = itemView.findViewById(R.id.pet_quality5);
//            quality6 = itemView.findViewById(R.id.pet_quality6);
            edit_petDetails = itemView.findViewById(R.id.edit_pet_details);
            delete_pet = itemView.findViewById(R.id.delete_pet);
            card = itemView.findViewById(R.id.card);
        }
    }
}