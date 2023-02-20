package com.example.petcare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.divider.MaterialDivider;

import java.util.ArrayList;

public class AddPetDetails extends AppCompatActivity {
    TextView p_name, species, breed, size, txt_male,txt_female;
    EditText pet_name, pet_species, pet_breed,pet_size;
    ImageView icon_male, icon_female, back_btn;
    MaterialDivider div1 , div2 , div3 ,div4;
    LinearLayout male , female;
    Button submit;
    RecyclerViewAdapter adapter;
    ArrayList<RecyclerViewModel> details = new ArrayList<>();
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_pet_details);
        changeStatusBarColor();

        init();

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView()
                    .setSystemUiVisibility(
                            View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        pet_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!pet_name.hasFocus()){
                    p_name.setTextColor(getColor(R.color.text_gray));
                    div1.setDividerColor(getColor(R.color.text_gray));
                }else {
                    p_name.setTextColor(getColor(R.color.text_blue));
                    div1.setDividerColor(getColor(R.color.text_blue));
                }
            }
        });
        pet_species.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!pet_species.hasFocus()){
                    species.setTextColor(getColor(R.color.text_gray));
                    div2.setDividerColor(getColor(R.color.text_gray));
                }else {
                    species.setTextColor(getColor(R.color.text_blue));
                    div2.setDividerColor(getColor(R.color.text_blue));
                }
            }
        });
        pet_breed.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!pet_breed.hasFocus()){
                    breed.setTextColor(getColor(R.color.text_gray));
                    div3.setDividerColor(getColor(R.color.text_gray));
                }else {
                    breed.setTextColor(getColor(R.color.text_blue));
                    div3.setDividerColor(getColor(R.color.text_blue));
                }
            }
        });
        pet_size.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!pet_size.hasFocus()){
                    size.setTextColor(getColor(R.color.text_gray));
                    div4.setDividerColor(getColor(R.color.text_gray));
                }else {
                    size.setTextColor(getColor(R.color.text_blue));
                    div4.setDividerColor(getColor(R.color.text_blue));
                }
            }
        });

        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (male.isPressed()){
                    male.setBackgroundResource(R.drawable.male_female_chacked);
                    txt_male.setTextColor(getResources().getColor(R.color.white));
                    icon_male.setImageResource(R.drawable.male_icon_white);

                    txt_female.setTextColor(getResources().getColor(R.color.black));
                    female.setBackgroundResource(R.drawable.male_female_bg_unchecked);
                    icon_female.setImageResource(R.drawable.female_icon);
                }
            }
        });

        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (female.isPressed()){
                    female.setBackgroundResource(R.drawable.male_female_chacked);
                    txt_male.setTextColor(getResources().getColor(R.color.black));
                    icon_male.setImageResource(R.drawable.male_icon);

                    txt_female.setTextColor(getResources().getColor(R.color.white));
                    male.setBackgroundResource(R.drawable.male_female_bg_unchecked);
                    icon_female.setImageResource(R.drawable.female_icon_white);
                }
            }
        });

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

    public void init(){
        p_name = findViewById(R.id.petname);
        pet_name = findViewById(R.id.pet_name);
        div1 = findViewById(R.id.pet_name_div);
        species = findViewById(R.id.species);
        pet_species = findViewById(R.id.species_of_pet);
        div2 = findViewById(R.id.species_of_pet_div);
        breed = findViewById(R.id.breed);
        pet_breed = findViewById(R.id.breed_of_pet);
        div3 = findViewById(R.id.breed_div);
        size = findViewById(R.id.size);
        pet_size = findViewById(R.id.pet_size);
        div4 = findViewById(R.id.pet_size_div);
        male = findViewById(R.id.male_btn);
        female = findViewById(R.id.female_btn);
        txt_male = findViewById(R.id.txt_male);
        txt_female = findViewById(R.id.txt_female);
        icon_male = findViewById(R.id.male_icon);
        icon_female = findViewById(R.id.female_icon);
        back_btn = findViewById(R.id.back_btn);
        submit = findViewById(R.id.submit);
        recyclerView = findViewById(R.id.recy_view_home);


    }
}