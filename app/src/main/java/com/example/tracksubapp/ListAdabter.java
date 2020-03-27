package com.example.tracksubapp;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListAdabter extends BaseAdapter {
    private  Context myContext;
    private Subs[] subs;
    @Override
    public int getCount() {
        return this.subs.length;
    }
    public ListAdabter(Context context , Subs[] sub){
        this.myContext = context;
        this.subs = sub;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView dummyTextView = new TextView(myContext);
        dummyTextView.setText(String.valueOf(position));
        return dummyTextView;
    }
}
