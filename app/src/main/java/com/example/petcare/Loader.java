package com.example.petcare;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
                Boolean check = preferences.getBoolean("flag", true);

                SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
                Boolean loginCheck = sharedPreferences.getBoolean("flag", false);

                if (loginCheck){
                    Intent i_home = new Intent(Loader.this, Home.class);
                        startActivity(i_home);
                        finish();
                } else if (check){
                    Intent i_next = new Intent(Loader.this, welcomeActivity.class);
                    startActivity(i_next);
                    finish();
                } else  {
                   Intent i_next = new Intent(Loader.this, DashBoard.class);
                    startActivity(i_next);
                    finish();
                }
            }
        }, 2000);

      /*  android.net.ConnectivityManager cm = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);

        android.net.NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
            Toast.makeText(Loader.this, "INTERNET is Available", Toast.LENGTH_SHORT).show();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setIcon(R.drawable.internet_logo);
            builder.setTitle("NO INTERNET");
            builder.setMessage("Please Check Your Internet Connection.");
            builder.setPositiveButton("Enable INTERNET", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Settings.ACTION_SETTINGS);
                    startActivity(intent);

                }
            });
            builder.show();

        }*/
    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

}
