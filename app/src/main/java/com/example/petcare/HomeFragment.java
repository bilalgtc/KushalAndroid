package com.example.petcare;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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
        View view= inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView= view.findViewById(R.id.recy_view_home);
        addBtn = view.findViewById(R.id.addPet);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplication(), AddPetDetails.class);
                startActivity(intent);
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        details.add(new RecyclerViewModel(R.drawable.troy_dog,"Troy","dog","German Shepherd","male", "80inch","Neutered","Vaccinated","Friendly with dogs","Friendly with cats"));
        details.add(new RecyclerViewModel(R.drawable.oscar_dog,"Oscar","dog","Labrador Retriever","male", "80inch","Neutered","Vaccinated","Friendly with dogs","Friendly with cats"));
        details.add(new RecyclerViewModel(R.drawable.light_dog,"Light","dog","Poodle","male", "80inch","Neutered","Vaccinated","Friendly with dogs","Friendly with cats"));
        details.add(new RecyclerViewModel(R.drawable.bosco_dog,"Bosco","dog","Rottweiler","male", "80inch","Neutered","Vaccinated","Friendly with dogs","Friendly with cats"));

        adapter = new RecyclerViewAdapter(view.getContext(),details);
        recyclerView.setAdapter(adapter);

        return view;
    }

}