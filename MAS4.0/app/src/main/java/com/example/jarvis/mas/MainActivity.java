package com.example.jarvis.mas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] list = {"BAT MAN", "SUPER MAN", "SPIDER MAN", "HANU MAN", "BAAL VEER", "HULK"
                , "ARROW", "KILLER FROST", "THE FLASH", "IRON MAN"};

        ListView listView = (ListView) findViewById(R.id.Listview);

        ArrayAdapter adapter = new ArrayAdapter<String>(getApplicationContext(),
                R.layout.rowlayout, R.id.tlv1, list);



        listView.setAdapter(adapter);
    }
}
