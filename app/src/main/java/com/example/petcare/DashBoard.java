package com.example.petcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DashBoard extends AppCompatActivity {
    LinearLayout cwf;
    Button r_w_gmail;
    TextView sign_in;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dash_board);
        cwf = findViewById(R.id.c_with_fb);
        r_w_gmail = findViewById(R.id.register_with_mail);
        sign_in = findViewById(R.id.sign_in);



        r_w_gmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashBoard.this,Registration.class);
                startActivity(intent);
            }
        });

        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashBoard.this,SignIn.class);
                startActivity(intent);
            }
        });



    }
}