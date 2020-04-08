package com.example.tracksubapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class welcome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }
    public void moveTologin2(View v) {
        Intent i = new Intent(this, login.class);
        startActivity(i);
        finish();
    }

    public void moveTosignup(View v) {
        Intent i = new Intent(this, signUp.class);
        startActivity(i);
        finish();
    }
}
