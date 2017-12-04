package com.example.rajeshkumarreddy.bealign;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tapadoo.alerter.Alerter;
import com.wang.avi.AVLoadingIndicatorView;

import es.dmoral.toasty.Toasty;

public class RegisterActivity extends AppCompatActivity {
    EditText roll,pass1,pass2;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    String email,spass1;
    TextView head,desc,easy;
    Typeface product,segoe,sans;
    AVLoadingIndicatorView avi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        avi=(AVLoadingIndicatorView)findViewById(R.id.avi);

        roll=(EditText)findViewById(R.id.roll);
        pass1=(EditText)findViewById(R.id.pass1);
        pass2=(EditText)findViewById(R.id.pass2);
        head=(TextView)findViewById(R.id.txtHead);
        desc=(TextView)findViewById(R.id.txtDesc);
        easy=(TextView)findViewById(R.id.easy);

        sans=Typeface.createFromAsset(getAssets(),"fonts/opensans/OpenSans-Regular.ttf");
        product=Typeface.createFromAsset(getAssets(),"fonts/productsans/ProductSansBold.ttf");
        segoe=Typeface.createFromAsset(getAssets(),"fonts/segoeui.ttf");
        head.setTypeface(product);
        desc.setTypeface(segoe);
        easy.setTypeface(sans);

        firebaseAuth=FirebaseAuth.getInstance();

        findViewById(R.id.register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String sroll=roll.getText().toString().trim();
                spass1=pass1.getText().toString().trim();
                String spass2=pass2.getText().toString();

                if (!sroll.equals("")&&!spass1.equals("")&&!spass2.equals("")){
                    if (spass1.equals(spass2)){
                        roll.setFocusableInTouchMode(false);
                        roll.setFocusable(false);
                        roll.setClickable(false);
                        pass1.setFocusableInTouchMode(false);
                        pass1.setFocusable(false);
                        pass1.setClickable(false);
                        pass2.setFocusableInTouchMode(false);
                        pass2.setFocusable(false);
                        pass2.setClickable(false);

                        findViewById(R.id.register).setVisibility(View.GONE);
                        avi.setVisibility(View.VISIBLE);
                        avi.show();
                        databaseReference= FirebaseDatabase.getInstance().getReference().child("Details").child(sroll).child("email");
                        databaseReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    email = dataSnapshot.getValue(String.class);
                                    final AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                    builder.setTitle("Email verification");
                                    builder.setMessage("We have found your email address as " + email + ".\nIf this is not your email address, please stop registration process and contact the developer.\n\nDo you want us to proceed with " + email + "?");
                                    builder.setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            //registerUser();

                                            findViewById(R.id.register).setVisibility(View.GONE);
                                            avi.setVisibility(View.VISIBLE);
                                            avi.show();
                                            firebaseAuth.createUserWithEmailAndPassword(email,spass1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                @Override
                                                public void onComplete(@NonNull Task<AuthResult> task) {
                                                    if (task.isSuccessful()){
                                                        String emailM=email.replace("@","A");
                                                        emailM=emailM.replace(".","D");
                                                        DatabaseReference users=FirebaseDatabase.getInstance().getReference().child("Users");
                                                        users.child(emailM).setValue(sroll);
                                                        FirebaseUser user=firebaseAuth.getCurrentUser();
                                                        user.sendEmailVerification();
                                                        firebaseAuth.signOut();
                                                        finish();
                                                        Intent i=new Intent(RegisterActivity.this,LoginActivity.class);
                                                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                        startActivity(i);
                                                        Toasty.success(RegisterActivity.this,"Account created!\nVerify your email!",Toast.LENGTH_LONG,true).show();
                                                    }

                                                    else{
                                                        try {
                                                            throw task.getException();
                                                        }
                                                        catch (FirebaseAuthWeakPasswordException e){
                                                            Alerter.create(RegisterActivity.this)
                                                                    .setIcon(R.drawable.ic_empty)
                                                                    .setTitle("Weak Password!")
                                                                    .setBackgroundColor(R.color.error)
                                                                    .show();
                                                        }
                                                        catch (FirebaseAuthUserCollisionException e){
                                                            Alerter.create(RegisterActivity.this)
                                                                    .setIcon(R.drawable.ic_empty)
                                                                    .setTitle("Account already exists!")
                                                                    .setText("Contact the developer for password change.")
                                                                    .setBackgroundColor(R.color.error)
                                                                    .show();
                                                        }
                                                        catch (Exception e) {
                                                            Alerter.create(RegisterActivity.this)
                                                                    .setIcon(R.drawable.ic_empty)
                                                                    .setTitle("Couldn't create account!")
                                                                    .setText("Contact the developer.")
                                                                    .setBackgroundColor(R.color.error)
                                                                    .show();
                                                        }
                                                        roll.setClickable(true);
                                                        roll.setFocusable(true);
                                                        roll.setFocusableInTouchMode(true);
                                                        pass1.setClickable(true);
                                                        pass1.setFocusable(true);
                                                        pass1.setFocusableInTouchMode(true);
                                                        pass2.setClickable(true);
                                                        pass2.setFocusable(true);
                                                        pass2.setFocusableInTouchMode(true);
                                                        avi.setVisibility(View.GONE);
                                                        avi.hide();
                                                        findViewById(R.id.register).setVisibility(View.VISIBLE);
                                                    }
                                                }

                                            });

                                        }
                                    });
                                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {

                                            roll.setClickable(true);
                                            roll.setFocusable(true);
                                            roll.setFocusableInTouchMode(true);
                                            pass1.setClickable(true);
                                            pass1.setFocusable(true);
                                            pass1.setFocusableInTouchMode(true);
                                            pass2.setClickable(true);
                                            pass2.setFocusable(true);
                                            pass2.setFocusableInTouchMode(true);

                                            findViewById(R.id.register).setVisibility(View.VISIBLE);
                                            avi.setVisibility(View.GONE);
                                            avi.hide();
                                            dialogInterface.cancel();
                                        }
                                    });
                                    AlertDialog alertDialog = builder.create();
                                    alertDialog.show();

                                    alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                        @Override
                                        public void onDismiss(DialogInterface dialogInterface) {
                                            roll.setClickable(true);
                                            roll.setFocusable(true);
                                            roll.setFocusableInTouchMode(true);
                                            pass1.setClickable(true);
                                            pass1.setFocusable(true);
                                            pass1.setFocusableInTouchMode(true);
                                            pass2.setClickable(true);
                                            pass2.setFocusable(true);
                                            pass2.setFocusableInTouchMode(true);

                                            findViewById(R.id.register).setVisibility(View.VISIBLE);
                                            avi.setVisibility(View.GONE);
                                            avi.hide();
                                        }
                                    });
                                }
                                else {
                                    Alerter.create(RegisterActivity.this)
                                            .setBackgroundColor(R.color.error)
                                            .setIcon(R.drawable.ic_empty)
                                            .setTitle("Couldn't create account!")
                                            .setText("We don't have your details yet. Please try again after some instance.")
                                            .setDuration(4000)
                                            .show();

                                    roll.setClickable(true);
                                    roll.setFocusable(true);
                                    roll.setFocusableInTouchMode(true);
                                    pass1.setClickable(true);
                                    pass1.setFocusable(true);
                                    pass1.setFocusableInTouchMode(true);
                                    pass2.setClickable(true);
                                    pass2.setFocusable(true);
                                    pass2.setFocusableInTouchMode(true);

                                    avi.setVisibility(View.GONE);
                                    avi.hide();
                                    findViewById(R.id.register).setVisibility(View.VISIBLE);
                                }

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                                roll.setClickable(true);
                                roll.setFocusable(true);
                                roll.setFocusableInTouchMode(true);
                                pass1.setClickable(true);
                                pass1.setFocusable(true);
                                pass1.setFocusableInTouchMode(true);
                                pass2.setClickable(true);
                                pass2.setFocusable(true);
                                pass2.setFocusableInTouchMode(true);

                                avi.setVisibility(View.GONE);
                                avi.hide();
                                findViewById(R.id.register).setVisibility(View.VISIBLE);
                                Alerter.create(RegisterActivity.this)
                                        .setTitle("We can't reach our servers right now!")
                                        .setText("Please try again later.")
                                        .setIcon(R.drawable.ic_empty)
                                        .setBackgroundColor(R.color.error)
                                        .show();
                            }
                        });
                    }
                    else
                        Alerter.create(RegisterActivity.this)
                        .setTitle("Passwords don't match!")
                        .setIcon(R.drawable.ic_empty)
                        .setBackgroundColor(R.color.error)
                        .show();
                }
                else{
                    Alerter.create(RegisterActivity.this)
                            .setIcon(R.drawable.ic_empty)
                            .setTitle("All fields required!")
                            .setBackgroundColor(R.color.error)
                            .show();
                }
            }
        });
    }

    private void registerUser() {
        findViewById(R.id.register).setVisibility(View.GONE);
        avi.setVisibility(View.VISIBLE);
        avi.show();
        firebaseAuth.createUserWithEmailAndPassword(email,spass1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                    FirebaseUser user=firebaseAuth.getCurrentUser();
                    user.sendEmailVerification();
                    firebaseAuth.signOut();
                    finish();
                    Intent i=new Intent(RegisterActivity.this,LoginActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    Toasty.success(RegisterActivity.this,"Account created!\nVerify your email!",Toast.LENGTH_LONG,true).show();
                }

                else{
                    try {
                        throw task.getException();
                    }
                    catch (FirebaseAuthWeakPasswordException e){
                        Alerter.create(RegisterActivity.this)
                                .setIcon(R.drawable.ic_empty)
                                .setTitle("Weak Password!")
                                .setBackgroundColor(R.color.error)
                                .show();
                    }
                    catch (FirebaseAuthUserCollisionException e){
                        Alerter.create(RegisterActivity.this)
                                .setIcon(R.drawable.ic_empty)
                                .setTitle("Account already exists!")
                                .setText("Contact the developer for password change.")
                                .setBackgroundColor(R.color.error)
                                .show();
                    }
                    catch (Exception e) {
                        Alerter.create(RegisterActivity.this)
                                .setIcon(R.drawable.ic_empty)
                                .setTitle("Couldn't create account!")
                                .setText("Contact the developer.")
                                .setBackgroundColor(R.color.error)
                                .show();
                    }
                    roll.setClickable(true);
                    roll.setFocusable(true);
                    roll.setFocusableInTouchMode(true);
                    pass1.setClickable(true);
                    pass1.setFocusable(true);
                    pass1.setFocusableInTouchMode(true);
                    pass2.setClickable(true);
                    pass2.setFocusable(true);
                    pass2.setFocusableInTouchMode(true);
                    avi.setVisibility(View.GONE);
                    avi.hide();
                    findViewById(R.id.register).setVisibility(View.VISIBLE);
                }
            }

        });
    }
}
