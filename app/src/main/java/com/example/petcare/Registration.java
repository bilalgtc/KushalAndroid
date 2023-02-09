package com.example.petcare;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.text.method.SingleLineTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class Registration extends AppCompatActivity {

    ImageView back_btn, password_eye;
    EditText password, email, phone_no, full_name;
    TextView signIn;
    Button sign_up;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";




    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);
        back_btn= findViewById(R.id.registration_back_button);
        signIn = findViewById(R.id.registration_signIn);
        password_eye = findViewById(R.id.registration_pass_eye);
        password = findViewById(R.id.registration_password);

        email = findViewById(R.id.email_id);
        phone_no = findViewById(R.id.mobile_no);
        full_name = findViewById(R.id.fullName);

        sign_up = findViewById(R.id.sign_up);

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = email.getText().toString();
                Drawable myIcon = getResources().getDrawable(R.drawable.sucess_icon);
                myIcon.setBounds(0, 0, myIcon.getIntrinsicWidth(), myIcon.getIntrinsicHeight());

                if(full_name.getText().toString().trim().equalsIgnoreCase("")){
                    full_name.setError("please enter name");
                }
                else {
                    full_name.setError("good",myIcon);
                }
                 if( email.getText().toString().trim().equalsIgnoreCase("")) {
                    email.setError("email address required");
                }
                else if(!mail.matches(emailPattern) ) {
                    email.setError("Please enter a valid email address");
                }
                else {
                     email.setError( "good",myIcon);
                }

                 if (phone_no.getText().toString().trim().equals("")){
                    phone_no.setError("please enter phone no");
                }
                else if (phone_no.getText().toString().length() < 10){
                    phone_no.setError("please enter valid phone no");
                }
                else {
                    //validation is true, so what to put here?

                    phone_no.setError("good",myIcon);

                }
            }
        });


        password_eye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (password.getTransformationMethod().getClass().getSimpleName().equals("PasswordTransformationMethod"))
                {
                    password.setTransformationMethod(new SingleLineTransformationMethod());
                    password_eye.setImageResource(R.drawable.eye_visibility);
                }
                else
                {
                    password.setTransformationMethod(new PasswordTransformationMethod());
                    password_eye.setImageResource(R.drawable.eye_visibility);
                }
                password.setSelection(password.getText().length());
            }
        });




        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Registration.this , SignIn.class);
                startActivity(intent);
            }
        });

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}