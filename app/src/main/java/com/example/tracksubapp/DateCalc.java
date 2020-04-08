package com.example.tracksubapp;

import com.google.firebase.database.Exclude;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.time.temporal.ChronoUnit;

public class DateCalc {
    private int day;
    private int month;
    private int year;
    public DateCalc endDate;
    public DateCalc startDate;
    public String strDate;
    //public Map<String, Object> startDate = new HashMap<>();
    //public Map<String, String> dateEnd = new HashMap<>();

    public DateCalc(){

    }
    public DateCalc(int day , int month , int year){
        this.day = day;
        this.month = month ;
        this.year = year;
    }
    public DateCalc getStartDate(DateCalc date){
        return startDate;
    }
    public DateCalc getEndDate(DateCalc date){
        return endDate;
    }
    public DateCalc calcEndDate(String type ){
        int month = this.month;
        int year = this.year;
        int day = this.day;
        if (type=="monthly"){
            endDate=new DateCalc(day,month+1,year);
        }
        if(type == "3 months"){
            endDate = new DateCalc( day , month+3 , year);
        }
        else if(type == "yearly"){
            endDate = new DateCalc( day , month , year+1);
        }
        return endDate;
    }
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("day", this.day);
        result.put("month", this.month);
        result.put("year", this.year);
        //startDate = result;
        return result;
    }
    @Override
    public String toString(){
        String monthStr = Integer.toString(this.month);
        String dayStr = Integer.toString(this.day);
        if(monthStr.length() == 1) {
            monthStr = 0+monthStr;
        } else monthStr = monthStr;
        if(dayStr.length() == 1) {
            dayStr = 0+dayStr;
        } else dayStr = dayStr;
        strDate = dayStr+"/"+monthStr+"/"+this.year;
        return strDate;
    }


}
