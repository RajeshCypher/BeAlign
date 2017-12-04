package com.example.rajeshkumarreddy.bealign;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

public class ProfileActivity extends AppCompatActivity {
    TextView name,branchT,branch,identity,identityT,section,sectionT,email,birth,join,fullNameT,fullName,fullBranchT,fullBranch;
    Typeface sansSBold,productBold,segoe;

    FirebaseAuth firebaseAuth;
    DatabaseReference rollDB;
    FirebaseUser firebaseUser;
    String roll;
    ArrayList<String> details=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        name=(TextView)findViewById(R.id.profileName);
        branchT=(TextView)findViewById(R.id.profileBranchT);
        branch=(TextView)findViewById(R.id.profileBranch);
        identityT=(TextView)findViewById(R.id.profileIdentityT);
        identity=(TextView)findViewById(R.id.profileIdentity);
        sectionT=(TextView)findViewById(R.id.profileSectionT);
        section=(TextView)findViewById(R.id.profileSection);
        email=(TextView)findViewById(R.id.profileEmail);
        birth=(TextView)findViewById(R.id.profileBirth);
        join=(TextView)findViewById(R.id.profileJoin);
        fullNameT=(TextView)findViewById(R.id.profileFullNameT);
        fullName=(TextView)findViewById(R.id.profileFullName);
        fullBranchT=(TextView)findViewById(R.id.profileFullBranchT);
        fullBranch=(TextView)findViewById(R.id.profileFullBranch);

        productBold = Typeface.createFromAsset(getAssets(), "fonts/productsans/ProductSansBold.ttf");
        segoe = Typeface.createFromAsset(getAssets(), "fonts/segoeui.ttf");
        sansSBold= Typeface.createFromAsset(getAssets(),"fonts/opensans/OpenSans-Semibold.ttf");

        name.setTypeface(sansSBold);
        branchT.setTypeface(productBold);
        branch.setTypeface(segoe);
        identityT.setTypeface(productBold);
        identity.setTypeface(segoe);
        sectionT.setTypeface(productBold);
        section.setTypeface(segoe);
        email.setTypeface(sansSBold);
        birth.setTypeface(segoe);
        join.setTypeface(segoe);
        fullNameT.setTypeface(segoe);
        fullName.setTypeface(sansSBold);
        fullBranchT.setTypeface(segoe);
        fullBranch.setTypeface(sansSBold);

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        String oEmail=firebaseUser.getEmail();
        String tEmail=oEmail.replace("@","A");
        tEmail=tEmail.replace(".","D");



        rollDB=FirebaseDatabase.getInstance().getReference().child("Users").child(tEmail);
        rollDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                roll=dataSnapshot.getValue(String.class);
                DatabaseReference detailsDB=FirebaseDatabase.getInstance().getReference().child("Details").child(roll);
                identity.setText(roll);
                detailsDB.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




    }
}
