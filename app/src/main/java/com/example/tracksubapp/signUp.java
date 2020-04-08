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
import android.widget.TextView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class signUp extends AppCompatActivity {
    public FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private EditText emailEdit , passwordEdit , nameEdit , confirmPass;
    public FirebaseUser loginUser , user;
    public FirebaseDatabase database;
    private DatabaseReference myRef;
    private ImageButton signBtn;
    private TextView warning;
    private String email , name , password , confPass , emailPattern;

    final private String TAG = "Main Activity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Users");
        //Initalization
        initializeUI();
        signBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signNewUser();
            }
        });

    }
    private void initializeUI() {
        emailEdit = (EditText) findViewById(R.id.email);
        passwordEdit = (EditText) findViewById(R.id.pass);
        nameEdit = (EditText) findViewById(R.id.name);
        confirmPass = (EditText) findViewById(R.id.confPass);
        progressBar = findViewById(R.id.progressBar2);
        signBtn = (ImageButton) findViewById(R.id.signBtn);
        warning = (TextView) findViewById(R.id.warning);
    }
    public void signNewUser() {
        progressBar.setVisibility(View.VISIBLE);
        email = emailEdit.getText().toString().trim();
        password = passwordEdit.getText().toString().trim();
        name = nameEdit.getText().toString();
        confPass = confirmPass.getText().toString().trim();
        emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (TextUtils.isEmpty(email)) {
            progressBar.setVisibility(View.GONE);
            warning.setText("Please enter email...");
            warning.setVisibility(View.VISIBLE);
            return;
        }
        if(!(email.matches(emailPattern))){
            progressBar.setVisibility(View.GONE);
            warning.setText("Please enter a valid email...");
            warning.setVisibility(View.VISIBLE);
            return;
        }
        if (TextUtils.isEmpty(password)) {
            progressBar.setVisibility(View.GONE);
            warning.setText("Please enter password...");
            warning.setVisibility(View.VISIBLE);
            return;
        }
        if (TextUtils.isEmpty(name)) {
            progressBar.setVisibility(View.GONE);
            warning.setText("Please enter your name...");
            warning.setVisibility(View.VISIBLE);
            return;
        }
        if (TextUtils.equals(password, confPass)) {
            warning.setVisibility(View.GONE);
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(signUp.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success");
                                user = mAuth.getCurrentUser();
                                User newUser = new User(user.getUid(), name, email);
                                newUser.writeNewUser(name, email);
                                progressBar.setVisibility(View.GONE);
                                warning.setVisibility(View.GONE);
                                loginUser = mAuth.getCurrentUser();
                                user = task.getResult().getUser();
                                Intent intent = new Intent(signUp.this, welcome.class);
                                startActivity(intent);
                            } else {
                                // If sign in fails, display a message to the user.
                                progressBar.setVisibility(View.GONE);
                                warning.setVisibility(View.VISIBLE);
                                warning.setText("Email entered is already sign in!");
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());

                            }

                            // ...
                        }
                    });
        } else {
            warning.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            warning.setText("Password entered didn't match the confirm password!");
        }
    }

    public void moveToMain(View v) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
    public void moveToLogin(View v) {
        Intent i = new Intent(this, login.class);
        startActivity(i);
    }
    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
        warning.setVisibility(View.GONE);
    }





}

