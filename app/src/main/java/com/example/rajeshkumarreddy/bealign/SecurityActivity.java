package com.example.rajeshkumarreddy.bealign;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.goodiebag.pinview.Pinview;
import com.tapadoo.alerter.Alerter;

import es.dmoral.toasty.Toasty;

public class SecurityActivity extends AppCompatActivity {
    Button setup,change,remove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security);

        setup=(Button)findViewById(R.id.setupPin);
        change=(Button)findViewById(R.id.changePin);
        remove=(Button)findViewById(R.id.removePin);

        SharedPreferences exist=getSharedPreferences("PREFS",0);
        String password=exist.getString("pin","");
        if (password.equals("")){
            setup.setVisibility(View.VISIBLE);
            change.setVisibility(View.GONE);
            remove.setVisibility(View.GONE);
        }
        else{
            setup.setVisibility(View.GONE);
            change.setVisibility(View.VISIBLE);
            remove.setVisibility(View.VISIBLE);
        }

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(SecurityActivity.this);
                final View mView=getLayoutInflater().inflate(R.layout.pin_view,null);
                TextView advice=(TextView)mView.findViewById(R.id.advicePin);
                advice.setText("Enter PIN to remove");
                Pinview pinview=(Pinview)mView.findViewById(R.id.pinView);
                pinview.setPinViewEventListener(new Pinview.PinViewEventListener() {
                    @Override
                    public void onDataEntered(Pinview pinview, boolean b) {
                        SharedPreferences remove=getSharedPreferences("PREFS",0);
                        SharedPreferences.Editor editor=remove.edit();
                        editor.putString("pin","");
                        editor.apply();
                        Toasty.success(SecurityActivity.this,"PIN removed",Toast.LENGTH_LONG,true).show();
                    }
                });
                builder.setView(mView);
                AlertDialog dialog=builder.create();
                dialog.show();
            }
        });


        setup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(SecurityActivity.this);
                final View mView=getLayoutInflater().inflate(R.layout.pin_view,null);
                TextView advice=(TextView)mView.findViewById(R.id.advicePin);
                advice.setText("Setup new PIN");
                Pinview pinview=(Pinview)mView.findViewById(R.id.pinView);
                pinview.setPinViewEventListener(new Pinview.PinViewEventListener() {
                    @Override
                    public void onDataEntered(Pinview pinview, boolean b) {
                        SharedPreferences settings=getSharedPreferences("PREFS",0);
                        SharedPreferences.Editor editor=settings.edit();
                        editor.putString("pin",pinview.getValue());
                        editor.apply();
                        Toasty.success(SecurityActivity.this,"New PIN : "+pinview.getValue()+" saved",Toast.LENGTH_LONG,true).show();
                    }
                });
                builder.setView(mView);
                AlertDialog dialog=builder.create();
                dialog.show();
            }
        });
    }
}
