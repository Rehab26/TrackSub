package com.example.tracksubapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class login extends AppCompatActivity {
    private EditText emailTV, passwordTV;
    private ImageButton loginBtn;
    private ProgressBar progressBar;
    public FirebaseUser loginUser;
    private FirebaseAuth mAuth;
    private TextView warning;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        initializeUI();

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUserAccount();
            }
        });
    }
    public void moveToMain(View v) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
    public void moveToSign(View v) {
        Intent i = new Intent(this, signUp.class);
        startActivity(i);
    }

    private void loginUserAccount() {
        progressBar.setVisibility(View.VISIBLE);
        warning.setVisibility(View.GONE);
        String email, password;
        email = emailTV.getText().toString();
        password = passwordTV.getText().toString();

        if (TextUtils.isEmpty(email)) {
            warning.setVisibility(View.VISIBLE);
            warning.setText("Please enter email!");
            progressBar.setVisibility(View.GONE);
            return;
        }
        if (TextUtils.isEmpty(password)) {
            warning.setVisibility(View.VISIBLE);
            warning.setText("Please enter password!");
            progressBar.setVisibility(View.GONE);
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressBar.setVisibility(View.GONE);
                            warning.setVisibility(View.GONE);
                            loginUser = mAuth.getCurrentUser();
                            Intent intent = new Intent(login.this, profile.class);
                            startActivity(intent);
                        }
                        else {
                            progressBar.setVisibility(View.GONE);
                            warning.setVisibility(View.VISIBLE);
                            warning.setText("The email or password you entered is incorrect, please try again.");
                        }
                    }
                });
    }

    private void initializeUI() {
        emailTV = findViewById(R.id.email);
        passwordTV = findViewById(R.id.password);
        warning = (TextView) findViewById(R.id.warning);
        loginBtn = findViewById(R.id.login);
        progressBar = findViewById(R.id.progressBar);
    }

}



