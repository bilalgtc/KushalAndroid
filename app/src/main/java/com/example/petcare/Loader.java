package com.example.petcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class Loader extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loader);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences preferences = getSharedPreferences("login",MODE_PRIVATE);
                Boolean check = preferences.getBoolean("flag",false);

                Intent i_next;

                if (check ){ // for true (when user logged in)

                    i_next = new Intent(Loader.this, DashBoard.class);

                }else {     // for true (for either first time or user is logged out)
                    i_next = new Intent(Loader.this, Welcome_activity.class);

                }
                startActivity(i_next);
            }
        },2000    );

    }
}