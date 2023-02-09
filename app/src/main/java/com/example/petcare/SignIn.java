package com.example.petcare;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.text.method.SingleLineTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class SignIn extends AppCompatActivity {

    EditText password;

    TextView registration;
    ImageView back_button;
    ImageView password_eye;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);

        registration = findViewById(R.id.signIn_registration);
        back_button = findViewById(R.id.singIn_back_button);
        password_eye = findViewById(R.id.signIn_pass_eye);
        password = findViewById(R.id.signIn_password);


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

        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignIn.this,Registration.class);
                startActivity(intent);
            }
        });

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}