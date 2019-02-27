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
public class ListviewAdapter extends ArrayAdapter<String> {
    public String depname;
    ArrayList<String> list;
    Context context;
    ArrayList<Classroom> classroom=new ArrayList<>();
    public ListviewAdapter(Context context, ArrayList<String> list) {

        super(context, R.layout.rowlayout, list);
        this.list=list;
        this.context=getContext();

    }
    int cid=0;

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View view = layoutInflater.inflate(R.layout.rowlayout2, parent, false);

        TextView textView1 = (TextView) view.findViewById(R.id.tlv1);

        String subject = getItem(position);

        TextView textView2 = (TextView) view.findViewById(R.id.tlv2);
        TextView textView3 = (TextView) view.findViewById(R.id.tlv3);
        TextView textView4 = (TextView) view.findViewById(R.id.tlv4);
        //TextView textView5 = (TextView) view.findViewById(R.id.tlv5);

        DatabaseManager databaseManager=new DatabaseManager(getContext());
        classroom=databaseManager.getclassroom(getItem(position));
        String depname = classroom.get(position).getdeptname();
        int sem=classroom.get(position).getSem();
        String div=classroom.get(position).getdivname();

        textView1.setText(depname);
        textView2.setText("-" + " "+div);
        textView3.setText(subject);
        textView4.setText(String.valueOf(sem) + "th  Semester");

        return view;
    }

    @Override
    public String getItem(int position) {
        return list.get(position);
    }
}

