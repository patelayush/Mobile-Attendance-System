package com.example.jarvis.mas;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by jarvis on 18/4/16.
 */
public class PastAttendance extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener{
    Intent calledactivity;
    String datetime;
    ListView presentlistView,absentlistview;
    ArrayList<Integer> acsidlist;
    ArrayList<String> snamelist,presentnames,absentnames;
    ArrayList<Integer> presentlist,presentcsidlist,absentcsidlist,presentsidlist,absentsidlist;
    int cid=0;
    ArrayAdapter<String> adapter;
    ImageView check;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pastattendancelayout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        presentlistView=(ListView) findViewById(R.id.presentlv);
        absentlistview=(ListView) findViewById(R.id.absentlv);
        calledactivity=getIntent();
        datetime=calledactivity.getStringExtra("datetime");
        cid=calledactivity.getIntExtra("cid", 0);

        System.out.println(cid);


        acsidlist=new ArrayList<>();
        snamelist=new ArrayList<>();
        presentlist=new ArrayList<>();
        presentcsidlist=new ArrayList<>();
        absentcsidlist=new ArrayList<>();
        presentsidlist=new ArrayList<>();
        absentsidlist=new ArrayList<>();
        presentnames=new ArrayList<>();
        absentnames=new ArrayList<>();

        DatabaseManager databaseManager=new DatabaseManager(getApplicationContext());
        acsidlist=databaseManager.getacsid(datetime);
        System.out.println(acsidlist);
        snamelist =databaseManager.getsname(cid);
        System.out.println(snamelist);

        getnames();
        System.out.println(presentnames);
        System.out.println(absentnames);

        ArrayAdapter<String> presentadapater=new ArrayAdapter<String>(getApplicationContext(),
                R.layout.rowlayout,R.id.tlv1,presentnames);

        ArrayAdapter<String> absentadapater=new ArrayAdapter<String>(getApplicationContext(),
                R.layout.rowlayout,R.id.tlv1,absentnames);

        presentlistView.setAdapter(presentadapater);
        absentlistview.setAdapter(absentadapater);
    }
    int id;
    private void getnames(){
        DatabaseManager databaseManager=new DatabaseManager(getApplicationContext());
        System.out.println(acsidlist);
        for(int i=0;i<acsidlist.size();i++)
        {
            id=databaseManager.getpresentinfo(acsidlist.get(i), datetime);
            presentlist.add(id);
        }


        for(int j=0;j<presentlist.size();j++)
        {
            if(presentlist.get(j)==1)
            {
                int id=databaseManager.getsid(acsidlist.get(j));
                presentsidlist.add(id);
            }
            else
            {
                int id=databaseManager.getsid(acsidlist.get(j));
                absentsidlist.add(id);
            }
        }

        for(int k=0;k<presentsidlist.size();k++)
        {
            presentnames.add(databaseManager.getstuname(presentsidlist.get(k),cid));
        }
        for(int s=0;s<absentsidlist.size();s++)
        {
            absentnames.add(databaseManager.getstuname(absentsidlist.get(s),cid));
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
        } else if (id == R.id.nav_stats) {
            navintent=new Intent(getApplicationContext(),Statsclassroom.class);
            startActivity(navintent);

        }
        else if (id==R.id.nav_excel)
        {
            navintent=new Intent(getApplicationContext(),Excelclassroom.class);
            startActivity(navintent);
            finish();
        }
        else if (id == R.id.nav_edit) {
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
