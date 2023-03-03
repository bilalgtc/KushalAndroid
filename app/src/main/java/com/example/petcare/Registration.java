package com.example.petcare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.petcare.dataBase.DbHelper;
import com.example.petcare.utils.Message;
import com.google.android.material.divider.MaterialDivider;

public class Registration extends AppCompatActivity {
    ImageView back_btn, password_eye;
    EditText password, email, phone_no, full_name;
    TextView signIn, f_name, mail, mNo, pwd;
    MaterialDivider div1, div2, div3, div4;
    Button sign_up;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    CheckBox check1, check2;
    Drawable dr;
    DbHelper db = new DbHelper(this);

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);

        init();
        changeStatusBarColor();
        imageSizeSet();

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView()
                    .setSystemUiVisibility(
                            View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        full_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!full_name.hasFocus()) {
                    f_name.setTextColor(getColor(R.color.text_gray));
                    div1.setDividerColor(getColor(R.color.text_gray));
                } else {
                    f_name.setTextColor(getColor(R.color.text_blue));
                    div1.setDividerColor(getColor(R.color.text_blue));
                }
            }
        });
        email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!email.hasFocus()) {
                    mail.setTextColor(getColor(R.color.text_gray));
                    div2.setDividerColor(getColor(R.color.text_gray));
                } else {
                    mail.setTextColor(getColor(R.color.text_blue));
                    div2.setDividerColor(getColor(R.color.text_blue));
                }
            }
        });
        phone_no.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!phone_no.hasFocus()) {
                    mNo.setTextColor(getColor(R.color.text_gray));
                    div3.setDividerColor(getColor(R.color.text_gray));
                } else {
                    mNo.setTextColor(getColor(R.color.text_blue));
                    div3.setDividerColor(getColor(R.color.text_blue));
                }
            }
        });
        password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!password.hasFocus()) {
                    pwd.setTextColor(getColor(R.color.text_gray));
                    div4.setDividerColor(getColor(R.color.text_gray));
                } else {
                    pwd.setTextColor(getColor(R.color.text_blue));
                    div4.setDividerColor(getColor(R.color.text_blue));
                }
            }
        });


        full_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (full_name.getText().toString().trim().length() == 0) {
                    full_name.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
                } else if (!full_name.getText().toString().trim().equalsIgnoreCase("")) {
                    full_name.setCompoundDrawables(null, null, dr, null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

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

        phone_no.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (phone_no.getText().toString().length() == 10) {
                    phone_no.setCompoundDrawables(null, null, dr, null);
                } else if (phone_no.getText().toString().length() < 10) {
                    phone_no.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
                } else if (phone_no.getText().toString().length() > 10) {
                    phone_no.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = email.getText().toString();
                if (full_name.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(Registration.this, "pls enter name", Toast.LENGTH_SHORT).show();
                } else if (email.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(Registration.this, "email is empty!", Toast.LENGTH_SHORT).show();
                } else if (!mail.matches(emailPattern)) {
                    Toast.makeText(Registration.this, "enter a valid email address", Toast.LENGTH_SHORT).show();
                } else if (phone_no.getText().toString().trim().equals("")) {
                    Toast.makeText(Registration.this, "phone no is empty!", Toast.LENGTH_SHORT).show();
                } else if (phone_no.getText().toString().length() < 10) {
                    Toast.makeText(Registration.this, "enter valid phone no", Toast.LENGTH_SHORT).show();
                } else if (phone_no.getText().toString().length() > 10) {
                    Toast.makeText(Registration.this, "enter valid phone no", Toast.LENGTH_SHORT).show();
                } else if (password.getText().toString().length() < 7) {
                    Toast.makeText(Registration.this, "enter strong password", Toast.LENGTH_SHORT).show();
                } else if (!check1.isChecked()) {
                    Toast.makeText(Registration.this, "pls check the terms", Toast.LENGTH_SHORT).show();
                } else if (!check2.isChecked()) {
                    Toast.makeText(Registration.this, "pls check the terms", Toast.LENGTH_SHORT).show();
                } else {
                    addUser(v);
                    Intent i_to_home = new Intent(Registration.this, Home.class);
                    startActivity(i_to_home);
                }
            }
        });

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

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Registration.this, SignIn.class);
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

    private void addUser(View v) {
        String name = full_name.getText().toString();
        String emil = email.getText().toString();
        String m_no = phone_no.getText().toString();
        String pwd = password.getText().toString();

        long id = db.insertData(name, emil, m_no, pwd);
        if (id <= 0) {
            Message.message(getApplicationContext(), "Insertion Unsuccessful");
            full_name.setText("");
            email.setText("");
            phone_no.setText("");
            password.setText("");

        } else {
            Message.message(getApplicationContext(), "Insertion Successful");
            full_name.setText("");
            email.setText("");
            phone_no.setText("");
            password.setText("");
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
        f_name = findViewById(R.id.f_name);
        mail = findViewById(R.id.maill);
        mNo = findViewById(R.id.m_no);
        pwd = findViewById(R.id.pwd);
        div1 = findViewById(R.id.name_div);
        div2 = findViewById(R.id.email_div);
        div3 = findViewById(R.id.mNo_div);
        div4 = findViewById(R.id.pwd_div);
        back_btn = findViewById(R.id.registration_back_button);
        signIn = findViewById(R.id.registration_signIn);
        password_eye = findViewById(R.id.registration_pass_eye);
        password = findViewById(R.id.registration_password);
        check1 = findViewById(R.id.checkBox1);
        check2 = findViewById(R.id.checkBox2);
        email = findViewById(R.id.email_id);
        phone_no = findViewById(R.id.mobile_no);
        full_name = findViewById(R.id.fullName);
        sign_up = findViewById(R.id.sign_up);
    }

    private void imageSizeSet() {
        dr = ContextCompat.getDrawable(Registration.this, R.drawable.success);
        assert dr != null;
        dr.setBounds(0, 0, 25, 25);
    }
}