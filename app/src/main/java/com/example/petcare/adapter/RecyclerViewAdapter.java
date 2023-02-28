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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petcare.AddPetDetails;
import com.example.petcare.MyDbHelper;
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
        byte[] imageBytes = item.getImage();
        if (imageBytes != null && imageBytes.length > 0) {
            // decode the byte array and set the image
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            holder.pet_img.setImageBitmap(bitmap);
        }
//        else {
////            Toast.makeText(context, "something went wrong!!", Toast.LENGTH_SHORT).show();
//            // handle the case where the byte array is null or empty
//        }

        holder.name.setText(item.getName());
        holder.pet_type.setText(item.getPet_type());
        holder.pet_verity.setText(item.getPet_verity());
        holder.pet_gender.setText(item.getPet_gender());
        holder.pet_size.setText(item.getPet_size());
        holder.quality1.setText(item.getQuality1());
        holder.quality2.setText(item.getQuality2());
        holder.quality3.setText(item.getQuality3());
        holder.quality4.setText(item.getQuality4());


        holder.pet_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, VeterinaryCard.class);

                intent.putExtra("pet_image", item.getImage());
                intent.putExtra("pet_name", item.getName());
                intent.putExtra("pet_species", item.getPet_type());
                intent.putExtra("pet_breed", item.getPet_verity());
                intent.putExtra("pet_gender", item.getPet_gender());
                intent.putExtra("pet_size", item.getPet_size());
                v.getContext().startActivity(intent);
            }
        });

        holder.edit_petDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), AddPetDetails.class);

                intent.putExtra("_id", item.getId());
                intent.putExtra("pet_image", item.getImage());
                intent.putExtra("pet_name", item.getName());
                intent.putExtra("pet_species", item.getPet_type());
                intent.putExtra("pet_breed", item.getPet_verity());
                intent.putExtra("pet_size", item.getPet_size());
                intent.putExtra("pet_gender", item.getPet_gender());
                v.getContext().startActivity(intent);
            }
        });

        holder.delete_pet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View modelBottomSheet = LayoutInflater.from(context).inflate(R.layout.delete_dialoge_box, null);
                BottomSheetDialog dialog = new BottomSheetDialog(context, R.style.SheetDialog);
                dialog.setContentView(modelBottomSheet);

//                Dialog dialog = new Dialog(context);
//                dialog.setContentView(R.layout.delete_dialoge_box);
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
                        int id = getItemCount();
                        MyDbHelper database = new MyDbHelper(context);
                        database.deleteEntry(id);
                        details.remove(id);
                        notifyItemRemoved(id);
                        notifyDataSetChanged();
                        notifyItemRangeChanged(position, details.size());
                        database.close();
                        del_yes.setBackgroundResource(R.drawable.yes_no_btn_selector);
                        details.remove(position);
                        notifyItemChanged(position);
                        notifyItemRangeChanged(position, details.size());
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
        TextView name, pet_type, pet_verity, pet_gender, pet_size, quality1, quality2, quality3, quality4;
        ImageView pet_img, edit_petDetails, delete_pet;
        MyDbHelper db = new MyDbHelper(context);

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
            edit_petDetails = itemView.findViewById(R.id.edit_pet_details);
            delete_pet = itemView.findViewById(R.id.delete_pet);


        }
    }

//    private void showEditDialog(RecyclerViewModel data) {
//        AddPetDetails fragment = new AddPetDetails();
//        Bundle args = new Bundle();
////        args.putString("pet_image", data.getName());
//        args.putString("pet_name", data.getName());
//        args.putString("pet_species", data.getPet_type());
//        args.putString("pet_breed", data.getPet_verity());
//        args.putString("pet_size", data.getPet_size());
//        fragment.setArguments(args);
//        fragment.show(getSupportFragmentManager(), "edit_dialog");
//    }


}
