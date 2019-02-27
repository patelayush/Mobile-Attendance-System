package com.example.jarvis.mas;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Displays the presetn students after taking the attendance.
 * Created by jarvis on 17/4/16.
 */
public class Displayselectedstudents extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener{

    FloatingActionButton fab;
    ArrayList<String> selectedstudents;
    ArrayList<Integer> sidlist;
    Intent intent,closeIntent;
    int cid = 0;
    int csid = 0;
    String dateandtime;
    TextView textView;
    ArrayList<String> studentnames;
    Attendance[] attendance;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diplay_present_students);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        intent = getIntent();
        sidlist = new ArrayList<Integer>();
        selectedstudents = new ArrayList<String>();
        studentnames = new ArrayList<String>();
        int count = intent.getIntExtra("count", 0);
        cid = intent.getIntExtra("cid", 0);
        dateandtime = intent.getExtras().getString("dateandtime");
        selectedstudents = intent.getStringArrayListExtra("selectedstudentslist");
        studentnames = intent.getStringArrayListExtra("studentnames");
        attendance = new Attendance[studentnames.size()];

        ListView list = (ListView) findViewById(R.id.editlv);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                R.layout.rowlayout, R.id.tlv1, selectedstudents);
        list.setAdapter(adapter);

        textView = (TextView) findViewById(R.id.total);
        textView.setText("Total Present are: " + count);


        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setImageResource(R.drawable.sheet);


        new SetAttendanceobjects().execute();
        System.out.println(studentnames.size());
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                System.out.println(selectedstudents.size());
                System.out.println(selectedstudents.size());
                System.out.println(selectedstudents.size());

                DatabaseManager databaseManager = new DatabaseManager(getApplicationContext());
                System.out.println(cid);
                sidlist = databaseManager.getAllSids(cid);
                new Saveattendance().executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
                System.out.println(sidlist.size());

               // Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
                closeIntent=new Intent(getApplicationContext(),Navigation_activity.class);
                startActivity(closeIntent);
            }


        });
    }

    public class SetAttendanceobjects extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {

            DatabaseManager databaseManager = new DatabaseManager(getApplicationContext());
            String name;
            sidlist = databaseManager.getAllSids(cid);

            for (int i = 0; i < studentnames.size(); i++) {
                attendance[i] = new Attendance();
            }
            for (int j = 0; j < studentnames.size(); j++) {
                name = studentnames.get(j);
                if (selectedstudents.contains(name)) {

                    csid = databaseManager.showcsid(sidlist.get(j), cid);
                    attendance[j] = new Attendance(dateandtime, 1, csid);
                    System.out.println("agar naam char toh itne attendance objects " +
                            attendance[j].getDateTime()+attendance[j].isPresent+
                            attendance[j].getCSid()+attendance[j].getaid());

                } else {

                    csid = databaseManager.showcsid(sidlist.get(j), cid);
                    attendance[j] = new Attendance(dateandtime, 0, csid);
                    System.out.println("agar naam nahi toh itne attendance objects " +
                            attendance[j].getDateTime()+attendance[j].isPresent+
                            attendance[j].getCSid()+attendance[j].getaid());
                }
            }
            return null;

        }

    }
    public class Saveattendance extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... params) {
                DatabaseManager databaseManager = new DatabaseManager(getApplicationContext());
                for (int k = 0; k < studentnames.size(); k++) {
                    System.out.println("save attendance " +attendance[k].getDateTime()+attendance[k].isPresent+
                            attendance[k].getCSid()+attendance[k].getaid());
                    databaseManager.saveattendance(attendance[k]);
                }


                return null;
            }

        }
    Intent navintent;
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_attendance) {
            navintent=new Intent(getApplicationContext(),Navigation_activity.class);
            startActivity(navintent);
        } else if (id == R.id.past_attendance) {
            navintent=new Intent(getApplicationContext(),Pastclassrooms.class);
            startActivity(navintent);
        }

        else if (id==R.id.nav_excel)
        {
            navintent=new Intent(getApplicationContext(),Excelclassroom.class);
            startActivity(navintent);
            finish();
        }

        else if (id == R.id.nav_stats) {
            navintent=new Intent(getApplicationContext(),Statsclassroom.class);
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