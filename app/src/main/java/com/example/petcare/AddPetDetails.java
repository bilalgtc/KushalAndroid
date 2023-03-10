package com.example.petcare;

import static com.example.petcare.dataBase.MyDbHelper.TABLE_NAME;

import android.Manifest;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
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

import com.example.petcare.dataBase.MyDbHelper;
import com.example.petcare.utils.Message;
import com.example.petcare.fragments.HomeFragment;
import com.google.android.material.divider.MaterialDivider;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

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
    Bitmap imgToStore, bitmap;
    public Boolean isEditMode = false;
    Boolean[] flag = {true, false};
    byte[] profile_img_byte;
    MyDbHelper db = new MyDbHelper(this);
    String id, petNameTxt, petSpeciesTxt, petBreedTxt, petSizeTxt;

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

        Intent i = getIntent();
        isEditMode = i.getBooleanExtra("isEditMode", false);

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

        if (isEditMode) {
            id = i.getStringExtra("_id");
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
            String qyality5 = i.getStringExtra("quality5");
            String qyality6 = i.getStringExtra("quality6");


            if (img == null) {
                profile_img.setImageResource(R.drawable.dog_img);

            } else if (img.equals(img)){
                bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
                profile_img.setImageBitmap(bitmap);
            }
            else {
                bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
                profile_img.setImageBitmap(bitmap);
            }
            pet_name.setText(petNameTxt);
            pet_species.setText(petSpeciesTxt);
            pet_breed.setText(petBreedTxt);
            pet_size.setText(petSizeTxt);

            if (petGender.equals("1")) {
                male.setBackgroundResource(R.drawable.male_female_chacked);
                txt_male.setTextColor(getResources().getColor(R.color.white));
                icon_male.setImageResource(R.drawable.male_icon_white);

            } else if (petGender.equals("0")) {
                female.setBackgroundResource(R.drawable.male_female_chacked);
                txt_female.setTextColor(getResources().getColor(R.color.white));
                icon_female.setImageResource(R.drawable.female_icon_white);

            }

            if (qyality1.equals("on")) {
                toggle1.setChecked(true);
            } else if (qyality1.equals("off")) {
                toggle1.setChecked(false);
            }
            if (qyality2.equals("on")) {
                toggle2.setChecked(true);
            } else if (qyality2.equals("off")) {
                toggle2.setChecked(false);
            }
            if (qyality3.equals("on")) {
                toggle3.setChecked(true);
            } else if (qyality3.equals("off")) {
                toggle3.setChecked(false);
            }
            if (qyality4.equals("on")) {
                toggle4.setChecked(true);
            } else if (qyality4.equals("off")) {
                toggle4.setChecked(false);
            }
            if (qyality5.equals("on")) {
                toggle5.setChecked(true);
            } else if (qyality4.equals("off")) {
                toggle5.setChecked(false);
            }
            if (qyality6.equals("on")) {
                toggle6.setChecked(true);
            } else if (qyality4.equals("off")) {
                toggle6.setChecked(false);
            }
            String header = "update pet Details";
            String update = "update";
            header_txt.setText(header);
            submit.setText(update);

            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updatePet(v);
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

    private void updatePet(View view) {
        petNameTxt = pet_name.getText().toString();
        petSpeciesTxt = pet_species.getText().toString();
        petBreedTxt = pet_breed.getText().toString();
        petSizeTxt = pet_size.getText().toString();
        ContentValues contentValues = new ContentValues();

//        if (male.isPressed()) {
//            flag = true;
//        } else if (female.isPressed()) {
//            flag = false;
//        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        if (imgToStore == null) {
            profile_img.setImageResource(R.drawable.dog_img);
        } else {
            imgToStore.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            profile_img_byte = outputStream.toByteArray();
        }

        contentValues.put("pet_image", profile_img_byte);
        contentValues.put("pet_name", petNameTxt);
        contentValues.put("pet_species", petSpeciesTxt);
        contentValues.put("pet_breed", petBreedTxt);
        contentValues.put("pet_size", petSizeTxt);
        contentValues.put("pet_gender", flag[0]);
        contentValues.put("pet_gender", flag[1]);

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
        boolean b = db.updateRecord(contentValues, id);
        if (b) {
            Message.message(this, "pet updated");
            Intent i_to_home = new Intent(getApplicationContext(), Home.class);
            startActivity(i_to_home);
            finish();
        } else {
            Message.message(this, "failed to update");
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

        petNameTxt = pet_name.getText().toString();
        petSpeciesTxt = pet_species.getText().toString();
        petBreedTxt = pet_breed.getText().toString();
        petSizeTxt = pet_size.getText().toString();

        if (petNameTxt.isEmpty()) {
            Message.message(getApplicationContext(), "pls enter name");
        } else if (petSpeciesTxt.isEmpty()) {
            Message.message(getApplicationContext(), "pls enter pet species");
        } else if (petBreedTxt.isEmpty()) {
            Message.message(getApplicationContext(), "pls enter pet breed");
        } else if (petSizeTxt.isEmpty()) {
            Message.message(getApplicationContext(), "pls enter pet size");
        } else {

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            if (imgToStore ==null){
                profile_img.setImageResource(R.drawable.dog_img);
            }else {
                imgToStore.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                profile_img_byte = outputStream.toByteArray();
            }

            SQLiteDatabase database = db.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("pet_image", profile_img_byte);
            contentValues.put("pet_name", petNameTxt);
            contentValues.put("pet_species", petSpeciesTxt);
            contentValues.put("pet_breed", petBreedTxt);
            contentValues.put("pet_size", petSizeTxt);
            contentValues.put("pet_gender", flag[0]);
            contentValues.put("pet_gender", flag[1]);

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
            Message.message(this, "pet added");
            Intent i_to_home = new Intent(getApplicationContext(), Home.class);
            startActivity(i_to_home);
            finish();
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
                 bitmap = (Bitmap) (data.getExtras().get("data"));
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                profile_img_byte = stream.toByteArray();
                profile_img.setImageBitmap(bitmap);
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