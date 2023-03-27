package com.example.petcare;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.HashMap;

public class DBOpreation {

    private DatabaseReference databaseReference;

    public DBOpreation() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference(RecyclerViewModel.class.getSimpleName());

    }

    public Task<Void> add(HashMap<String,Object> hashMap){
        return databaseReference.push().setValue(hashMap);
    }

    public Task<Void> update(String key , HashMap<String,Object> hashMap){
        return databaseReference.child(key).updateChildren(hashMap);
    }
    public Task<Void> remove(String key){
        return databaseReference.child(key).removeValue();
    }

    public Query get (String key){
        if (key == null){
           return databaseReference.orderByKey().limitToFirst(8);
        }
        return databaseReference.orderByKey().startAfter(key).limitToFirst(8);
    }

}
