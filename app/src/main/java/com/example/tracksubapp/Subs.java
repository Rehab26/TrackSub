package com.example.tracksubapp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Subs extends DateCalc{
    public String name;
    //public String subImg;
    public String price;
    public String startDate;
    public String endDate;
    //public Map<String, String> allSubs = new HashMap<>();
    //public String uid;
    //public Map<String, Integer> startDate = new HashMap<>();
    //public Map<String, Integer> endDate = new HashMap<>();
    //public DateCalc endDate;
    //public DateCalc startDate;
    public Subs() {

    }

    @Override
    public DateCalc getEndDate(DateCalc date) {
        return super.getEndDate(date);
    }

    public Subs(String subName, String price) {
        this.name = subName;
        this.price = price;

    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", this.name);
        result.put("price", this.price);
        return result;
    }


    public String getSubName() {
        return this.name;
    }

}
