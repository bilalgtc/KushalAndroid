package com.example.petcare;


import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.appbar.AppBarLayout;
import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;

public class VeterinaryCard extends AppCompatActivity {

    private ConstraintLayout card;
    LinearLayout header;
    ImageView profile_pic, statusIcon1, statusIcon2, statusIcon3, statusIcon4, statusIcon5, statusIcon6;
    ImageView backbtn;
    TextView pet_name, pet_type, pet_breed, pet_gender, pet_size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.veterinary_card);
        init();

        Animation pop_up = AnimationUtils.loadAnimation(VeterinaryCard.this, R.anim.pop);
        AppBarLayout appBarLayout = findViewById(R.id.appbar);

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                int totalScrollRange = appBarLayout.getTotalScrollRange();
                float halfwayPoint = -totalScrollRange / 1.28f;

                if (verticalOffset <= halfwayPoint) {
                    card.setVisibility(View.GONE);
//                    card.setAnimation(pop_up);

                } else if (verticalOffset == 0) {
                    card.setVisibility(View.VISIBLE);
//                    card.setAnimation(pop_up);
                }
            }
        });

        changeStatusBarColor();

        if (Build.VERSION.SDK_INT >= 21) {
//            getWindow().getDecorView()
//                    .setSystemUiVisibility(
//                            View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//            getWindow().getDecorView()
//                    .setSystemUiVisibility(
//                            View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        RecyclerViewModel pet_data = (RecyclerViewModel) getIntent().getSerializableExtra("EDIT");

        String img = pet_data.getPet_img();
        if (img != null){
            Picasso.get().load(img).into(profile_pic);
        }
        else {
            profile_pic.setImageResource(R.drawable.dog_img);
        }

        String petNameTxt = pet_data.getPet_name();
        String petSpeciesTxt = pet_data.getPet_species();
        String petBreedTxt = pet_data.getPet_breed();
        String petSizeTxt = pet_data.getPet_size();
        Boolean petGender = pet_data.getPet_gender();
        Boolean quality1 = pet_data.getNeutered();
        Boolean quality2 = pet_data.getVaccinated();
        Boolean quality3 = pet_data.getFriendly_with_dogs();
        Boolean quality4 = pet_data.getFriendly_with_cats();
        Boolean quality5 = pet_data.getFriendly_with_kids_less_then_10_year();
        Boolean quality6 = pet_data.getFriendly_with_kids_greater_then_10_year();

        /*if (img == null) {
            profile_pic.setImageResource(R.drawable.dog_img);

        } else {
            Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
            profile_pic.setImageBitmap(bitmap);
        }*/

        pet_name.setText(petNameTxt);
        pet_type.setText(petSpeciesTxt);
        pet_breed.setText(petBreedTxt);
        pet_size.setText(petSizeTxt);

        if (petGender == true) {
            pet_gender.setText("Male");
        } else if (petGender == false) {
            pet_gender.setText("Female");
        }

        if (quality1 == true) {
            statusIcon1.setImageResource(R.drawable.success);
        } else if (quality1 == false) {
            statusIcon1.setImageResource(R.drawable.unsucess_icon);
        }
        if (quality2 == true) {
            statusIcon2.setImageResource(R.drawable.success);
        } else if (quality2 == false) {
            statusIcon2.setImageResource(R.drawable.unsucess_icon);
        }
        if (quality3 == true) {
            statusIcon3.setImageResource(R.drawable.success);
        } else if (quality3 == false) {
            statusIcon3.setImageResource(R.drawable.unsucess_icon);
        }
        if (quality4 == true) {
            statusIcon4.setImageResource(R.drawable.success);
        } else if (quality4 == false) {
            statusIcon4.setImageResource(R.drawable.unsucess_icon);
        }
        if (quality5 == true) {
            statusIcon5.setImageResource(R.drawable.success);
        } else if (quality5 == false) {
            statusIcon5.setImageResource(R.drawable.unsucess_icon);
        }
        if (quality6 == true) {
            statusIcon6.setImageResource(R.drawable.success);
        } else if (quality6 == false) {
            statusIcon6.setImageResource(R.drawable.unsucess_icon);
        }
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VeterinaryCard.this, Home.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    public void init() {
        profile_pic = findViewById(R.id.profile_pic);
        card = findViewById(R.id.card);
        header = findViewById(R.id.header);
        pet_type = findViewById(R.id.pet_type);
        pet_breed = findViewById(R.id.pet_verity);
        pet_gender = findViewById(R.id.pet_gender);
        pet_size = findViewById(R.id.pet_size);
        pet_name = findViewById(R.id.pet_name);
        statusIcon1 = findViewById(R.id.statusIcon1);
        statusIcon2 = findViewById(R.id.statusIcon2);
        statusIcon3 = findViewById(R.id.statusIcon3);
        statusIcon4 = findViewById(R.id.statusIcon4);
        statusIcon5 = findViewById(R.id.statusIcon5);
        statusIcon6 = findViewById(R.id.statusIcon6);
        backbtn = findViewById(R.id.back_btn);
    }
}