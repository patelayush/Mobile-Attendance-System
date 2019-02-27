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
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

/*** Page to edit the classroom. that is wither to delte or add more students.
 */
public class Editclassroom extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private int cid;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editclassroomlayout);
        listView=(ListView) findViewById(R.id.editlv);

        /*ArrayAdapter<String> arrayAdapter= new ArrayAdapter<String>(getApplicationContext(),
                R.layout.rowlayout,R.id.tlv1,Navigation_activity.getInstance().clist);*/


        setlistview();

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
                Intent intent = new Intent(getApplicationContext(), Editstudents.class);
                DatabaseManager databaseManager=new DatabaseManager(getApplicationContext());
                cid = databaseManager.getcid(String.valueOf(parent.getItemAtPosition(position)));
                intent.putExtra("Cid", cid);
                startActivity(intent);
                finish();

            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> parent, View view, final int position, long id) {
                Vibrator vibrator = (Vibrator) getApplicationContext().
                        getSystemService(getApplicationContext().VIBRATOR_SERVICE);
                vibrator.vibrate(200);
                final Dialog dialog=new Dialog(Editclassroom.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.delconfdialog);
                TextView textView=(TextView) dialog.findViewById(R.id.tvroll);
                textView.append(String.valueOf(parent.getItemAtPosition(position))+" ?");

                Button btnok=(Button) dialog.findViewById(R.id.positiveButton);
                Button btncancel=(Button) dialog.findViewById(R.id.negativebutton);

                dialog.show();

                btnok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int cid = 0;
                        DatabaseManager databaseManager = new DatabaseManager(getApplicationContext());
                        cid = databaseManager.getcid(String.valueOf(parent.getItemAtPosition(position)));
                        databaseManager.deleteclassroom(cid);
                        Navigation_activity.getInstance().setListview();
                        setlistview();
                        dialog.cancel();
                    }
                });

                btncancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
                return true;
            }
        });

    }

    void setlistview(){
        ListviewAdapter arrayAdapter=new ListviewAdapter(getApplicationContext(),
                Navigation_activity.getInstance().clist);
        listView.setAdapter(arrayAdapter);
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
            drawer.closeDrawer(GravityCompat.START);
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
