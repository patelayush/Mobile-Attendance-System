package com.example.jarvis.mas;

import android.content.Intent;

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
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class Statsstudents extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener{

    public static ArrayList<String> studentlist;
    public ArrayList<Integer> sidlist;
    public int subject;
    ListView listView;
    static Statsstudents statsstudents;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editstudentlayout);
        sidlist=new ArrayList<>();
        studentlist=new ArrayList<String>();

        listView=(ListView) findViewById(R.id.editlv);
        final Intent intent =getIntent();
        subject=intent.getIntExtra("cid",0);
        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        floatingActionButton.setVisibility(View.GONE);

        setlistView();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        statsstudents=this;

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DatabaseManager databaseManager=new DatabaseManager(getApplicationContext());
                sidlist=databaseManager.getAllSids(subject);

                Integer sid=sidlist.get(position);
                Intent intent1 = new Intent(getApplicationContext(),Statistics.class);
                intent1.putExtra("sid",sid);
                intent1.putExtra("cid",subject);
                startActivity(intent1);

            }
        });

    }
    public static Statsstudents getInstance(){
        return statsstudents;
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
        } else if (id == R.id.past_attendance) {
            navintent=new Intent(getApplicationContext(),Pastclassrooms.class);
            startActivity(navintent);
        } else if (id == R.id.nav_stats) {
            drawer.closeDrawer(GravityCompat.START);

        }else if (id==R.id.nav_excel)
        {
            navintent=new Intent(getApplicationContext(),Excelclassroom.class);
            startActivity(navintent);
            finish();
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


    private void setlistView() {

        DatabaseManager databaseManager=new DatabaseManager(getApplicationContext());

        studentlist=databaseManager.showStudentslist(subject);

        /*ArrayAdapter<String> arrayAdapter= new ArrayAdapter<String>(getApplicationContext(),
                R.layout.rowlayout,R.id.tlv1,studentlist);
        */
        Statsadapter arrayAdapter=new Statsadapter(getApplicationContext(),
                studentlist);

        listView.setAdapter(arrayAdapter);


    }

}
