package com.example.tracksubapp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class User  {
    public String name;
    public String email;
    public String uid;
    public int subs;

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
