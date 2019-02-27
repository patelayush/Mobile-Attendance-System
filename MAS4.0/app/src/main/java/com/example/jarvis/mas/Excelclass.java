package com.example.jarvis.mas;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;


/**
 * Created by jarvis on 26/4/16.
 */
public class Excelclass extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener{
    static Excelclass excel_class;
    ArrayList<String> snames=new ArrayList<>();
    ArrayList<Integer> srolls=new ArrayList<>();
    ArrayList<Student> students=new ArrayList<>();
    public int cid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.excel_file_generated);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        if(isStoragePermissionGranted())
        {
            Toast.makeText(getApplicationContext(),"Storage permission granted",Toast.LENGTH_SHORT).show();
            Intent intent=getIntent();
            cid=intent.getIntExtra("cid", 0);

            DatabaseManager databaseManager=new DatabaseManager(getApplicationContext());
            students=databaseManager.getstudent(cid);
            for(int i=0;i<students.size();i++)
            {
                snames.add(students.get(i).getSname());
            }
            for(int i=0;i<students.size();i++)
            {
                srolls.add(students.get(i).getSroll());
            }

            excel_class=this;

            WriteExcel writeExcel = new WriteExcel();
            try {
                writeExcel.write();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else{
            Toast.makeText(getApplicationContext(),"Please grant storage permission to create excel record",Toast.LENGTH_SHORT).show();
        }



    }


    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("Permission","Permission is granted");
                return true;
            } else {

                Log.v("Permission","Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v("Permission","Permission is granted");
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
            Log.v("Permission","Permission: "+permissions[0]+ "was "+grantResults[0]);
            //resume tasks needing this permission
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Please grant storage permission to create excel record",Toast.LENGTH_SHORT).show();

        }
    }


    public static Excelclass getInstance()
    {
        return excel_class;
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
            finish();
        } else if (id == R.id.nav_stats) {
            navintent=new Intent(getApplicationContext(),Statsclassroom.class);
            startActivity(navintent);
            finish();

        }else if (id == R.id.past_attendance) {
            navintent=new Intent(getApplicationContext(),Pastclassrooms.class);
            startActivity(navintent);
            finish();
        }
        else if (id==R.id.nav_excel)
        {
            drawer.closeDrawer(GravityCompat.START);
        }

        else if (id == R.id.nav_edit) {
            navintent=new Intent(getApplicationContext(),Editclassroom.class);
            startActivity(navintent);
            finish();
        } else if (id == R.id.nav_delete) {
            navintent=new Intent(getApplicationContext(),Delete.class);
            startActivity(navintent);
            finish();

        }
        else if (id == R.id.nav_about) {
            navintent=new Intent(getApplicationContext(),About.class);
            startActivity(navintent);
            finish();
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
