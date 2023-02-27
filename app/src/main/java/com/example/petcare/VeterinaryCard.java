package com.example.petcare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class VeterinaryCard extends AppCompatActivity {

    ScrollView scrollView;
    ConstraintLayout card;
    LinearLayout header;
    TextView pet_name,pet_type, pet_breed, pet_gender, pet_size;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.veterinary_card);
        init();
        changeStatusBarColor();

//        focusonview();

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView()
                    .setSystemUiVisibility(
                            View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        Intent i = getIntent();

        String petNameTxt = i.getStringExtra("pet_name");
        String petSpeciesTxt = i.getStringExtra("pet_species");
        String petBreedTxt = i.getStringExtra("pet_breed");
        String petGenderTxt = i.getStringExtra("pet_gender");
        String petSizeTxt = i.getStringExtra("pet_size");


        pet_name.setText(petNameTxt);
        pet_type.setText(petSpeciesTxt);
        pet_breed.setText(petBreedTxt);
        pet_gender.setText(petGenderTxt);
        pet_size.setText(petSizeTxt);

    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    public void init() {
        scrollView = findViewById(R.id.scrollview);
        card = findViewById(R.id.card);
        header = findViewById(R.id.header);
        pet_type = findViewById(R.id.pet_type);
        pet_breed = findViewById(R.id.pet_verity);
        pet_gender = findViewById(R.id.pet_gender);
        pet_size = findViewById(R.id.pet_size);
        pet_name = findViewById(R.id.pet_name);
    }
}