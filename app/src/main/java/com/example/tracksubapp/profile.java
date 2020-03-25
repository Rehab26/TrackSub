package com.example.tracksubapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class profile extends AppCompatActivity {
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser loginUser = mAuth.getCurrentUser();
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    String userID = loginUser.getUid();
    private TextView name;
    private TextView sub;
    DatabaseReference myRef = database.getReference("Users/"+userID);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                System.out.println(loginUser);
                System.out.println(user);
                display(user);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    public void display(User user) {
     name = (TextView) findViewById(R.id.name22);
     sub = (TextView) findViewById(R.id.sub);
        name.setText(user.getName());
        if(user.subs == 0 || user.subs == 1) {
            sub.setText(user.getSubs() + " subsecription");
        }
        else {
            sub.setText(user.getSubs()+" subsecriptions");
        }
    }
}
