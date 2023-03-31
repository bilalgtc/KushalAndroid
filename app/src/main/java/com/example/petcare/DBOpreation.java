package com.example.petcare;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class DBOpreation {

    private DatabaseReference databaseReference;
    private FirebaseUser user;

    public DBOpreation() {
        user = FirebaseAuth.getInstance().getCurrentUser();
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

    public void delete(String key) {


//        Query query = FirebaseDatabase.getInstance().getReference("Post").orderByChild("uploadTime").equalTo(key);
        Query query = FirebaseDatabase.getInstance().getReference("PetCare").child("Users").child(user.getUid()).child(key);
        query.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot ds : snapshot.getChildren())
                    ds.getRef().removeValue();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
