package com.example.petcare.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petcare.AddPetDetails;
import com.example.petcare.DBOpreation;
import com.example.petcare.R;
import com.example.petcare.RecyclerViewModel;
import com.example.petcare.adapter.RecyclerViewAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    ImageView addBtn;
    RecyclerView recyclerView;
    RecyclerViewAdapter adapter;
    DBOpreation dao;
    String id = null;
    ArrayList<RecyclerViewModel> details;
    FirebaseFirestore db;
    FirebaseUser user;

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

        db = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        details = new ArrayList<>();

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

        db.collection(user.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        details.clear();
                        // called when data successfully retrieved from fireStore
                        for (DocumentSnapshot doc: task.getResult()){
//                            Log.e("data", "pet data"+task.getResult() );
                            RecyclerViewModel model = doc.toObject(RecyclerViewModel.class);
                            details.add(model);
                        }
                        adapter = new RecyclerViewAdapter(getContext(),details,HomeFragment.this);
                        recyclerView.setAdapter(adapter);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // called when there is any error to fetch data from fire store
                        Toast.makeText(getContext(), "Something Wrong...", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    public void deleteData(int index){
        //set title of progress bar
        db.collection(user.getUid()).document(details.get(index).getId())
                .delete()
                .addOnCompleteListener(task -> {
                    Toast.makeText(getContext(), "pet deleted", Toast.LENGTH_SHORT).show();
                    //update data
                    loadData();
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(getContext(), "failed to deleted...", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}


