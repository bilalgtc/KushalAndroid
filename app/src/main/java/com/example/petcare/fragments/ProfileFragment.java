package com.example.petcare.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.petcare.DashBoard;
import com.example.petcare.Home;
import com.example.petcare.R;
import com.example.petcare.Registration;
import com.example.petcare.SignIn;
import com.google.firebase.analytics.FirebaseAnalytics;


public class ProfileFragment extends Fragment {

    Button logout;
    FirebaseAnalytics analytics ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        analytics = FirebaseAnalytics.getInstance(getContext());
        logout = view.findViewById(R.id.log_out);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref =getActivity().getSharedPreferences("login",MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putBoolean("flag",false);
                editor.apply();

                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.METHOD, "logout");
                analytics.logEvent("pet_add", bundle);

                Intent i_logout = new Intent(getActivity().getApplication(), DashBoard.class);
                startActivity(i_logout);
                getActivity().finish();
            }
        });


        return view;
    }
}