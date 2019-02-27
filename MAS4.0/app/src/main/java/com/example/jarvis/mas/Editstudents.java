package com.example.jarvis.mas;

import android.annotation.SuppressLint;

import android.app.Dialog;
import android.content.Intent;

import android.os.Bundle;
import android.os.Vibrator;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Editstudents extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener{

    String name;
    int roll;
    public static ArrayList<String> studentlist;
    public int subject;
    ListView listView;
    EditText editText;
    private int cid;
    static Editstudents edit_students;
    public static ArrayList<String> sname;
    ArrayList<Integer> rolls=new ArrayList<>();
    int[] csid;
    Vibrator vibrator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editstudentlayout);
        listView=(ListView) findViewById(R.id.editlv);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();



        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        studentlist=new ArrayList<String>();
        csid=new int[2];
        sname=new ArrayList<>();
        Intent intent =getIntent();
        subject=intent.getIntExtra("Cid",0);
        edit_students=this;
        setlistView();

        /*new ShowStudentslist().execute(subject);
        setlistView();
*/
        //new Setcids().executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);

        FloatingActionButton floatingActionButton=(FloatingActionButton) findViewById(R.id.fab);
        floatingActionButton.setImageResource(R.drawable.cross);
        floatingActionButton.setVisibility(View.VISIBLE);
//        new ShowStudentslist().executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, subject);


        /*listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> parent, View view, final int position, long id) {
                vibrator.vibrate(2000);
                final Dialog dialog = new Dialog(Editstudents.this);
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
                        sid = databaseManager.getsidbyname(
                                String.valueOf(parent.getItemAtPosition(position)));
                        databaseManager.deleteStudent(sid, subject);
                        setlistView();
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
                return true;
            }
        });
*/
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                setlistView();
                final EditText eroll, ename;
                final Dialog dialog = new Dialog(Editstudents.this);

                DatabaseManager databaseManager=new DatabaseManager(getApplicationContext());
                rolls=databaseManager.getrolls(subject);


                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.add_student_dialog);

                final TextView troll = (TextView) dialog.findViewById(R.id.tvroll);
                final TextView tname = (TextView) dialog.findViewById(R.id.tvname);
                eroll = (EditText) dialog.findViewById(R.id.edroll);
                ename = (EditText) dialog.findViewById(R.id.edname);
                Button buttonok = (Button) dialog.findViewById(R.id.positiveButton);

                troll.setText("Enter Roll no.");
                tname.setText("Enter Student name");
                dialog.show();

                buttonok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        name = String.valueOf(ename.getText());

                        try{roll = Integer.parseInt(eroll.getText().toString());}

                        catch(Exception e)
                        {
                            Toast.makeText(getApplicationContext(),
                                    "Don't leave Roll blank",Toast.LENGTH_LONG).show();
                            dialog.cancel();
                        }
                        if(roll==0)
                        {
                            dialog.cancel();
                        }
                      else if(rolls.contains(roll)){
                            Toast.makeText(getApplicationContext(), "Roll no. should be unique", Toast.LENGTH_SHORT).show();
                            dialog.cancel();

                        } else if (name.matches("")) {
                            Toast.makeText(getApplicationContext(), "Enter Name", Toast.LENGTH_SHORT).show();
                            dialog.cancel();
                        }
                        else{
                            Toast.makeText(getApplicationContext(),
                                    roll + " " + name, Toast.LENGTH_SHORT).show();
                            dialog.cancel();

                            storestudentdata();
                            setlistView();
                        }

                    }
                });
            }
        });



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

        } else if (id==R.id.nav_excel)
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

        }
        else if (id == R.id.nav_about) {
            navintent=new Intent(getApplicationContext(),About.class);
            startActivity(navintent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public static Editstudents getInstance(){
        return edit_students;
    }

    private void storestudentdata(){
        DatabaseManager databaseManager=new DatabaseManager(getApplicationContext());
        Student student=new Student(roll,name,subject);

        databaseManager.storeStudentData(student);

    }
    private void setlistView() {
        DatabaseManager databaseManager=new DatabaseManager(getApplicationContext());

        studentlist=databaseManager.showStudentslist(subject);

        Studentsdisplaylist arrayAdapter=new Studentsdisplaylist(getApplicationContext(),studentlist);
        listView.setAdapter(arrayAdapter);
        registerForContextMenu(listView);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Select Action");
        menu.add(0, v.getId(), 0, "Edit Student Name");
        menu.add(0,v.getId(),1,"Delete Student");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        super.onContextItemSelected(item);
        if (item.getTitle() == "Edit Student Name") {

            final Dialog dialog = new Dialog(Editstudents.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.edit_name_dialog);

            TextView textView = (TextView) dialog.findViewById(R.id.tvroll);
            final String stuname;
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            stuname=studentlist.get(info.position);
            textView.append(stuname);
            editText=(EditText) dialog.findViewById(R.id.edroll);


            Button btnok = (Button) dialog.findViewById(R.id.positiveButton);

            dialog.show();

            btnok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int sid = 0;
                    DatabaseManager databaseManager = new DatabaseManager(getApplicationContext());
                    sid = databaseManager.getsidbyname(stuname);
                    String newname=editText.getText().toString();
                    if(newname.isEmpty())
                    {
                        Toast.makeText(getApplicationContext(),"Do not leave field blank",
                                Toast.LENGTH_SHORT).show();
                    }
                    databaseManager.updatestudentname(sid, newname);
                    setlistView();
                    dialog.cancel();
                }
            });
            return true;
        }
        else{

            final Dialog dialog = new Dialog(Editstudents.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.delconfdialog);

            TextView textView = (TextView) dialog.findViewById(R.id.tvroll);
            final String stuname;
            final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            stuname=studentlist.get(info.position);
            textView.append(stuname);


            Button btnok = (Button) dialog.findViewById(R.id.positiveButton);

            dialog.show();

            btnok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int sid = 0;
                    DatabaseManager databaseManager = new DatabaseManager(getApplicationContext());
                    sid = databaseManager.getsidbyname(stuname);
                    databaseManager.deleteStudent(sid, subject);
                    setlistView();
                    dialog.cancel();
                }
            });

            return true;
        }
    }

}


    /*

        private class ShowStudentslist extends AsyncTask<Integer,Void,ArrayList<String>> {
            @Override
            protected ArrayList<String> doInBackground(Integer... params) {

                DatabaseManager databaseManager=new DatabaseManager(getApplicationContext());

                studentlist=databaseManager.showStudentslist(params[0]);

                return studentlist;

            }

            @Override
            protected void onPostExecute(ArrayList<String> strings) {
                sname.clear();
                sname.addAll(strings);
                ArrayAdapter<String> arrayAdapter= new ArrayAdapter<String>(getApplicationContext(),
                        R.layout.rowlayout,R.id.tlv1,sname);
                listView.setAdapter(arrayAdapter);

            }
        }
    */
    /*private class StoreStudentdata extends AsyncTask<Void,Void,Void>{
        @Override
        protected Void doInBackground(Void... params) {
            DatabaseManager databaseManager=new DatabaseManager(getApplicationContext());
            Student student=new Student(roll,name,subject);

            databaseManager.storeStudentData(student);


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_SHORT).show();


        }
    }


    private class Setcids extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            DatabaseManager databaseManager=new DatabaseManager(getApplicationContext());
            //databaseManager.setcsid();
            return null;

        }
    }*/

