package com.example.rajeshkumarreddy.bealign;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Handler;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity extends AppCompatActivity {
    TextView textView;
    Typeface typeface;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Animation topToCenter= AnimationUtils.loadAnimation(this,R.anim.toptocenter);
        Animation bottomToCenter=AnimationUtils.loadAnimation(this,R.anim.bottomtocenter);
        final Animation centerToBottom=AnimationUtils.loadAnimation(this,R.anim.centertobottom);
        final Animation centerToTop=AnimationUtils.loadAnimation(this,R.anim.centertotop);


        textView=(TextView)findViewById(R.id.brandName);
        imageView=(ImageView)findViewById(R.id.brandLogo);

        textView.startAnimation(bottomToCenter);
        imageView.startAnimation(topToCenter);
        typeface=Typeface.createFromAsset(getAssets(),"fonts/productsans/ProductSansBold.ttf");
        textView.setTypeface(typeface);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                textView.startAnimation(centerToBottom);
                imageView.startAnimation(centerToTop);
            }
        },1500);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                textView.setVisibility(View.GONE);
                imageView.setVisibility(View.GONE);
                startActivity(new Intent(SplashActivity.this, AuthHelperActivity.class));
                finish();
            }
        },2100);
    }


}
