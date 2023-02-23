package com.example.petcare;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;


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

        name = new ArrayList<>();
        image = new ArrayList<>();
        pet_type = new ArrayList<>();
        pet_verity = new ArrayList<>();
        pet_gender = new ArrayList<>();
        pet_size = new ArrayList<>();
        quality1 = new ArrayList<>();
        quality2 = new ArrayList<>();
        quality3 = new ArrayList<>();
        quality4 = new ArrayList<>();


        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplication(), AddPetDetails.class);
                startActivity(intent);
            }
        });






        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        details.add(new RecyclerViewModel(R.drawable.troy_dog, "Troy", "dog", "German Shepherd", "male", "80inch", "Neutered", "Vaccinated", "Friendly with dogs", "Friendly with cats"));
        details.add(new RecyclerViewModel(R.drawable.oscar_dog, "Oscar", "dog", "Labrador Retriever", "male", "80inch", "Neutered", "Vaccinated", "Friendly with dogs", "Friendly with cats"));
        details.add(new RecyclerViewModel(R.drawable.light_dog, "Light", "dog", "Poodle", "male", "80inch", "Neutered", "Vaccinated", "Friendly with dogs", "Friendly with cats"));
        details.add(new RecyclerViewModel(R.drawable.bosco_dog, "Bosco", "dog", "Rottweiler", "male", "80inch", "Neutered", "Vaccinated", "Friendly with dogs", "Friendly with cats"));
        adapter = new RecyclerViewAdapter(view.getContext(), details);
        recyclerView.setAdapter(adapter);

        return view;
    }


}


//    private void displayData(){
////        Cursor cursor = db.getData();
////        if (cursor.getCount() == 0){
////            Message.message(getContext(),"no data exists");
////            return;
////        }
////        else {
////            while (cursor.moveToNext()){
////                details.add(R.drawable.troy_dog,);
////            }
////        }
//        Cursor data = db.getData();
//
//        if (data.getCount() == 0){
//            Message.message(getContext(),"no data exists");
//            return;
//        }
//        else {
//            details.add(new RecyclerViewModel(R.drawable.troy_dog,name.add(data.getString(0).toString()),pet_type.add(data.getString(1)),pet_verity.add(data.getString(2)),pet_size.add(data.getString(3)),pet_gender.add(data.getString(4)),"Neutered","Vaccinated","Friendly with dogs","Friendly with cats"));
//            }
//        }
//    }

