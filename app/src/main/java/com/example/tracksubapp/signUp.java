package com.example.tracksubapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class signUp extends AppCompatActivity {
    private FirebaseAuth mAuth;
    //private ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar2);
    private DatabaseReference mDatabase;
    
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference();
    DatabaseReference myRef = database.getReference("Users");

    final private String TAG = "Main Activity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();
        //Initalization
        ImageButton signBtn = (ImageButton) findViewById(R.id.signBtn);
        signBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 EditText emailEdit = (EditText) findViewById(R.id.email);
                 EditText passwordEdit = (EditText) findViewById(R.id.pass);
                 EditText nameEdit = (EditText) findViewById(R.id.name);
                 EditText confirmPass = (EditText) findViewById(R.id.confPass);
                 final String email = emailEdit.getText().toString().trim();
                 String password = passwordEdit.getText().toString().trim();
                 final String name = nameEdit.getText().toString();
                 String confPass = confirmPass.getText().toString().trim();
                 FirebaseDatabase database = FirebaseDatabase.getInstance();
                     mAuth.createUserWithEmailAndPassword(email, password)
                             .addOnCompleteListener(signUp.this, new OnCompleteListener<AuthResult>() {
                                 @Override
                                 public void onComplete(@NonNull Task<AuthResult> task) {
                                     if (task.isSuccessful()) {

                                         // Sign in success, update UI with the signed-in user's information
                                         Log.d(TAG, "createUserWithEmail:success");
                                         FirebaseUser user = mAuth.getCurrentUser();
                                         User newUser = new User(user.getUid() , name , email );
                                         newUser.writeNewUser( name , email);
                                         //writeNewUser( user.getUid() , name , email);
                                         Toast.makeText(signUp.this , "Welcome"+user.getUid() , Toast.LENGTH_LONG).show();
                                     } else {
                                         // If sign in fails, display a message to the user.
                                         Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                         Toast.makeText(signUp.this, "Authentication failed.",
                                                 Toast.LENGTH_SHORT).show();

                                     }

                                     // ...
                                 }
                             });



            }
        });
    }
    public void moveToMain(View v) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
    public void moveToLogin(View v) {
        Intent i = new Intent(this, login.class);
        startActivity(i);
    }

    public void writeNewUser(String userId, String name, String email) {
        myRef.child(userId).child("uid").setValue(userId);
        myRef.child(userId).child("name").setValue(name);
        myRef.child(userId).child("email").setValue(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {
                            //Task was successful, data written!
                            Toast.makeText(signUp.this, "Data saved!", Toast.LENGTH_SHORT).show();
                        } else {
                            //Task was not successful,
                            Toast.makeText(signUp.this, "Something went wrong", Toast.LENGTH_SHORT).show();

                            //Log the error message

                        }

                    }
                });
    }



}

