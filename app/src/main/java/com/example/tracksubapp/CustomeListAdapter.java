package com.example.tracksubapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CustomeListAdapter extends ArrayAdapter {
    //to reference the Activity
    private final Activity context;

    //to store the animal images
    private final Integer[] imageIDarray;

    //to store the list of countries
    private final String[] nameArray;

    //to store the list of countries
    private final String[] infoArray;
    private String currentApp;
    private String currentPrice;

    public CustomeListAdapter(Activity context, String[] nameArrayParam, String[] infoArrayParam, Integer[] imageIDArrayParam){

        super(context, R.layout.listview_row , nameArrayParam);
        this.context=context;
        this.imageIDarray = imageIDArrayParam;
        this.nameArray = nameArrayParam;
        this.infoArray = infoArrayParam;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.listview_row, null,true);

        //this code gets references to objects in the listview_row.xml file
        TextView nameTextField = (TextView) rowView.findViewById(R.id.appName);
        TextView infoTextField = (TextView) rowView.findViewById(R.id.price);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.imageView1ID);
        ImageButton btnAdd = (ImageButton) rowView.findViewById(R.id.btnAdd);

        //this code sets the values of the objects to values from the arrays
        nameTextField.setText(nameArray[position]);
        infoTextField.setText("$"+infoArray[position]+" /month");
        imageView.setImageResource(imageIDarray[position]);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeNewSub(nameArray[position] , infoArray[position]);
            }
        });
        return rowView;

    };
    private void writeNewSub( String name, String price) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser loginUser = mAuth.getCurrentUser();
        String userID = loginUser.getUid();
        int day , month , year;
        Subs post = new Subs(name, price);
        Calendar calender = Calendar.getInstance();
        year = calender.get(Calendar.YEAR);
        month = calender.get(Calendar.MONTH);
        day = calender.get(Calendar.DAY_OF_MONTH);
        DateCalc startD = new DateCalc( day , month+1 , year );
        DateCalc endD = startD.calcEndDate( "monthly");
        post.startDate = startD.toString();
        post.endDate = endD.toString();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("/Users/" + userID + "/subsecriptions/" + post.name);
        myRef.child("name").setValue(post.name);
        myRef.child("price").setValue(post.price);
        myRef.child("startDate").setValue(post.startDate);
        myRef.child("endDate").setValue(post.endDate);
        Toast t = Toast.makeText(context,"Subsecription Add", Toast.LENGTH_SHORT);
        t.show();

    }
}
