package com.example.petcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.petcare.utils.Message;

public class Loader extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loader);

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        changeStatusBarColor();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences preferences = getSharedPreferences("splash", MODE_PRIVATE);
                Boolean check = preferences.getBoolean("flag", false);

                SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
                Boolean loginCheck = sharedPreferences.getBoolean("flag", false);


                Intent i_next;

//                if (check) {
//                    i_next = new Intent(Loader.this, DashBoard.class);
//                    startActivity(i_next);
//                    finish();

                    if (loginCheck) {
                        Intent i_home = new Intent(Loader.this, Home.class);
                        startActivity(i_home);
                        finish();
                    } else {
                        Intent i_dash = new Intent(Loader.this, DashBoard.class);
                        startActivity(i_dash);
                        finish();
                    }
//                } else {
//                    i_next = new Intent(Loader.this, welcomeActivity.class);
//                    startActivity(i_next);
//                    finish();
//                }
            }
        }, 2000);

    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

}
