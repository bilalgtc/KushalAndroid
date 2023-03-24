package com.example.petcare.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.petcare.AddPetDetails;
import com.example.petcare.R;
import com.example.petcare.RecyclerViewModel;
import com.example.petcare.adapter.RecyclerViewAdapter;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    ImageView addBtn;
    RecyclerView recyclerView;
    RecyclerViewAdapter adapter;
    ArrayList<RecyclerViewModel> details = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.recy_view_home);
        addBtn = view.findViewById(R.id.addPet);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplication(), AddPetDetails.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        adapter = new RecyclerViewAdapter(view.getContext(), details);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        return view;
    }
}


