package com.example.jarvis.mas;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by jarvis on 15/4/16.
 */
public class Studentsactivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    String dateandtime;
    ListView listView;
    ArrayList<String> selectedlist,studentnames,selectedstudents,items;

    FloatingActionButton fab;
    Intent intent;
    Intent dintent;
    int cid,myear,mmonth,mhour,mminute,mday,mseconds;
    CheckBox checkBox;
    int selectedmonth,selectedyear,selectedday,selectedhour,selectedmin,selectedsec;
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_activity);
        dintent = new Intent(getApplicationContext(), Displayselectedstudents.class);
        selectedlist = new ArrayList<String>();
        studentnames = new ArrayList<String>();
        selectedstudents = new ArrayList<String>();
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        intent = getIntent();
        cid = intent.getIntExtra("cid", 0);
        dintent.putExtra("cid", cid);
        System.out.println(cid);
        getstudentlist(cid);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setImageResource(R.drawable.arrows);

        TextView textView=(TextView) findViewById(R.id.infotext);
        TextView textView1=(TextView) findViewById(R.id.text);
        textView1.setVisibility(View.GONE);
        listView = (ListView) findViewById(R.id.Listview);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        dateandtime="";

        final AttendanceAdapter adapter = new AttendanceAdapter(getApplicationContext(),
                R.layout.studentrowlayout3,cid);
        listView.setAdapter(adapter);
        if(selectedlist.isEmpty()){
            textView.setVisibility(View.VISIBLE);
            textView.setText(R.string.stu_info);
        }
        else{
            textView.setVisibility(View.GONE);
        }
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Vibrator vibrator = (Vibrator) getApplicationContext().
                        getSystemService(getApplicationContext().VIBRATOR_SERVICE);
                vibrator.vibrate(100);
                CheckBox checkbox = (CheckBox) view.findViewById(R.id.checkBox1);
                if (checkbox.isChecked()) {
                    adapter._selected[position] = false;
                    adapter.students.get(position).setSelected(false);
                    checkbox.setChecked(false);
                } else {
                    adapter._selected[position] = true;
                    adapter.students.get(position).setSelected(true);
                    checkbox.setChecked(true);
                }
            }
        });



        final Calendar calendar = Calendar.getInstance();
        selectedyear = calendar.get(Calendar.YEAR);
        selectedmonth = calendar.get(Calendar.MONTH)+1;
        selectedday = calendar.get(Calendar.DAY_OF_MONTH);
        selectedhour = calendar.get(Calendar.HOUR);
        selectedmin = calendar.get(Calendar.MINUTE);
        selectedsec = calendar.get(Calendar.SECOND);

        dateandtime();


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateandtime();
               // Toast.makeText(getApplicationContext(), dateandtime, Toast.LENGTH_SHORT).show();


                selectedstudents.clear();
                int count=0;
                for(int i=0;i<adapter.students.size();i++)
                {
                    if(adapter.students.get(i).isSelected())
                    {
                        selectedstudents.add(adapter.students.get(i).getSname());
                        count++;
                    }
                }
                dintent.putStringArrayListExtra("selectedstudentslist",selectedstudents );
                dintent.putExtra("count", count);

                dintent.putExtra("dateandtime", dateandtime);
                Intent intent = dintent.putStringArrayListExtra("studentnames", selectedlist);
                startActivity(dintent);

            }
        });
    }
    public void dateandtime(){
        Date date;
        String expectedPattern="MM/dd/yyyy hh:mm:ss";
        SimpleDateFormat formatter0=new SimpleDateFormat(expectedPattern);

        String userInput=(selectedmonth)+"/"+selectedday+"/"+selectedyear+" "+
                selectedhour+":"+selectedmin+":"+mseconds;
        date=new Date();
        try {
            date=formatter0.parse(userInput);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date todate;
        todate=date;

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        dateandtime=formatter.format(todate);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if(id == R.id.datetime) {
            setdate();
            settime();
        }
        if(id == R.id.pastattendance){
            Intent pastintent=new Intent(this,Pastlist.class);
            System.out.println(cid);
            pastintent.putExtra("cid",cid);
            startActivity(pastintent);

        }

        return super.onOptionsItemSelected(item);
    }

    private void setdate() {


        DatePickerDialog datePickerDialog=new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        selectedmonth=monthOfYear+1;
                        selectedyear=year;
                        selectedday=dayOfMonth;
                    }

                },selectedyear,selectedmonth -1,selectedday);

        datePickerDialog.show();


    }

    private void settime() {

        TimePickerDialog timePickerDialog=new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                selectedhour=hourOfDay;
                selectedmin = minute;

            }
        },mhour,mminute,false);

        timePickerDialog.show();



    }
    public void getstudentlist(int classroomid){
        DatabaseManager databaseManager=new DatabaseManager(getApplicationContext());
        selectedlist=databaseManager.showStudentslist(classroomid);

    }
    Intent navintent;
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_attendance) {
          //  Toast.makeText(getApplicationContext(),"Clicked",Toast.LENGTH_SHORT).show();
            navintent=new Intent(getApplicationContext(),Navigation_activity.class);
            startActivity(navintent);
        } else if (id == R.id.nav_stats) {
            navintent=new Intent(getApplicationContext(),Statsclassroom.class);
            startActivity(navintent);

        } else if (id == R.id.past_attendance) {
            navintent=new Intent(getApplicationContext(),Pastclassrooms.class);
            startActivity(navintent);
        } else if (id == R.id.nav_edit) {
            navintent=new Intent(getApplicationContext(),Editclassroom.class);
            startActivity(navintent);
        } else if (id == R.id.nav_delete) {
            navintent=new Intent(getApplicationContext(),Delete.class);
            startActivity(navintent);

        } else if (id == R.id.nav_about) {
            navintent=new Intent(getApplicationContext(),About.class);
            startActivity(navintent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
