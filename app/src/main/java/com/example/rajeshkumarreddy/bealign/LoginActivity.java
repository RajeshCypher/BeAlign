package com.example.rajeshkumarreddy.bealign;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tapadoo.alerter.Alerter;
import com.wang.avi.AVLoadingIndicatorView;

import org.w3c.dom.Text;

import java.lang.reflect.Type;

import es.dmoral.toasty.Toasty;


public class LoginActivity extends AppCompatActivity {
    TextView title,desc,login;
    Typeface segoe,product,sans;
    EditText etRoll,etPass;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    FirebaseAuth.AuthStateListener authStateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etRoll=(EditText)findViewById(R.id.editText);
        etPass=(EditText)findViewById(R.id.editText2);
        title=(TextView)findViewById(R.id.login_title);
        desc=(TextView)findViewById(R.id.login_desc);
        login=(TextView)findViewById(R.id.login);

        sans=Typeface.createFromAsset(getAssets(),"fonts/opensans/OpenSans-Regular.ttf");
        segoe= Typeface.createFromAsset(getAssets(),"fonts/segoeui.ttf");
        product=Typeface.createFromAsset(getAssets(),"fonts/productsans/ProductSansRegular.ttf");

        firebaseAuth=FirebaseAuth.getInstance();

        authStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser()!=null){
                    startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                    finish();
                }
            }
        };

        login.setTypeface(sans);
        title.setTypeface(product);
        desc.setTypeface(segoe);

        findViewById(R.id.button11).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,LearnActivity.class));
            }
        });

        findViewById(R.id.button10).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etRoll.setText("");
                etPass.setText("");
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });

        findViewById(R.id.button8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AVLoadingIndicatorView avi=(AVLoadingIndicatorView)findViewById(R.id.avi);
                String roll=etRoll.getText().toString().trim();
                final String pass=etPass.getText().toString().trim();

                if (!roll.equals("")&&!pass.equals("")){

                    findViewById(R.id.button8).setVisibility(View.GONE);
                    etRoll.setClickable(false);
                    etRoll.setFocusable(false);
                    etRoll.setFocusableInTouchMode(false);
                    etPass.setClickable(false);
                    etPass.setFocusable(false);
                    etPass.setFocusableInTouchMode(false);
                    avi.show();
                    avi.setVisibility(View.VISIBLE);
                    databaseReference= FirebaseDatabase.getInstance().getReference().child("Details").child(roll).child("email");
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                String email = dataSnapshot.getValue(String.class);
                                //Toasty.info(LoginActivity.this,email,Toast.LENGTH_LONG,true).show();
                                firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            FirebaseUser user = firebaseAuth.getCurrentUser();
                                            if (user.isEmailVerified()) {
                                                Intent i=new Intent(LoginActivity.this,HomeActivity.class);
                                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                startActivity(i);
                                                finish();
                                                Toasty.success(LoginActivity.this,"Login success!",Toast.LENGTH_LONG,true).show();
                                            } else {
                                                Toasty.warning(LoginActivity.this,"Please verify your Email!",Toast.LENGTH_LONG,true).show();
                                                firebaseAuth.signOut();
                                                finish();
                                                startActivity(new Intent(LoginActivity.this,LoginActivity.class));
                                            }
                                        }
                                        else {
                                            try {
                                                throw task.getException();
                                            }
                                            catch(FirebaseAuthInvalidCredentialsException e){
                                                Alerter.create(LoginActivity.this)
                                                        .setTitle("Password Incorrect!")
                                                        .setIcon(R.drawable.ic_empty)
                                                        .setBackgroundColor(R.color.error)
                                                        .show();
                                                avi.setVisibility(View.GONE);
                                                avi.hide();
                                                etRoll.setFocusable(true);
                                                etRoll.setFocusableInTouchMode(true);
                                                etRoll.setClickable(true);
                                                etPass.setFocusable(true);
                                                etPass.setFocusableInTouchMode(true);
                                                etPass.setClickable(true);
                                                findViewById(R.id.button8).setVisibility(View.VISIBLE);
                                            }
                                            catch (Exception e) {
                                                Alerter.create(LoginActivity.this)
                                                        .setTitle("Couldn't login!")
                                                        .setIcon(R.drawable.ic_empty)
                                                        .setBackgroundColor(R.color.error)
                                                        .show();
                                                avi.setVisibility(View.GONE);
                                                avi.hide();
                                                etRoll.setFocusable(true);
                                                etRoll.setFocusableInTouchMode(true);
                                                etRoll.setClickable(true);
                                                etPass.setFocusable(true);
                                                etPass.setFocusableInTouchMode(true);
                                                etPass.setClickable(true);
                                                findViewById(R.id.button8).setVisibility(View.VISIBLE);
                                            }
                                        }
                                    }
                                });
                            }
                            else{
                                Alerter.create(LoginActivity.this)
                                        .setTitle("Couldn't login!")
                                        .setIcon(R.drawable.ic_empty)
                                        .setBackgroundColor(R.color.error)
                                        .show();
                                avi.setVisibility(View.GONE);
                                avi.hide();
                                etRoll.setFocusable(true);
                                etRoll.setFocusableInTouchMode(true);
                                etRoll.setClickable(true);
                                etPass.setFocusable(true);
                                etPass.setFocusableInTouchMode(true);
                                etPass.setClickable(true);
                                findViewById(R.id.button8).setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Alerter.create(LoginActivity.this)
                                    .setTitle("We can't reach our server right now!\nPlease try again later!")
                                    .setIcon(R.drawable.ic_empty)
                                    .setBackgroundColor(R.color.error)
                                    .show();
                            avi.setVisibility(View.GONE);
                            avi.hide();
                            etRoll.setFocusable(true);
                            etRoll.setFocusableInTouchMode(true);
                            etRoll.setClickable(true);
                            etPass.setFocusable(true);
                            etPass.setFocusableInTouchMode(true);
                            etPass.setClickable(true);
                            findViewById(R.id.button8).setVisibility(View.VISIBLE);
                        }
                    });
                }
                else{
                    Alerter.create(LoginActivity.this)
                            .setTitle("All details must be filled!")
                            .setIcon(R.drawable.ic_empty)
                            .setBackgroundColor(R.color.error)
                            .show();
                }
            }
        });
    }


}
