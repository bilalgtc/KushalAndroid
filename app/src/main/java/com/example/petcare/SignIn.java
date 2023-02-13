package com.example.petcare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.DividerItemDecoration;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.divider.MaterialDivider;

public class SignIn extends AppCompatActivity {

    EditText email, password;

    TextView registration,mail,pass;
    ImageView back_button;
    ImageView password_eye;
    MaterialDivider divi1, divi2;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);

        registration = findViewById(R.id.signIn_registration);
        back_button = findViewById(R.id.singIn_back_button);
        mail = findViewById(R.id.e_mail);
        pass = findViewById(R.id.pwd);
        divi1 = findViewById(R.id.email_id_divider);
        divi2 = findViewById(R.id.pass_divider);
        password_eye = findViewById(R.id.signIn_pass_eye);
        password = findViewById(R.id.signIn_password);
        email = findViewById(R.id.email_id);

        changeStatusBarColor();
//        setEditTextDrawables(email, R.drawable.sucess_icon);

        email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!email.hasFocus()){
                    mail.setTextColor(getColor(R.color.text_gray));
                    divi1.setDividerColor(getColor(R.color.text_gray));
                }
                else {
                    mail.setTextColor(getColor(R.color.text_blue));
                    divi1.setDividerColor(getColor(R.color.text_blue));
                }
            }
        });

        password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!password.hasFocus()){
                    pass.setTextColor(getColor(R.color.text_gray));
                    divi2.setDividerColor(getColor(R.color.text_gray));
                }
                else {
                    pass.setTextColor(getColor(R.color.text_blue));
                    divi2.setDividerColor(getColor(R.color.text_blue));
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
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

//    public Drawable setTint(Drawable d, int color) {
//        Drawable wrappedDrawable = DrawableCompat.wrap(d);
//        DrawableCompat.setTint(wrappedDrawable, color);
//        return wrappedDrawable;
//    }
//    public void setEditTextDrawables(final EditText editText, final int drawable) {
//        editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, drawable, 0);
//        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean b) {
//                if (b){
//                    Drawable icon = getResources().getDrawable(drawable);
//                    editText.setCompoundDrawablesWithIntrinsicBounds(setTint(icon,
//                            getResources().getColor(R.color.text_blue)), null, null, null);
//                }else if (!b){
//                    Drawable icon = getResources().getDrawable(drawable);
//                    editText.setCompoundDrawablesWithIntrinsicBounds(setTint(icon,
//                            getResources().getColor(R.color.text_gray)), null, null, null);
//                }
//            }
//        });
//    }

}