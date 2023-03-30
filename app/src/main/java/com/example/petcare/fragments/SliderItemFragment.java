package com.example.petcare.fragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.petcare.R;

public class SliderItemFragment extends Fragment {
    private static final String ARG_PARAM1 = "slider-position";

    private static final int[] PAGE_IMAGE =
            new int[] {
                    R.drawable.welcome_s1_img, R.drawable.welcome_s2_img, R.drawable.welcome_s3_img
            };

    private static final int[] PAGE_TITLES =
            new int[] {R.string.s1, R.string.s2, R.string.s3 };

    private static final int[] PAGE_TEXT =
            new int[] {
                    R.string.s11, R.string.s22, R.string.s33
            };
    private int position;

    public SliderItemFragment() {
        // Required empty public constructor
    }

    public static SliderItemFragment newInstance(int position) {
        SliderItemFragment fragment = new SliderItemFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            position = getArguments().getInt(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_slider_item, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView imageView = view.findViewById(R.id.imageView);
        imageView.setImageResource(PAGE_IMAGE[position]);

        TextView title = view.findViewById(R.id.text_dis1);
        TextView titleText = view.findViewById(R.id.text_dis2);
        // set page title
        title.setText(PAGE_TITLES[position]);
        // set page sub title text
        titleText.setText(PAGE_TEXT[position]);
    }
}