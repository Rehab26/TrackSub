package com.example.tracksubapp;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ListAdabter extends ArrayAdapter {
    private Activity myContext;
    private Subs[] subs;
    String nameArray[];
    String infoArray[];
    String endDates[];
    Integer imageIDArray[];
    long days[];
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser loginUser = mAuth.getCurrentUser();
    String userID = loginUser.getUid();
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Users/"+userID);

    public ListAdabter(Activity context, String[] subsecription , String[] prices , String[] endDates , long[] days) {
        super(context ,  R.layout.list_view_sub  , subsecription);
        this.myContext = context;
        nameArray = subsecription;
        infoArray = prices;
        this.endDates = endDates;
        this.days = days;

    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = this.myContext.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_view_sub, null, false);
        TextView nameTextField = (TextView) rowView.findViewById(R.id.appName);
        TextView infoTextField = (TextView) rowView.findViewById(R.id.left);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.imageView1ID);
        TextView end = (TextView) rowView.findViewById(R.id.end);
        TextView cancelBtn = (TextView) rowView.findViewById(R.id.cancel);
        if(nameArray!=null) {
            cancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myRef.child("subsecriptions").child(nameArray[position]).removeValue();
                    Toast.makeText(myContext, "subsecriptions Deleted", Toast.LENGTH_SHORT).show();
                }
            });
            Resources resources = myContext.getResources();
            String imageName = nameArray[position];
            imageName = imageName.toLowerCase().split(" ")[0];
            int resourceId = resources.getIdentifier(imageName, "drawable", myContext.getPackageName());
            //this code sets the values of the objects to values from the arrays
            if (resourceId == 0) {
                resourceId = R.drawable.logo;
            }
            nameTextField.setText(nameArray[position].split(" ")[0]);
            if(days[position] < 0){
                infoTextField.setText("    End!    ");
                infoTextField.setBackgroundColor(Color.RED);
                infoTextField.setTextColor(Color.WHITE);
            }
            if(days[position] == 0){
                infoTextField.setText("  End today  ");
                infoTextField.setBackgroundColor(Color.YELLOW);
                infoTextField.setTextColor(Color.DKGRAY);
            }
            else if( days[position] == 1) {
                infoTextField.setText("  one day left  ");
                infoTextField.setBackgroundColor(Color.GREEN);
                infoTextField.setTextColor(Color.WHITE);
            }
            else if(days[position] > 1){
                infoTextField.setText(days[position]+" days left");
            }
            end.setText("End at "+endDates[position]);
            imageView.setImageResource(resourceId);

            return rowView;
        }
        else {
            nameTextField.setText("You don't have any subsecription yet");
            return rowView;
        }

    }
}
