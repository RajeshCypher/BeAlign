package com.example.rajeshkumarreddy.bealign;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tapadoo.alerter.Alerter;

import java.util.Locale;

public class FindClassActivity extends AppCompatActivity {
    DatabaseReference databaseReference;
    Spinner spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_class);

        spinner=(Spinner)findViewById(R.id.classesSpinner);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String spinnerClass=adapterView.getItemAtPosition(i).toString();
                if (!spinnerClass.equals("Select...")){
                    databaseReference= FirebaseDatabase.getInstance().getReference().child("classrooms").child(spinnerClass);
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String cloudClass=dataSnapshot.getValue(String.class);
                            Toast.makeText(FindClassActivity.this, cloudClass, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
                else {
                    Toast.makeText(FindClassActivity.this, "Select the section", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        findViewById(R.id.FCNavigate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String spinnerClass=spinner.getSelectedItem().toString();
                if (!spinnerClass.equals("Select...")){
                    databaseReference=FirebaseDatabase.getInstance().getReference().child("classrooms").child(spinnerClass);
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String cloudClass=dataSnapshot.getValue(String.class);
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(String.format(Locale.ENGLISH, "geo:"+cloudClass))));
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
                else{
                    Alerter.create(FindClassActivity.this)
                            .setBackgroundColor(R.color.error)
                            .setIcon(R.drawable.ic_empty)
                            .setTitle("Please pick section!")
                            .show();
                }
            }
        });


    }


}
