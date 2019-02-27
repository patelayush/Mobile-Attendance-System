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

/**
 * Created by jarvis on 19/4/16.
 */
public class About extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
        }
        else if (id==R.id.nav_excel)
        {
            navintent=new Intent(getApplicationContext(),Excelclassroom.class);
            startActivity(navintent);
            finish();
        }
        else if (id == R.id.past_attendance) {
            navintent=new Intent(getApplicationContext(),Pastclassrooms.class);
            startActivity(navintent);
        } else if (id == R.id.nav_stats) {
            navintent=new Intent(getApplicationContext(),Statsclassroom.class);
            startActivity(navintent);

        } else if (id == R.id.nav_edit) {
            navintent=new Intent(getApplicationContext(),Editclassroom.class);
            startActivity(navintent);

        } else if (id == R.id.nav_delete) {
            drawer.closeDrawer(GravityCompat.START);

        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
