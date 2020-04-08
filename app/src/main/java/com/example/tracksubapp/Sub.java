package com.example.tracksubapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Sub extends AppCompatActivity {
    private static final String TAG = "Sub";
    private DatabaseReference mDatabase;
    private DatabaseReference dDatabase;
    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    int year;
    int day;
    int month;
    DateCalc endDate;
    String type;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser loginUser = mAuth.getCurrentUser();
    String userID = loginUser.getUid();
    EditText name;
    private String appname;
    EditText price;
    private String subprice;
    ImageButton subscribe;
    private ImageButton backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);
        name=(EditText)findViewById(R.id.name);
        price=(EditText)findViewById(R.id.price);
        subscribe=(ImageButton)findViewById(R.id.subscribe);
        backBtn = (ImageButton)findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToProfile();
            }
        });
        subscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewSub();
                moveToProfile();

            }
        });
        mDisplayDate = (Button) findViewById(R.id.button1);
        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        Sub.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year1, int month1, int day1) {
                month = month1 + 1;
                year=year1;
                day=day1;
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + month1 + "/" + day1 + "/" + year1);

                String date = month + "/" + day + "/" + year;
                mDisplayDate.setText(date);
            }
        };

    }



    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("day", this.day);
        result.put("month", this.month);
        result.put("year", this.year);
        return result;
    }


    public void fun(View V) {
        switch (V.getId()) {

            case R.id.threemonth:
                type = "3 months";
                break;

            case R.id.monthly:
                type = "monthly";
                break;

            case R.id.annual:
                type = "yearly";
                break;
        }

    }
    public void moveToProfile() {
        Intent i = new Intent(Sub.this, profile.class);
        startActivity(i);
        finish();
    }
    public void AddNewSub(){
        appname=name.getText().toString();
        subprice=price.getText().toString();
        mDatabase = FirebaseDatabase.getInstance().getReference("Users/" + userID + "/subsecriptions/");
        Subs post = new Subs( appname, subprice);
        DateCalc startD = new DateCalc(day, month, year);
        DateCalc endD = startD.calcEndDate(type);
        post.startDate = startD.toString();
        post.endDate = endD.toString();
        Map<String, Object> postValues = post.toMap();
        //Map<String, Object> postStartDates = startD.toMap();
        //Map<String, Object> postEndDates = endD.toMap();
        //to store from post into child
        //Map<String, Object> childUpdates = new HashMap<>();
        //Map<String, Object> childUpdates2 = new HashMap<>();
        //childUpdates.put(appname, postValues);
        //mDatabase.updateChildren(childUpdates);
        dDatabase = FirebaseDatabase.getInstance().getReference("/Users/" + userID + "/subsecriptions/" + post.name);
        //childUpdates2.put("/startDate", postStartDates);
        //childUpdates2.put("/endDate/", postEndDates);
        //TESTING
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("/Users/" + userID + "/subsecriptions/" + post.name);
        myRef.child("name").setValue(post.name);
        myRef.child("price").setValue(post.price);
        myRef.child("startDate").setValue(post.startDate);
        myRef.child("endDate").setValue(post.endDate);
        //

        //dDatabase.updateChildren(childUpdates2);
        Toast t = Toast.makeText(getApplicationContext(),"Subsecription Add",Toast.LENGTH_LONG);
        t.show();


    }
}


 /*   public DateCalc calcEndDate(String type) {
        int month = this.month;
        int year = this.year;
        int day = this.day;
        if (type == "weekly") {
            endDate = new DateCalc(day + 7, month, year);
        }
        if (type == "monthly") {
            endDate = new DateCalc(day, month, year);
        } else if (type == "yearly") {
            endDate = new DateCalc(day, month, year + 1);
        }
    return endDate;}*/