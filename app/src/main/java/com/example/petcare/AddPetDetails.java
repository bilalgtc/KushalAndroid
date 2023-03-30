package com.example.petcare;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petcare.utils.Message;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.divider.MaterialDivider;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

public class AddPetDetails extends AppCompatActivity {

    TextView p_name, species, breed, size, txt_male, txt_female, header_txt;
    EditText pet_name, pet_species, pet_breed, pet_size;
    ImageView icon_male;
    ImageView icon_female;
    ImageView back_btn;
    ImageView add_img;
    ImageView profile_img;
    MaterialDivider div1, div2, div3, div4;
    LinearLayout male, female;
    Button submit;
    RecyclerView recyclerView;
    SwitchCompat toggle1, toggle2, toggle3, toggle4, toggle5, toggle6;
    Bitmap imgToStore;
    Boolean[] flag = {true, false};
    Boolean petGender, quality1, quality2, quality3, quality4, quality5, quality6;
    String img;
    int Temp;
    FirebaseStorage storage;
    StorageReference uploader;

    //PERMISSION CONSTANTS
    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int STORAGE_REQUEST_CODE = 101;
    //IMAGE PICK CONSTANTS
    private static final int IMAGE_PICK_CAMERA_CODE = 102;
    private static final int IMAGE_PICK_GALLERY_CODE = 103;
    //ARRAYS OF PERMISSION
    private String[] cameraPermission;     //camera and storage
    private String[] storagePermission;    //only storage
    private Uri imageUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_pet_details);
        changeStatusBarColor();
        init();

        storage = FirebaseStorage.getInstance();
        uploader = storage.getReference("img" + UUID.randomUUID().toString());

        //init permission arrays
        cameraPermission = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        pet_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!pet_name.hasFocus()) {
                    p_name.setTextColor(getColor(R.color.text_gray));
                    div1.setDividerColor(getColor(R.color.text_gray));
                } else {
                    p_name.setTextColor(getColor(R.color.text_blue));
                    div1.setDividerColor(getColor(R.color.text_blue));
                }
            }
        });
        pet_species.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!pet_species.hasFocus()) {
                    species.setTextColor(getColor(R.color.text_gray));
                    div2.setDividerColor(getColor(R.color.text_gray));
                } else {
                    species.setTextColor(getColor(R.color.text_blue));
                    div2.setDividerColor(getColor(R.color.text_blue));
                }
            }
        });
        pet_breed.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!pet_breed.hasFocus()) {
                    breed.setTextColor(getColor(R.color.text_gray));
                    div3.setDividerColor(getColor(R.color.text_gray));
                } else {
                    breed.setTextColor(getColor(R.color.text_blue));
                    div3.setDividerColor(getColor(R.color.text_blue));
                }
            }
        });
        pet_size.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!pet_size.hasFocus()) {
                    size.setTextColor(getColor(R.color.text_gray));
                    div4.setDividerColor(getColor(R.color.text_gray));
                } else {
                    size.setTextColor(getColor(R.color.text_blue));
                    div4.setDividerColor(getColor(R.color.text_blue));
                }
            }
        });

        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Temp = 1;
                flag[1] = male.isEnabled();
                male.setBackgroundResource(R.drawable.male_female_chacked);
                txt_male.setTextColor(getResources().getColor(R.color.white));
                icon_male.setImageResource(R.drawable.male_icon_white);

                txt_female.setTextColor(getResources().getColor(R.color.black));
                female.setBackgroundResource(R.drawable.male_female_bg_unchecked);
                icon_female.setImageResource(R.drawable.female_icon);

            }
        });
        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Temp = 1;
                flag[0] = female.isEnabled();
                female.setBackgroundResource(R.drawable.male_female_chacked);
                txt_male.setTextColor(getResources().getColor(R.color.black));
                icon_male.setImageResource(R.drawable.male_icon);

                txt_female.setTextColor(getResources().getColor(R.color.white));
                male.setBackgroundResource(R.drawable.male_female_bg_unchecked);
                icon_female.setImageResource(R.drawable.female_icon_white);


            }
        });
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i_to_back = new Intent(getApplicationContext(), Home.class);
                startActivity(i_to_back);
                finish();
            }
        });
        add_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagePickDialog();
            }
        });

        DBOpreation op = new DBOpreation();
        RecyclerViewModel pet_data = (RecyclerViewModel) getIntent().getSerializableExtra("EDIT");
        if (pet_data != null) {
            header_txt.setText("Update Pet Details");
            submit.setText("Update");
            img = pet_data.getPet_img();
            if (img != null) {
                Picasso.get().load(img).into(profile_img);
            } else {
                profile_img.setImageResource(R.drawable.dog_img);
            }
            pet_name.setText(pet_data.getPet_name());
            pet_species.setText(pet_data.getPet_species());
            pet_breed.setText(pet_data.getPet_breed());
            pet_size.setText(pet_data.getPet_size());
            petGender = pet_data.getPet_gender();
            quality1 = pet_data.getNeutered();
            quality2 = pet_data.getVaccinated();
            quality3 = pet_data.getFriendly_with_dogs();
            quality4 = pet_data.getFriendly_with_cats();
            quality5 = pet_data.getFriendly_with_kids_less_then_10_year();
            quality6 = pet_data.getFriendly_with_kids_greater_then_10_year();

            if (petGender == true) {
                male.setBackgroundResource(R.drawable.male_female_chacked);
                txt_male.setTextColor(getResources().getColor(R.color.white));
                icon_male.setImageResource(R.drawable.male_icon_white);

            } else if (petGender == false) {
                female.setBackgroundResource(R.drawable.male_female_chacked);
                txt_female.setTextColor(getResources().getColor(R.color.white));
                icon_female.setImageResource(R.drawable.female_icon_white);
            }

            if (quality1 == true) {
                toggle1.setChecked(true);
            } else {
                toggle1.setChecked(false);
            }
            if (quality2 == true) {
                toggle2.setChecked(true);
            } else {
                toggle2.setChecked(false);
            }
            if (quality3 == true) {
                toggle3.setChecked(true);
            } else {
                toggle3.setChecked(false);
            }
            if (quality4 == true) {
                toggle4.setChecked(true);
            } else {
                toggle4.setChecked(false);
            }
            if (quality5 == true) {
                toggle5.setChecked(true);
            } else {
                toggle5.setChecked(false);
            }
            if (quality6 == true) {
                toggle6.setChecked(true);
            } else {
                toggle6.setChecked(false);
            }

        } else {
            submit.setText("submit");
        }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pet_data == null) {
                    // Add pet to firebase realtime database on btn click
                    if (imageUri == null){
                        Toast.makeText(AddPetDetails.this, "pls select image...", Toast.LENGTH_SHORT).show();
                    }else if (pet_name.getText().toString().isEmpty()){
                        Toast.makeText(AddPetDetails.this, "pls enter name..", Toast.LENGTH_SHORT).show();
                    }else if (pet_species.getText().toString().isEmpty()){
                        Toast.makeText(AddPetDetails.this, "pls enter species..", Toast.LENGTH_SHORT).show();
                    }else if (pet_breed.getText().toString().isEmpty()){
                        Toast.makeText(AddPetDetails.this, "pls enter breed..", Toast.LENGTH_SHORT).show();
                    }else if (pet_size.getText().toString().isEmpty() ){
                        Toast.makeText(AddPetDetails.this, "pls enter size..", Toast.LENGTH_SHORT).show();
                    }else if (Temp != 1){
                        Toast.makeText(AddPetDetails.this, "pls select gender..", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        //add pet details to fire base
                        HashMap<String, Object> map = new HashMap<>();

                        // upload img to storage
                        uploader.putFile(imageUri);
                        map.put("pet_img", imageUri.toString());
                        map.put("pet_name", pet_name.getText().toString());
                        map.put("pet_species", pet_species.getText().toString());
                        map.put("pet_breed", pet_breed.getText().toString());
                        map.put("pet_size", pet_size.getText().toString());

                        map.put("pet_gender", flag[0]);
                        map.put("pet_gender", flag[1]);

                        if (toggle1.isChecked()) {
                            map.put("Neutered", true);
                        } else {
                            map.put("Neutered", false);
                        }
                        if (toggle2.isChecked()) {
                            map.put("Vaccinated", true);
                        } else {
                            map.put("Vaccinated", false);
                        }
                        if (toggle3.isChecked()) {
                            map.put("Friendly_with_dogs", true);
                        } else {
                            map.put("Friendly_with_dogs", false);
                        }
                        if (toggle4.isChecked()) {
                            map.put("Friendly_with_cats", true);
                        } else {
                            map.put("Friendly_with_cats", false);
                        }
                        if (toggle5.isChecked()) {
                            map.put("Friendly_with_kids_less_then_10_year", true);
                        } else {
                            map.put("Friendly_with_kids_less_then_10_year", false);
                        }
                        if (toggle6.isChecked()) {
                            map.put("Friendly_with_kids_greater_then_10_year", true);
                        } else {
                            map.put("Friendly_with_kids_greater_then_10_year", false);
                        }

                        op.add(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(AddPetDetails.this, "pet added", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(AddPetDetails.this, Home.class);
                                startActivity(i);
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(AddPetDetails.this, "failed to add...", Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                } else {
                    // Update pet detail to firebase realtime database on btn click
                    HashMap<String, Object> map_update = new HashMap<>();
                    if (imageUri!= null){
                        uploader.putFile(imageUri);
                        map_update.put("pet_img", imageUri.toString());
                    }else {
                        Uri my = Uri.parse(img);
                        uploader.putFile(my);
                        map_update.put("pet_img",my.toString());
                    }

                    map_update.put("pet_name", pet_name.getText().toString());
                    map_update.put("pet_species", pet_species.getText().toString());
                    map_update.put("pet_breed", pet_breed.getText().toString());
                    map_update.put("pet_size", pet_size.getText().toString());
                    map_update.put("pet_gender", flag[0]);
                    map_update.put("pet_gender", flag[1]);

                    if (toggle1.isChecked()) {
                        map_update.put("Neutered", true);
                    } else {
                        map_update.put("Neutered", false);
                    }
                    if (toggle2.isChecked()) {
                        map_update.put("Vaccinated", true);
                    } else {
                        map_update.put("Vaccinated", false);
                    }
                    if (toggle3.isChecked()) {
                        map_update.put("Friendly_with_dogs", true);
                    } else {
                        map_update.put("Friendly_with_dogs", false);
                    }
                    if (toggle4.isChecked()) {
                        map_update.put("Friendly_with_cats", true);
                    } else {
                        map_update.put("Friendly_with_cats", false);
                    }
                    if (toggle5.isChecked()) {
                        map_update.put("Friendly_with_kids_less_then_10_year", true);
                    } else {
                        map_update.put("Friendly_with_kids_less_then_10_year", false);
                    }
                    if (toggle6.isChecked()) {
                        map_update.put("Friendly_with_kids_greater_then_10_year", true);
                    } else {
                        map_update.put("Friendly_with_kids_greater_then_10_year", false);
                    }

                    op.update(pet_data.getId(), map_update).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(AddPetDetails.this, "pet updated", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(AddPetDetails.this, Home.class);
                            startActivity(i);
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddPetDetails.this, "failed to update...", Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            }
        });
    }
    private void imagePickDialog() {
        //options to display in dialog
        String[] options = {"Camera", "Gallery"};
        //alert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("pick Image From");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //handel clicks
                if (which == 0) {
                    //camera clicked
                    if (!checkCameraPermission()) {
                        requestCameraPermission();
                    } else {
                        //permission already granted
                        pickFromCamera();
                    }
                } else if (which == 1) {
                    if (!checkStoragePermission()) {
                        requestStoragePermission();
                    } else {
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
        Intent i = new Intent(Intent.ACTION_PICK);
        i.setType("image/*");
        startActivityIfNeeded(i, IMAGE_PICK_GALLERY_CODE);
    }

    private void pickFromCamera() {
        //intent to pick image from camera,the image will be returned in onActivityResult method
        //intent to open camera for image
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(cameraIntent, IMAGE_PICK_CAMERA_CODE);
    }

    private Boolean checkStoragePermission() {
        //check storage permission is enabled or not
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result;
    }

    private void requestStoragePermission() {
        ActivityCompat.requestPermissions(this, storagePermission, STORAGE_REQUEST_CODE);
    }

    private Boolean checkCameraPermission() {

        //check camera permission is enabled or not

        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result && result1;
    }

    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(this, cameraPermission, CAMERA_REQUEST_CODE);
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
        switch (requestCode) {
            case CAMERA_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    // if allowed returns true otherwise false
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean storageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (cameraAccepted && storageAccepted) {
                        //both permission allowed
                        pickFromCamera();
                    } else {
                        Message.message(this, "Camera & Storage permissions are required ");
                    }
                }
            }
            break;
            case STORAGE_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    // if allowed returns true otherwise false
                    boolean storageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (storageAccepted) {
                        //storage permission allowed
                        pickFromGallery();
                    } else {
                        Message.message(this, "Storage permission is required");
                    }
                }
            }
            break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //image picked from camera and gallery will be received here
        if (resultCode == RESULT_OK) {
            //image is picked
            if (requestCode == IMAGE_PICK_CAMERA_CODE) {
                Bundle extras = data.getExtras();
                imgToStore = (Bitmap) extras.get("data");
                profile_img.setImageBitmap(imgToStore);
                imageUri = getImageUri(getApplicationContext(), imgToStore);

            } else if (requestCode == IMAGE_PICK_GALLERY_CODE && data != null && data.getData() != null) {

                imageUri = data.getData();
                try {
                    imgToStore = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                profile_img.setImageBitmap(imgToStore);
            }
        } else {
            Message.message(this, "error");
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private Uri getImageUri(Context context, Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "title", null);
        return Uri.parse(path);
    }

    public void init() {
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
        toggle1 = findViewById(R.id.toggle1);
        toggle2 = findViewById(R.id.toggle2);
        toggle3 = findViewById(R.id.toggle3);
        toggle4 = findViewById(R.id.toggle4);
        toggle5 = findViewById(R.id.toggle5);
        toggle6 = findViewById(R.id.toggle6);
        header_txt = findViewById(R.id.header_text);

    }
}