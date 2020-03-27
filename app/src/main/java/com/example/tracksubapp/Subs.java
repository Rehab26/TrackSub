package com.example.tracksubapp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Subs {
    public int subCount = 0;
    public String subName;
    public int subID = 0;
    public String subImg;
    public String price;
    public Map<String, String> allSubs = new HashMap<>();
    public String uid;

    public Subs(String uid, String subName , String price) {
        this.subID+=1;
        this.subName = subName;
        this.subImg = subName+".png";
        this.price = price;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("subID", this.subID);
        result.put("name", this.subName);
        result.put("price", this.price);
        return result;
    }


    public String getSubName(){
        return this.subName;
    }
    public  String getSubImg(){
        return this.subImg;
    }
    public int getSubId(){
        return this.subID;
    }
    public void addToFirebase(String uid){

    }
}
