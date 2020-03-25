package com.example.tracksubapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;


public class MainActivity extends AppCompatActivity {
    ImageButton signBtn;
    ImageButton loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }
    public void moveToAbout(View v) {
        Intent i = new Intent(this, about.class);
        startActivity(i);
    }
    public void moveToSign(View v) {
        Intent i = new Intent(this, signUp.class);
        startActivity(i);
    }
    public void moveToLogin(View v) {
        Intent i = new Intent(this, login.class);
        startActivity(i);
    }

}
