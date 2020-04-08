package com.example.tracksubapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.DAYS;
import static java.util.concurrent.TimeUnit.HOURS;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class profile extends AppCompatActivity implements EditDialog.EditDialogListener {
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser loginUser = mAuth.getCurrentUser();
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference mDatabase;
    private DatabaseReference dDatabase;
    String userID = loginUser.getUid();
    private ImageButton addBtn;
    private TextView name;
    private TextView sub;
    public int counts;
    private List<String> names;
    private String appNames[];
    public ArrayList <Subs> subsecription;
    public String prices[];
    String endDates[];
    String startDates[];
    long days[];
    String text;
    DateCalc date;
    private ListView listView;
    public ImageButton editBtn;
    // private ListView listView;
    public void openDialog() {
        EditDialog editDialog = new EditDialog();
        editDialog.show(getSupportFragmentManager(), "Edit user name");
    }
    public void moveTotest(View v) {
        Intent i = new Intent(this, test.class);
        startActivity(i);
    }
    @Override
    public void applyTexts(String username) {
        if(username.length() > 0){
            myRef.child("name").setValue(username);
        } return;
    }
    DatabaseReference myRef = database.getReference("Users/"+userID);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        addBtn = (ImageButton) findViewById(R.id.addBtn);
        editBtn = (ImageButton) findViewById(R.id.editBtn);
        name = (TextView) findViewById(R.id.name);
        sub = (TextView) findViewById(R.id.sub);
        init();


        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                moveTosub();
            }
        });
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
        displayCounts(counts);
        dataChanged();


    }
    public void dataChanged(){
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Users user = dataSnapshot.getValue(Users.class);
                display(user);
                editBtn.setVisibility(View.VISIBLE);
                addBtn.setVisibility(View.VISIBLE);
                names = new ArrayList<>();
                subsecription = new ArrayList<>();
                if (dataSnapshot.hasChild("subsecriptions")) {
                    counts = (int)dataSnapshot.child("subsecriptions").getChildrenCount();
                    prices = new String[counts];
                    appNames = new String[counts];
                    endDates = new String[counts];
                    startDates = new String[counts];
                    days = new long[counts];
                    displayCounts(counts);
                    dataSnapshot = dataSnapshot.child("subsecriptions");
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        names.add(child.getKey());
                    }

                    for(int i = 0 ; i<counts ; i++){
                        DataSnapshot child = dataSnapshot.child(names.get(i));
                        subsecription.add(child.getValue(Subs.class));
                        prices[i] = subsecription.get(i).price;
                        endDates[i] = subsecription.get(i).endDate;
                        System.out.println(endDates[i]);
                        appNames[i] = names.get(i);
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        try {
                            if(endDates[i]!=null) {
                                Date now = new Date();
                                Date enddate = sdf.parse(endDates[i]);
                                long diff = enddate.getTime() - now.getTime();
                                long day = TimeUnit.DAYS.convert(diff, MILLISECONDS);
                                days[i] = day;
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    if(appNames!=null) {

                        ListAdabter uy = new ListAdabter(profile.this, appNames, prices, endDates, days);
                        listView = (ListView) findViewById(R.id.subs_list_view);
                        listView.setAdapter(uy);


                    }
                } else {
                    init();
                    displayCounts(counts);
                    call();
                }





            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }

        });
    }

    public void call(){
        ListAdabter uy = new ListAdabter(this , appNames , prices , endDates , days);
        listView = (ListView) findViewById(R.id.subs_list_view);
        listView.setAdapter(uy);
    }


    public void display(Users user) {
        name.setText(user.getName());
    }
    public void displayCounts(int count){
        if(names!=null) {
            if (count == 0 || count == 1) {
                sub.setText(count + " subsecription");
            } else {
                sub.setText(count + " subsecriptions");
            }
        } return;
    }
    public void init(){
        if(names!=null){
            names.clear();
            counts=0;
            endDates = new String[counts];
            appNames = new String[counts];
            subsecription.clear();
            days = new long[counts];
            prices = new String[counts];
        }
        return;
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        init();
        displayCounts(counts);
        dataChanged();
    }

    @Override
    protected void onStart() {
        super.onStart();
        init();
        displayCounts(counts);
        dataChanged();
    }
    @Override
    protected void onResume() {
        init();
        dataChanged();
        super.onResume();
        displayCounts(counts);
    }
    @Override
    protected void onStop() {
        super.onStop();
        init();
        displayCounts(counts);
        dataChanged();
    }

    public void moveTosub() {
        Intent i = new Intent(this, Sub.class);
        startActivity(i);
    }}
