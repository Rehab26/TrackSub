package com.example.tracksubapp;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class User  {
    private DatabaseReference mDatabase;
    public String name;
    public String email;
    public String uid;
    public int subs;
    Subs sub;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)

    }
    public User(String uid, String username, String email) {
        this.name = username;
        this.email = email;
        this.uid = uid;

    }
    public void writeNewUser( String name, String email) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users");
        myRef.child(this.uid).child("uid").setValue(this.uid);
        myRef.child(this.uid).child("name").setValue(name);
        myRef.child(this.uid).child("email").setValue(email);

    }
    public String getName() {
        return this.name;
    }
    public int getSubs() {
        return this.subs;
    }

}
