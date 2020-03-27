package com.example.tracksubapp;

public class DateCalc {
    private int day;
    private int month;
    private int year;

    DateCalc(int day , int month , int year){
        this.day = day;
        this.month = month +1;
        this.year = year;
    }

}
