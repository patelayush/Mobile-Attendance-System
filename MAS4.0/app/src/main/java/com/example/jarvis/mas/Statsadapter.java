package com.example.jarvis.mas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by jarvis on 15/4/16.
 */
public class Statsadapter extends ArrayAdapter<String> {
    ArrayList<String> list=new ArrayList<>();
    Context context1;
    int subject;

    ArrayList<Student> students=new ArrayList<>();
    public Statsadapter(Context context, ArrayList<String> list) {

        super(context, R.layout.rowlayout, list);
        this.list=list;
        this.context1=getContext();

    }
    int cid=0;


    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View view = layoutInflater.inflate(R.layout.rowlayout3, parent, false);
        cid=Statsstudents.getInstance().subject;

        TextView textView1 = (TextView) view.findViewById(R.id.tlv1);

        TextView textView2 = (TextView) view.findViewById(R.id.tlv2);

        DatabaseManager databaseManager=new DatabaseManager(getContext());
        students=databaseManager.getstudent(cid);

        textView1.setText(String.valueOf(students.get(position).getSroll())+" )");

        textView2.setText(students.get(position).getSname());

        return view;
    }

    @Override
    public String getItem(int position) {
        return list.get(position);
    }
}

