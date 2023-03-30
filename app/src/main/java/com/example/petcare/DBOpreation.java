package com.example.petcare;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.HashMap;

public class DBOpreation {

    private DatabaseReference databaseReference;

    public DBOpreation() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference("PetCare").child("Users").child(user.getUid());

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
           return databaseReference.orderByKey().limitToFirst(50);
        }
        return databaseReference.orderByKey().startAfter(key).limitToFirst(50);
    }

}
