package com.example.petcare;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
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

import com.example.petcare.adapter.RecyclerViewAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RAdapter extends FirebaseRecyclerAdapter<RecyclerViewModel,RAdapter.ViewHolder> {
    Context context;
    private ArrayList<RecyclerViewModel> details = new ArrayList<>();


    public RAdapter(@NonNull FirebaseRecyclerOptions<RecyclerViewModel> options , Context context) {
        super(options);
        this.context = context;
    }

    public void setitems(ArrayList<RecyclerViewModel> data) {
        details.addAll(data);
    }


    @Override
    protected void onBindViewHolder(@NonNull RAdapter.ViewHolder holder, @SuppressLint("RecyclerView")final int position, @NonNull RecyclerViewModel model) {

        String imageBytes = model.getPet_img();
        if (imageBytes != null) {
            Picasso.get().load(imageBytes).into(holder.pet_img);
        } else {
            holder.pet_img.setImageResource(R.drawable.dog_img);
        }
        holder.pet_name.setText(model.getPet_name());
        holder.pet_type.setText(model.getPet_breed());
        holder.pet_verity.setText(model.getPet_species());
        holder.pet_size.setText(model.getPet_size());

        if (model.getPet_gender() == true) {
            holder.pet_gender.setText("Male");
        } else if (model.getPet_gender() == false) {
            holder.pet_gender.setText("Female");
        }

        if (model.getNeutered() == true) {
            holder.quality1.setText("Neutered");
        } else {
            holder.quality1.setVisibility(View.GONE);
        }
        if (model.getVaccinated() == true) {
            holder.quality2.setText("Vaccinated");
        } else {
            holder.quality2.setVisibility(View.GONE);
        }
        if (model.getFriendly_with_dogs() == true) {
            holder.quality3.setText("Friendly with dogs");
        } else {
            holder.quality3.setVisibility(View.GONE);
        }
        if (model.getFriendly_with_cats() == true) {
            holder.quality4.setText("Friendly with cats");
        } else {
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

                i.putExtra("EDIT", model);
                v.getContext().startActivity(i);

            }
        });

        holder.edit_petDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, AddPetDetails.class);
                intent.putExtra("EDIT", model);
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
                        /*HomeFragment homeFragment = new HomeFragment();

                        homeFragment.remove(item.getId()).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                notifyItemRemoved(position);
                                dialog.dismiss();
                                del_yes.setBackgroundResource(R.drawable.yes_no_btn_selector);
                            }
                        });*/

//                        List<RecyclerViewModel> items = getSnapshots();
                        int lastPosition = getItemCount();
                        Log.d("TAG", "item count before delete:- " +getItemCount());
                        if (position >=0 && position <= lastPosition){
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        Log.d("TAG", "deleted from position:- " +position);
                        Log.d("TAG", "updated last position:- " +lastPosition);
                            FirebaseDatabase.getInstance().getReference().child("PetCare").child("Users").child(user.getUid()).child(Objects.requireNonNull(getRef(position).getKey())).removeValue();
                            Toast.makeText(context, "Record is removed", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            del_yes.setBackgroundResource(R.drawable.yes_no_btn_selector);
                            notifyDataSetChanged();

                        }



                        /*DBOpreation dao = new DBOpreation();
                        dao.remove(model.getId()).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {

                                Toast.makeText(context, "Record is removed", Toast.LENGTH_SHORT).show();
                                details.remove(model);
                                Intent i = new Intent(context, Home.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                context.startActivity(i);
                                dialog.dismiss();
                                del_yes.setBackgroundResource(R.drawable.yes_no_btn_selector);

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(context, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });*/

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

    @NonNull
    @Override
    public RAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_model, parent, false);
        RAdapter.ViewHolder viewHolder = new RAdapter.ViewHolder(view);
        return viewHolder;
    }


    class ViewHolder extends RecyclerView.ViewHolder{

        TextView pet_name, pet_type, pet_verity, pet_gender, pet_size, quality1, quality2, quality3, quality4, quality5, quality6;
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
