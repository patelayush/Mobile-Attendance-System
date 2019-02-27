package com.example.jarvis.mas;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;


public class SplashScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Runtime rt=Runtime.getRuntime();
        long maxMemory=rt.maxMemory();
        Log.v("onCreate","maxMemory:"+Long.toString(maxMemory));
        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext()
                        , Navigation_activity.class);
                startActivity(intent);
                finish();

            }
        }, 3000);
    }

}
