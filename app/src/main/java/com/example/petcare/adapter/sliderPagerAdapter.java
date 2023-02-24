package com.example.petcare.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.petcare.fragments.SliderItemFragment;

public class sliderPagerAdapter extends FragmentPagerAdapter {
    public sliderPagerAdapter(@NonNull FragmentManager fm, int behaviour) {
        super(fm, behaviour);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return SliderItemFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return 3;
    }
}
