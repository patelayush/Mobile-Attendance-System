package com.example.jarvis.mas;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Navigation_activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ArrayList<Integer> cidlist;
    Intent intent;
    static Classroom classroom;
    public ArrayList<String> clist = new ArrayList<String>();
    ListView listView;
    static Navigation_activity navigation_activity;
    public int cid;
    int[] csid;
    int count=0;
    int tcid = 0;
    TextView infoview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
         DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        infoview=(TextView) findViewById(R.id.infotext);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigation_activity = this;
        csid=new int[2];

        cidlist=new ArrayList<>();

        listView = (ListView) findViewById(R.id.Listview);
        getallcsid();
        getalldates();

        setListview();
        final Intent detailsintent=new Intent(getApplicationContext(),Studentsactivity.class);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DatabaseManager databaseManager = new DatabaseManager(getApplicationContext());
                cid = databaseManager.getcid(String.valueOf(parent.getItemAtPosition(position)));
                detailsintent.putExtra("cid", cid);


                /*Toast.makeText(getApplicationContext(), "You selected " +
                                String.valueOf(parent.getItemAtPosition(position)),
                        Toast.LENGTH_LONG).show();
*/

                startActivity(detailsintent);


            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(), ClassroomDetails.class);
                startActivity(intent);
            }
        });
        fab.setImageResource(R.drawable.cross);
        setListview();


    }


    public static Navigation_activity getInstance() {
        return navigation_activity;
    }


    public void setListview() {
        DatabaseManager databaseManager = new DatabaseManager(getApplicationContext());
        clist = databaseManager.showClassroomlist();
        if(clist.isEmpty())
        {
            infoview.setVisibility(View.VISIBLE);
        }
        else
        {
            infoview.setVisibility(View.GONE);
        }
        ListviewAdapter adapter=new ListviewAdapter(getApplicationContext(), clist);
        listView.setAdapter(adapter);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    Intent navintent;
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        int id = item.getItemId();

        if (id == R.id.nav_attendance) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (id == R.id.nav_stats) {
            navintent=new Intent(getApplicationContext(),Statsclassroom.class);
            startActivity(navintent);


        }else if (id == R.id.past_attendance) {
            navintent=new Intent(getApplicationContext(),Pastclassrooms.class);
            startActivity(navintent);

        }
        else if (id==R.id.nav_excel)
        {
            navintent=new Intent(getApplicationContext(),Excelclassroom.class);
            startActivity(navintent);

        }

        else if (id == R.id.nav_edit) {
            navintent=new Intent(getApplicationContext(),Editclassroom.class);
            startActivity(navintent);
        } else if (id == R.id.nav_delete) {
            navintent=new Intent(getApplicationContext(),Delete.class);
            startActivity(navintent);

        }
        else if (id == R.id.nav_about) {
            navintent=new Intent(getApplicationContext(),About.class);
            startActivity(navintent);

        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void getallcsid()
    {
        ArrayList<Integer> allcsid=new ArrayList<>();
        DatabaseManager databaseManager=new DatabaseManager(getApplicationContext());
        allcsid=databaseManager.getallcsid();
        for(int i=0;i<allcsid.size();i++)
        {
            System.out.println("csid "+i+" : "+allcsid.get(i));
        }
    }
    public void getalldates()
    {
        ArrayList<String> alldates = new ArrayList<>();
        DatabaseManager databaseManager=new DatabaseManager(getApplicationContext());
        alldates=databaseManager.getalldates();
        for(int i=0;i<alldates.size();i++)
        {
            System.out.println("csid "+i+" : "+alldates.get(i));
        }
    }


}
