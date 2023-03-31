package com.example.petcare.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.petcare.AddPetDetails;
import com.example.petcare.DBOpreation;
import com.example.petcare.R;
import com.example.petcare.RecyclerViewModel;
import com.example.petcare.adapter.RecyclerViewAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    ImageView addBtn;
    RecyclerView recyclerView;
    RecyclerViewAdapter adapter;
    DBOpreation dao;
    String id = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.recy_view_home);
        addBtn = view.findViewById(R.id.addPet);
        dao = new DBOpreation();
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        adapter = new RecyclerViewAdapter(getContext());
        recyclerView.setAdapter(adapter);


        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplication(), AddPetDetails.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        loadData();
        return view;
    }

    public void loadData() {
        dao.get(id).addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                ArrayList<RecyclerViewModel> data = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    RecyclerViewModel m = dataSnapshot.getValue(RecyclerViewModel.class);
                    m.setId(dataSnapshot.getKey());
                    data.add(m);
                    id = dataSnapshot.getKey();
                }
                adapter.setitems(data);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }

}


