package com.example.petcare;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.text.method.SingleLineTransformationMethod;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.divider.MaterialDivider;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignIn extends AppCompatActivity {

    EditText email, password;
    TextView registration, mail, pass, forgot_pass;
    ImageView back_button;
    ImageView password_eye;
    Button sing_in;
    MaterialDivider divi1, divi2;
    Drawable dr;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    FirebaseAuth firebaseAuth;
    FirebaseUser user;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);

        init();
        imageSizeSet();
        changeStatusBarColor();
        email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!email.hasFocus()) {
                    mail.setTextColor(getColor(R.color.text_gray));
                    divi1.setDividerColor(getColor(R.color.text_gray));
                } else {
                    mail.setTextColor(getColor(R.color.text_blue));
                    divi1.setDividerColor(getColor(R.color.text_blue));
                }
            }
        });
        password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!password.hasFocus()) {
                    pass.setTextColor(getColor(R.color.text_gray));
                    divi2.setDividerColor(getColor(R.color.text_gray));
                } else {
                    pass.setTextColor(getColor(R.color.text_blue));
                    divi2.setDividerColor(getColor(R.color.text_blue));
                }
            }
        });

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!email.getText().toString().matches(emailPattern)) {
                    email.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
                } else if (email.getText().toString().matches(emailPattern)) {
                    email.setCompoundDrawables(null, null, dr, null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        sing_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (email.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(SignIn.this, "email is empty!", Toast.LENGTH_SHORT).show();
                } else if (!email.getText().toString().trim().matches(emailPattern)) {
                    Toast.makeText(SignIn.this, "email not found", Toast.LENGTH_SHORT).show();
                } else {

                    SharedPreferences pref = getSharedPreferences("login", MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putBoolean("flag",true);
                    editor.apply();
                    performLogin();

                }
            }
        });

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView()
                    .setSystemUiVisibility(
                            View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        password_eye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (password.getTransformationMethod().getClass().getSimpleName().equals("PasswordTransformationMethod")) {
                    password.setTransformationMethod(new SingleLineTransformationMethod());
                    password_eye.setImageResource(R.drawable.password_visible_eye);
                } else {
                    password.setTransformationMethod(new PasswordTransformationMethod());
                    password_eye.setImageResource(R.drawable.password_visible_off_eye);
                }
                password.setSelection(password.getText().length());
            }
        });

        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignIn.this, Registration.class);
                startActivity(intent);
                finish();
            }
        });

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignIn.this, DashBoard.class);
                startActivity(intent);
                finish();
            }
        });

        forgot_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignIn.this,ForgotPass.class);
                startActivity(i);
            }
        });
    }

    private void performLogin() {
        firebaseAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Intent i_to_home = new Intent(SignIn.this, Home.class);
                    i_to_home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i_to_home);
                    Toast.makeText(SignIn.this, "Login successful", Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(SignIn.this, "pls check id && pass", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void imageSizeSet() {
        dr = ContextCompat.getDrawable(SignIn.this, R.drawable.success);
        assert dr != null;
        dr.setBounds(0, 0, 25, 25);
    }

    private void init() {
        registration = findViewById(R.id.signIn_registration);
        back_button = findViewById(R.id.singIn_back_button);
        mail = findViewById(R.id.e_mail);
        pass = findViewById(R.id.pwd);
        divi1 = findViewById(R.id.email_id_divider);
        divi2 = findViewById(R.id.pass_divider);
        password_eye = findViewById(R.id.signIn_pass_eye);
        password = findViewById(R.id.signIn_password);
        email = findViewById(R.id.email_id);
        sing_in = findViewById(R.id.sign_in);
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        forgot_pass = findViewById(R.id.singIn_forgot_pass);
    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }
}