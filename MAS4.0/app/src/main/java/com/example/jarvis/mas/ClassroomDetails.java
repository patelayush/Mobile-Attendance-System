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
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * Add classroom page where the classroom is created.
 * Created by jarvis on 13/4/16.
 */
public class ClassroomDetails extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener{

    String subject, department, division;
    int semester;
    ArrayList<Integer> rolls=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.classroomdetails);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        final EditText subname = (EditText) findViewById(R.id.editText);
        final EditText deptname = (EditText) findViewById(R.id.editText2);
        final EditText divname = (EditText) findViewById(R.id.editText3);
        final EditText sem = (EditText) findViewById(R.id.editText4);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab1);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                subject = String.valueOf(subname.getText());
                department = String.valueOf(deptname.getText());
                division = String.valueOf(divname.getText());
                try{semester = Integer.parseInt(sem.getText().toString());}

                catch(Exception e)
                {
                    Toast.makeText(getApplicationContext(),
                            "Don't leave semester blank",Toast.LENGTH_LONG).show();
                    finish();
                }

                if(subject.matches("")||department.matches("")||division.matches(""))
                {
                    Toast.makeText(getApplicationContext(),
                            "Don't leave any field blank",Toast.LENGTH_LONG).show();
                }
                else {
                    storeclassroomdata();
                    //new Storeclassroomdata().execute();//Asynctask
                    //new UpdateClassroomLists().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    Navigation_activity.getInstance().setListview();
                    finish();
                }

            }
        });

    }

    public void storeclassroomdata(){
        DatabaseManager databaseManager = new DatabaseManager(getApplicationContext());
        Classroom classroom = new Classroom(subject, department, division, semester);

        databaseManager.storeClassroomdata(classroom);
    }

    /*private class Storeclassroomdata extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {

            DatabaseManager databaseManager = new DatabaseManager(getApplicationContext());
            Classroom classroom = new Classroom(subject, department, division, semester);

            databaseManager.storeClassroomdata(classroom);
            return null;

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();

        }
    }
*/
/*

    private class UpdateClassroomLists extends AsyncTask<Void,Void,ArrayList<String>>{
        @Override
        protected ArrayList<String> doInBackground(Void... params) {
            DatabaseManager databaseManager=new DatabaseManager(getApplicationContext());
            ArrayList<String> arrayList;
            arrayList = databaseManager.showClassroomlist();
            return arrayList;

        }

        @Override
        protected void onPostExecute(ArrayList<String> classrooms) {
            super.onPostExecute(classrooms);
            Navigation_activity.getInstance().clist.clear();
            Navigation_activity.getInstance().clist.addAll(classrooms);
            Navigation_activity.getInstance().setListview();

        }
    }
*/
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

        } else if (id == R.id.nav_edit) {
            navintent=new Intent(getApplicationContext(),Editclassroom.class);
            startActivity(navintent);
        } else if (id==R.id.nav_excel)
        {
            navintent=new Intent(getApplicationContext(),Excelclassroom.class);
            startActivity(navintent);
            finish();
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
