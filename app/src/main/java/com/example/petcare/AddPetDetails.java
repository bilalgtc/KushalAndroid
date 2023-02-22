package com.example.petcare;

import androidx.annotation.ContentView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.divider.MaterialDivider;


import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class AddPetDetails extends AppCompatActivity {
    TextView p_name, species, breed, size, txt_male,txt_female;
    EditText pet_name, pet_species, pet_breed,pet_size;
    ImageView icon_male, icon_female, back_btn , add_img , profile_img;
    MaterialDivider div1 , div2 , div3 ,div4;
    LinearLayout male , female;
    Button submit;
    RecyclerView recyclerView;



    //PERMISSION CONSTANTS
    private static final int CAMERA_REQUEST_CODE = 100 ;
    private static final int STORAGE_REQUEST_CODE = 101 ;
    //IMAGE PICK CONSTANTS
    private static final int IMAGE_PICK_CAMERA_CODE = 102 ;
    private static final int IMAGE_PICK_GALLERY_CODE = 103 ;
    //ARRAYS OF PERMISSION
    private String [] cameraPermission;     //camera and storage
    private String [] storagePermission;    //only storage
    private Uri imageUri;

    MyDbHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_pet_details);
        changeStatusBarColor();

        init();

        //init permission arrays
        cameraPermission = new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

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

        add_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagePickDialog();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPet(v);

            }
        });
    }

    private void imagePickDialog() {
        //options to display in dialog
        String[] options = {"Camera","Gallery"};
        //alert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("pick Image From");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //handel clicks
                if (which == 0){
                    //camera clicked
                    if (!checkCameraPermission()){
                        requestCameraPermission();
                    }
                    else {
                        //permission already granted
                        pickFromCamera();
                    }
                }
                else if(which == 1) {
                    if (!checkStoragePermission()){
                        requestStoragePermission();
                    }
                    else {
                        //permission already granted
                        pickFromGallery();
                    }
                }
            }
        });
        //show dialog
        builder.create().show();
    }

    private void pickFromGallery() {
        //intent to pick image from gallery,the image will be returned in onActivityResult method
        Intent galleryIntent = new Intent(Intent.ACTION_PICK);
//        galleryIntent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryIntent.setType("image/*");
         startActivityForResult(galleryIntent,IMAGE_PICK_GALLERY_CODE);
    }

    private void pickFromCamera() {
//        //intent to pick image from camera,the image will be returned in onActivityResult method
//        ContentValues values = new ContentValues();
//        values.put(MediaStore.Images.Media.TITLE,"Image_Title");
//        values.put(MediaStore.Images.Media.DESCRIPTION,"Image_Description");
//        // put image uri;
//        imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);

        //intent to open camera for image
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
        startActivityForResult(cameraIntent,IMAGE_PICK_CAMERA_CODE);
    }

    private Boolean checkStoragePermission(){
        //check storage permission is enabled or not
        boolean result = ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return  result;
    }

    private void requestStoragePermission(){
        ActivityCompat.requestPermissions(this,storagePermission,STORAGE_REQUEST_CODE);
    }

    private Boolean checkCameraPermission(){

        //check camera permission is enabled or not

        boolean result = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return  result && result1;
    }
    private void requestCameraPermission(){
        ActivityCompat.requestPermissions(this,cameraPermission,CAMERA_REQUEST_CODE);
    }


    public void addPet(View view){
        String petNameTxt = pet_name.getText().toString();
        String petSpeciesTxt = pet_species.getText().toString();
        String petBreedTxt = pet_breed.getText().toString();
        String petSizeTxt = pet_size.getText().toString();
        male.setClickable(true);
        female.setClickable(true);
        String maleS = "male";
        String femaleS = "female";

        if(petNameTxt.isEmpty() || petSpeciesTxt.isEmpty() || petBreedTxt.isEmpty() || petSizeTxt.isEmpty())
        {
            Message.message(getApplicationContext(),"Enter Both Name and Password");
        }

        else {
            if (male.isPressed()){
                long id = db.insertPetDetail(
                        ""+petNameTxt,
//                        ""+profile_img.toString(),
                        ""+petSpeciesTxt,
                        ""+petBreedTxt,
                        ""+petSizeTxt,
                        ""+maleS);
                if(id<=0)
                {
                    Message.message(getApplicationContext(),"Insertion Unsuccessful");
                    pet_name.setText("");
                    pet_species.setText("");
                    pet_breed.setText("");
                    pet_size.setText("");
                } else
                {
                    Message.message(getApplicationContext(),"Insertion Successful");
                    pet_name.setText("");
                    pet_species.setText("");
                    pet_breed.setText("");
                    pet_size.setText("");

                }
            }
            else {
                long id = db.insertPetDetail(petNameTxt,petSpeciesTxt,petBreedTxt,petSizeTxt,femaleS);
                if(id<=0)
                {
                    Message.message(getApplicationContext(),"Insertion Unsuccessful");
                    pet_name.setText("");
                    pet_species.setText("");
                    pet_breed.setText("");
                    pet_size.setText("");
                } else
                {
                    Message.message(getApplicationContext(),"Insertion Successful");
                    pet_name.setText("");
                    pet_species.setText("");
                    pet_breed.setText("");
                    pet_size.setText("");
                }
            }
        }

    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        //result of permission allowed/denied
        switch (requestCode){
            case CAMERA_REQUEST_CODE:{
                if (grantResults.length>0){
                    // if allowed returns true otherwise false
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean storageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (cameraAccepted && storageAccepted){
                        //both permission allowed
                        pickFromCamera();
                    }
                    else {
                        Message.message(this,"Camera & Storage permissions are required ");
                    }
                }
            }
            break;
            case STORAGE_REQUEST_CODE:{
                if (grantResults.length>0){
                    // if allowed returns true otherwise false
                    boolean storageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (storageAccepted){
                        //storage permission allowed
                        pickFromGallery();
                    }
                    else {
                        Message.message(this,"Storage permission is required");
                    }
                }
            }
            break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //image picked from camera and gallery will be received here
        if (resultCode == RESULT_OK){
            //image is picked
            if (requestCode == IMAGE_PICK_CAMERA_CODE){
                Bitmap img = (Bitmap)(data.getExtras().get("data"));
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                img.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();

//                MyDbHelper db = new MyDbHelper(this);
                SQLiteDatabase database = db.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(MyDbHelper.IMAGE, byteArray);
                database.insert(MyDbHelper.TABLE_NAME,null,values);
                database.close();

                profile_img.setImageBitmap(img);
            }
            else if (requestCode == IMAGE_PICK_GALLERY_CODE){
                profile_img.setImageURI(data.getData());
            }
        }
        else {
            Message.message(this,"error");
        }

        super.onActivityResult(requestCode, resultCode, data);

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
        add_img = findViewById(R.id.add_img);
        profile_img = findViewById(R.id.profile_pic);

        db = new MyDbHelper(this);
    }



}