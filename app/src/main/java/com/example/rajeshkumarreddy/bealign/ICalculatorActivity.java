package com.example.rajeshkumarreddy.bealign;

import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.tapadoo.alerter.Alerter;

public class ICalculatorActivity extends AppCompatActivity {
    EditText i1et,i2et,met,aet,a1et,a2et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_icalculator);

        i1et=(EditText)findViewById(R.id.internal1);
        i2et=(EditText)findViewById(R.id.internal2);
        met=(EditText)findViewById(R.id.model);
        aet=(EditText)findViewById(R.id.attendanceET);
        a1et=(EditText)findViewById(R.id.assignment1ET);
        a2et=(EditText)findViewById(R.id.assignment2ET);

        findViewById(R.id.calculateInternals).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String si1=i1et.getText().toString();
                String si2=i2et.getText().toString();
                String sm=met.getText().toString();
                String sa=aet.getText().toString();
                String sa1=a1et.getText().toString();
                String sa2=a2et.getText().toString();

                if (!si1.equals("")&&!si2.equals("")&&!sm.equals("")&&!sa.equals("")&&!sa1.equals("")&&!sa2.equals("")){
                    int i1=Integer.parseInt(si1);
                    int i2=Integer.parseInt(si2);
                    int m=Integer.parseInt(sm);
                    int a=Integer.parseInt(sa);
                    int a1=Integer.parseInt(sa1);
                    int a2=Integer.parseInt(sa2);

                    AlertDialog.Builder builder;
                    builder=new AlertDialog.Builder(ICalculatorActivity.this);

                    builder.setTitle("Your Marks");
                    builder.setMessage("haha"+i1);
                    builder.show();
                }
                else{
                    Alerter.create(ICalculatorActivity.this)
                            .setTitle("Enter all marks!")
                            .setBackgroundColor(R.color.error)
                            .setIcon(R.drawable.ic_empty)
                            .show();
                }

            }
        });
    }
}
