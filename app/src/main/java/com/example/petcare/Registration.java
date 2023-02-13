package com.example.petcare;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.text.method.SingleLineTransformationMethod;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.divider.MaterialDivider;

public class Registration extends AppCompatActivity {

    ImageView back_btn, password_eye;
    EditText password, email, phone_no, full_name;
    TextView signIn, f_name,mail,mNo,pwd;
    MaterialDivider div1,div2,div3,div4;
    Button sign_up;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";




    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);

        init();
        changeStatusBarColor();

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView()
                    .setSystemUiVisibility(
                            View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        full_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!full_name.hasFocus()){
                    f_name.setTextColor(getColor(R.color.text_gray));
                    div1.setDividerColor(getColor(R.color.text_gray));
                }else {
                    f_name.setTextColor(getColor(R.color.text_blue));
                    div1.setDividerColor(getColor(R.color.text_blue));
                }
            }
        });
        email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!email.hasFocus()){
                    mail.setTextColor(getColor(R.color.text_gray));
                    div2.setDividerColor(getColor(R.color.text_gray));
                }
                else {
                    mail.setTextColor(getColor(R.color.text_blue));
                    div2.setDividerColor(getColor(R.color.text_blue));
                }
            }
        });
        phone_no.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!phone_no.hasFocus()){
                    mNo.setTextColor(getColor(R.color.text_gray));
                    div3.setDividerColor(getColor(R.color.text_gray));
                }else {
                    mNo.setTextColor(getColor(R.color.text_blue));
                    div3.setDividerColor(getColor(R.color.text_blue));
                }
            }
        });
        password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!password.hasFocus()){
                    pwd.setTextColor(getColor(R.color.text_gray));
                    div4.setDividerColor(getColor(R.color.text_gray));
                }else {
                    pwd.setTextColor(getColor(R.color.text_blue));
                    div4.setDividerColor(getColor(R.color.text_blue));
                }
            }
        });


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
                    password_eye.setImageResource(R.drawable.password_visible_eye);
                }
                else
                {
                    password.setTransformationMethod(new PasswordTransformationMethod());
                    password_eye.setImageResource(R.drawable.password_visible_off_eye);
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
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }
    public void init(){
        f_name = findViewById(R.id.f_name);
        mail = findViewById(R.id.maill);
        mNo = findViewById(R.id.m_no);
        pwd = findViewById(R.id.pwd);
        div1 =findViewById(R.id.name_div);
        div2 = findViewById(R.id.email_div);
        div3 = findViewById(R.id.mNo_div);
        div4 = findViewById(R.id.pwd_div);
        back_btn= findViewById(R.id.registration_back_button);
        signIn = findViewById(R.id.registration_signIn);
        password_eye = findViewById(R.id.registration_pass_eye);
        password = findViewById(R.id.registration_password);

        email = findViewById(R.id.email_id);
        phone_no = findViewById(R.id.mobile_no);
        full_name = findViewById(R.id.fullName);
        sign_up = findViewById(R.id.sign_up);

    }
}