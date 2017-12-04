package com.example.rajeshkumarreddy.bealign;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.wang.avi.AVLoadingIndicatorView;

public class AuthHelperActivity extends AppCompatActivity {
    AVLoadingIndicatorView avLoadingIndicatorView;
    FirebaseAuth firebaseAuth;
    FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_helper);

        avLoadingIndicatorView=(AVLoadingIndicatorView)findViewById(R.id.authHelperAvi);
        avLoadingIndicatorView.show();

        firebaseAuth=FirebaseAuth.getInstance();

        authStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser()==null){
                    startActivity(new Intent(AuthHelperActivity.this,LoginActivity.class));
                    finish();
                }
                else {
                    startActivity(new Intent(AuthHelperActivity.this,HomeActivity.class));
                    finish();
                }
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }
}
