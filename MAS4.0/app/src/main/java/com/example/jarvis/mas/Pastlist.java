package com.example.jarvis.mas;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by jarvis on 17/4/16.
 */
public class Pastlist extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener{
    Intent calledactivity;
    int cid;
    ListView listView;
    ArrayList<Integer> csidlist;
    ArrayList<String> dates;
    Intent pastactivity;
    static Pastlist pastlist;
    PrettyTime p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diplay_present_students);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        pastlist=this;
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        TextView textView=(TextView) findViewById(R.id.total);
        textView.setVisibility(View.GONE);
        TextView title=(TextView) findViewById(R.id.editclassroomtv);
        title.setText("Past Attendance");
        csidlist=new ArrayList<>();
        dates=new ArrayList<>();
        calledactivity=getIntent();
        cid= calledactivity.getIntExtra("cid", 0);
        pastactivity=new Intent(getApplicationContext(),PastAttendance.class);
        DatabaseManager databaseManager=new DatabaseManager(getApplicationContext());
        csidlist=databaseManager.getcsid(cid);
        System.out.println(csidlist.size());
        listView=(ListView) findViewById(R.id.editlv);
        try {
            getdates();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> parent, View view, final int position, long id) {
                Vibrator vibrator = (Vibrator) getApplicationContext().
                        getSystemService(getApplicationContext().VIBRATOR_SERVICE);
                vibrator.vibrate(2000);
                final Dialog dialog = new Dialog(Pastlist.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.delconfdialog);

                TextView textView = (TextView) dialog.findViewById(R.id.tvroll);
                textView.append(String.valueOf(parent.getItemAtPosition(position)) + " ?");

                Button btnok = (Button) dialog.findViewById(R.id.positiveButton);
                Button btncancel = (Button) dialog.findViewById(R.id.negativebutton);

                dialog.show();

                btnok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int sid = 0;
                        DatabaseManager databaseManager = new DatabaseManager(getApplicationContext());
                        databaseManager.deleteAttendance(
                                String.valueOf(parent.getItemAtPosition(position)),cid);
                        try {
                            getdates();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        dialog.cancel();
                        finish();
                    }
                });

                btncancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();

                    }
                });
               // Toast.makeText(getApplicationContext(), "Ab Chhod bhi do", Toast.LENGTH_SHORT).show();
                return true;
            }
        });



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              /*  Toast.makeText(getApplicationContext(),"You clicked "+
                        String.valueOf(parent.getItemAtPosition(position)),
                        Toast.LENGTH_SHORT).show();
              */

                SimpleDateFormat spf=new SimpleDateFormat("dd,MMM-yyyy:kk.mm");
                Date newDate= null;
                try {
                    newDate = spf.parse(String.valueOf(parent.getItemAtPosition(position)));

                spf= new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
                String pretty_date = spf.format(newDate);

                pastactivity.putExtra("datetime",pretty_date);
                pastactivity.putExtra("cid",cid);
                startActivity(pastactivity);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        });

    }
    public static Pastlist getInstance(){
        return pastlist;
    }
    int countdates=0;
    ArrayList<String> datetime=new ArrayList<>();
    private void getdates() throws ParseException {
        String date;
        for(int i=0;i<csidlist.size();i++)
        {
            DatabaseManager databaseManager=new DatabaseManager(getApplicationContext());
            countdates=databaseManager.getdatesfromatendance(csidlist.get(i));
            datetime=(databaseManager.getdates(csidlist.get(i)));
            Log.d("dates and count dates",datetime.toString() +countdates);

            for(int j=0;j<countdates;j++)
            {   date=datetime.get(j);

                if(date==null)
                {
                continue;
                }
                if((!dates.contains(date))) {
                    /*SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");

                    Date convertedDate = new Date();

                    try {
                        convertedDate = dateFormat.parse(date);
                    } catch (ParseException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    p  = new PrettyTime();

                    String pretty_date= p.format(convertedDate);*/


                    SimpleDateFormat spf=new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
                    Date newDate=spf.parse(date);
                    spf= new SimpleDateFormat("dd,MMM-yyyy:kk.mm");
                    String pretty_date = spf.format(newDate);
                    dates.add(pretty_date);
                }
            }
        }
        System.out.println(dates);
        ArrayAdapter<String> adapter=new ArrayAdapter<>(getApplicationContext(),
                R.layout.rowlayout, R.id.tlv1,dates);

       /* Studentsdisplaylist adapter=new Studentsdisplaylist(getApplicationContext(),
                dates);
       */
        listView.setAdapter(adapter);
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
        } else if (id == R.id.nav_stats) {
            navintent=new Intent(getApplicationContext(),Statsclassroom.class);
            startActivity(navintent);

        } else if (id == R.id.past_attendance) {
            navintent=new Intent(getApplicationContext(),Pastclassrooms.class);
            startActivity(navintent);
        } else if (id == R.id.nav_edit) {
            navintent=new Intent(getApplicationContext(),Editclassroom.class);
            startActivity(navintent);
        } else if (id==R.id.nav_excel)
        {
            navintent=new Intent(getApplicationContext(),Excelclassroom.class);
            startActivity(navintent);
            finish();
        }
        else if (id == R.id.nav_delete) {
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
