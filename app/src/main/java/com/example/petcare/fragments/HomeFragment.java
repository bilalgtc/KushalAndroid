package com.example.petcare.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.petcare.AddPetDetails;
import com.example.petcare.MyDbHelper;
import com.example.petcare.R;
import com.example.petcare.RecyclerViewModel;
import com.example.petcare.adapter.RecyclerViewAdapter;

import java.util.ArrayList;


public class HomeFragment extends Fragment {
    ImageView addBtn;
    RecyclerView recyclerView;
    RecyclerViewAdapter adapter;
    ArrayList<RecyclerViewModel> details = new ArrayList<>();
    MyDbHelper db;
    ArrayList<String> name, image, pet_type, pet_verity, pet_gender, pet_size, quality1, quality2, quality3, quality4;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.recy_view_home);
        addBtn = view.findViewById(R.id.addPet);
        db = new MyDbHelper(getContext());

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplication(), AddPetDetails.class);
                startActivity(intent);
            }
        });

        Cursor cursor = new MyDbHelper(getContext()).getData();

        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        if(cursor!=null && cursor.getCount() > 0)
        {
            if (cursor.moveToFirst())
            {
                do {                                            //cursor.getBlob(1),cursor.getString(2),cursor.getString(3), cursor.getString(4)
                    RecyclerViewModel obj = new RecyclerViewModel(cursor.getBlob(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(6),cursor.getString(5),cursor.getString(7),cursor.getString(8),cursor.getString(9),cursor.getString(10), cursor.getString(11), cursor.getString(12));
                    details.add(obj);
                } while (cursor.moveToNext());
            }
        }
        adapter = new RecyclerViewAdapter(view.getContext(), details);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        return view;
    }


}



