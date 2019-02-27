package com.example.jarvis.mas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by jarvis on 20/4/16.
 */
public class AttendanceAdapter extends BaseAdapter {
    //    final Boolean[] aBoolean=new Boolean[80];
    ArrayList<String> list = new ArrayList<>();
    Context context1;
    int cid;
    int layout;
    int subject;
    Boolean[] _selected;
    final boolean[] aboolean = new boolean[80];
    Student student;

    public ArrayList<Student> students = new ArrayList<>();



    public AttendanceAdapter(Context context, int layout, int cid) {

        super();


        System.out.println("Inside adapter");

        this.cid = cid;
        this.layout = layout;

        this.context1 = context;
        _selected = new Boolean[80];

        DatabaseManager databaseManager = new DatabaseManager(context1);
        students = databaseManager.getstudent(cid);
        for(int i=0;i<80;i++)
        {
            _selected[i]=false;
        }

    }

    private class ViewHolder {
        TextView tv1, tv2;
        CheckBox checkBox;

        ViewHolder(View view1) {
            tv1 = (TextView) view1.findViewById(R.id.tlvroll);
            tv2 = (TextView) view1.findViewById(R.id.tlv2);
            checkBox = (CheckBox) view1.findViewById(R.id.checkBox1);
        }
    }


    @Override
    public int getCount() {
        return students.size();
    }

    @Override
    public String getItem(int position) {
        return students.get(position).getSname();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
       /* DatabaseManager databaseManager = new DatabaseManager(context1);
        students = databaseManager.getstudent(cid);
       */
        System.out.println(students.get(position).getSname());
        final int pos=position;
        View rowview = convertView;
        final ViewHolder holder;

        LayoutInflater inflater = (LayoutInflater)
                context1.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (rowview == null) {
            rowview = inflater.inflate(R.layout.studentrowlayout3, parent,false);
            holder = new ViewHolder(rowview);
            System.out.println("New view created");
            rowview.setTag(holder);
            holder.checkBox.setChecked(_selected[position]);
            students.get(position).setSelected(_selected[position]);
        } else {
            holder = (ViewHolder) rowview.getTag();
            System.out.println("Recycled view created");
            if (_selected[position])
                holder.checkBox.setChecked(true);
            else
                holder.checkBox.setChecked(false);
        }


        holder.tv1.setText(String.valueOf(students.get(position).getSroll()) + " )");

        holder.tv2.setText(students.get(position).getSname());

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.checkBox.isChecked()) {
                    _selected[position] = true;
                    students.get(position).setSelected(true);
                } else {
                    _selected[position] = false;
                    students.get(position).setSelected(false);
                }
            }
        });
        return rowview;

    }

    /*
    @Override
    public View getView(final int position, View convertView, ViewGroup parentView) {
        DatabaseManager databaseManager = new DatabaseManager(context1);
        students = databaseManager.getstudent(cid);
        System.out.println("hi");

        System.out.println(students.get(position).getSname());

        View rowview = convertView;
        final ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater)
                context1.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (rowview == null) {
            rowview = inflater.inflate(R.layout.studentrowlayout3, parentView,false);
            holder = new ViewHolder(rowview);
            System.out.println("New view created");
            rowview.setTag(holder);
        } else {
            holder = (ViewHolder) rowview.getTag();
            System.out.println("Recycled view created");
            if (_selected[position])
                holder.checkBox.setChecked(true);
            else
                holder.checkBox.setChecked(false);
        }

        Student student=students.get(position);
        holder.tv1.setText(String.valueOf(student.getSroll()) + " )");
        System.out.println(String.valueOf(students.get(position).getSroll()));
        holder.tv2.setText(student.getSname());
        System.out.println(students.get(position).getSname());

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.checkBox.isChecked()) {
                    _selected[position] = true;
                } else
                    _selected[position] = false;
            }
        });
        return rowview;
    }
*/


             /*{

                @Override
                public void onClick(View v) {

                    if(aboolean[pos]=true) {

                        viewHolder.cb.setChecked(false);
                        aboolean[pos]=false;
                    }
                    else
                    {

                        viewHolder.cb.setChecked(true);
                        aboolean[pos]=true;
                    }

                }

            });*/



        /*ViewHolder holder = (ViewHolder) view.getTag();
        holder.tv.setText("Text");
        holder.tv1.setText("Text");
        holder.cb.setChecked(_selected[position]); //Geting state of check box from array
        holder.cb.setId(position); // set check box ID(position in adapter)
*/





/*

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View view = layoutInflater.inflate(R.layout.studentrowlayout3, parent, false);
        cid=Navigation_activity.getInstance().cid;

        TextView textView1 = (TextView) view.findViewById(R.id.tlvroll);

        TextView textView2 = (TextView) view.findViewById(R.id.tlv2);

        final CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkBox1);

        DatabaseManager databaseManager=new DatabaseManager(getContext());
        students=databaseManager.getstudent(cid);

        textView1.setText(String.valueOf(students.get(position).getSroll()) + " )");

        textView2.setText(students.get(position).getSname());

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int getposition=(Integer) buttonView.getTag()
            }
        });
        return view;
    }
*/



}


