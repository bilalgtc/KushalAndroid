package com.example.petcare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

public class Welcome_activity extends AppCompatActivity {

    private ViewPager viewPager;
    private Button button;
    private sliderPagerAdapter adapter;
    TextView singIn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView()
                    .setSystemUiVisibility(
                            View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }


        viewPager = findViewById(R.id.pagerIntroSlider);
        singIn = findViewById(R.id.welcome_sign_in);
        TabLayout tabLayout = findViewById(R.id.tab);
        button = findViewById(R.id.next_btn);


        adapter = new sliderPagerAdapter(getSupportFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(adapter);


        tabLayout.setupWithViewPager(viewPager);

        changeStatusBarColor();


        singIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Welcome_activity.this ,SignIn.class);
                startActivity(intent);
            }
        });



        button.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {

                if (getitem(viewPager.getCurrentItem())<adapter.getCount()){
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                }

                else {

                    SharedPreferences pref = getSharedPreferences("login", MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putBoolean("flag",true);
                    editor.apply();


                    Intent i_home = new Intent(Welcome_activity.this , DashBoard.class);
                    startActivity(i_home);

                }


            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override public void onPageSelected(int position) {
                if (position == adapter.getCount() - 1) {

                        button.setText(R.string.getStarted);

                } else {
                    button.setText(R.string.next);
                }
            }
            @Override public void onPageScrollStateChanged(int state) {
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

    public int getitem(int position) {
        return viewPager.getCurrentItem() + position;
    }

}
