package com.example.tracksubapp;

import java.util.HashMap;
import java.util.Map;

public class Users {
    public String name;
    public String email;
    public String uid;
    Map<String , Object> subsecriptions = new HashMap<>();
    Map <String , Object> startDate = new HashMap<>();
    Map <String , Object> endtDate = new HashMap<>();
    int subs = subsecriptions.size();
    public Map<String, Object> getSubsecriptions() {
        return subsecriptions;
    }

    public Map<String, Object> getStartDate() {
        return startDate;
    }

    public Map<String, Object> getEndtDate() {
        return endtDate;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getUid() {
        return uid;
    }

    public int getSubs() {
        return subs;
    }
}
