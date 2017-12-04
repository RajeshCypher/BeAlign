package com.example.rajeshkumarreddy.bealign;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class IntroHelperActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_helper);

        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences getPrefs= PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                boolean isFirstStart=getPrefs.getBoolean("firstStart",true);
                if (isFirstStart){
                    startActivity(new Intent(IntroHelperActivity.this,Intro.class));
                    finish();
                    SharedPreferences.Editor e=getPrefs.edit();
                    e.putBoolean("firstStart",false);
                    e.apply();
                }

                else{
                    startActivity(new Intent(IntroHelperActivity.this,SplashActivity.class));
                    finish();
                }
            }
        });

        thread.start();
    }
}
