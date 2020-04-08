package com.example.tracksubapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class test extends AppCompatActivity {
    ListView listView;
    String[] nameArray = {"Netflix","SoundCloud","iTunes","Apple Tv","Youtube Premiume","Spotify","ShahidNet" , "AnghamiPlus" };

    String[] infoArray = {
            "29",
            "11",
            "9",
            "56",
            "23",
            "6",
            "17",
            "5"
    };



    Integer[] imageArray = {R.drawable.netflix,
            R.drawable.soundcloud,
            R.drawable.itunes,
            R.drawable.applelogo,
            R.drawable.youtube,
            R.drawable.spotify,
            R.drawable.shahid,
            R.drawable.anghami,
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        CustomeListAdapter whatever = new CustomeListAdapter(this, nameArray, infoArray, imageArray);
        listView = (ListView) findViewById(R.id.listViewID);
        listView.setAdapter(whatever);

    }

    public void moveToprofile(View v) {
        Intent i = new Intent(this, profile.class);
        startActivity(i);
        finish();
    }





}
