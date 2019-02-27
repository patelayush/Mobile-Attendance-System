package com.example.jarvis.mas;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by jarvis on 19/4/16.
 */
public class Statistics extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener{

    private int sid,cid,csid,percent,total,presentcount;
    String stuname;
    ArrayList<String> datelist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics);

        Intent intent=getIntent();
        sid=intent.getIntExtra("sid",0);
        cid=intent.getIntExtra("cid", 0);

        DatabaseManager databaseManager=new DatabaseManager(getApplicationContext());
        stuname=databaseManager.getstuname(sid, cid);
        csid=databaseManager.showcsid(sid, cid);
        percent=databaseManager.getattendancepercentage(csid);
        total=databaseManager.gettotalattendance(csid);
        presentcount=databaseManager.getpresentcount(csid);


        Button btnok=(Button) findViewById(R.id.showdates);
        TextView stuinfo=(TextView) findViewById(R.id.total);
        TextView attendance=(TextView) findViewById(R.id.attendance);
        TextView percenttv=(TextView) findViewById(R.id.percent);

        stuinfo.setText("Data for "+stuname+":");
        attendance.setText("Total Present : " + presentcount + " \n\nTotal Classes : " + total);
        percenttv.setText(String.valueOf(percent));

        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseManager databaseManager1=new DatabaseManager(getApplicationContext());
                datelist=databaseManager1.getdateslist(csid);
                System.out.println(datelist);

                final TextView textView;
                final ListView listView;

                final Dialog dialog = new Dialog(Statistics.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.datesdialog);

                listView=(ListView) dialog.findViewById(R.id.present_dates);
                Button btnok=(Button) dialog.findViewById(R.id.positiveButton);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                        R.layout.rowlayout, R.id.tlv1,datelist);

                textView=(TextView) dialog.findViewById(R.id.datestv);
                textView.setText("Dates when "+stuname+" was present");
                listView.setAdapter(adapter);
                dialog.show();

                btnok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });

            }
        });


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }
    Intent navintent;
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        int id = item.getItemId();

        if (id == R.id.nav_attendance) {
            navintent=new Intent(getApplicationContext(),Navigation_activity.class);
            startActivity(navintent);
        } else if (id==R.id.nav_excel)
        {
            navintent=new Intent(getApplicationContext(),Excelclassroom.class);
            startActivity(navintent);
            finish();
        }
        else if (id == R.id.past_attendance) {
            navintent=new Intent(getApplicationContext(),Pastclassrooms.class);
            startActivity(navintent);


        } else if (id == R.id.nav_stats) {
            drawer.closeDrawer(GravityCompat.START);

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

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
