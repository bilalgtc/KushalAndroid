package com.example.petcare;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;
import static com.example.petcare.MyDbHelper.IMAGE;
import static com.example.petcare.MyDbHelper.TABLE_NAME;
import static com.example.petcare.MyDbHelper.UID;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentValues;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petcare.commonMethod.Message;
import com.example.petcare.fragments.HomeFragment;
import com.google.android.material.divider.MaterialDivider;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AddPetDetails extends AppCompatActivity {
    //PERMISSION CONSTANTS
    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int STORAGE_REQUEST_CODE = 101;
    //IMAGE PICK CONSTANTS
    private static final int IMAGE_PICK_CAMERA_CODE = 102;
    private static final int IMAGE_PICK_GALLERY_CODE = 103;
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
    Boolean isEditMode;
    String gender;
    Boolean flag;
    byte[] profile_img_byte;
    MyDbHelper db = new MyDbHelper(this);
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

        Intent i = getIntent();
//        byte[] profile_img1 = i.getByteArrayExtra("pet_image");
        String petNameTxt = i.getStringExtra("pet_name");
        String petSpeciesTxt = i.getStringExtra("pet_species");
        String petBreedTxt = i.getStringExtra("pet_breed");
        String petSizeTxt = i.getStringExtra("pet_size");

        pet_name.setText(petNameTxt);
        pet_species.setText(petSpeciesTxt);
        pet_breed.setText(petBreedTxt);
        pet_size.setText(petSizeTxt);

        isEditMode = i.getBooleanExtra("editmode", false);

        //init permission arrays
        cameraPermission = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView()
                    .setSystemUiVisibility(
                            View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
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
                if (male.isPressed()) {
                    male.setBackgroundResource(R.drawable.male_female_chacked);
                    txt_male.setTextColor(getResources().getColor(R.color.white));
                    icon_male.setImageResource(R.drawable.male_icon_white);

                    txt_female.setTextColor(getResources().getColor(R.color.black));
                    female.setBackgroundResource(R.drawable.male_female_bg_unchecked);
                    icon_female.setImageResource(R.drawable.female_icon);
                    flag = true;
                }
            }
        });
        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (female.isPressed()) {
                    female.setBackgroundResource(R.drawable.male_female_chacked);
                    txt_male.setTextColor(getResources().getColor(R.color.black));
                    icon_male.setImageResource(R.drawable.male_icon);

                    txt_female.setTextColor(getResources().getColor(R.color.white));
                    male.setBackgroundResource(R.drawable.male_female_bg_unchecked);
                    icon_female.setImageResource(R.drawable.female_icon_white);
                    flag = false;
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

        if (isEditMode) {
            header_txt.setText("update pet Details");
            submit.setText("update");
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updatePet();
                }
            });
        } else {
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addPet(v);
                }
            });
        }
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

        boolean result = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result && result1;
    }

    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(this, cameraPermission, CAMERA_REQUEST_CODE);
    }


    public void addPet(View view) {
        String petNameTxt = pet_name.getText().toString();
        String petSpeciesTxt = pet_species.getText().toString();
        String petBreedTxt = pet_breed.getText().toString();
        String petSizeTxt = pet_size.getText().toString();


        if (petNameTxt.isEmpty() || petSpeciesTxt.isEmpty() || petBreedTxt.isEmpty() || petSizeTxt.isEmpty()) {
            Message.message(getApplicationContext(), "Enter details");

            if (male.isPressed()) {
                flag = true;
            } else if (female.isPressed()) {
                flag = false;
            }
        } else {
            SQLiteDatabase database = db.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("pet_image", profile_img_byte);
            contentValues.put("pet_name", petNameTxt);
            contentValues.put("pet_species", petSpeciesTxt);
            contentValues.put("pet_breed", petBreedTxt);
            contentValues.put("pet_size", petSizeTxt);
            contentValues.put("pet_gender", flag);


            if (toggle1.isChecked()) {
                contentValues.put("neutered", "on");
            } else {
                contentValues.put("neutered", "off");
            }
            if (toggle2.isChecked()) {
                contentValues.put("Vaccinated", "on");
            } else {
                contentValues.put("Vaccinated", "off");
            }
            if (toggle3.isChecked()) {
                contentValues.put("Friendly_with_dogs", "on");
            } else {
                contentValues.put("Friendly_with_dogs", "off");
            }
            if (toggle4.isChecked()) {
                contentValues.put("Friendly_with_cats", "on");
            } else {
                contentValues.put("Friendly_with_cats", "off");
            }
            if (toggle5.isChecked()) {
                contentValues.put("Friendly_with_kids_less_then_10_year", "on");
            } else {
                contentValues.put("Friendly_with_kids_less_then_10_year", "off");
            }
            if (toggle6.isChecked()) {
                contentValues.put("Friendly_with_kids_greater_then_10_year", "on");
            } else {
                contentValues.put("Friendly_with_kids_greater_then_10_year", "off");
            }

            database.insert(TABLE_NAME, null, contentValues);
            Intent i = new Intent(getApplicationContext(), HomeFragment.class);
            Message.message(this, "inserted");
            startActivity(i);

        }
    }

    private void updatePet() {


//        Intent i = getIntent();
//
//        petNameTxt = i.getStringExtra("pet_name");
//        petSpeciesTxt = i.getStringExtra("pet_species");
//        petBreedTxt = i.getStringExtra("pet_breed");
////           petSizeTxt = i.getString("pet_size");
//
//
//        pet_name.setText(petNameTxt);
//        pet_species.setText(petSpeciesTxt);
//        pet_breed.setText(petBreedTxt);
//        pet_size.setText(petSizeTxt);


//        if (male.isPressed()){
//            flag = true;
//        }
//        else if (female.isPressed()){
//            flag = false;
//        }
//        else if(petNameTxt.isEmpty() || petSpeciesTxt.isEmpty() || petBreedTxt.isEmpty() || petSizeTxt.isEmpty())
//        {
//            Message.message(getApplicationContext(),"Enter details");
//        }
//        else {
//            SQLiteDatabase database = db.getWritableDatabase();
//            ContentValues contentValues = new ContentValues();
//
//        }

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
                Bitmap img = (Bitmap) (data.getExtras().get("data"));
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                img.compress(Bitmap.CompressFormat.PNG, 100, stream);
                profile_img_byte = stream.toByteArray();
                profile_img.setImageBitmap(img);
            } else if (requestCode == IMAGE_PICK_GALLERY_CODE && data != null && data.getData() != null) {

                imageUri = data.getData();
                try {
                    imgToStore = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    imgToStore.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    profile_img_byte = stream.toByteArray();
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

//    private void editData(){
//        if (getIntent().getBundleExtra("data")!= null){
//            Bundle bundle =getIntent().getBundleExtra("data");
//            pet_name.setText(bundle.getString("pet_name"));
//            pet_species.setText(bundle.getString("pet_species"));
//            pet_breed.setText(bundle.getString("pet_breed"));
//            pet_size.setText(bundle.getString("pet_size"));
//            submit.setText("submit");
//        }
//    }

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

        db = new MyDbHelper(this);
    }

}