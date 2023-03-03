package com.example.petcare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class VeterinaryCard extends AppCompatActivity {

    ScrollView scrollView;
    ConstraintLayout card;
    LinearLayout header;
    ImageView profile_pic,statusIcon1,statusIcon2,statusIcon3,statusIcon4;
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

        byte[] img = i.getByteArrayExtra("pet_image");
        String petNameTxt = i.getStringExtra("pet_name");
        String petSpeciesTxt = i.getStringExtra("pet_species");
        String petBreedTxt = i.getStringExtra("pet_breed");
        String petSizeTxt = i.getStringExtra("pet_size");
        String petGender = i.getStringExtra("pet_gender");
        String qyality1 = i.getStringExtra("quality1");
        String qyality2 = i.getStringExtra("quality2");
        String qyality3 = i.getStringExtra("quality3");
        String qyality4 = i.getStringExtra("quality4");

        if (img == null){
            profile_pic.setImageResource(R.drawable.dog_img);

        }else {
            Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
            profile_pic.setImageBitmap(bitmap);
        }

        pet_name.setText(petNameTxt);
        pet_type.setText(petSpeciesTxt);
        pet_breed.setText(petBreedTxt);
        pet_size.setText(petSizeTxt);

        if (petGender.equals("1")){
            pet_gender.setText("Male");
        }
        else if (petGender.equals("0")){
            pet_gender.setText("Female");
        }

        if (qyality1.equals("on")){
            statusIcon1.setImageResource(R.drawable.success);
        }
        else if (qyality1.equals("off")){
            statusIcon1.setImageResource(R.drawable.unsucess_icon);
        }
        if (qyality2.equals("on")){
            statusIcon2.setImageResource(R.drawable.success);
        }
        else if (qyality2.equals("off")){
            statusIcon2.setImageResource(R.drawable.unsucess_icon);
        }
        if (qyality3.equals("on")){
            statusIcon3.setImageResource(R.drawable.success);        }
        else if (qyality3.equals("off")){
            statusIcon3.setImageResource(R.drawable.unsucess_icon);
        }
        if (qyality4.equals("on")){
            statusIcon4.setImageResource(R.drawable.success);
        }
        else if (qyality4.equals("off")){
            statusIcon4.setImageResource(R.drawable.unsucess_icon);
        }

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
        scrollView = findViewById(R.id.scrollview);
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
    }
}