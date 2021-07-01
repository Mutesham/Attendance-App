package com.dcet.attendance;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by SHAFIQ on 1/24/2017.
 */

public class MyAdapter extends BaseAdapter {

    ArrayList<String> list= new ArrayList<>();
    ArrayList<String> list11= new ArrayList<>();
    Context context;

    LayoutInflater inflater;
    public MyAdapter(Context con,ArrayList<String> list2,ArrayList<String> list22)
    {
        list= list2;
        list11=list22;

        context=con;
        inflater = (LayoutInflater)context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);

    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
       View v1= inflater.inflate(R.layout.custom_layout,null);
        TextView T1= (TextView) v1.findViewById(R.id.StudentName);
        TextView T2= (TextView) v1.findViewById(R.id.Status);
        T1.setText(""+list.get(i).toString());
        T2.setText(""+list11.get(i).toString());
        return v1;
    }
}
