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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petcare.AddPetDetails;
import com.example.petcare.dataBase.MyDbHelper;
import com.example.petcare.R;
import com.example.petcare.RecyclerViewModel;
import com.example.petcare.VeterinaryCard;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    Context context;
    private ArrayList<RecyclerViewModel> details;

    public RecyclerViewAdapter(Context context, ArrayList<RecyclerViewModel> details) {
        this.context = context;
        this.details = details;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_model, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        RecyclerViewModel item = details.get(position);
        String id = item.getId();

        byte[] imageBytes = item.getImage();
        if (imageBytes != null && imageBytes.length > 0) {
            // decode the byte array and set the image
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            holder.pet_img.setImageBitmap(bitmap);
        }

        holder.name.setText(item.getName());
        holder.pet_type.setText(item.getPet_breed());
        holder.pet_verity.setText(item.getPet_species());

        if (item.getPet_gender().equals("1")){
            holder.pet_gender.setText("Male");
        }
        else if (item.getPet_gender().equals("0")){
            holder.pet_gender.setText("Female");
        }
        holder.pet_size.setText(item.getPet_size());

        if (item.getQuality1().equals("on")) {
            holder.quality1.setText("Neutured");
        }else {
            holder.quality1.setVisibility(View.GONE);
        }
        if (item.getQuality2().equals("on")) {
            holder.quality2.setText("Vaccinated");
        }else {
            holder.quality2.setVisibility(View.GONE);
        }
        if (item.getQuality3().equals("on")) {
            holder.quality3.setText("Friendly with dogs");
        }else {
            holder.quality3.setVisibility(View.GONE);
        }
        if (item.getQuality4().equals("on")) {
            holder.quality4.setText("Friendly with cats");
        }else {
            holder.quality4.setVisibility(View.GONE);
        }
//        if (item.getQuality5().equals("on")) {
//            holder.quality5.setText("Friendly with kids <10 year");
//        }else {
//            holder.quality5.setText("");
//        }
//        if (item.getQuality6().equals("on")) {
//            holder.quality6.setText("Friendly with kids >10 year");
//        }else {
//            holder.quality6.setText("");
//        }

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, VeterinaryCard.class);

                intent.putExtra("pet_image", item.getImage());
                intent.putExtra("pet_name", item.getName());
                intent.putExtra("pet_species", item.getPet_species());
                intent.putExtra("pet_breed", item.getPet_breed());
                intent.putExtra("pet_gender", item.getPet_gender());
                intent.putExtra("pet_size", item.getPet_size());
                intent.putExtra("quality1", item.getQuality1());
                intent.putExtra("quality2", item.getQuality2());
                intent.putExtra("quality3", item.getQuality3());
                intent.putExtra("quality4", item.getQuality4());
                intent.putExtra("quality5", item.getQuality5());
                intent.putExtra("quality6", item.getQuality6());
                v.getContext().startActivity(intent);
            }
        });

        holder.edit_petDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), AddPetDetails.class);
                intent.putExtra("_id", id);
                intent.putExtra("pet_image", item.getImage());
                intent.putExtra("pet_name", item.getName());
                intent.putExtra("pet_species", item.getPet_species());
                intent.putExtra("pet_breed", item.getPet_breed());
                intent.putExtra("pet_size", item.getPet_size());
                intent.putExtra("pet_gender", item.getPet_gender());
                intent.putExtra("quality1", item.getQuality1());
                intent.putExtra("quality2", item.getQuality2());
                intent.putExtra("quality3", item.getQuality3());
                intent.putExtra("quality4", item.getQuality4());
                intent.putExtra("quality5", item.getQuality5());
                intent.putExtra("quality6", item.getQuality6());
                intent.putExtra("isEditMode", true);
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
                        MyDbHelper database = new MyDbHelper(context);
                        database.deleteData(id);
                        notifyDataSetChanged();

                        details.remove(position);
                        del_yes.setBackgroundResource(R.drawable.yes_no_btn_selector);
                        dialog.dismiss();
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
        TextView name, pet_type, pet_verity, pet_gender, pet_size, quality1, quality2, quality3, quality4,quality5,quality6;
        ImageView pet_img, edit_petDetails, delete_pet;
        LinearLayout card;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            pet_img = itemView.findViewById(R.id.pet_img);
            name = itemView.findViewById(R.id.pet_name);
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