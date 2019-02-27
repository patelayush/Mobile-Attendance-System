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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

/**
 * Created by jarvis on 18/4/16.
 */
public class Pastclassrooms extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private int cid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pastclassroomslayout);
        ListView listView=(ListView) findViewById(R.id.editlv);

        /*ArrayAdapter<String> arrayAdapter= new ArrayAdapter<String>(getApplicationContext(),
                R.layout.rowlayout,R.id.tlv1,Navigation_activity.getInstance().clist);
        */
        ListviewAdapter adapter=new ListviewAdapter(getApplicationContext(),
                Navigation_activity.getInstance().clist);
        listView.setAdapter(adapter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getApplicationContext(),Pastlist.class);
                DatabaseManager databaseManager=new DatabaseManager(getApplicationContext());
                cid=databaseManager.getcid(String.valueOf(parent.getItemAtPosition(position)));
                intent.putExtra("cid",cid);
                startActivity(intent);
                finish();

            }
        });
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
            drawer.closeDrawer(GravityCompat.START);

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

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}

