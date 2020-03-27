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

import java.util.HashMap;
import java.util.Map;

public class profile extends AppCompatActivity {
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser loginUser = mAuth.getCurrentUser();
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference mDatabase;
    String userID = loginUser.getUid();
    private ImageButton addBtn;
    private TextView name;
    private TextView sub;
    private ListView listView;
    DatabaseReference myRef = database.getReference("Users/"+userID);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        listView = (ListView)findViewById(R.id.subs_list_view);
        addBtn = (ImageButton)findViewById(R.id.addBtn);
        //Subs sub = new Subs();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                System.out.println(loginUser);
                System.out.println(user);
                display(user);
                //int subCount = user.sub;
                //ListAdabter listAdabter = new ListAdabter(this, sub);
                //listView.setAdapter(listAdabter);
                addBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //writeNewSub(userID ,2 ,"netflix" , "28$");
                        moveToTest();
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }
    //TEST PURPOSE
    public void moveToTest(){
        Intent i = new Intent(this , test.class);
        startActivity(i);
    }
    //END TO-DO <<REDO IT>>
    private void writeNewSub(String userId, int id, String name, String price) {
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        //ID = data.allItems[type][data.allItems[type].length - 1].id + 1;
        mDatabase = FirebaseDatabase.getInstance().getReference("Users/"+userId+"/subsecriptions/");
        Subs post = new Subs(userId ,name, price);
        Map<String, Object> postValues = post.toMap();
        String subID = Integer.toString(post.subID);
        Map<String, Object> childUpdates = new HashMap<>();
        //childUpdates.put("/Users/"+userId+"/subsecriptions/" + key, postValues);
        childUpdates.put(post.subName , postValues);


        mDatabase.updateChildren(childUpdates);
    }

    public void display(User user) {
     name = (TextView) findViewById(R.id.name);
     sub = (TextView) findViewById(R.id.sub);
        name.setText(user.getName());
        if(user.subs == 0 || user.subs == 1) {
            sub.setText(user.getSubs() + " subsecription");
        }
        else {
            sub.setText(user.getSubs()+" subsecriptions");
        }
    }
}
