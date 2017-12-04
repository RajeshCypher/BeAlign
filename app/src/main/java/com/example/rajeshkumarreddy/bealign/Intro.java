package com.example.rajeshkumarreddy.bealign;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;


public class Intro extends AppIntro {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addSlide(AppIntroFragment.newInstance("Welcome to Be Align","Customize a feed & post it",R.drawable.ic_feed, Color.parseColor("#0B7FF8")));
        addSlide(AppIntroFragment.newInstance("Welcome to Be Align","Know Attendance Percentage",R.drawable.ic_attendance, Color.parseColor("#E53576")));
        addSlide(AppIntroFragment.newInstance("Welcome to Be Align","Get Syllabus & Notes",R.drawable.ic_notes, Color.parseColor("#03A373")));
        addSlide(AppIntroFragment.newInstance("Welcome to Be Align","Know Timetable & Upcoming Events",R.drawable.ic_timetable, Color.parseColor("#B604C4")));
        addSlide(AppIntroFragment.newInstance("Welcome to Be Align","Attractive Campus Tour",R.drawable.ic_college, Color.parseColor("#FFA015")));
        addSlide(AppIntroFragment.newInstance("Welcome to Be Align","Security & more",R.drawable.ic_security, Color.parseColor("#F4251F")));

        showStatusBar(false);
        setBarColor(Color.parseColor("#333639"));
        setSeparatorColor(Color.parseColor("#2196f3"));
    }

    @Override
    public void onDonePressed() {
        startActivity(new Intent(this,SplashActivity.class));
        finish();
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        startActivity(new Intent(this,SplashActivity.class));
        finish();
    }


}
