package com.example.petcare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

public class VeterinaryCard extends AppCompatActivity {

    ScrollView scrollView;
    ConstraintLayout card;
    LinearLayout header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.veterinary_card);
        init();
        changeStatusBarColor();

//        focusonview();

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView()
                    .setSystemUiVisibility(
                            View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }
    public void init(){
        scrollView =findViewById(R.id.scrollview);
        card = findViewById(R.id.card);
        header = findViewById(R.id.header);
    }

//    private final void focusonview(){
//        scrollView.post(new Runnable() {
//            @Override
//            public void run() {
//                scrollView.scrollTo(0,card.getBottom());
////                header.setBackgroundColor(getResources().getColor(R.color.text_gray));
//
////                Toast.makeText(VeterinaryCard.this, (int)header.getBottom() + ":" + (int)header.getBottom(), Toast.LENGTH_LONG).show();
//
//
//            }
//        });
//    }
}