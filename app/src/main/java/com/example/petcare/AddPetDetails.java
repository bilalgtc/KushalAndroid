package com.example.petcare;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.divider.MaterialDivider;

import org.w3c.dom.Text;

public class AddPetDetails extends AppCompatActivity {
    TextView p_name, species, breed, size;
    EditText pet_name, pet_species, pet_breed,pet_size;
    MaterialDivider div1 , div2 , div3 ,div4;

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


    }
}